package com.penguinpay.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.components.ViewModelComponent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.penguinpay.model.LatestRate
import com.penguinpay.repository.AppRepository
import com.penguinpay.util.EXCHANGE_API_APP_ID
import com.penguinpay.util.LAST_FETCH_KEY
import com.penguinpay.util.PrefUtil
import com.penguinpay.util.shouldFetch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


abstract class BaseViewModel constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _latestRatesMutableLiveData = MutableLiveData<LatestRate>()
    val latestRatesLiveData: LiveData<LatestRate>
        get() = _latestRatesMutableLiveData


    //Fetch from local DB
    //If null or timer over 30 min force API call
    //Else notify observes with data from DB
    suspend fun getLatestRatesFromDB() {
        val response = appRepository.getAppDatabaseDao()
            .getLatestRatesFromDBAsync()
        if (response == null || shouldFetch())
            getLatestRatesFromAPI()
        else
            withContext(Dispatchers.Main) {
                Log.d("Main", Gson().toJson(response[0]))
                _latestRatesMutableLiveData.postValue(response[0])
            }
    }

    //Fetch latest exchange rates from an API
    //Insert into DB
    //Notify observers
    //Save time when last fetch
    private suspend fun getLatestRatesFromAPI() {
        val response = appRepository.getLatestRates(EXCHANGE_API_APP_ID)
        appRepository.getAppDatabaseDao().insertLatestRates(response.body()!!)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful && response.body() != null) {
                _latestRatesMutableLiveData.postValue(response.body()!!)

                PrefUtil().saveLongToPref(System.currentTimeMillis(), LAST_FETCH_KEY)

            }
        }
    }
}
package com.penguinpay.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.penguinpay.R
import com.penguinpay.base.BaseActivity
import com.penguinpay.presentation.fragments.HomeFragment
import com.penguinpay.util.changeFragment
import com.penguinpay.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFragment(
            this, HomeFragment::class,
            HomeFragment::class.qualifiedName
        )
    }

    override fun onStart() {
        super.onStart()
       scope.launch(Dispatchers.IO) {
            sharedViewModel.getLatestRatesFromDB()
        }
    }
}
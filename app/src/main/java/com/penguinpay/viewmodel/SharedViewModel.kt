package com.penguinpay.viewmodel

import com.penguinpay.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(appRepository: AppRepository) : BaseViewModel(appRepository) {
}
package com.penguinpay.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.penguinpay.viewmodel.BaseViewModel

abstract class BaseFragment<
    VM: BaseViewModel>(@LayoutRes private val resId: Int,
                       private val viewModelClass: Class<VM>?): Fragment() {

    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelClass?.let {
            viewModel = ViewModelProvider(requireActivity())
                .get(viewModelClass)
        }
        return layoutInflater.inflate(resId, container, false)
    }
}
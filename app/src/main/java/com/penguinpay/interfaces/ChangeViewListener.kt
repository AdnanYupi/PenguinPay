package com.penguinpay.interfaces

import com.penguinpay.base.BaseFragment
import kotlin.reflect.KClass

interface ChangeViewListener {
    fun changeView(fragment: KClass<out BaseFragment<*>>, tag: String?, vararg data: Any?)
}
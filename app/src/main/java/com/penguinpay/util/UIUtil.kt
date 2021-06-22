package com.penguinpay.util

import android.app.Activity
import com.penguinpay.base.BaseFragment
import com.penguinpay.interfaces.ChangeViewListener
import kotlin.reflect.KClass

fun changeFragment(
    activity: Activity,
    fragment: KClass<out BaseFragment<*>>,
    tag: String?,
    vararg data: Any?
) {
    (activity as ChangeViewListener).changeView(fragment, tag, *data)
}
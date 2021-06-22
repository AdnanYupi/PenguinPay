package com.penguinpay.base

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.penguinpay.R
import com.penguinpay.interfaces.ChangeViewListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class BaseActivity(@LayoutRes private val resId: Int) : AppCompatActivity(),
    ChangeViewListener {

    var hostActivity: AppCompatActivity? = null
    private val job: Job = Job()
    val scope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)

        hostActivity = this
    }

    override fun changeView(
        fragment: KClass<out BaseFragment<*>>, tag: String?, vararg data: Any?
    ) {
        hostActivity?.let {
            val f = createNewFragment(fragment, tag) ?: return

            if (data.isNotEmpty()) {
                val args = Bundle()
                for (idx in data.indices) {

                    val dataObj = data[idx]
                    val key = if (idx == 0) "data" else "data$idx"

                    @Suppress("UNCHECKED_CAST") when (dataObj) {
                        is Parcelable -> args.putParcelable(key, dataObj)
                        is ArrayList<*> -> args.putParcelableArrayList(
                            key, dataObj as ArrayList<out Parcelable>
                        )
                        is Array<*> -> args.putStringArray(key, dataObj as Array<String>)
                        is String -> args.putString(key, dataObj)
                        is Int -> args.putInt(key, dataObj)
                        is Float -> args.putFloat(key, dataObj)
                        is Double -> args.putDouble(key, dataObj)
                        is Boolean -> args.putBoolean(key, dataObj)
                        else -> throw RuntimeException("Unrecognized data type: $dataObj")
                    }
                }
                f.arguments = args
            }
            showFragment(f, tag)
        }
    }

    private val getActiveFragment: BaseFragment<*>?
        get() {

            val entryCount = supportFragmentManager.backStackEntryCount
            if (entryCount == 0) return null

            val tag = supportFragmentManager.getBackStackEntryAt(entryCount - 1).name
            if (tag != null)
                return supportFragmentManager.findFragmentByTag(tag) as BaseFragment<*>
            return null
        }

    private fun createNewFragment(
        viewClass: KClass<out BaseFragment<*>>, tag: String?
    ): BaseFragment<*>? {

        val activeFragment = getActiveFragment
        if (activeFragment?.tag == tag) return null
        try {
            return viewClass.createInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun showFragment(fragment: BaseFragment<*>, tag: String?) {

        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            ).replace(R.id.fragmentContainer, fragment, tag)
            .addToBackStack(tag).commit()
    }

    override fun onDestroy() {
        job.cancel()
        scope.cancel()
        super.onDestroy()
    }

}
package com.penguinpay.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.penguinpay.R
import com.penguinpay.base.BaseFragment
import com.penguinpay.enums.CurrenciesEnum
import com.penguinpay.model.Rates
import com.penguinpay.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<SharedViewModel>(
    R.layout.fragment_home,
    SharedViewModel::class.java
) {

    private var currency = CurrenciesEnum.KES
    private var rates: Rates? = null
    private val spinner by lazy { phonesSpinner }
    private var spinnerCodes = mutableListOf<String>("+254", "+234", "+255", "+256")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.latestRatesLiveData.observe(viewLifecycleOwner, Observer {
            if (it?.rates == null)
                return@Observer
            rates = it.rates
        })
        initSpinner()

        convertBtn.setOnClickListener {
            if (!isAdded)
                return@setOnClickListener
            rates?.let {
                try {
                    val latestRate = getExchangeRateBasedOnCountry(currency)
                    val amount = sendingAmountEditText.text.toString()
                    val fromBinary = fromBinary(amount)
                    val exchange = (fromBinary * latestRate).toInt()
                    Log.d("Main", "${toBinary(exchange)}")
                    receivedAmount.text = toBinary(exchange)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Unable to convert. Check your input",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    private fun initSpinner() {
        spinner.adapter = initSpinnerAdapter()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (spinnerCodes[position]) {
                    "+254" -> currency = CurrenciesEnum.KES
                    "+234" -> currency = CurrenciesEnum.NGN
                    "+255" -> currency = CurrenciesEnum.TZS
                    "+256" -> currency = CurrenciesEnum.UGX
                }

                phoneEditText.hint = selectedCountry(currency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun initSpinnerAdapter(): ArrayAdapter<String> {
        return ArrayAdapter(
            requireContext(),
            R.layout.spinner_text_dropdown,
            R.id.text,
            spinnerCodes
        )
    }

    private fun selectedCountry(enum: CurrenciesEnum): String {
        return when (enum) {
            CurrenciesEnum.UGX -> "Uganda"
            CurrenciesEnum.TZS -> "Tanzania"
            CurrenciesEnum.NGN -> "Nigeria"
            CurrenciesEnum.KES -> "Kenya"
            CurrenciesEnum.USD -> "USA"
        }
    }

    private fun getExchangeRateBasedOnCountry(enum: CurrenciesEnum): Double {
        return when (enum) {
            CurrenciesEnum.UGX -> rates!!.ugx
            CurrenciesEnum.TZS -> rates!!.tzs
            CurrenciesEnum.NGN -> rates!!.ngn
            CurrenciesEnum.KES -> rates!!.kes
            CurrenciesEnum.USD -> rates!!.usd
        }
    }

    private fun fromBinary(amount: String): Int {
        if (amount.isDigitsOnly()) {
            return Integer.parseInt(amount, 2)
        }
        return 0
    }

    private fun toBinary(amount: Int): String {
        return Integer.toBinaryString(amount)
    }
}
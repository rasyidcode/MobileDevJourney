package com.rasyidcode.cupcakeapp2.model

import android.util.Log
import androidx.lifecycle.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _flavors = MutableLiveData<MutableList<String>>()

    //    Bug: returns null
    val flavors: LiveData<String> = _flavors.map {
        it.toList().joinToString()
    }
//    val flavors: LiveData<List<String>> = _flavors.map {
//        it.toList()
//    }
//    val flavors: LiveData<MutableList<String>> = _flavors

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = _price.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun setQuantity(numberOfCupcakes: Int) {
        _quantity.value = numberOfCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setFlavors(desiredFlavor: String) {
        _flavors.value?.let {
            if (!it.contains(desiredFlavor)) {
                _flavors.value?.add(desiredFlavor)
            } else {
                _flavors.value?.remove(desiredFlavor)
            }
        }
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun setUserName(customerName: String) {
        _userName.value = customerName
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    fun isMultipleFlavors(): Boolean {
        return _flavors.value?.isNotEmpty() ?: false
    }

    fun isQuantityMoreThanOne(): Boolean {
        val qty = _quantity.value ?: 0
        return qty > 1
    }

    fun isFlavorsContain(desiredFlavor: String): Boolean {
        return _flavors.value?.contains(desiredFlavor) ?: false
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _flavors.value = mutableListOf()
        _date.value = dateOptions[0]
        _price.value = 0.0
        _userName.value = ""
    }

    fun updateDateToTomorrow() {
        _date.value = dateOptions[1]
    }

    fun updateDateToToday() {
        _date.value = dateOptions[0]
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    private fun updatePrice() {
        var calculatedPrice = (_quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }

        _price.value = calculatedPrice
    }
}
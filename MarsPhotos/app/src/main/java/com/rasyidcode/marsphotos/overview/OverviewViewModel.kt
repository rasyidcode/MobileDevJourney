package com.rasyidcode.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidcode.marsphotos.network.MarsApi
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewViewModel]
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    var status: LiveData<String> = _status

    /**
     * call getMarsPhotos() on init so we can display status immediately
     */
    init {
        getMarsPhotos()
    }

    /**
     * Get mars photos information from the Mars API Retrofit service and update
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                _status.value = listResult
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}
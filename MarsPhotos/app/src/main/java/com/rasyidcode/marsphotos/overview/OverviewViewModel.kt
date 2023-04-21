package com.rasyidcode.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidcode.marsphotos.network.MarsApi
import com.rasyidcode.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

enum class MarsApiStatus {
    LOADING, ERROR, DONE
}

/**
 * The [ViewModel] that is attached to the [OverviewViewModel]
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    var status: LiveData<MarsApiStatus> = _status

    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos

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
            _status.value = MarsApiStatus.LOADING
            try {
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }

}
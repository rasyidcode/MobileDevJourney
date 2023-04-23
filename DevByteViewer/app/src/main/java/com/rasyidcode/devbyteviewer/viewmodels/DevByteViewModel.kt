package com.rasyidcode.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rasyidcode.devbyteviewer.domain.DevByteVideo
import com.rasyidcode.devbyteviewer.network.DevByteNetwork
import com.rasyidcode.devbyteviewer.network.asDomainModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param application The application that viewModel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during activity
 * or fragment lifecycle events.
 */
class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The data source this viewModel will fetch result from.
     */
    // TODO: Add a reference to the VideosRepository class

    /**
     * A playlist of videos displayed on the screen.
     */
    // TODO: Replace the MutableLiveData and backing property below a reference to the 'videos'
    //  from the VideosRepository

    /**
     * A playlist of videos that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _playlist = MutableLiveData<List<DevByteVideo>>()

    /**
     * A playlist of videos that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    val playlist: LiveData<List<DevByteVideo>>
        get() = _playlist

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display error message. Views should use this to get access
     * to the data
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        // TODO: Replace with a call to the refreshDataFromRepository() method
        refreshDataFromNetwork()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    // TODO: Replace with refreshDataFromRepository() method
    private fun refreshDataFromNetwork() = viewModelScope.launch {
        try {
            val playlist = DevByteNetwork.devBytes.getPlaylist()
            _playlist.postValue(playlist.asDomainModel())

            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            _eventNetworkError.value = true
        }
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }

            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

}
package com.rasyidcode.movieapp.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidcode.movieapp.data.domain.Movie
import com.rasyidcode.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val popularMoviesList: LiveData<List<Movie>>? = movieRepository.getPopularMovies()?.asLiveData()

    val nowPlayingList: LiveData<List<Movie>>? = movieRepository.getNowPlaying()?.asLiveData()

    val topRatedList: LiveData<List<Movie>>? = movieRepository.getTopRated()?.asLiveData()

    val upcomingList: LiveData<List<Movie>>? = movieRepository.getUpcoming()?.asLiveData()

    val movieLatest: LiveData<Movie>? = movieRepository.movieLatest?.asLiveData()

    private val _movieLatestIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val movieLatestIsLoading: LiveData<Boolean> = _movieLatestIsLoading

    private val _moviePopularIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val moviePopularIsLoading: LiveData<Boolean> = _moviePopularIsLoading

    private val _nowPlayingIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val nowPlayingIsLoading: LiveData<Boolean> = _nowPlayingIsLoading

    private val _topRatedIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val topRatedIsLoading: LiveData<Boolean> = _topRatedIsLoading

    private val _upcomingIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val upcomingIsLoading: LiveData<Boolean> = _upcomingIsLoading

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies(page: Int = 1) {
        viewModelScope.launch {
            _moviePopularIsLoading.value = true
            try {
                movieRepository.fetchPopularMovies(page)
                _moviePopularIsLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())
                _moviePopularIsLoading.value = false
            }
        }
    }

    fun fetchNowPlaying(page: Int = 1) {
        viewModelScope.launch {
            _nowPlayingIsLoading.value = true
            try {
                movieRepository.fetchNowPlaying(page)
                _nowPlayingIsLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())
                _nowPlayingIsLoading.value = false
            }
        }
    }

    fun fetchTopRated(page: Int = 1) {
        viewModelScope.launch {
            _topRatedIsLoading.value = true
            try {
                movieRepository.fetchTopRated(page)
                _topRatedIsLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())
                _topRatedIsLoading.value = false
            }
        }
    }

    fun fetchUpcoming(page: Int = 1) {
        viewModelScope.launch {
            _upcomingIsLoading.value = true
            try {
                movieRepository.fetchUpcoming(page)
                _upcomingIsLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())
                _upcomingIsLoading.value = false
            }
        }
    }

    fun fetchLatestMovie() {
        viewModelScope.launch {
            _movieLatestIsLoading.value = true
            try {
                movieRepository.fetchLatestMovie()
                _movieLatestIsLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())
                _movieLatestIsLoading.value = false
            }
        }
    }

    class Factory(
        private val movieRepository: MovieRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                MovieViewModel(movieRepository) as T
            } else {
                throw IllegalArgumentException("Unable to construct viewModel")
            }
        }
    }

    companion object {
        const val TAG = "MovieViewModel"
    }

}
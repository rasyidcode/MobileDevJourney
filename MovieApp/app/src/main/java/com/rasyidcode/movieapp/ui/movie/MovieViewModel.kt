package com.rasyidcode.movieapp.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
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

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies(page: Int = 1) = viewModelScope.launch {
        try {
            movieRepository.fetchPopularMovies(page)
        } catch (exception: IOException) {
            Log.e(TAG, exception.message.toString())
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
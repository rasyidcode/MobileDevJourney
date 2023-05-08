package com.rasyidcode.movieapp.ui.movie_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.data.domain.Genre
import com.rasyidcode.movieapp.data.domain.Movie
import com.rasyidcode.movieapp.data.domain.Review
import com.rasyidcode.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MovieDetailViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val genres: LiveData<List<Genre?>?>? = movieRepository.genres?.asLiveData()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMovieDetail(id: Int, movieId: Int, movieListType: String): LiveData<Movie?>? =
        movieRepository.getMovieDetail(id = id, movieId = movieId, movieListType = movieListType)
            ?.asLiveData()

    private val _reviews: MutableLiveData<List<Review>>? = MutableLiveData(listOf())
    val reviews: LiveData<List<Review>>? = _reviews

    fun getTheFirstThreeReviews(movieId: Int): LiveData<List<Review>>? =
        movieRepository.getReviews(movieId = movieId)?.asLiveData()?.map {
            it.take(3)
        }

    fun getTheFirstThreeSimilar(movieId: Int): LiveData<List<Movie>>? =
        movieRepository.getSimilarMovies()?.asLiveData()?.map {
            it.take(3)
        }

    fun fetchMovieDetail(id: Int, movieId: Int, movieListType: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                movieRepository.fetchMovieDetail(
                    id = id,
                    movieId = movieId,
                    movieListType = movieListType
                )

                _isLoading.value = false
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())

                _isLoading.value = false
            }
        }
    }

    fun fetchReviews(movieId: Int) {
        viewModelScope.launch {
            try {
                movieRepository.fetchReviews(
                    page = 1,
                    movieId = movieId
                )
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
            }
        }

        getTheFirstThreeReviews(movieId)
    }

    fun fetchSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            try {
                movieRepository.fetchSimilarMovies(
                    movieId = movieId,
                    page = 1
                )
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    class Factory(
        private val movieRepository: MovieRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                MovieDetailViewModel(movieRepository) as T
            } else {
                throw IllegalArgumentException("Unable to construct viewModel")
            }
        }
    }

    companion object {
        const val TAG = "MovieDetailViewModel"
    }
}
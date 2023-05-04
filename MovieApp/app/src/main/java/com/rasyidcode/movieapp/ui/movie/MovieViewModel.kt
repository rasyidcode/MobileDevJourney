package com.rasyidcode.movieapp.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.rasyidcode.movieapp.data.database.movie.MovieListType
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

    private val _isMovieListLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isMovieListLoading: LiveData<Boolean> = _isMovieListLoading

    private val _isNetworkError: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNetworkError: LiveData<Boolean> = _isNetworkError

    private var _popularMoviePage: Int = 0
    private var _nowPlayingPage: Int = 0
    private var _topRatedPage: Int = 0
    private var _upcomingPage: Int = 0
    private var _isListReset: Boolean = false

    private val _selectedGenreIds: MutableLiveData<MutableList<Int>> =
        MutableLiveData(mutableListOf())
    val selectedGenreIds: LiveData<List<Int>?> = _selectedGenreIds.map { it?.toList() }

    init {
        clearData()

        fetchNetworkGenres()
        fetchPopularMovies()
        fetchNowPlaying()
        fetchTopRated()
        fetchUpcoming()
    }

    fun fetchPopularMovies() {
        Log.d(
            TAG,
            "withGenres: ${
                _selectedGenreIds.value?.map { it.toString() }?.toList().toString()
            }"
        )

        viewModelScope.launch {
            _isMovieListLoading.value = true

            if (!_selectedGenreIds.value.isNullOrEmpty()) {
                if (!_isListReset) {
                    movieRepository.clearList(listType = MovieListType.POPULAR)

                    _popularMoviePage = 0
                    _isListReset = true
                }
            }

            _popularMoviePage++

            try {
                movieRepository.fetchPopularMovies(
                    page = _popularMoviePage,
                    withGenres = _selectedGenreIds.value?.map { it.toString() }?.toList()
                )

                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
            }
        }
    }

    fun fetchNowPlaying() {
        _nowPlayingPage++
        viewModelScope.launch {
            _isMovieListLoading.value = true
            try {
                movieRepository.fetchNowPlaying(_nowPlayingPage)
                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
            }
        }
    }

    fun fetchTopRated() {
        _topRatedPage++
        viewModelScope.launch {
            _isMovieListLoading.value = true
            try {
                movieRepository.fetchTopRated(_topRatedPage)
                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
            }
        }
    }

    fun fetchUpcoming() {
        _upcomingPage++
        viewModelScope.launch {
            _isMovieListLoading.value = true
            try {
                movieRepository.fetchUpcoming(_upcomingPage)
                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
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

    fun refreshList(listType: MovieListType?) {
        listType?.let {
            viewModelScope.launch {
                movieRepository.clearList(it)

                when (it) {
                    MovieListType.POPULAR -> {
                        _popularMoviePage = 0
                        fetchPopularMovies()
                    }

                    MovieListType.NOW_PLAYING -> {
                        _nowPlayingPage = 0
                        fetchNowPlaying()
                    }

                    MovieListType.TOP_RATED -> {
                        _topRatedPage = 0
                        fetchTopRated()
                    }

                    MovieListType.UPCOMING -> {
                        _upcomingPage = 0
                        fetchUpcoming()
                    }

                    else -> {}
                }
            }
        }
    }

    fun setGenre(id: Int?) {
        Log.d(TAG, "setGenre: $id")

        id?.let {
            if (_selectedGenreIds.value?.contains(it) != true) {
                _selectedGenreIds.value?.add(it)
            } else {
                _selectedGenreIds.value?.remove(it)
            }

            Log.d(TAG, "setGenre inside let: $it")
        }

        Log.d(TAG, "currentSelectedGenreList: ${_selectedGenreIds.value}")
    }

    fun clearFilters() {
        _selectedGenreIds.value = mutableListOf()
        _isListReset = false
    }

    private fun fetchNetworkGenres() {
        viewModelScope.launch {
            try {
                movieRepository.fetchNetworkGenres()
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())
            }
        }
    }

    private fun clearData() {
        viewModelScope.launch {
            movieRepository.clearData()
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
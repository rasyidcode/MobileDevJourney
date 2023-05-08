package com.rasyidcode.movieapp.ui.movie_list

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
import com.rasyidcode.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val genres: LiveData<List<Genre?>?>? = movieRepository.genres?.asLiveData()

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
    private var _popularMovieFilterPage: Int = 0
    private var _lastGenreFilterList: List<Int> = emptyList()

    private var _nowPlayingPage: Int = 0
    private var _topRatedPage: Int = 0
    private var _upcomingPage: Int = 0

    private val _selectedGenreIds: MutableLiveData<MutableList<Int>> =
        MutableLiveData(mutableListOf())
    val selectedGenreIds: LiveData<List<Int>?> = _selectedGenreIds.map { it?.toList() }

    val selectedGenreIdsSize: LiveData<Int> = _selectedGenreIds.map { it.size }

    private val _lastSelectedGenreId: MutableLiveData<Int> = MutableLiveData(0)
    val lastSelectedGenreId: LiveData<Int> = _lastSelectedGenreId

    init {
        clearMoviesData()

        fetchNetworkGenres()

        fetchPopularMovies()
        fetchNowPlaying()
        fetchTopRated()
        fetchUpcoming()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            _isMovieListLoading.value = true

            _popularMoviePage++

            try {
                movieRepository.fetchPopularMovies(
                    page = _popularMoviePage
                )

                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
            }
        }
    }

    fun fetchPopularMovieWithFilter(keepFilter: Boolean = false) {
        _isMovieListLoading.value = true

        viewModelScope.launch {
            if (!keepFilter) {
                movieRepository.clearList(listType = MovieListType.POPULAR)

                _popularMovieFilterPage = 0
            }

            _popularMovieFilterPage++

            try {
                movieRepository.fetchPopularMovies(
                    page = _popularMovieFilterPage,
                    withGenres = _selectedGenreIds.value?.map { it.toString() }?.toList()
                )

                _selectedGenreIds.value?.let {
                    _lastGenreFilterList = it
                }

                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isNetworkError.value = true
                _isMovieListLoading.value = false
            }
        }
    }

    fun fetchNowPlaying() {
        viewModelScope.launch {
            _isMovieListLoading.value = true

            _nowPlayingPage++

            try {
                movieRepository.fetchNowPlaying(
                    page = _nowPlayingPage
                )

                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
            }
        }
    }

    fun fetchTopRated() {
        viewModelScope.launch {
            _isMovieListLoading.value = true

            _topRatedPage++

            try {
                movieRepository.fetchTopRated(
                    page = _topRatedPage
                )

                _isMovieListLoading.value = false
            } catch (exception: IOException) {
                Log.e(TAG, exception.message.toString())

                _isMovieListLoading.value = false
                _isNetworkError.value = true
            }
        }
    }

    fun fetchUpcoming() {
        viewModelScope.launch {
            _isMovieListLoading.value = true

            _upcomingPage++

            try {
                movieRepository.fetchUpcoming(
                    page = _upcomingPage
                )
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
        _selectedGenreIds.value = mutableListOf()
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
        id?.let {
            if (_selectedGenreIds.value?.contains(it) != true) {
                val oldList = _selectedGenreIds.value
                oldList?.add(it)
                oldList?.let { list ->
                    _selectedGenreIds.value = list
                }
            } else {
                val oldList = _selectedGenreIds.value
                oldList?.remove(it)
                oldList?.let { list ->
                    _selectedGenreIds.value = list
                }
            }
        }

        Log.d(TAG, "selectedGenres: ${_selectedGenreIds.value}")

        _lastSelectedGenreId.value = if (_selectedGenreIds.value.isNullOrEmpty()) {
            0
        } else {
            _selectedGenreIds.value?.last()
        }
    }

    fun clearFilters() {
        _selectedGenreIds.value = mutableListOf()
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

    private fun clearMoviesData() {
        viewModelScope.launch {
            movieRepository.clearMovieData()
        }
    }

    class Factory(
        private val movieRepository: MovieRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                MovieListViewModel(movieRepository) as T
            } else {
                throw IllegalArgumentException("Unable to construct viewModel")
            }
        }
    }

    companion object {
        const val TAG = "MovieListViewModel"
    }

}
package com.rasyidcode.movieku.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidcode.movieku.api.RequestState
import com.rasyidcode.movieku.models.GenreResponse
import com.rasyidcode.movieku.models.MovieResponse
import com.rasyidcode.movieku.repositories.MovieRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val repository: MovieRepository = MovieRepository()

    private var page: Int = 1

    private var pageSearch: Int = 1

    private var movieResponse: MovieResponse? = null

    private var searchMovieResponse: MovieResponse? = null

    private val _movieList = MutableLiveData<RequestState<MovieResponse?>>()
    val movieList: LiveData<RequestState<MovieResponse?>> = _movieList

    private val _genreList = MutableLiveData<RequestState<GenreResponse?>>()
    val genreList: LiveData<RequestState<GenreResponse?>> = _genreList

    private val _searchMovieList = MutableLiveData<RequestState<MovieResponse?>>()
    val searchMovieList: LiveData<RequestState<MovieResponse?>> = _searchMovieList

    init {
        getGenres()
        getPopularMovie()
    }

    private fun getPopularMovie() {
        viewModelScope.launch {
            _movieList.postValue(RequestState.Loading)
            val response = repository.getPopularMovie(page)
            _movieList.postValue(handleGetPopularMovieResponse(response))
        }
    }

    fun getNextPopularMovie() {
        getPopularMovie()
    }

    private fun handleGetPopularMovieResponse(response: Response<MovieResponse>): RequestState<MovieResponse?> {
        if (response.isSuccessful) {
            response.body()?.let {
                page++
                if (movieResponse == null) movieResponse = it else {
                    movieResponse?.results?.let { old ->
                        it.results?.let { new ->
                            old.addAll(new)
                        }
                    }
                }
            }

            RequestState.Success(movieResponse ?: response.body())
        }

        return RequestState.Error(
            try {
                response.errorBody()?.string()?.let {
                    JSONObject(it).get("status_message")
                }
            } catch (e: JSONException) {
                e.localizedMessage
            } as String
        )
    }

    private fun getGenres() {
        viewModelScope.launch {
            try {
                val response = repository.getGenres()
                _genreList.postValue(RequestState.Success(response))
            } catch (e: HttpException) {
                _genreList.postValue(RequestState.Error(e.localizedMessage ?: "Something went wrong"))
            }
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            _searchMovieList.postValue(RequestState.Loading)
            val response = repository.searchMovie(query, pageSearch)
            _searchMovieList.postValue(handleSearchMovieResponse(response))
        }
    }

    private fun handleSearchMovieResponse(response: Response<MovieResponse>): RequestState<MovieResponse?> {
        if (response.isSuccessful) {
            response.body()?.let {
                pageSearch++
                if (searchMovieResponse == null) searchMovieResponse = it else {
                    searchMovieResponse?.results?.let { old ->
                        it.results?.let { new ->
                            old.addAll(new)
                        }
                    }
                }
            }

            RequestState.Success(searchMovieResponse ?: response.body())
        }

        return RequestState.Error(
            try {
                response.errorBody()?.string()?.let {
                    JSONObject(it).get("status_message")
                }
            } catch (e: JSONException) {
                e.localizedMessage
            } as String
        )
    }
}
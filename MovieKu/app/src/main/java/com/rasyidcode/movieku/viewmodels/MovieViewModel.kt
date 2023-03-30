package com.rasyidcode.movieku.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rasyidcode.movieku.api.RequestState
import com.rasyidcode.movieku.models.GenreResponse
import com.rasyidcode.movieku.models.MovieResponse
import com.rasyidcode.movieku.repositories.MovieRepository
import retrofit2.HttpException

class MovieViewModel: ViewModel() {

    private val repository: MovieRepository = MovieRepository()

    private var page: Int = 1

    fun getPopularMovie(): LiveData<RequestState<MovieResponse>> = liveData {
        emit(RequestState.Loading)

        try {
            val response: MovieResponse = repository.getPopularMovie(page)
            emit(RequestState.Success(response))
        } catch (e: HttpException) {
            e.response()?.errorBody()?.string()?.let {
                RequestState.Error(it)
            }?.let {
                emit(it)
            }
        }
    }

    fun getGenres(): LiveData<RequestState<GenreResponse>> = liveData {
        try {
            val response: GenreResponse = repository.getGenres()
            emit(RequestState.Success(response))
        } catch (e: HttpException) {
            emit(RequestState.Error("Failed loading genres"))
        }
    }

}
package com.rasyidcode.moviefinder.search_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidcode.moviefinder.data.ResultData
import com.rasyidcode.moviefinder.data.source.network.SearchMovieResponse
import com.rasyidcode.moviefinder.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _searchResult = MutableLiveData<ResultData<SearchMovieResponse>>()
    val searchResult: LiveData<ResultData<SearchMovieResponse>>
        get() = _searchResult

    fun searchMovie(keyword: String) {
        viewModelScope.launch {
            searchMovieUseCase.invoke(keyword).collect {
                _searchResult.postValue(it)
            }
        }
    }

}
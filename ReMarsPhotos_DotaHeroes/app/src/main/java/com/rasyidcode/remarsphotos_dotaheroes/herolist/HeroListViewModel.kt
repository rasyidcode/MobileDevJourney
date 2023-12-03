package com.rasyidcode.remarsphotos_dotaheroes.herolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidcode.remarsphotos_dotaheroes.network.DotaHero
import com.rasyidcode.remarsphotos_dotaheroes.network.OpenDotaApi
import kotlinx.coroutines.launch

enum class OpenDotaApiStatus {
    LOADING, ERROR, DONE
}

class HeroListViewModel : ViewModel() {

    private val _status = MutableLiveData<OpenDotaApiStatus>()
    val status: LiveData<OpenDotaApiStatus> = _status

    private val _heroes = MutableLiveData<List<DotaHero>>()
    val heroes: LiveData<List<DotaHero>> = _heroes

    init {
        fetchHeroes()
    }

    private fun fetchHeroes() {
        viewModelScope.launch {
            _status.value = OpenDotaApiStatus.LOADING
            try {
                _status.value = OpenDotaApiStatus.DONE
                _heroes.value = OpenDotaApi.apiService.getHeroes()
            } catch (e: Exception) {
                _status.value = OpenDotaApiStatus.ERROR
                _heroes.value = listOf()
            }
        }
    }

}
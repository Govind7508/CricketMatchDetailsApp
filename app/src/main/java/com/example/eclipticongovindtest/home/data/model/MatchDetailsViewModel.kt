package com.example.eclipticongovindtest.home.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eclipticongovindtest.home.data.network.Resource
import com.example.eclipticongovindtest.home.data.repository.MatchDetailsRepository
import com.example.eclipticongovindtest.home.data.response.MatchDataCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(private val repository: MatchDetailsRepository) :ViewModel() {
    private val _matchDetailsResponse :MutableLiveData<Resource<MatchDataCallBack>> = MutableLiveData()
val matchResponse :LiveData<Resource<MatchDataCallBack>>
    get() = _matchDetailsResponse
    private val _updateBalance = MutableLiveData<String>()
    val updateBalance: LiveData<String> = _updateBalance
    fun getMatchDetailsView(pageNumber :Int)= viewModelScope.launch {
        repository.safeApiCall {  }
        _matchDetailsResponse.value= repository.getMatch(pageNumber)
    }


    fun updateTopBalance(title: String) {
        _updateBalance.value = title
    }
}
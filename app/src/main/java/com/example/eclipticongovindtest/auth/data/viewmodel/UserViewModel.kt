package com.example.eclipticongovindtest.auth.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.eclipticongovindtest.auth.data.repository.UserRepository
import com.example.eclipticongovindtest.auth.model.UserDetailsCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun insertUser(user: UserDetailsCallBack) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun getUser() = liveData {
        val user = userRepository.getUser()
        emit(user)
    }
}
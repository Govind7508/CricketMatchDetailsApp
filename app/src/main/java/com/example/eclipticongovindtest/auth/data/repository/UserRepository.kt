package com.example.eclipticongovindtest.auth.data.repository

import com.example.eclipticongovindtest.auth.data.UserDAO
import com.example.eclipticongovindtest.auth.model.UserDetailsCallBack
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDAO) {

    suspend fun insertUser(user: UserDetailsCallBack) {
        userDao.insertUserDetails(user)
    }

    suspend fun getUser(): UserDetailsCallBack? {
        return userDao.getUser()
    }
}
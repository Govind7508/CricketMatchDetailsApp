package com.example.eclipticongovindtest.home.data.repository

import retrofit2.HttpException
import com.example.eclipticongovindtest.home.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {
    suspend fun<T> safeApiCall(apiCall :suspend () ->T) : Resource<T> {
        return withContext(Dispatchers.IO){
            try {
               Resource.Success(apiCall.invoke())
            }catch (throwExp :Throwable){
                when(throwExp){
                    is HttpException ->{
                        Resource.Failure(false,throwExp.code(),throwExp.response()?.errorBody())
                    }else ->{
                        Resource.Failure(true, null,null)
                    }

                }
            }
        }
    }
}
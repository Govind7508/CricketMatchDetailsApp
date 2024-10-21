package com.example.eclipticongovindtest.home.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource  @Inject constructor(){
    companion object {
        private const val BASE_URL = "http://dev.api.ufmsports.com/"
    }

    fun <Api> buildApi(api: Class<Api>): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

}
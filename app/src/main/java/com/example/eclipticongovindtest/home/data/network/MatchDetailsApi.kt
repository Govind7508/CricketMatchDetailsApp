package com.example.eclipticongovindtest.home.data.network

import com.example.eclipticongovindtest.home.data.response.MatchDataCallBack
import com.example.eclipticongovindtest.home.data.response.Tournament
import com.example.eclipticongovindtest.home.data.response.UpcomingMatches
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchDetailsApi {

    @GET("data-pipeline/v1/mock-frontend/homepage/{pageNumber}")
   suspend fun MatchApi(
@Path("pageNumber")pageNumber : Int
    ) : MatchDataCallBack


   @GET("data-pipeline/v1/mock-frontend/matches/upcoming/{pageNumber}")
   suspend fun UpcomingApi(
@Path("pageNumber")pageNumber : Int
    ) : UpcomingMatches

   @GET("/data-pipeline/v1/mock-frontend/matches/featured/{pageNumber}")
   suspend fun FeatureApi(
@Path("pageNumber")pageNumber : Int
    ) : Tournament
}
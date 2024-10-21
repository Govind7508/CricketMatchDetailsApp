package com.example.eclipticongovindtest.home.data.repository

import android.util.Log
import com.example.eclipticongovindtest.home.data.network.MatchDetailsApi
import com.example.eclipticongovindtest.home.data.network.Resource
import com.example.eclipticongovindtest.home.data.response.FeaturedTournament
import com.example.eclipticongovindtest.home.data.response.MatchDataCallBack
import javax.inject.Inject

class MatchDetailsRepository @Inject constructor(private val api: MatchDetailsApi) :
    BaseRepository() {

    suspend fun getMatch(pageNumber: Int): Resource<MatchDataCallBack> {
        return safeApiCall {
            try {
                val matchResponse = api.MatchApi(pageNumber)
                // Log response for debugging
                Log.d("API Response", "Match Response: $matchResponse")

                val upcomingMatchesResponse = api.UpcomingApi(pageNumber)
                Log.d("API Response", "Upcoming Matches Response: $upcomingMatchesResponse")

                val featuredMatchesResponse = api.FeatureApi(pageNumber)
                Log.d("API Response", "Featured Matches Response: $featuredMatchesResponse")

                // Combine upcoming matches
                val updatedUpcomingMatches = matchResponse.upcoming_matches?.copy(
                    match_list = matchResponse.upcoming_matches.match_list?.plus((upcomingMatchesResponse.match_list ?: emptyList())),
                    next_page = upcomingMatchesResponse.next_page,
                    prev_page = upcomingMatchesResponse.prev_page
                )


                val existingTournaments = matchResponse.featured_tournament?.tournament_list

                val combinedTournaments = existingTournaments?.plus(listOf(featuredMatchesResponse))

                val featuredTournament = FeaturedTournament(
                    tournament_list = combinedTournaments
                )

                MatchDataCallBack(
                    current_offers = matchResponse.current_offers,
                    featured_tournament = featuredTournament,
                    upcoming_matches = updatedUpcomingMatches,
                    user_matches = matchResponse.user_matches,
                    wallet_summary = matchResponse.wallet_summary
                )
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching match data: ${e.message}")
                throw e // or handle the error accordingly
            }

        }
    }
}



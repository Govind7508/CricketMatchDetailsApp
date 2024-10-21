package com.example.eclipticongovindtest.home.data.response

data class MatchDataCallBack(
    val current_offers: CurrentOffers? = null,
    val featured_tournament: FeaturedTournament? = null,
    val upcoming_matches: UpcomingMatches? = null,
    val user_matches: UserMatches? = null,
    val wallet_summary: WalletSummary? = null
)
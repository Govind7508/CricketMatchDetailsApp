package com.example.eclipticongovindtest.home.data.response

data class Match(
    val id: String? = null,
    val match_format: String? = null,
    val match_offers: List<MatchOffer>? = null,
    val metadata: Metadata? = null,
    val name: String? = null,
    val starts_at: Long? = null,
    val status: String? = null,
    val teams: Teams? = null,
    val tournament_name: String? = null

)
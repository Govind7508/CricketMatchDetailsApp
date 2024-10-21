package com.example.eclipticongovindtest.home.data.response

data class UpcomingMatches(
    val match_list: List<Match>? = null,
    val next_page: Int? = null,
    val prev_page: Any? = null
)
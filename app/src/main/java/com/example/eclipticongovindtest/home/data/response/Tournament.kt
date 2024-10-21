package com.example.eclipticongovindtest.home.data.response

data class Tournament(
    val match_list: List<Match>? = null,
    val next_page: Int? = null,
    val prev_page: Any? = null,
    val tournament_id: String? = null,
    val tournament_name: String? = null
)
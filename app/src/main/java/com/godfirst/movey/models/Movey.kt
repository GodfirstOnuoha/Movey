package com.godfirst.movey.models

data class Movey(
    val poster_path: String,
    val title: String,
    val overview: String,
    val release_date: String
)

data class MoveyList(
    val results: List<Movey>
)

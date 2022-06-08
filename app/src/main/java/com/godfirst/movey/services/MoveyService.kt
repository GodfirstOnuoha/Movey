package com.godfirst.movey.services

import com.godfirst.movey.models.MoveyList
import retrofit2.Call
import retrofit2.http.GET

interface MoveyService {
    @GET("trending/movie/day?api_key=7a47e869cd6e8a4eae2b386a0be1ed69")
    fun getTrendingMovies(): Call<MoveyList>

    @GET("movie/top_rated?api_key=7a47e869cd6e8a4eae2b386a0be1ed69")
    fun getTopRatedMovies(): Call<MoveyList>
}
package com.bangkit.whatdish.data.source.remote

import com.bangkit.whatdish.data.source.remote.response.FoodResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("query")
    fun getFood(
        @Query("image") id: String
    ): Call<FoodResponse>
}
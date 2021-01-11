package com.example.everywhere.api

import com.example.everywhere.api.RetrofitAPI.API_KEY
import com.example.everywhere.models.Items
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LocalSearchService {
    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("/v2/local/search/keyword.json")
    fun requestSearchLocal(
        @Query("query") keyword: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 15
    ): Call<Items>
}
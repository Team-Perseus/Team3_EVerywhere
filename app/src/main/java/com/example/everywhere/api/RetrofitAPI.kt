package com.example.everywhere.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    const val API_KEY = "b13f92f1a4b314a41fb67719205f5bf6"
    const val BASE_URL = "https://dapi.kakao.com"

    fun getService(): LocalSearchService = retrofit.create(LocalSearchService::class.java)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
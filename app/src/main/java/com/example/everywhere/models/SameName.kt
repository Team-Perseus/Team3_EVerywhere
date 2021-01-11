package com.example.everywhere.models


import com.google.gson.annotations.SerializedName

data class SameName(
    val keyword: String,
    val region: List<Any>,
    @SerializedName("selected_region") val selectedRegion: String
)
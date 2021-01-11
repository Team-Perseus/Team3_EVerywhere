package com.example.everywhere.models


import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("address_name") val addressName: String,
    @SerializedName("category_group_code") val categoryGroupCode: String,
    @SerializedName("category_group_name") val categoryGroupName: String,
    @SerializedName("category_name") val categoryName: String,
    val distance: String,
    val id: String,
    val phone: String,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("place_url") val placeUrl: String,
    @SerializedName("road_address_name") val roadAddressName: String,
    val x: String,
    val y: String
)
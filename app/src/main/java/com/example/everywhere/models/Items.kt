package com.example.everywhere.models


import com.google.gson.annotations.SerializedName

data class Items(
    val documents: List<Document>,
    val meta: Meta
) {
    fun isEmpty() = documents.isEmpty()
    fun isEnd() = meta.isEnd
}
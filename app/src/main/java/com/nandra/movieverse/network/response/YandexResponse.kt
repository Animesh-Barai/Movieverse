package com.nandra.movieverse.network.response


data class YandexResponse(
    val code: Int,
    val lang: String,
    val text: List<String>
)
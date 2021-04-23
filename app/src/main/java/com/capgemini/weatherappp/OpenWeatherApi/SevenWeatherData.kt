package com.capgemini.weatherappp.OpenWeatherApi


import com.google.gson.annotations.SerializedName

data class SevenWeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<week>,
    val message: Int
)
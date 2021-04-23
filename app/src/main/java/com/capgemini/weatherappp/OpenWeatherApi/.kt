package com.capgemini.weatherappp.OpenWeatherApi


import com.google.gson.annotations.SerializedName

data class week (
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
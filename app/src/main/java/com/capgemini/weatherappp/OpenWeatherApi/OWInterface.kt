package com.capgemini.weatherappp.OpenWeatherApi

import android.util.Log
import com.capgemini.weatherappp.OpenWeatherAPI7Days.SSevenDays
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface OWInterface {

    @GET("data/2.5/forecast")
    fun getWeather(@Query("lat") lat: String,@Query("lon") lon: String,
                  @Query("appid") key : String,@Query("cnt") cnt: String): Call<SevenWeatherData>
//https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&exclude=minutely,hourly,alerts
// &appid=015a0e163e2cd6e99253a34375bf29b7
    @GET("data/2.5/onecall")
    fun getDailyWeather(@Query("lat") lat: String, @Query("lon") lon: String,
                        @Query("exclude") exclude: String = "minutely,hourly,alerts",
                        @Query("appid") key : String): Call<SSevenDays>
    @GET
    fun getImage(@Url url : String) : Call<ResponseBody>
//https://api.openweathermap.org/data/2.5
    //https://api.openweathermap.org/data/2.5/forecast?lat=12.9716&lon=77.5946&appid=015a0e163e2cd6e99253a34375bf29b7&cnt=7
    companion object{
       val BASE_URL ="https://api.openweathermap.org/"
        fun getInstance(): OWInterface
        {   Log.d("succ","get instance")
           val retrobuilder= Retrofit.Builder()
            retrobuilder.addConverterFactory(GsonConverterFactory.create())
            retrobuilder.baseUrl(BASE_URL)
           val retrofit= retrobuilder.build()

            return retrofit.create(OWInterface::class.java)
        }
    }
}
class caller {


}
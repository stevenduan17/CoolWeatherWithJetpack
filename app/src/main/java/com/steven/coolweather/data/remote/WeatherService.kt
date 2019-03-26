package com.steven.coolweather.data.remote

import com.steven.coolweather.data.db.bean.weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
interface WeatherService {

    @GET("api/weather")
    fun getWeather(@Query("cityid") cityId: String, @Query("key") key: String): Call<Weather>

    @GET("api/bing_pic")
    fun getBingPic(): Call<String>
}
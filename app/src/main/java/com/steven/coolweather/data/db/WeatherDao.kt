package com.steven.coolweather.data.db

import com.google.gson.Gson
import com.steven.coolweather.data.db.bean.weather.Weather
import com.tencent.mmkv.MMKV

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
class WeatherDao private constructor(){

    companion object {
        const val WEATHER_INFO = "WEATHER_INFO"
        const val BING_PIC = "BING_PIC"

        @Volatile
        private var instance: WeatherDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: WeatherDao().also {
                instance = it
            }
        }
    }

    private val kv by lazy { MMKV.defaultMMKV() }

    fun cacheWeatherInfo(weather: Weather?) {
        weather?.let {
            kv.putString(WEATHER_INFO, Gson().toJson(weather))
        }
    }

    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo = kv.getString(WEATHER_INFO, null) ?: return null
        return Gson().fromJson(weatherInfo, Weather::class.java)
    }

    fun cacheBingPic(pic: String?) {
        pic?.let {
            kv.putString(BING_PIC, pic)
        }
    }

    fun getCachedBingPic(): String? = kv.getString(BING_PIC, null)
}
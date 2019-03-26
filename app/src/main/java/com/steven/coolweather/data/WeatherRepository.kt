package com.steven.coolweather.data

import com.steven.coolweather.data.db.WeatherDao
import com.steven.coolweather.data.db.bean.weather.Weather
import com.steven.coolweather.data.remote.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
class WeatherRepository private constructor(private val weatherDao: WeatherDao, private val remote: Remote) {

    companion object {
        @Volatile
        private var instance: WeatherRepository? = null

        fun getInstance(weatherDao: WeatherDao, remote: Remote) = instance ?: synchronized(this) {
            instance ?: WeatherRepository(weatherDao, remote).also {
                instance = it
            }
        }
    }

    private suspend fun fetchWeather(cityId: String, key: String) = withContext(Dispatchers.IO) {
        val weather = remote.fetchWeather(cityId, key)
        weatherDao.cacheWeatherInfo(weather)
        weather
    }

    private suspend fun fetchBingPic() = withContext(Dispatchers.IO) {
        remote.fetchBingPic().also {
            weatherDao.cacheBingPic(it)
        }
    }

    suspend fun getWeather(cityId: String, key: String): Weather {
        return weatherDao.getCachedWeatherInfo() ?: fetchWeather(cityId, key)
    }

    suspend fun refreshWeather(cityId: String, key: String) = fetchWeather(cityId, key)

    suspend fun getBingPic(): String {
        return weatherDao.getCachedBingPic() ?: fetchBingPic()
    }

    suspend fun refreshBingPic() = fetchBingPic()

    fun isWeatherCached() = weatherDao.getCachedWeatherInfo() != null

    fun getCachedWeather() = weatherDao.getCachedWeatherInfo()
}
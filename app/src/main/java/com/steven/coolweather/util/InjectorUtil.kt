package com.steven.coolweather.util

import android.content.Context
import com.steven.coolweather.data.PlaceRepository
import com.steven.coolweather.data.WeatherRepository
import com.steven.coolweather.data.db.AppDatabase
import com.steven.coolweather.data.db.WeatherDao
import com.steven.coolweather.data.remote.Remote
import com.steven.coolweather.ui.area.ChooseAreaModelFactory
import com.steven.coolweather.ui.weather.WeatherModelFactory

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
object InjectorUtil {

    private fun getPlaceRepository(context: Context): PlaceRepository {
        return PlaceRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).placeDao(),
            Remote.getInstance()
        )
    }

    private fun getWeatherRepository(context: Context): WeatherRepository {
        return WeatherRepository.getInstance(
            WeatherDao.getInstance(),
            Remote.getInstance()
        )
    }

    fun getChooseAreaModelFactory(context: Context) = ChooseAreaModelFactory(getPlaceRepository(context))

    fun getWeatherModelFactory(context:Context)= WeatherModelFactory(getWeatherRepository(context))
}
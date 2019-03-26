package com.steven.coolweather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.coolweather.data.WeatherRepository

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
class WeatherModelFactory(private val repository: WeatherRepository) :ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}
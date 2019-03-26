package com.steven.coolweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.coolweather.App
import com.steven.coolweather.util.KEY
import com.steven.coolweather.data.WeatherRepository
import com.steven.coolweather.data.db.bean.weather.Weather
import com.steven.coolweather.util.toast
import kotlinx.coroutines.launch

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val weather = MutableLiveData<Weather>()

    val bingPicUrl = MutableLiveData<String>()

    val refreshing = MutableLiveData<Boolean>()

    val weatherInitialized = MutableLiveData<Boolean>().apply { value = false }

    var weatherId = ""

    fun getWeather() {
        launch({
            weather.value = repository.getWeather(weatherId, KEY)
            weatherInitialized.value = true
        }, {
            App.getInstance().toast(it.message)
        })
        getBingPic(false)
    }

    fun refreshWeather() {
        refreshing.value = true
        launch({
            weather.value = repository.refreshWeather(weatherId, KEY)
            refreshing.value = false
            weatherInitialized.value = true
        }, {
            refreshing.value = false
            App.getInstance().toast(it.message)
        })
        getBingPic(true)
    }

    fun isWeatherCached() = repository.isWeatherCached()

    fun getCachedWeather() = repository.getCachedWeather()

    private fun getBingPic(refresh: Boolean) {
        launch({
            bingPicUrl.value = if (refresh) repository.refreshBingPic() else repository.getBingPic()
        }, {
            App.getInstance().toast(it.message)
        })
    }

    private fun launch(f: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            f()
        } catch (t: Throwable) {
            error(t)
        }
    }

}
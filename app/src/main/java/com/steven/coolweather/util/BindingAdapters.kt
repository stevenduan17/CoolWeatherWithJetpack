package com.steven.coolweather.util

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.steven.coolweather.R
import com.steven.coolweather.data.db.bean.weather.Weather
import com.steven.coolweather.databinding.ForecastItemBinding

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */

@BindingAdapter("bind:loadBingPic")
fun ImageView.loadBingPic(url: String?) {
    Glide.with(context).load(url).into(this)
}

@BindingAdapter("bind:colorSchemeResources")
fun SwipeRefreshLayout.colorSchemeResources(resId: Int) {
    setColorSchemeColors(resId)
}

@BindingAdapter("bind:showForecast")
fun LinearLayout.showForecast(weather: Weather?) = weather?.let {
    removeAllViews()
    for (forecast in it.HeWeather[0].daily_forecast) {
        val view = LayoutInflater.from(context).inflate(R.layout.forecast_item, this, false)
        DataBindingUtil.bind<ForecastItemBinding>(view)?.forecast = forecast
        addView(view)
    }
}


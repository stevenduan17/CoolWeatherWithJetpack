package com.steven.coolweather.ui.weather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.steven.coolweather.R
import com.steven.coolweather.databinding.ActivityWeatherBinding
import com.steven.coolweather.util.InjectorUtil
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title.*

class WeatherActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWeatherBinding
    lateinit var mViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        mViewModel = ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory(this))
            .get(WeatherViewModel::class.java)
        mBinding.apply {
            viewModel = mViewModel
            resId = R.color.colorPrimary
            lifecycleOwner = this@WeatherActivity
        }
        mViewModel.weatherId = if (mViewModel.isWeatherCached()) {
            mViewModel.getCachedWeather()!!.HeWeather[0].basic.cid
        } else {
            intent.getStringExtra("weather_id")
        }

        navButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        mViewModel.getWeather()
    }
}

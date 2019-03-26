package com.steven.coolweather.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.steven.coolweather.util.KEY
import com.steven.coolweather.R
import com.steven.coolweather.ui.area.AreaFragment
import com.steven.coolweather.ui.weather.WeatherActivity
import com.steven.coolweather.ui.weather.WeatherViewModel
import com.steven.coolweather.util.InjectorUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (KEY.isEmpty()) {
            AlertDialog.Builder(this)
                .setMessage("请配置API KEY")
                .setCancelable(false)
                .setPositiveButton("确定") { _, _ ->
                    finish()
                }
                .show()
        } else {
            val viewModel = ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory(this))
                .get(WeatherViewModel::class.java)
            if (viewModel.isWeatherCached()) {
                startActivity(Intent(this, WeatherActivity::class.java))
                finish()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.mContainer, AreaFragment.newInstance()).commit()
            }
        }
    }
}

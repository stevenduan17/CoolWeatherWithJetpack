package com.steven.coolweather.data.db.bean.weather

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
data class Weather(
    val HeWeather: List<HeWeather>
)

data class HeWeather(
    val aqi: Aqi,
    val basic: Basic,
    val daily_forecast: List<DailyForecast>,
    val now: Now,
    val status: String,
    val suggestion: Suggestion,
    val update: Update
)

data class Aqi(
    val city: City
)

data class City(
    val aqi: String,
    val pm25: String,
    val qlty: String
)

data class Suggestion(
    val comf: Comf,
    val cw: Cw,
    val sport: Sport
)

data class Comf(
    val brf: String,
    val txt: String,
    val type: String
)

data class Sport(
    val brf: String,
    val txt: String,
    val type: String
)

data class Cw(
    val brf: String,
    val txt: String,
    val type: String
)

data class Now(
    val cloud: String,
    val cond: Cond,
    val cond_code: String,
    val cond_txt: String,
    val fl: String,
    val hum: String,
    val pcpn: String,
    val pres: String,
    val tmp: String,
    val vis: String,
    val wind_deg: String,
    val wind_dir: String,
    val wind_sc: String,
    val wind_spd: String
)

data class Cond(
    val code: String,
    val txt: String
)

data class DailyForecast(
    val cond: CondX,
    val date: String,
    val tmp: Tmp
)

data class CondX(
    val txt_d: String
)

data class Tmp(
    val max: String,
    val min: String
)

data class Update(
    val loc: String,
    val utc: String
)

data class Basic(
    val admin_area: String,
    val cid: String,
    val city: String,
    val cnty: String,
    val id: String,
    val lat: String,
    val location: String,
    val lon: String,
    val parent_city: String,
    val tz: String,
    val update: Update
)
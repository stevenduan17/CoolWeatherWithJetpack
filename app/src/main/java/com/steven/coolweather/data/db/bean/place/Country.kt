package com.steven.coolweather.data.db.bean.place

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
@Entity(tableName = "country")
data class Country(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "weather_id") @SerializedName("weather_id") val weatherId: String,
    @ColumnInfo(name = "city_id") var cityId: Int = 0
)
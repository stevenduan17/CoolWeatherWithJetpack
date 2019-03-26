package com.steven.coolweather.data.db.bean.place

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
@Entity(tableName = "city")
data class City(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "province_id") var provinceId: Int = 0
)
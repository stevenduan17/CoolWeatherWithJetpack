package com.steven.coolweather.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.steven.coolweather.data.db.bean.place.City
import com.steven.coolweather.data.db.bean.place.Country
import com.steven.coolweather.data.db.bean.place.Province

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
@Dao
interface PlaceDao {

    @Query("SELECT * FROM province ORDER BY name")
    fun getProvinces(): MutableList<Province>

    @Query("SELECT * FROM city WHERE id= :provinceId ORDER BY name")
    fun getCities(provinceId: Int): MutableList<City>

    @Query("SELECT * FROM country WHERE city_id= :cityId ORDER BY name")
    fun getCountries(cityId: Int): MutableList<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProvinces(provinces: List<Province>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<City>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)
}
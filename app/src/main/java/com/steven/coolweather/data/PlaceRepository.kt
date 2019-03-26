package com.steven.coolweather.data

import com.steven.coolweather.data.db.PlaceDao
import com.steven.coolweather.data.remote.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
class PlaceRepository private constructor(private val placeDao: PlaceDao, private val remote: Remote) {

    companion object {
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao, remote: Remote) = instance ?: synchronized(this) {
            instance ?: PlaceRepository(placeDao, remote).also {
                instance = it
            }
        }
    }

    suspend fun getProvinces() = withContext(Dispatchers.IO) {
        var provinces = placeDao.getProvinces()
        if (provinces.isEmpty()) {
            provinces = remote.fetchProvinces()
            placeDao.insertProvinces(provinces)
        }
        provinces
    }

    suspend fun getCities(provinceId: Int) = withContext(Dispatchers.IO) {
        var cities = placeDao.getCities(provinceId)
        if (cities.isEmpty()) {
            cities = remote.fetchCitites(provinceId)
            cities.forEach { it.provinceId = provinceId }
            placeDao.insertCities(cities)
        }
        cities
    }

    suspend fun getCountries(provinceId: Int, cityId: Int) = withContext(Dispatchers.IO) {
        var countries = placeDao.getCountries(cityId)
        if (countries.isEmpty()) {
            countries = remote.fetchCountries(provinceId, cityId)
            countries.forEach { it.cityId = cityId }
            placeDao.insertCountries(countries)
        }
        countries
    }


}
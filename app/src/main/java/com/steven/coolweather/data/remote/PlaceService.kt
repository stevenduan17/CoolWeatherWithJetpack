package com.steven.coolweather.data.remote

import com.steven.coolweather.data.db.bean.place.City
import com.steven.coolweather.data.db.bean.place.Country
import com.steven.coolweather.data.db.bean.place.Province
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
interface PlaceService {

    @GET("api/china")
    fun getProvinces(): Call<MutableList<Province>>

    @GET("api/china/{provinceId}")
    fun getCities(@Path("provinceId") provinceId: Int): Call<MutableList<City>>

    @GET("api/china/{provinceId}/{weatherId}")
    fun getCountries(@Path("provinceId") provinceId: Int, @Path("weatherId") cityId: Int): Call<MutableList<Country>>
}
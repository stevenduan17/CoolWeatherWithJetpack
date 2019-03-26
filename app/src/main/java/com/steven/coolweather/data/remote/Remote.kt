package com.steven.coolweather.data.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
class Remote private constructor() {

    companion object {
        @Volatile
        private var instance: Remote? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Remote().also {
                instance = it
            }
        }
    }


    private val placeService by lazy { Api.create(PlaceService::class.java) }

    private val weatherService by lazy { Api.create(WeatherService::class.java) }

    private suspend fun <T> Call<T>.await(): T = suspendCoroutine {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let { res ->
                    it.resume(res)
                } ?: it.resumeWithException(Throwable("response body is null"))
            }
        })
    }

    suspend fun fetchProvinces() = placeService.getProvinces().await()

    suspend fun fetchCitites(provinceId: Int) = placeService.getCities(provinceId).await()

    suspend fun fetchCountries(provinceId: Int, cityId: Int) = placeService.getCountries(provinceId, cityId).await()

    suspend fun fetchWeather(cityId: String, key: String) = weatherService.getWeather(cityId, key).await()

    suspend fun fetchBingPic() = weatherService.getBingPic().await()

}
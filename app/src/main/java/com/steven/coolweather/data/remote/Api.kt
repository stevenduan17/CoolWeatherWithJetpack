package com.steven.coolweather.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
object Api {

    private const val BASE_URL = "http://guolin.tech/"
    private val okHttpClient = OkHttpClient.Builder().build()

    fun <T> create(service: Class<T>): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

}
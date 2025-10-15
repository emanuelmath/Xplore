package com.example.xplore.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
     val weatherApiService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    val geocodingApiService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}
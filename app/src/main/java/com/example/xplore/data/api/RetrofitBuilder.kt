package com.example.xplore.data.api

import okhttp3.OkHttpClient
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

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header("User-Agent", "XploreApp/1.0")
                .build()
            chain.proceed(request)
        }
        .build()

    val geocodingApiService: GeocodingApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL_GEOCODING)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }
}

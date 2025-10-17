package com.example.xplore.data.api

import com.example.xplore.data.api.dtos.ReverseGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("reverse")
    suspend fun getReverseGeocoding(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): ReverseGeocodingResponse
}
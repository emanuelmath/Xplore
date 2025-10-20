package com.example.xplore.data.api

import com.example.xplore.data.api.dtos.ReverseGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("reverse")
    suspend fun getReverseGeocoding(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("format") format: String = "json"
    ): ReverseGeocodingResponse
}
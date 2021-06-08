package com.example.fxbuddy.data.datasource

import com.example.fxbuddy.model.RateHistoryResponse
import com.example.fxbuddy.model.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FxDataSource {
    @GET("/apihistorical")
    suspend fun getRates(
        @Query("api_key") key: String,
        @Query("date") date: String,
        @Query("interval") interval: String,
        @Query("currency") currency: String
    ): Response<RateResponse>

    @GET("/apitimeseries")
    suspend fun getRatesHistory(
        @Query("api_key") key: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("interval") interval: String,
        @Query("currency") currency: String,
        @Query("format") format: String
    ): Response<RateHistoryResponse>
}
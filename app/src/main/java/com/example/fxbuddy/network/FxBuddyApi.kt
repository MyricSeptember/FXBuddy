package com.example.fxbuddy.network

import com.example.fxbuddy.network.model.RateResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface FxBuddyApi {
    @GET("/apiconvert")
    suspend fun getRates(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Int
    ): Response<RateResponse>

    @GET("/apitimeseries")
    suspend fun getRatesHistory(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("interval") interval: String,
        @Query("currency") currency: String,
        @Query("format") format: String
    ): Response<RateHistoryResponse>

    companion object {
        fun create(retroFit: Retrofit): FxBuddyApi = retroFit.create(
            FxBuddyApi::class.java
        )
    }
}
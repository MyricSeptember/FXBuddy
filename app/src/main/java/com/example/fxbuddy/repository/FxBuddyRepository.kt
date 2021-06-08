package com.example.fxbuddy.repository

import com.example.fxbuddy.db.FxBuddyDao
import com.example.fxbuddy.db.model.DatabaseRate
import com.example.fxbuddy.domain.RateData
import com.example.fxbuddy.domain.RateHistoryData
import com.example.fxbuddy.network.FxBuddyApi
import com.example.fxbuddy.network.RateHistoryResponse
import com.example.fxbuddy.network.model.RateResponse
import com.example.fxbuddy.util.Resource
import javax.inject.Inject

class FxBuddyRepository @Inject constructor(
    private val api: FxBuddyApi,
    private val FxBuddyDao: FxBuddyDao
) {
    suspend fun getForeignExchangeRate(ratesData: RateData): Resource<RateResponse> {
        return try {
            val response =
                api.getRates(ratesData.fromCurrency, ratesData.toCurrency, ratesData.amount)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    suspend fun getForeignExchangeRateHistory(ratesData: RateHistoryData): Resource<RateHistoryResponse> {
        return try {
            val response = api.getRatesHistory(
                ratesData.startDate, ratesData.endDate, "daily", ratesData.currency, "ohlc"
            )
            val result = response.body()
            val list: ArrayList<DatabaseRate> = arrayListOf()
            if (response.isSuccessful && result != null) {
                for (key in result.price.keys) {
                    result.price[key]?.values?.first()?.let {
                        val intoItem = DatabaseRate(key, it.close, it.high, it.low, it.open)
                        list.add(intoItem)
                    }
                }
                FxBuddyDao.deleteAll()
                FxBuddyDao.insertAll(list)
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    fun getAllRates() = FxBuddyDao.getAllRates()
}
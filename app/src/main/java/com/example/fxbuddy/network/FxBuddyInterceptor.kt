package com.example.fxbuddy.network

import com.example.fxbuddy.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class FxBuddyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.FX_MARKET_KEY)
            .build()

        val requestBuilder = request.newBuilder()
            .url(url)
        return chain.proceed(requestBuilder.build())
    }
}
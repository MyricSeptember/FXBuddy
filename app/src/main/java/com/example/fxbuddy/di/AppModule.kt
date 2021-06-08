package com.example.fxbuddy.di

import com.example.fxbuddy.data.datasource.FxDataSource
import com.example.fxbuddy.data.repository.DefaultFxRepository
import com.example.fxbuddy.data.repository.FXRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://fxmarketapi.com/"

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideFXApi(): FxDataSource = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FxDataSource::class.java)

    @Singleton
    @Provides
    fun provideFXRepository(fxDataSource: FxDataSource): FXRepository =
        DefaultFxRepository(fxDataSource)
}
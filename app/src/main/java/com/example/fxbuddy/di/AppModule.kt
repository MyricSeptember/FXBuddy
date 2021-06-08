package com.example.fxbuddy.di

import android.content.Context
import androidx.room.Room
import com.example.fxbuddy.db.FxBuddyDao
import com.example.fxbuddy.db.FxBuddyDataBase
import com.example.fxbuddy.network.FxBuddyApi
import com.example.fxbuddy.network.FxBuddyInterceptor
import com.example.fxbuddy.repository.FxBuddyRepository
import com.example.fxbuddy.util.DispatcherProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://fxmarketapi.com/"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .client(okHttpClient)
            .build()


    @Provides
    fun provideFxApi(retrofit: Retrofit): FxBuddyApi = FxBuddyApi.create(retrofit)

    @Singleton
    @Provides
    fun provideFXRepository(api: FxBuddyApi, dao: FxBuddyDao): FxBuddyRepository =
        FxBuddyRepository(api, dao)

    @Singleton
    @Provides
    fun provideOkHttp(
        fxBuddyInterceptor: FxBuddyInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(fxBuddyInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FxBuddyDataBase::class.java,
        "fx_db"
    ).build()

    @Provides
    fun provideFxDao(fxDatabase: FxBuddyDataBase): FxBuddyDao {
        return fxDatabase.fxRateDao()
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}
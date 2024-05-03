package com.syamil.izzat.goldappsample.hilt

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.syamil.izzat.goldappsample.network.MetalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent :: class)
@Module
class NetworkModule {
    @Provides
    fun providesOkHttp() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @Provides
    fun providesGSON() = GsonBuilder().serializeNulls().create()

    @Provides
    fun providesGSONConverterFactory(gson: Gson) = GsonConverterFactory.create(gson)

    @Provides
    fun providesRetrofit(client: OkHttpClient, converter: GsonConverterFactory) : MetalApi =
        Retrofit.Builder().baseUrl("https://api.qmdev.xyz/")
            .client(client)
            .addConverterFactory(converter)
            .build().create(MetalApi :: class.java)
}
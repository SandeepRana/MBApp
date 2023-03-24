package com.example.mbapp.di

import com.example.mbapp.api.MBApi
import com.example.mbapp.utils.constants.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun getApi(): MBApi {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build().create(MBApi::class.java)
    }

    /**
     * Creating client for API request
     */
    private fun getClient() =
        OkHttpClient.Builder().readTimeout(NetworkConstants.READ_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .build()
}
package com.softaai.unittesting.di.module

import com.softaai.unittesting.data.remote.JobsApiService
import com.softaai.unittesting.data.remote.MockInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class JobsApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().addInterceptor(MockInterceptor()).build();


    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): JobsApiService = Retrofit.Builder()
        .baseUrl(JobsApiService.API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .client(okHttpClient)
        .build()
        .create(JobsApiService::class.java)

}
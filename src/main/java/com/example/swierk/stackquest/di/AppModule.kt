package com.example.swierk.stackquest.di

import android.app.Application
import android.content.Context
import com.example.swierk.stackquest.api.StackAPI
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule() {


    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideApi(retrofit : Retrofit) = StackAPI(retrofit)

    @Provides
    @Singleton
    fun provideRetrofit(logger : OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .client(logger)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun provideLogger() : OkHttpClient {
        val  logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()

    }

}
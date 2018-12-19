package com.example.swierk.stackquest.di

import android.app.Application
import android.content.Context
import com.example.swierk.stackquest.api.StackAPI
import dagger.Module
import dagger.Provides
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
    fun provideApi() = StackAPI()

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

}
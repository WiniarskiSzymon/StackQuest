package com.example.swierk.stackquest.di

import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.viewModel.SearchActivityViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

    val appModule = module  {

        single{ StackAPI(get()) }

        single{ Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/2.2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build() }


        viewModel { SearchActivityViewModel(get()) }
    }

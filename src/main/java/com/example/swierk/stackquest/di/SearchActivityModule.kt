package com.example.swierk.stackquest.di

import com.example.swierk.stackquest.SearchActivityViewModel
import com.example.swierk.stackquest.SearchActivityViewModelFactory
import com.example.swierk.stackquest.api.StackAPI
import dagger.Module
import dagger.Provides

@Module
abstract class SearchActivityModule{

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSearchActivityViewModelFactory(stackAPI: StackAPI) = SearchActivityViewModelFactory(stackAPI)
    }
}
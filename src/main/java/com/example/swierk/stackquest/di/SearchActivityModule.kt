package com.example.swierk.stackquest.di

import com.example.swierk.stackquest.SearchActivityViewModel
import com.example.swierk.stackquest.api.StackAPI
import dagger.Module
import dagger.Provides

@Module
abstract class SearchActivityModule{

    @Provides
    fun provideSearchActivityViewModel(stackAPI : StackAPI) = SearchActivityViewModel(stackAPI)
}
package com.example.swierk.stackquest.di

import com.example.swierk.stackquest.viewModel.SearchActivityViewModelFactory
import com.example.swierk.stackquest.api.StackAPI
import dagger.Module
import dagger.Provides

@Module
 class SearchActivityModule{

        @Provides
        fun provideSearchActivityViewModelFactory(stackAPI: StackAPI) =
            SearchActivityViewModelFactory(stackAPI)
}
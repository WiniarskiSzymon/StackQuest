package com.example.swierk.stackquest.di

import com.example.swierk.stackquest.View.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(SearchActivity::class))
    internal abstract fun contributeSearchActivity(): SearchActivity
}

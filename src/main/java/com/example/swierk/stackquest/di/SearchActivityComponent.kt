package com.example.swierk.stackquest.di

import com.example.swierk.stackquest.View.SearchActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


@Subcomponent
interface SearchActivityComponent : AndroidInjector<SearchActivity> {



    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<SearchActivity>()
}

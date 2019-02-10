package com.example.swierk.stackquest

import android.app.Activity
import android.app.Application
import com.example.swierk.stackquest.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class StackQuestApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var  activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)

    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }
}
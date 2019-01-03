package com.example.swierk.stackquest


import android.app.Application
import com.example.swierk.stackquest.di.appModule
import org.koin.android.ext.android.startKoin
class StackQuestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }


}
package com.example.swierk.stackquest.di

import android.app.Application
import com.example.swierk.stackquest.StackQuestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, AndroidInjectionModule::class,
    ActivitiesModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: StackQuestApplication)
}

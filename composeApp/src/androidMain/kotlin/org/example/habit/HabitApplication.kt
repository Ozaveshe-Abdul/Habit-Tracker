package org.example.habit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

//
class HabitApplication : Application(), KoinComponent {
//
    override fun onCreate() {
        super.onCreate()
        KoinModule.initKoin {
            androidLogger()
            androidContext(this@HabitApplication)
        }
    }
//
}
package com.supersnippets.tmdb

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.supersnippets.tmdb.di.appModule
import com.supersnippets.tmdb.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, appModule))
        }
    }
}
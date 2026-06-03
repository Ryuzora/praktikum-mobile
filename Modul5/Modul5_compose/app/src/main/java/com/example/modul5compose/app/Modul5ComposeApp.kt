package com.example.modul5compose.app

import android.app.Application
import timber.log.Timber

class modul5composeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

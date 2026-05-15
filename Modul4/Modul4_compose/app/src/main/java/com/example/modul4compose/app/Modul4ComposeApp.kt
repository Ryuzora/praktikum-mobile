package com.example.modul4compose.app

import android.app.Application
import timber.log.Timber

class Modul4ComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
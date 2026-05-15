package com.example.modul4xml

import android.app.Application
import timber.log.Timber

class Modul4XmlApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

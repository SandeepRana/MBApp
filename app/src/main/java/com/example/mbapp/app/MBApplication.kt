package com.example.mbapp.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MBApplication : Application() {

    companion object {
        // Application context
        lateinit var appContext: Context
    }

    /**
     * Called when application is launched
     */
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
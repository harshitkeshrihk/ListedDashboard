package com.hk.listeddashboard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ListedDashboard : Application() {

    companion object {
        lateinit var instance: ListedDashboard
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}
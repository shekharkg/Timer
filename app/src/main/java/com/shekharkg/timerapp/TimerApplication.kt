package com.shekharkg.timerapp

import android.app.Application
import com.shekharkg.timerapp.data.Preference

class TimerApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        Preference.create(this)
    }


}
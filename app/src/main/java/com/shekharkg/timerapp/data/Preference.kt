package com.shekharkg.timerapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


const val CONST_TIMER_SP = "CONST_TIMER_SP"

class Preference constructor(context: Context) {

    var sharedPreferences: SharedPreferences = context.getSharedPreferences("MyTimer", MODE_PRIVATE)

    fun setTimer(value: Long) {
        sharedPreferences.edit().putLong(CONST_TIMER_SP, value).apply()
    }

    fun getTimer() = sharedPreferences.getLong(CONST_TIMER_SP, 0) - System.currentTimeMillis()


    companion object {

        @Volatile
        var instance: Preference? = null

        fun create(context: Context): Preference {
            return instance ?: synchronized(this) {
                instance ?: Preference(context).also { instance = it }
            }
        }

    }


}
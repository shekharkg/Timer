package com.shekharkg.timerapp

import androidx.lifecycle.ViewModel
import com.shekharkg.timerapp.data.Preference
import javax.inject.Inject

class MainViewModel @Inject constructor(private val preference: Preference) : ViewModel() {


    fun saveTimeStamp(duration: Int, unit: Int) {

        //Idea is to same the end time for TIMER.
        //So, if its in seconds i.e: unit == 2 then multiply by 1000 to convert to millis
        var durationInMillis = duration * 1000

        //If its in minutes i.e: unit == 1 then multiply by 60 to convert to millis
        if (unit == 1) {
            durationInMillis *= 60
        }
        //If its in hours i.e: unit == 0 then multiply again by 60*60 to convert to millis
        else if (unit == 2) {
            durationInMillis *= 60 * 60
        }

        val timeStamp = System.currentTimeMillis() + durationInMillis

        preference.setTimer(timeStamp)
    }


}
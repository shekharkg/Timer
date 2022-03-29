package com.shekharkg.timerapp

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekharkg.timerapp.data.Preference
import javax.inject.Inject

class MainViewModel @Inject constructor(private val preference: Preference) : ViewModel() {

    private var _isTimerRunning: MutableLiveData<Long> = MutableLiveData(0)

    private var _remainingDuration: MutableLiveData<String> = MutableLiveData("")

    private var timer: CountDownTimer? = null

    init {
        getTimeStamp()
    }

    fun setupTimer() {
        val duration = preference.getTimer()
        if (duration <= 0) return

        timer = object : CountDownTimer(duration, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / 1000) / 60
                _remainingDuration.value = "$minutes : $seconds"
                Log.e("TAG", "$millisUntilFinished")
            }

            override fun onFinish() {

            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
    }

    fun isTimerRunning() = _isTimerRunning

    fun remainingDuration() = _remainingDuration

    fun getTimeStamp() {
        _isTimerRunning.value = preference.getTimer()
    }


    fun saveTimeStamp(duration: Int, unit: Int) {

        //Idea is to same the end time for TIMER.
        //So, if its in seconds i.e: unit == 2 then multiply by 1000 to convert to millis
        var durationInMillis = duration * 1000

        //If its in minutes i.e: unit == 1 then multiply by 60 to convert to millis
        if (unit == 1) {
            durationInMillis *= 60
        }
        //If its in hours i.e: unit == 0 then multiply again by 60*60 to convert to millis
        else if (unit == 0) {
            durationInMillis *= 60 * 60
        }

        val timeStamp = System.currentTimeMillis() + durationInMillis

        preference.setTimer(timeStamp)
    }


}
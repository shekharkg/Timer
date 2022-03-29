package com.shekharkg.timerapp.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.shekharkg.timerapp.MainActivity
import com.shekharkg.timerapp.R
import com.shekharkg.timerapp.data.Preference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TimerService : Service() {

    @Inject
    lateinit var preference: Preference

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            setupNotification()
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setupNotification()
        return START_STICKY
    }

    private fun setupNotification(){
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)



        val notification = NotificationCompat.Builder(this, "MyTimerChannelId")
            .setContentTitle("Timer")
            .setContentText("CountDown")
            .setSound(null)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        createChannel(notificationManager)
        notificationManager.notify(notificationId, notification.build())


        val timer = preference.getTimer()

        if (timer > 0) {
            object : CountDownTimer(timer, 1000) {

                override fun onTick(millisUntilFinished: Long) {

                    val seconds = (millisUntilFinished / 1000) % 60
                    val minutes = (millisUntilFinished / 1000) / 60

                    notification.setContentTitle("$minutes : $seconds")
                    notificationManager.notify(notificationId, notification.build())
                }

                override fun onFinish() {
                    stopSelf()

                }
            }.start()
        }
    }

    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel =
            NotificationChannel("MyTimerChannelId", "name", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Hello! This is a notification."
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
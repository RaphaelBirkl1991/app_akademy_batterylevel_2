package com.example.batterylevel_2

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import io.flutter.embedding.android.FlutterActivity
import android.content.pm.PackageManager
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {

    private var ringtone: Ringtone? = null
    private val airplaneModeReceiver = AirplaneModeReceiver()
    private val shutdownReceiver = ShutdownReceiver()
    private val doNotDisturbReceiver = DoNotDisturbReceiver()
    private val bootCompletedReceiver = BootCompletedReceiver()

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "MyStickyNotificationChannel"
        private const val NOTIFICATION_ID = 123
        private const val ALARM_CHANNEL = "samples.flutter.io/alarm"
        private const val TAG = "BootCompletedReceiver"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        //     ForegroundService()
    //    registerBootCompletedReceiver()
        super.onCreate(savedInstanceState)
        showStickyNotification()
    //    registerAirplaneModeReceiver()
    //    registerShutdownreceiver()
     //   registerDoNotDisturbReceiver()
    }

    override fun onDestroy() {
        removeStickyNotification()
        super.onDestroy()
//        unregisterAirplaneModeReceiver()
//        unregisterShutdownReceiver()
//        unregisterDoNotDisturbReceiver()
//        unregisterBootCompletedReceiver()
    }

    private fun showStickyNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setColor(android.graphics.Color.parseColor("#010536"))
                .setContentTitle("4H Active!")
                .setContentText("The App works in the background.")
                .setSmallIcon(android.R.drawable.ic_secure)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        com.example.batterylevel_2.R.drawable.logo4h
                    )
                )
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun removeStickyNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "My Sticky Notification Channel"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createForegroundNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ForegroundService"
            val descriptionText = "ForegroundServiceDescription"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("ForegroundService", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }

//
//    private fun registerBootCompletedReceiver() {
//        val filter = IntentFilter(Intent.ACTION_BOOT_COMPLETED)
//        registerReceiver(bootCompletedReceiver, filter)
//    }
//
//    private fun unregisterBootCompletedReceiver() {
//        unregisterReceiver(this.bootCompletedReceiver)
//    }
//}
//
//private fun registerDoNotDisturbReceiver() {
//    val filter = IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
//    registerReceiver(doNotDisturbReceiver, filter)
//}
//
//    private fun unregisterDoNotDisturbReceiver() {
//        unregisterReceiver(doNotDisturbReceiver)
//    }
//    private fun registerShutdownreceiver() {
//        val filter = IntentFilter(Intent.ACTION_SHUTDOWN)
//        registerReceiver(shutdownReceiver, filter)
//    }
//
//    private fun registerAirplaneModeReceiver() {
//        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        registerReceiver(airplaneModeReceiver, filter)
//    }
//
//    private fun unregisterAirplaneModeReceiver() {
//        unregisterReceiver(airplaneModeReceiver)
//    }
//
//    private fun unregisterShutdownReceiver() {
//        unregisterReceiver(shutdownReceiver)
//    }
//
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        println("FIRST LOG TRYOUT")
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor, ALARM_CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "setAlarm" -> {
                    setAlarm()
                    result.success(null)
                }
                "stopAlarm" -> {
                    stopAlarm()
                    result.success(null)
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun setAlarm() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone?.play()
    }

    private fun stopAlarm() {
        ringtone?.stop()
    }

}

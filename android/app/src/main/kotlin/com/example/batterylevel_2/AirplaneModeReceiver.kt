package com.example.batterylevel_2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Vibrator

import androidx.core.app.NotificationCompat


class AirplaneModeReceiver : BroadcastReceiver() {
    private var ringtone: Ringtone? = null
    private val handler = Handler()
    val customSoundUri = Uri.parse("android.resource://com.example.batterylevel_2/raw/warning")

//    override fun onReceive(context: Context?, intent: Intent?) {
//        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
//            val isAirplaneModeOn = intent.getBooleanExtra("state", false)
//            if (isAirplaneModeOn) {
//                Toast.makeText(context, "Flugmodus eingeschaltet", Toast.LENGTH_LONG).show()
//                playAlarm(context)
//                handler.postDelayed({stopAlarm()},3000)
//                vibrate(context)
//            } else {
//                Toast.makeText(context, "Flugmodus ausgeschaltet", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = intent.getBooleanExtra("state", false)
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (isAirplaneModeOn) {
                val notificationId = 1
                val notificationChannelId = "airplane_mode_channel"
                val notificationBuilder = NotificationCompat.Builder(context, notificationChannelId)
                    .setContentTitle("WARNING Flightmode activated")
                    .setContentText("You are currently NOT listening to Signals")
                    .setSmallIcon(android.R.drawable.ic_secure)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo4h))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(null)

                // Erstellen des Notification Channels für Android Oreo und höher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelName = "Airplane Mode Channel"
                    val channel = NotificationChannel(notificationChannelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                    notificationManager.createNotificationChannel(channel)
                }

                notificationManager.notify(notificationId, notificationBuilder.build())

                playCustomAlarm(context, customSoundUri)
                handler.postDelayed({stopAlarm()},3000)
                vibrate(context)
            } else {
                notificationManager.cancel(1)
            }
        }
    }



    private fun playAlarm(context: Context?) {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
         this.ringtone = RingtoneManager.getRingtone(context, notification)
        ringtone?.play()
    }
    private fun stopAlarm() {
        ringtone?.stop()
    }

    private fun playCustomAlarm(context: Context?, customSoundUri: Uri) {
        try {
            val mediaPlayer = MediaPlayer.create(context, customSoundUri)
            mediaPlayer.isLooping = false
            mediaPlayer.start()
            Handler().postDelayed({
                mediaPlayer.stop()
                mediaPlayer.release()
            }, 2000)
            // Optional: Setze einen OnCompletionListener, um Aktionen nach dem Abspielen des Alarms auszuführen
            mediaPlayer.setOnCompletionListener { mp ->
                // Aktionen nach dem Abspielen des Alarms
                mp.release() // Freigabe der MediaPlayer-Ressourcen
            }
        } catch (e: Exception) {
            // Fehlerbehandlung, falls das Abspielen des Alarms fehlschlägt
            e.printStackTrace()
        }
    }

    private fun vibrate(context: Context?) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000)
    }

}
//package com.example.batterylevel_2
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.Service.START_STICKY
//import android.content.Context
//import android.content.Context.NOTIFICATION_SERVICE
//import android.content.Intent
//import android.os.Build
//import android.os.IBinder
//import androidx.core.app.NotificationCompat
//import androidx.core.content.ContextCompat.getSystemService
//import java.security.Provider.Service
//
//class PermanentNotification : Service() {
//    override fun onBind(intent: Intent): IBinder? {
//        return null
//    }
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        showNotification()
//        return START_STICKY
//    }
//    private fun showNotification() {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channelId = "background_service_channel"
//        val channelName = "Background Service Channel"
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setContentTitle("App läuft im Hintergrund")
//            .setContentText("Die App läuft im Hintergrund. Tippen, um zurückzukehren.")
//            .setSmallIcon(R.drawable.ic_notification)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setOngoing(true)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        startForeground(1, notificationBuilder.build())
//    }
//}
package com.example.batterylevel_2

import android.app.ActivityOptions
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast


class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {

            Toast.makeText(context, "BOOT COMPLETED", Toast.LENGTH_LONG).show()
            println("BOOTCOMPLETEDRECEIVER: Boot Completed")

            val intent = Intent(context, MainActivity::class.java)
//----------------------
//            val channelId = "default_channel_id"
//            val channelName = "Default Channel"
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel(
//                    channelId,
//                    channelName,
//                    NotificationManager.IMPORTANCE_DEFAULT
//                )
//                val notificationManager =
//                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notificationManager.createNotificationChannel(channel)
//            }
//
//            val notificationBuilder = NotificationCompat.Builder(context, channelId)
//                .setContentTitle("App gestartet")
//                .setContentText("Tippen Sie hier, um die App zu öffnen")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//
//            val notificationManager = NotificationManagerCompat.from(context)
//            notificationManager.notify(123, notificationBuilder.build())
//        }
// -------------------------
             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                 val activityOptions = ActivityOptions.makeBasic()
                     .setPendingIntentCreatorBackgroundActivityStartMode(
                         ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED)
                 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                     val pendingIntent = PendingIntent.getActivity(
                         context,
                         1,
                         intent,
                         PendingIntent.FLAG_MUTABLE,
                         activityOptions.toBundle()
                     )
                     try {
                         pendingIntent.send()
                     } catch (e: PendingIntent.CanceledException) {
                         e.printStackTrace()
                     }
                 }
            } else {
                 val pendingIntent = PendingIntent.getActivity(
                     context,
                     1,
                     intent,
                     PendingIntent.FLAG_MUTABLE
                 )
                 try {
                     pendingIntent.send()
                 } catch (e: PendingIntent.CanceledException) {
                     e.printStackTrace()
                 }
            }


        }
//            val launchIntent = Intent(context, MainActivity::class.java)
//            launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context?.startActivity(launchIntent)
//            Toast.makeText(context, "App wird im Hintergrund gestartet", Toast.LENGTH_LONG).show()
        }
    }



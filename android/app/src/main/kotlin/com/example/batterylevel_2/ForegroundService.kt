/*
package com.example.batterylevel_2
import android.R
import android.Manifest
import android.app.ForegroundServiceStartNotAllowedException
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.content.ContextCompat
import java.security.Provider.Service


class ForegroundService: Service() {


    private fun startForeground() {
        val dataSyncPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
        if(dataSyncPermission == PackageManager.PERMISSION_DENIED) {
            stopForeground()
            return
        }
   try {
       val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

           val builder = NotificationCompat.Builder(this, "ForegroundService")
               .setColor(android.graphics.Color.parseColor("#010536"))
               .setContentTitle("4H Active!")
               .setContentText("The App works in the background.")
               .setSmallIcon(android.R.drawable.ic_secure)
               .setLargeIcon(BitmapFactory.decodeResource(resources, com.example.batterylevel_2.R.drawable.logo4h))
               .setOngoing(true)
           builder.build()
       } else {
           null
       }
       )
   } catch (e: Exception) {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && e is ForegroundServiceStartNotAllowedException) {

       }
   }

    }
}*/

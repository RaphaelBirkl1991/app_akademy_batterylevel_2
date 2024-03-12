package com.example.batterylevel_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Vibrator
import android.widget.Toast

class MobileDataReceiver : BroadcastReceiver() {
    private var ringtone: Ringtone? = null
    private val handler = Handler()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.EXTRA_NETWORK_REQUEST) {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.getNetworkCapabilities(cm.activeNetwork)
            } else {

            }
            val mobileDataEnabled = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                networkCapabilities?.equals(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
            if (!mobileDataEnabled) {
                Toast.makeText(context, "Mobile Daten wurden deaktiviert", Toast.LENGTH_LONG).show()
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

    private fun vibrate(context: Context?) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000)
    }
}
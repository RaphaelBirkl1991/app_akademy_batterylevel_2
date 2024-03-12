package com.example.batterylevel_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.widget.Toast

class ShutdownReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
            println("\n\nIN SHUTDOWN RECEIVER\n\n")
            if (intent?.action == Intent.ACTION_SHUTDOWN) {
                Toast.makeText(context, "WARNING 4H DEACTIVATED", Toast.LENGTH_LONG).show()
                vibrate(context)
            }
        }
    private fun vibrate(context: Context?) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000)
    }
    }
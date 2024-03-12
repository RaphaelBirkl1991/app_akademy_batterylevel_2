package com.example.batterylevel_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Handler
import android.os.Vibrator
import android.widget.Toast

class DoNotDisturbReceiver : BroadcastReceiver() {
    private var ringtone: Ringtone? = null
    private val handler = Handler()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
            val ringerMode = intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, AudioManager.RINGER_MODE_NORMAL)
            if (ringerMode == AudioManager.RINGER_MODE_SILENT || ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
                Toast.makeText(context, "Nicht stören aktiviert", Toast.LENGTH_LONG).show()

                playAlarm(context)
                handler.postDelayed({stopAlarm()},3000)
                vibrate(context)
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


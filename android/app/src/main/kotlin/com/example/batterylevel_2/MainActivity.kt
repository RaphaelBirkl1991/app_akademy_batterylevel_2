package com.example.batterylevel_2
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private var ringtone: Ringtone? = null
    private val airplaneModeReceiver = AirplaneModeReceiver()
    private val shutdownReceiver = ShutdownReceiver()
   private val doNotDisturbReceiver = DoNotDisturbReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerAirplaneModeReceiver()
        registerShutdownreceiver()
        registerDoNotDisturbReceiver()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterAirplaneModeReceiver()
        Toast.makeText(this, "App wird geschlossen", Toast.LENGTH_LONG).show()
        vibrate()
        unregisterShutdownReceiver()
        unregisterDoNotDisturbReceiver()

    }
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }

private fun registerDoNotDisturbReceiver() {
    val filter = IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
    registerReceiver(doNotDisturbReceiver, filter)
}

    private fun unregisterDoNotDisturbReceiver() {
        unregisterReceiver(doNotDisturbReceiver)
    }
    private fun registerShutdownreceiver() {
        val filter = IntentFilter(Intent.ACTION_SHUTDOWN)
        registerReceiver(shutdownReceiver, filter)
    }

    private fun registerAirplaneModeReceiver() {
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filter)
    }

    private fun unregisterAirplaneModeReceiver() {
        unregisterReceiver(airplaneModeReceiver)
    }

    private fun unregisterShutdownReceiver() {
        unregisterReceiver(shutdownReceiver)
    }

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
    companion object {
        private const val ALARM_CHANNEL = "samples.flutter.io/alarm"
    }
}

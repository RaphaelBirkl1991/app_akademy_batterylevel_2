package com.example.batterylevel_2
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.BatteryManager
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class MainActivity : FlutterActivity() {

    private var ringtone: Ringtone? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
//        EventChannel(flutterEngine.dartExecutor, CHARGING_CHANNEL).setStreamHandler(
//            object : EventChannel.StreamHandler {
//                private var chargingStateChangeReceiver: BroadcastReceiver? = null
//
//                override fun onListen(arguments: Any?, events: EventSink) {
//                    chargingStateChangeReceiver = createChargingStateChangeReceiver(events)
//                    registerReceiver(
//                        chargingStateChangeReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//                    )
//                }
//
//                override fun onCancel(arguments: Any?) {
//                    unregisterReceiver(chargingStateChangeReceiver)
//                    chargingStateChangeReceiver = null
//                }
//            }
//        )

//        MethodChannel(flutterEngine.dartExecutor, BATTERY_CHANNEL).setMethodCallHandler { call, result ->
//            when (call.method) {
//                "getBatteryLevel" -> {
//                    val batteryLevel: Int = getBatteryLevel()
//                    if (batteryLevel != -1) {
//                        result.success(batteryLevel)
//                    } else {
//                        result.error("UNAVAILABLE", "Battery level not available.", null)
//                    }
//                }
//                else -> result.notImplemented()
//            }
//        }

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

//    private fun createChargingStateChangeReceiver(events: EventSink): BroadcastReceiver {
//        return object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
//                if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
//                    events.error("UNAVAILABLE", "Charging status unavailable", null)
//                } else {
//                    val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
//                            status == BatteryManager.BATTERY_STATUS_FULL
//                    events.success(if (isCharging) "charging" else "discharging")
//                }
//            }
//        }
//    }

//    private fun getBatteryLevel(): Int {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
//            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//        } else {
//            val intent = ContextWrapper(applicationContext).registerReceiver(
//                null,
//                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//            )
//            intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 /
//                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//        }
//    }

    private fun setAlarm() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone?.play()
    }

    private fun stopAlarm() {
        ringtone?.stop()
    }

    companion object {
   //     private const val BATTERY_CHANNEL = "samples.flutter.io/battery"
   //     private const val CHARGING_CHANNEL = "samples.flutter.io/charging"
        private const val ALARM_CHANNEL = "samples.flutter.io/alarm"
    }
}

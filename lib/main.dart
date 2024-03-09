import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class PlatformChannel extends StatefulWidget {
  const PlatformChannel({super.key, Key? kkey});

  @override
  State<PlatformChannel> createState() => _PlatformChannelState();
}

class _PlatformChannelState extends State<PlatformChannel> {
  // static const MethodChannel methodChannel =
  //     MethodChannel('samples.flutter.io/battery');
  // static const EventChannel eventChannel =
  //     EventChannel('samples.flutter.io/charging');
  static const MethodChannel alarmMethodChannel =
      MethodChannel('samples.flutter.io/alarm');

  // String _batteryLevel = 'Battery level: unknown.';
  // String _chargingStatus = 'Battery status: unknown.';

  // Future<void> _getBatteryLevel() async {
  //   String batteryLevel;
  //   try {
  //     final int? result = await methodChannel.invokeMethod('getBatteryLevel');
  //     batteryLevel = 'Battery level: $result%.';
  //   } on PlatformException catch (e) {
  //     if (e.code == 'NO_BATTERY') {
  //       batteryLevel = 'No battery.';
  //     } else {
  //       batteryLevel = 'Failed to get battery level.';
  //     }
  //   }
  //   setState(() {
  //     _batteryLevel = batteryLevel;
  //   });
  // }

  // @override
  // void initState() {
  //   super.initState();
  //   eventChannel.receiveBroadcastStream().listen(_onEvent, onError: _onError);
  // }

  // void _onEvent(Object? event) {
  //   setState(() {
  //     _chargingStatus =
  //         "Battery status: ${event == 'charging' ? '' : 'dis'}charging.";
  //   });
  // }

  // void _onError(Object error) {
  //   setState(() {
  //     _chargingStatus = 'Battery status: unknown.';
  //   });
  // }

  Future<void> setAlarm() async {
    try {
      await alarmMethodChannel.invokeMethod("setAlarm");
    } on PlatformException catch (e) {
      print(e);
    }
  }

  Future<void> stopAlarm() async {
    try {
      await alarmMethodChannel.invokeMethod("stopAlarm");
    } on PlatformException catch (e) {
      print(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              // Text(_batteryLevel, key: const Key('Battery level label')),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    // ElevatedButton(
                    //   onPressed: _getBatteryLevel,
                    //   child: const Text('Refresh'),
                    // ),
                    ElevatedButton(
                      onPressed: setAlarm,
                      child: const Text('Set Alarm'),
                    ),
                    ElevatedButton(
                      onPressed: stopAlarm,
                      child: const Text('Stop Alarm'),
                    ),
                  ],
                ),
              ),
            ],
          ),
          // Text(_chargingStatus),
        ],
      ),
    );
  }
}

void main() {
  runApp(const MaterialApp(home: PlatformChannel()));
}
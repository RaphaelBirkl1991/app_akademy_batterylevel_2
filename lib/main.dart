import 'dart:async';
import 'dart:developer';

import 'package:batterylevel_2/firebase_options.dart';
import 'package:batterylevel_2/smoke_repo.dart';
import 'package:batterylevel_2/smokesign.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class PlatformChannel extends StatefulWidget {
  const PlatformChannel({super.key, Key? kkey});

  @override
  State<PlatformChannel> createState() => _PlatformChannelState();
}

class _PlatformChannelState extends State<PlatformChannel> {
  static const MethodChannel alarmMethodChannel =
      MethodChannel("samples.flutter.io/alarm");

  final SmokeRepo smokeRepo = SmokeRepo();
  StreamSubscription<DocumentSnapshot>? smokeSingSubscription;

  @override
  void initState() {
    super.initState();
    subscribeToSmokeSignChanges();
  }

  @override
  void dispose() {
    unsubscribeFromSmokeSignChanges();
    super.dispose();
  }

  void subscribeToSmokeSignChanges() {
    debugPrint("\n\nMETHOD: SUBSCRIBE TO SMOKE SIGN CHANGES\n\n");
    smokeRepo.smokeSignStream.listen((List<SmokeSign> smokeSigns) {
      if (smokeSigns.isNotEmpty) {
        debugPrint("SMOKESIGNS COLLECTION IS NOT EMPTY");
        setAlarm();
      }
    });
  }

  void unsubscribeFromSmokeSignChanges() {
    smokeSingSubscription?.cancel();
  }

  Future<void> setAlarm() async {
    debugPrint("SET ALARM");
    try {
      await alarmMethodChannel.invokeMethod("setAlarm");
    } on PlatformException catch (e) {
      log(e.toString());
    }
  }

  Future<void> stopAlarm() async {
    try {
      await alarmMethodChannel.invokeMethod("stopAlarm");
    } on PlatformException catch (e) {
      log(e.toString());
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
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: [
                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: setAlarm,
                      child: const Text('Set Alarm'),
                    ),
                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: stopAlarm,
                      child: const Text('Stop Alarm'),
                    ),
                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: () {
                        smokeRepo.createSmokeSign();
                      },
                      child: const Text("create smokesign"),
                    ),
                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: () {
                        smokeRepo.deleteSmokeSigns(context);
                      },
                      child: const Text("delete Smokesigns"),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  await Future.delayed(const Duration(seconds: 2));
  runApp(const MaterialApp(home: PlatformChannel()));
}

import 'dart:async';
import 'dart:developer';

import 'package:batterylevel_2/smokesign.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class SmokeRepo {
  static const MethodChannel alarmMethodChannel =
      MethodChannel("samples.flutter.io/alarm");
  final FirebaseFirestore instance = FirebaseFirestore.instance;
  final StreamController<List<SmokeSign>> smokeSignController =
      StreamController<List<SmokeSign>>.broadcast();

  Stream<List<SmokeSign>> get smokeSignStream => smokeSignController.stream;

  SmokeRepo() {
    subscribeToSmokeSigns();
  }

  void dispose() {
    smokeSignController.close();
  }

  void subscribeToSmokeSigns() {
    debugPrint("SUBSCRIBE TO SMOKE SIGNS");
    instance.collection("SmokeSign").snapshots().listen((snapshot) {
      if (snapshot.docs.isNotEmpty) {
        debugPrint("SMOKESIGNS COLLECTION IS NOT EMPTY");
        setAlarm();
      }
    });
  }

  Future<void> createSmokeSign() async {
    debugPrint("CREATE SMOKE SIGN");
    SmokeSign smokeSign = SmokeSign("pretty SmokeSign");

    Map<String, dynamic> smokeMap = smokeSign.toMap();
    debugPrint(smokeMap.toString());

    try {
      // Daten in der Firestore-Datenbank speichern
      await FirebaseFirestore.instance.collection("SmokeSign").add(smokeMap);
      log("SmokeSign erfolgreich erstellt und in der Datenbank gespeichert.");
    } catch (e) {
      log("Fehler beim Erstellen von SmokeSign und Speichern in der Datenbank: $e");
    }
  }

  Future<void> deleteSmokeSigns(context) async {
    try {
      final querySnapshot = await instance.collection("SmokeSign").get();

      if (querySnapshot.docs.isNotEmpty) {
        for (var doc in querySnapshot.docs) {
          await doc.reference.delete();
        }
      } else {
        log("no entry in database found");
      }
    } catch (e) {
      log(e.toString());
    }
  }

  Future<void> setAlarm() async {
    debugPrint("SET ALARM");
    try {
      await alarmMethodChannel.invokeMethod("setAlarm");
    } on PlatformException catch (e) {
      log(e.toString());
    }
  }
}

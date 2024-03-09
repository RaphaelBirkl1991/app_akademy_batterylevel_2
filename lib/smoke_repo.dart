import 'dart:async';
import 'dart:developer';

import 'package:batterylevel_2/smokesign.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class SmokeRepo {
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
    instance.collection("SmokeSign").snapshots().listen((snapshot) {
      final List<SmokeSign> smokeList = [];
      for (final doc in snapshot.docs) {
        final smokeSign = SmokeSign.fromMap(doc.data());
        smokeList.add(smokeSign);
      }
    });
  }

  Future<void> createSmokeSign() async {
    SmokeSign smokeSign = SmokeSign("pretty SmokeSign");

    Map<String, dynamic> smokeMap = smokeSign.toMap();
    log(smokeMap.toString());

    try {
      // Daten in der Firestore-Datenbank speichern
      await FirebaseFirestore.instance.collection("SmokeSign").add(smokeMap);
      log("SmokeSign erfolgreich erstellt und in der Datenbank gespeichert.");
    } catch (e) {
      log("Fehler beim Erstellen von SmokeSign und Speichern in der Datenbank: $e");
    }
  }

  Future<void> deleteSmokeSign(context) async {
    try {
      final querySnapshot = await instance.collection("SmokeSign").get();

      if (querySnapshot.docs.isNotEmpty) {
        await instance.collection("SmokeSign").doc().delete();
      } else {
        log("no entry in database found");
      }
    } catch (e) {
      log(e.toString());
    }
  }
}

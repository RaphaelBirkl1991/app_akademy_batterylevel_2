// File generated by FlutterFire CLI.
// ignore_for_file: lines_longer_than_80_chars, avoid_classes_with_only_static_members
import 'package:firebase_core/firebase_core.dart' show FirebaseOptions;
import 'package:flutter/foundation.dart'
    show defaultTargetPlatform, kIsWeb, TargetPlatform;

/// Default [FirebaseOptions] for use with your Firebase apps.
///
/// Example:
/// ```dart
/// import 'firebase_options.dart';
/// // ...
/// await Firebase.initializeApp(
///   options: DefaultFirebaseOptions.currentPlatform,
/// );
/// ```
class DefaultFirebaseOptions {
  static FirebaseOptions get currentPlatform {
    if (kIsWeb) {
      return web;
    }
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return android;
      case TargetPlatform.iOS:
        return ios;
      case TargetPlatform.macOS:
        return macos;
      case TargetPlatform.windows:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for windows - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      case TargetPlatform.linux:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for linux - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      default:
        throw UnsupportedError(
          'DefaultFirebaseOptions are not supported for this platform.',
        );
    }
  }

  static const FirebaseOptions web = FirebaseOptions(
    apiKey: 'AIzaSyCL2q08b6sCteUfEZQdwmkeb3zx4_vHPcE',
    appId: '1:1629452045:web:ddd52b25644d92ff4a6244',
    messagingSenderId: '1629452045',
    projectId: 'batterylevel2-d30b0',
    authDomain: 'batterylevel2-d30b0.firebaseapp.com',
    storageBucket: 'batterylevel2-d30b0.appspot.com',
  );

  static const FirebaseOptions android = FirebaseOptions(
    apiKey: 'AIzaSyCE5tG0_bekgg49Mb0LNPiu7ik-eSK21Jc',
    appId: '1:1629452045:android:886e17f4142d77234a6244',
    messagingSenderId: '1629452045',
    projectId: 'batterylevel2-d30b0',
    storageBucket: 'batterylevel2-d30b0.appspot.com',
  );

  static const FirebaseOptions ios = FirebaseOptions(
    apiKey: 'AIzaSyDzltn33YUhCPN7M6vu0IaYwzaiwEAmBjk',
    appId: '1:1629452045:ios:058f15c04c834cf44a6244',
    messagingSenderId: '1629452045',
    projectId: 'batterylevel2-d30b0',
    storageBucket: 'batterylevel2-d30b0.appspot.com',
    iosBundleId: 'com.example.batterylevel2',
  );

  static const FirebaseOptions macos = FirebaseOptions(
    apiKey: 'AIzaSyDzltn33YUhCPN7M6vu0IaYwzaiwEAmBjk',
    appId: '1:1629452045:ios:141fa4a3b2b69d734a6244',
    messagingSenderId: '1629452045',
    projectId: 'batterylevel2-d30b0',
    storageBucket: 'batterylevel2-d30b0.appspot.com',
    iosBundleId: 'com.example.batterylevel2.RunnerTests',
  );
}
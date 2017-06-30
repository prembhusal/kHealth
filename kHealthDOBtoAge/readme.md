# kHealth Asthma Android Application Setup
# APKs
| Date | App Version  | Minimum SDK | Target SDK | Download Link| Description
| :-- | :-- | :--: | :--: | :--: |:--
| 05-08-2017 | v 2.2.1 | 21 | 21 |  [download](https://github.com/knoesis/SemanticSensorWeb/blob/master/apks/SPIRO_EMAIL.apk)| APK with Only Spirometer entry and Email Service (NODE excluded)
| 05-08-2017 | v 2.2.0 | 21 | 21 |  [download](https://github.com/knoesis/SemanticSensorWeb/blob/master/apks/NO_SPIRO_EMAIL.apk)|APK with NODE integrated, Spirometer entry and Email Service
| 03-22-2017 | v 2.2 | 21 | 21 |  [download](https://github.com/knoesis/SemanticSensorWeb/blob/master/apks/EMAIL.apk)|APK with Email Service only (NODE and SPIRO Excluded)


# Requirements
###### Android Studio 2.2.3
###### JDK 1.7 or greater
###### JRE 1.7 or greater
###### SDK Requirements

``
     Compile Version 23
``
``
     Build Tools Version 24.0.1
``
``
     Minimum SDK Version 19
``
``
     Target SDK Version 19
``
###### Gradle 2.2.3

# Instructions
1. Open Android Studio
2. Click on "Check out project from Version Control"
3. Under the field "Git Repository URL" enter the following:
    https://github.com/knoesis/SemanticSensorWeb.git
4. Rest of the fields "Parent Directory", and "Directory Name" are of your choice
5. Use an Emulator or Android Smartphone to build and Run the App.


# Setting NODE 

1. Go to app/java/org.knoesis.health/constants.Constants class
2. Set the NODE_AVAILABLE flag according to whether you want NO sensor to be integrated or not. (False or True)

# License 

Copyright © 2017 Kno.e.sis

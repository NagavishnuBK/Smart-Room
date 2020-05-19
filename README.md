# Smart-Room
A Bluetooth interface between android app and micro-controller-operated room switches to turn ON/OFF the light and fan of a room via Bluetooth.

Repository contents:
1. layout
   Contains the xml code for the User Interface of the android application.
   
2. smarthome
   This directory contains the java codes for the backend of the android application.
   
3. smartroom_esp32
   This directory contains .ino file that is loaded in the ESP32 micro-controller in the circuit setup at the switch board.
   
4. AndroidManifest.xml and build.gradle
   Core files of the android application containing important data for the app development.
   
5. app-debug.apk
   The installable apk file that is to be installed in the android device that is used to control the switches.

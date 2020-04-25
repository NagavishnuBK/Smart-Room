//This example code is in the Public Domain (or CC0 licensed, at your option.)
//By Evandro Copercini - 2018
//
//This example creates a bridge between Serial and Classical Bluetooth (SPP)
//and also demonstrate that SerialBT have the same functionalities of a normal Serial

#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

String str="";

BluetoothSerial SerialBT;

void setup() {
  Serial.begin(115200);
  SerialBT.begin("ESP32test"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  pinMode(2, OUTPUT);
  pinMode(4, OUTPUT);
}

void loop() {
  while(SerialBT.available()){
    str += char(SerialBT.read());
    //Serial.print(char(SerialBT.read()));
    }
    Serial.println(str);
  if(str.equals("lighton"))
  {
    digitalWrite(2, HIGH);
    Serial.println(1);
    }
  else if(str.equals("lightoff"))
  {
    digitalWrite(2, LOW);
    Serial.println(0);
    }
  else if(str.equals("fanon"))
  {
    digitalWrite(4, HIGH);
    Serial.println(1);
    }
  else if(str.equals("fanoff"))
  {
    digitalWrite(4, LOW);
    Serial.println(0);
    }

  str = "";
  delay(20);
}

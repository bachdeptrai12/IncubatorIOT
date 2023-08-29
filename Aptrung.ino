#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include "DHT.h"

#define FIREBASE_HOST "aptrungfinal-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "dZecP7LmJNla7jNid1vQWpsm0brAuPrjq6Z0bMPB"
#define WIFI_SSID "Bach"
#define WIFI_PASSWORD "11111111"

#define DHTTYPE DHT22
#define DHTPIN 2

DHT dht(DHTPIN, DHTTYPE);

String fireStatus = "";
String fireStatus1 = "";
void setup() {
  pinMode(14, OUTPUT);
  pinMode(4, OUTPUT);
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connection to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.setString("LED", "OFF");
  delay(1000);
  Serial.println(F("DHTxx test!"));
  dht.begin();
}

void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  Serial.print("Nhiet do: ");
  Serial.println(t);
  Serial.print("Do am: ");
  Serial.println(h);

  Firebase.setFloat("Temperature", t);
  Firebase.setFloat("Humidity", h);
  
    if (t > 37.5) {
      Firebase.setString("relay", "OFF");
    } else {
      Firebase.setString("relay", "ON");
    }

    fireStatus = Firebase.getString("relay");
    if (fireStatus == "ON") {
      Serial.println("Led Turned ON");
      digitalWrite(14, LOW);
    }
    if (fireStatus == "OFF") {
      Serial.println("Led Turned OFF");
      digitalWrite(14, HIGH);
    }
    fireStatus1 = Firebase.getString("Fan");
    if (fireStatus1 == "ON") {
      Serial.println("Led Turned ON");
      digitalWrite(4, LOW);
    }
    if (fireStatus1 == "OFF") {
      Serial.println("Fan Turned OFF");
      digitalWrite(4, HIGH);
    }
}

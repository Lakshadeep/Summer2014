#include <LCD.h>

//program for testing gps on software serial 
// RX PIN 10
// TX PIN 11
#include <SoftwareSerial.h>
LCD lcd(7, 6, 5, 4, 3, 2);
SoftwareSerial port1Serial(10,11); 
void setup() {
 
  Serial.begin(9600); 
  Serial.println("Hello");
 
 
  port1Serial.begin(4800);     //gps
  lcd.begin(16, 4);
  lcd.setCursor(0, 0);
  lcd.print("Hello Gps"); 
}

void loop() {
   
     if(port1Serial.available()){ 
       Serial.write(port1Serial.read());
     }
   
}

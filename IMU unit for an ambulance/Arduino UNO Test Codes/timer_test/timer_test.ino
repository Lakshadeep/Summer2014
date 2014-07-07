//program for testing timer1 in arduino
//prints "Hi" after every 5 seconds
#include <TimerOne.h>
#include <SoftwareSerial.h>
SoftwareSerial port1Serial(10,11);
void setup() {
  // put your setup code here, to run once:
  Timer1.initialize(5000000);   
  Serial.begin(9600);
  Timer1.attachInterrupt(callback);
    port1Serial.begin(4800);//gps
}

void loop() {
  // put your main code here, to run repeatedly:
   if(port1Serial.available())
        Serial.write(port1Serial.read()); 
}
void callback(){
  
  Serial.write("Hi");
}
  

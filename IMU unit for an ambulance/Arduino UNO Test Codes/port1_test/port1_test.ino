//Program for testing software serial on different I/O pins using buffer cable
#include <SoftwareSerial.h>
SoftwareSerial port1Serial(10,11); //software uart
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("Hello");

  //port2Serial.begin(9600);
  port1Serial.begin(4800); 

}

void loop() {
  // put your main code here, to run repeatedly:
    
    if(Serial.available()){  
        port1Serial.write(Serial.read());
       
    }
    
    if(port1Serial.available()){
      Serial.write(port1Serial.read());
    }
}

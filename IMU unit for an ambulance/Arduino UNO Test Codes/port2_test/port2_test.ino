//Program for testing software serial on different I/O pins using buffer cable
#include <SoftwareSerial.h>
SoftwareSerial port2Serial(12,13); 
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600); 
  Serial.println("Hello");

  port2Serial.begin(9600);
  
}

void loop() {
  // put your main code here, to run repeatedly:
    
    if(Serial.available()){  
        port2Serial.write(Serial.read());
       
    }
    
    
    if(port2Serial.available()){
      Serial.write(port2Serial.read());
      
    }
    
      
}

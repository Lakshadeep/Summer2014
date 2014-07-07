//program for reading accelerometer,gyroscope,magnetometer data
//from Microstrain IMU

#include <TimerOne.h>
#include <SoftwareSerial.h>
SoftwareSerial port2Serial(12,13);
// deifine variables
unsigned int StabMagField_x, StabMagField_y, StabMagField_z;
unsigned int StabAccel_x, StabAccel_y, StabAccel_z;
unsigned int CompAngRate_x, CompAngRate_y, CompAngRate_z;
unsigned int noofbyte=0;
unsigned char imudata[25];
unsigned char  sendimucmd=0; 

void setup() {
  // put your setup code here, to run once:
  Timer1.initialize(1000000);   
  Serial.begin(9600);
  Timer1.attachInterrupt(callback);
  port2Serial.begin(38400);//imu
}

int Analyse_imudata(unsigned int checksum)
{
  unsigned int comp_chksum=0, timerticks;;
  StabMagField_x = imudata[1]*256 + imudata[2];
  StabMagField_y = imudata[3]*256 + imudata[4];
  StabMagField_z = imudata[5]*256 + imudata[6];
  
  StabAccel_x    = imudata[7]*256 + imudata[8];
  StabAccel_y    = imudata[9]*256 + imudata[10];
  StabAccel_z    = imudata[11]*256 + imudata[12];
   
  CompAngRate_x    = imudata[13]*256 + imudata[14];
  CompAngRate_y    = imudata[15]*256 + imudata[16];
  CompAngRate_z    = imudata[17]*256 + imudata[18];
  timerticks     = imudata[19]*256 + imudata[20];
  
  comp_chksum    = imudata[0] + StabMagField_x + StabMagField_y + StabMagField_z;
  comp_chksum   += StabAccel_x + StabAccel_y + StabAccel_z;
  comp_chksum   += CompAngRate_x + CompAngRate_y + CompAngRate_z;
  comp_chksum   += timerticks;
  
  Serial.print("\r\n");
    
  if(comp_chksum==checksum)
      return comp_chksum;
  else
      return 0;
  
}

 
void loop() {
  // put your main code here, to run repeatedly:
  unsigned int i=0, checksum;
  if(sendimucmd){
     sendimucmd=0;
     port2Serial.write(0x02);
     delay(20);
     noofbyte=port2Serial.available();  
   
  }
   if(noofbyte){
     for(i=0;i<noofbyte;i++)
        imudata[i]=port2Serial.read(); 
     checksum=imudata[21]*256 + imudata[22];
     
     unsigned int comp_chksum = Analyse_imudata(checksum);
     if(comp_chksum){
       
        Serial.print("Mag : "); Serial.print(StabMagField_x);Serial.print(", ");Serial.print(StabMagField_y);Serial.print(", ");Serial.print(StabMagField_z);Serial.print("\r\n");
        Serial.print("Accl : "); Serial.print(StabAccel_x);Serial.print(", ");Serial.print(StabAccel_y);Serial.print(", ");Serial.print(StabAccel_z);Serial.print("\r\n");
        Serial.print("Gyro : "); Serial.print(CompAngRate_x);Serial.print(", ");Serial.print(CompAngRate_y);Serial.print(", ");Serial.print(CompAngRate_z);Serial.print("\r\n");
     }
     else{
       
       Serial.print("\r\n Check sum mismatch \r\n");
     }
     noofbyte=0; 
   }
}
void callback(){
  
    sendimucmd=1;
}
  

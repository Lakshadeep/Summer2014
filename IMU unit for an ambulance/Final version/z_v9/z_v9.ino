#include <TinyGPS.h>
#include <LCD.h>
#include <SoftwareSerial.h>
#include <TimerOne.h>
#include <MPU6050.h>
#include <HMC5883L.h>
#include <Wire.h>
#include <i2c.h>
#define Todeg  180/PI
#define Torad  PI/180

SoftwareSerial port1Serial(10, 11);
LCD lcd(7, 6, 5, 4, 3, 2);

MPU6050 mpu6050;
HMC5883L magnetometer;
i2c i2chelper;
int mpu6050_status, magnetometer_status;
int byteGPS1 = -1,gpsready =0;;
int return_num = 0,disptime=10;
bool initialise_status;
bool check = false;
bool check1 = false;
unsigned char timercount=1;

bool mode_status = true;
bool dispblue = true;
char mode;
unsigned char gpsreadbuf[300];
unsigned long time1, time2;
//gps data
TinyGPS gps;
bool newData = false;
unsigned long chars,date,time;
unsigned short sentences, failed;
float flat=0, flon=0,gpsspeed=0,gpscourse=0;
unsigned long age;
long int lat_int=0,lng_int=0;

String latdeg,londeg;

void setup() {
// put your setup code here, to run once:
    lcd.begin(16, 4);
    Serial.begin(9600);
        
    initialise();
    lcd.setCursor(0, 0);
    lcd.print("IMU Project");
    Timer1.initialize(50000); // 20 Hz
    Timer1.attachInterrupt(callback);
    time1 = micros();  
}

void readsensors(void){
          
     mpu6050.read_accelerometer();
     mpu6050.process_accl_data();
     mpu6050.read_gyroscope();
     mpu6050.process_gyro_data();
     magnetometer.read_data();
     magnetometer.process_magneto_data();
     magnetometer.calculate_heading(0, 0);               
}
void displayport(void){
       Serial.print("IMU");
            Serial.print(",");
            Serial.print(mpu6050.roll_angle*Todeg);
            Serial.print(",");
            Serial.print(mpu6050.pitch_angle*Todeg);
            Serial.print(",");
            Serial.print(magnetometer.heading_degrees);
            Serial.print(",");
            Serial.print(mpu6050.acclx_p);
            Serial.print(",");
            Serial.print(mpu6050.accly_p);
            Serial.print(",");
            Serial.print(mpu6050.acclz_p);
            Serial.print(",");
            
// Print GPS section
            Serial.print(time);
            Serial.print(",");
            Serial.print(flat);
            Serial.print(",");
            Serial.print(flon);
            Serial.print(",");
            Serial.print(gpsspeed);
            Serial.print(",");
            Serial.print(gpscourse);
            Serial.print(",");
            Serial.print(mpu6050.gyrox_p);
            Serial.print(",");
            Serial.print(mpu6050.gyroy_p);
            Serial.print(",");
            Serial.print(mpu6050.gyroz_p);
//            Serial.print(",");
//            Serial.print(mpu6050.dt);            
            Serial.print("\r\n");
            //Serial.print("\n");
            
      //   Serial.print(velocity);
      //   Serial.print("\n");
           Serial.flush();
}
void displayscreen(void){
   
            lcd.clearScreen();
//            lcd.setCursor(0, 0);
//            lcd.print("READING");
            
            lcd.setCursor(0, 0);
            lcd.print(mpu6050.acclx_p);
            lcd.print(",");
            lcd.print(mpu6050.accly_p);
            lcd.print(",");
            lcd.print(mpu6050.acclz_p);
            lcd.print(";");
            lcd.print(time);
            
            lcd.setCursor(0, 1);
            lcd.print(mpu6050.roll_angle*Todeg,1);
            lcd.print(";");
            lcd.print(mpu6050.pitch_angle*Todeg,1);
            lcd.print(";");
            lcd.print(magnetometer.heading_degrees,1);
            
            lcd.setCursor(0, 2);            
            lcd.print(gpsspeed,1);
            lcd.print(";");
            lcd.print(gpscourse,1);
            lcd.print(";");
            
            lcd.setCursor(0, 3);
            lcd.print(flat/1e5,5);
            lcd.print(";");
            lcd.print(flon/1e5,5);
           
          
}
void Analysemode(char mode)
{
   switch(mode){
//                case 'B':
//                    dispblue=true;
//                    break;
                case 'C':
                 // Calibration
                    mode_status = false;
                    lcd.clearScreen();
                    lcd.setCursor(0, 0);
                    lcd.print("CALIBRATION");
                    initialise();
                    mode_status = true;
                    break;
                case 'S':
//                    mode_status = false;
//                    lcd.clearScreen();
//                    lcd.setCursor(0, 0);
//                    lcd.print("STOPPED");
//                    //stopadruino();
                    dispblue=false;
                    break;
                case 'A':
                    dispblue=true;
                    break;
                    
                case 49:              
                    disptime = 20; //1Hz                   
                    break;
                    
                case 50:
                    disptime = 10;//2 Hz
                    break;
                    
                case 51:
                      disptime = 4;//5 Hz
                    break;
                    
                case 52:
                      disptime = 2;//10 Hz
                    break;
                    
                case 53:
                      disptime = 1;//20 Hz
                    break;    
                default:                   
                    break;
            }
}

void loop() {
// put your main code here, to run repeatedly:
    
    if (Serial.available()) {
        mode=Serial.read();        
        Analysemode(mode);
    }
    
    if(mode_status){
        while (port1Serial.available())// Read gps here
        {
            char c = port1Serial.read();
            //Serial.write(c); // uncomment this line if you want to see the GPS data flowing
            if (gps.encode(c)) // Did a new valid sentence come in?
                newData = true;
        }
        
        if(newData){ // This is gps as well
           newData = false;
            gps.f_get_position(&flat, &flon, &age);
         // flat = TinyGPS::GPS_INVALID_F_ANGLE ? 0.0 : flat;
        //  flon = TinyGPS::GPS_INVALID_F_ANGLE ? 0.0 : flon;
            
            //gps.stats(unsigned long *chars, unsigned short *sentences, unsigned short *failed_cs)
            //lat_int = flat;
            //lng_int = (int)flon;
              //latdeg = String((long )flat);
              //londeg = String((long)flon);
            
            gps.get_datetime(&date,&time,&age);    
            gpscourse = gps.f_course();
            gpsspeed  = gps.f_speed_kmph();     
        }
        
        readsensors(); 
//           Serial.print("\r\n");
//           Serial.print(mpu6050.dt);            
//           Serial.flush();
       if(check){
         check=false;
         mpu6050.dt=micros()-time1;  
         mpu6050.dt=mpu6050.dt/1000000;
         mpu6050.calculate_roll_pitch();
         time1 = micros();  
       }
        //check1 =false;
        if(check1){
          check1=false;
          if(dispblue)
             displayport();  
          displayscreen();
        }
    }
}



void initialise() {
    mpu6050_status =  mpu6050.initialise();
    lcd.clearScreen();
    lcd.setCursor(0, 0);
    lcd.print("Init. MPU6050..");
    delay(1000);
   
    lcd.setCursor(0, 1);
    lcd.print("Init. GPS..");
    port1Serial.begin(9600);
    port1Serial.write("$PMTK251,4800*14\r\n");
    delay(2000);
    port1Serial.begin(4800);
    port1Serial.write("$PMTK300,1000,0,0,0,0*1C\r\n");
    delay(500);
    port1Serial.write("$PMTK314,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0*29\r\n"); 
    delay(1000);
//    lcd.print("MPU6050 module NA");
//  if (mpu6050_status == 0) {
//    Serial.print("MPU6050 module not available");
//    Serial.print("\n");
//    lcd.setCursor(0, 1);
//    lcd.print("MPU6050 module NA");
//    mpu6050.roll_angle = 0;
//    mpu6050.pitch_angle = 0;
//    mpu6050.acclx_p = 0;
//    mpu6050.accly_p = 0;
//    mpu6050.acclz_p = 0;
// }
    
    magnetometer.initialise();
    delay(1000);
    
//Serial.print("D");
}


void callback() {
    check=true;
//       mpu6050.dt=micros()-time1;  
//       mpu6050.dt=mpu6050.dt/1000000;
//       mpu6050.calculate_roll_pitch();
//       time1 = micros();        
        
    if(!(timercount++ % disptime)){// display counter
      check1=true; timercount=1;
    } 
}

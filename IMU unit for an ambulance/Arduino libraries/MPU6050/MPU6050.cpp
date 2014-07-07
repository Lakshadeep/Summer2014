#include "MPU6050.h"
//#define PI 3.14159265358979323
MPU6050::MPU6050()
{
    pitch_angle = 0;
    roll_angle = 0;
	dt=0;
}

int MPU6050::initialise(){
  int get_mpu6050_address = i2chelper.readRegister(MPU6050_Address,WHO_AM_I);

  if(get_mpu6050_address == MPU6050_Address){

      //waking up from sleep mode
      i2chelper.writeRegister(MPU6050_Address,MPU6050_PWR_MGMT_1, 0b00000000);

      i2chelper.writeRegister(MPU6050_Address, MPU6050_SMPRT_DIV, 0b00010011);
      //Sampling rate division factor = 19 for 50Hz data rate

      //Configures LPF with cutoff 184 Hz and 188 Hz for accelerometer & gyroscope resp.
      i2chelper.writeRegister(MPU6050_Address,MPU6050_CONFIG, 0b00000001);

      //Setting full scale range to +- 2 g
      i2chelper.writeRegister(MPU6050_Address,ACCL_SELF_TEST, 0b00000000);

      //Setting full scale range to +- 500 degree/sec
      i2chelper.writeRegister(MPU6050_Address,GYRO_SELF_TEST, 0b00001000);
      delay(200);

      return 1;
  }

  else{
    return 0;
  }
}
void MPU6050::read_accelerometer(){
  byte xMSB = i2chelper.readRegister(MPU6050_Address, 0x3B);
  byte xLSB = i2chelper.readRegister(MPU6050_Address, 0x3C);
  acclx_raw = -((xMSB << 8) | xLSB);

  byte yMSB = i2chelper.readRegister(MPU6050_Address, 0x3D);
  byte yLSB = i2chelper.readRegister(MPU6050_Address, 0x3E);
  accly_raw = -((yMSB << 8) | yLSB);

  byte zMSB = i2chelper.readRegister(MPU6050_Address, 0x3F);
  byte zLSB = i2chelper.readRegister(MPU6050_Address, 0x40);
  acclz_raw = -((zMSB << 8) | zLSB);

}
void MPU6050::print_accelerometer(){
   Serial.print("Accelerometer X:");
   Serial.print(acclx_raw);
   Serial.print("\t");

   Serial.print("Accelerometer Y:");
   Serial.print(accly_raw);
   Serial.print("\t");

   Serial.print("Accelerometer Z:");
   Serial.println(acclz_raw);
   Serial.print("\n");

}
void MPU6050::read_gyroscope(){
  byte xMSB = i2chelper.readRegister(MPU6050_Address, 0x43);
  byte xLSB = i2chelper.readRegister(MPU6050_Address, 0x44);
  gyrox_raw = ((xMSB << 8) | xLSB);

  byte yMSB = i2chelper.readRegister(MPU6050_Address, 0x45);
  byte yLSB = i2chelper.readRegister(MPU6050_Address, 0x46);
  gyroy_raw = ((yMSB << 8) | yLSB);

  byte zMSB = i2chelper.readRegister(MPU6050_Address, 0x47);
  byte zLSB = i2chelper.readRegister(MPU6050_Address, 0x48);
  gyroz_raw = ((zMSB << 8) | zLSB);

}
void MPU6050::print_gyroscope(){
   Serial.print("Gyroscope X:");
   Serial.print(gyrox_raw);
   Serial.print("\t");

   Serial.print("Gyroscope Y:");
   Serial.print(gyroy_raw);
   Serial.print("\t");

   Serial.print("Gyroscope Z:");
   Serial.print(gyroz_raw);
   Serial.print("\n");

}

void MPU6050::process_accl_data(){
  acclx_p = (acclx_raw/16327.5)*9.8;
  accly_p = (accly_raw/16327.5)*9.8;
  acclz_p = (acclz_raw/16327.5)*9.8;
}

void MPU6050::process_gyro_data(){
   gyrox_p = (gyrox_raw/16327.5)*250;
   gyroy_p = (gyroy_raw/16327.5)*250;
   gyroz_p = (gyroz_raw/16327.5)*250;
}

void MPU6050::print_process_accl_data(){
   Serial.print("Accelerometer X:");
   Serial.print(acclx_p);
   Serial.print("\t");

   Serial.print("Accelerometer Y:");
   Serial.print(accly_p);
   Serial.print("\t");

   Serial.print("Accelerometer Z:");
   Serial.println(acclz_p);
   Serial.print("\n");
}
void MPU6050::print_process_gyro_data(){
   Serial.print("Gyroscope X:");
   Serial.print(gyrox_p);
   Serial.print("\t");

   Serial.print("Gyroscope Y:");
   Serial.print(gyroy_p);
   Serial.print("\t");

   Serial.print("Gyroscope Z:");
   Serial.print(gyroz_p);
   Serial.print("\n");

}
void MPU6050::send_data_to_matlab(){

   Serial.print(acclx_p);
   Serial.print(",");
   Serial.print(accly_p);
   Serial.print(",");
   Serial.print(acclz_p);
   Serial.print("\n");

}
void MPU6050::calculate_roll_pitch(){
    float x2,y2,z2;
    float accl_pitch,accl_roll, rollrate,pitchrate,yawrate;
    float result;
    //float dt = 0.1;
    float K1=1, fact1;
    fact1 = 1-(K1*dt);
    x2 = (unsigned long)(acclx_p*acclx_p);
    y2 = (unsigned long)(accly_p*accly_p);
    z2 = (unsigned long)(acclz_p*acclz_p);
    rollrate   = gyrox_p*PI/180;
	pitchrate  = gyroy_p*PI/180;
	yawrate    = gyroz_p*PI/180;
   
    result = sqrt(y2+z2);
	accl_pitch = -atan2(acclx_p,result);
    //result=acclx_p/result;
    //accl_pitch = atan(result)*180/PI;

    //pitch_angle = 0.90*(pitch_angle+ gyroy_p*dt) + 0.10*accl_pitch;
	
    pitch_angle= fact1*(pitch_angle)+ pitchrate*dt + K1*dt*accl_pitch;

    //result=sqrt(x2+z2);
	accl_roll = atan2(accly_p,acclz_p);
    //result=accly_p/result;
    //accl_roll = atan(result)*180/PI;

    roll_angle = fact1*(roll_angle)+ rollrate*dt + K1*dt*accl_roll;


}
void MPU6050::print_roll_pitch(){
    Serial.print(pitch_angle);
    Serial.print(",");
    Serial.print(roll_angle);
    Serial.print("\n");
}

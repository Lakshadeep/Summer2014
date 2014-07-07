#include "HMC5883L.h"

HMC5883L::HMC5883L()
{
    //ctor
}
void HMC5883L::initialise(){
   
    i2chelper.writeRegister(HMC5883L_ADDRESS,HMC5883L_RA_CONFIG_A, 0b01110000);   //15Hz

    i2chelper.writeRegister(HMC5883L_ADDRESS,HMC5883L_RA_CONFIG_B, 0b00100000);

    i2chelper.writeRegister(HMC5883L_ADDRESS,HMC5883L_MODE, 0b00000000);

}
void HMC5883L::read_data(){
  byte xMSB = i2chelper.readRegister(HMC5883L_ADDRESS, 0x03);
  byte xLSB = i2chelper.readRegister(HMC5883L_ADDRESS, 0x04);
  magnetox_raw = ((xMSB << 8) | xLSB);

  byte yMSB = i2chelper.readRegister(HMC5883L_ADDRESS, 0x07);
  byte yLSB = i2chelper.readRegister(HMC5883L_ADDRESS, 0x08);
  magnetoy_raw = ((yMSB << 8) | yLSB);

  byte zMSB = i2chelper.readRegister(HMC5883L_ADDRESS, 0x05);
  byte zLSB = i2chelper.readRegister(HMC5883L_ADDRESS, 0x06);
  magnetoz_raw = ((zMSB << 8) | zLSB);

  //i2chelper.writeRegister(HMC5883L_ADDRESS,HMC5883L_MODE, 0b00000001);
}
void HMC5883L::print_data(){

  Serial.print("Magnetometer X:");
  Serial.print(magnetox_raw);
  Serial.print("\t");

  Serial.print("Magnetometer Y:");
  Serial.print(magnetoy_raw);
  Serial.print("\t");

  Serial.print("Magnetometer Z:");
  Serial.print(magnetoz_raw);
  Serial.print("\t");


}
void HMC5883L::process_magneto_data(){
  //magnetox_p = (magnetox_raw/2048.0)*1.3*1000;           //magnetic fields in mill gauss
  //magnetoy_p = (magnetoy_raw/2048.0)*1.3*1000;
  //magnetoz_p = (magnetoz_raw/2048.0)*1.3*1000;

    magnetox_p = magnetox_raw*0.92;           //magnetic fields in mill gauss
    magnetoy_p = magnetoy_raw*0.92;
    magnetoz_p = magnetoz_raw*0.92;
}
void HMC5883L::calculate_heading(float pitch_degrees,float roll_degrees){
  float x_3d,y_3d;
  //x_3d = magnetox_p* cos(pitch_degrees) - magnetoy_p* sin(pitch_degrees);
  //y_3d = magnetox_p* sin(pitch_degrees) + magnetoy_p* cos(pitch_degrees);

  //x_3d = magnetox_p*cos(pitch_degrees) + magnetoy_p*sin(roll_degrees)*sin(pitch_degrees) + magnetoz_p*cos(roll_degrees)*sin(pitch_degrees);
  //y_3d = magnetoy_p*cos(roll_degrees) - magnetoz_p*sin(roll_degrees);

  heading = atan2(magnetoy_p,magnetox_p);

  // heading = atan2(y_3d,x_3d);
  //float declinationAngle = 0.0404;
  //heading += declinationAngle;

  // Correct for when signs are reversed.
  if(heading < 0)
    heading += 2*3.14;

  // Check for wrap due to addition of declination.
  if(heading > 2*3.14)
    heading -= 2*3.14;

  // Convert radians to degrees for readability.
  heading_degrees = heading * 180/3.14;



}
void HMC5883L::print_process_magneto_data(){
  Serial.print("Magnetometer X:");
  Serial.print(magnetox_p);
  Serial.print("\t");

  Serial.print("Magnetometer Y:");
  Serial.print(magnetoy_p);
  Serial.print("\t");

  Serial.print("Magnetometer Z:");
  Serial.print(magnetoz_p);
  Serial.print("\t");

  Serial.print("Heading:");
  Serial.print(heading_degrees);
  Serial.print("\n");
}

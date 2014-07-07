#include "i2c.h"

i2c::i2c()
{

}
void i2c::writeRegister(int deviceAddress, byte address, byte val)
{
    Wire.begin();
    Wire.beginTransmission(deviceAddress); // start transmission to device
    Wire.write(address);       // send register address
    Wire.write(val);         // send value to write
    Wire.endTransmission();     // end transmission
}
int i2c::readRegister(int deviceAddress, byte address){

    int v;
    Wire.begin();
    Wire.beginTransmission(deviceAddress);
   Wire.write(address); // register to read
    Wire.endTransmission();


    Wire.requestFrom(deviceAddress, 1); // read a byte

    int number_of_bytes =  Wire.available();
    if(number_of_bytes != 0){

    while(!Wire.available()) {
        // waiting

    }


    v = Wire.read();
    return v;
    }else{
      return 0;
    }

}

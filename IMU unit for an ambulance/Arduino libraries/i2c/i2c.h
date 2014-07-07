#ifndef I2C_H
#define I2C_H
#include "Wire.h"
#include "Arduino.h"
class i2c
{
    public:
        i2c();
        void writeRegister(int deviceAddress, byte address, byte val);
        int readRegister(int deviceAddress, byte address);
    protected:
    private:
        int deviceAddress;
        byte address,val;
};

#endif // I2C_H

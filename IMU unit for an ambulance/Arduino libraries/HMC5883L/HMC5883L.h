#ifndef HMC5883L_H
#define HMC5883L_H
#define HMC5883L_RA_CONFIG_A 0x00
#define HMC5883L_RA_CONFIG_B 0x01
#define HMC5883L_MODE 0x02
#define HMC5883L_ADDRESS 30
#include <i2c.h>
#include "Arduino.h"
#include <math.h>

class HMC5883L
{
    public:
        HMC5883L();
        void initialise();
        void read_data();
        void print_data();
        void process_magneto_data();
        void calculate_heading(float pitch_degrees,float roll_degrees);
        void print_process_magneto_data();
        i2c i2chelper;
        int magnetox_raw,magnetoy_raw,magnetoz_raw;
        float magnetox_p,magnetoy_p,magnetoz_p;
        float heading,heading_degrees;
    protected:
    private:
        float pitch_degrees,roll_degrees;
};

#endif // HMC5883L_H

#ifndef MPU6050_H
#define MPU6050_H
#define MPU6050_PWR_MGMT_1 0x6B
#define MPU6050_SMPRT_DIV 0x19
#define ACCL_SELF_TEST 0x1C
#define GYRO_SELF_TEST 0x1B
#define MPU6050_CONFIG 0x1A
#define WHO_AM_I 0x75
#define MPU6050_Address 104
#include <i2c.h>
#include "Arduino.h"

class MPU6050
{
    public:
        MPU6050();

        int initialise();
        i2c i2chelper;
        void read_accelerometer();
        void print_accelerometer();
        void process_accl_data();
        void print_process_accl_data();
        void read_gyroscope();
        void print_gyroscope();
        void process_gyro_data();
        void print_process_gyro_data();
        void send_data_to_matlab();
        void calculate_roll_pitch();
        void print_roll_pitch();
        int acclx_raw,accly_raw,acclz_raw;
        float acclx_p,accly_p,acclz_p;
        int gyrox_raw,gyroy_raw,gyroz_raw;
        float gyrox_p,gyroy_p,gyroz_p;
        float roll_angle,pitch_angle, dt;

    protected:
    private:
};

#endif // MPU6050_H

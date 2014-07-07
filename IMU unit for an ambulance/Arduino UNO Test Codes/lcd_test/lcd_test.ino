//program for testing lcd module
//PIN 2,3,4,5 - Data lines
//PIN 7 Register select 
//PIN 7 enable
//Note: Addresses mentioned in the official arduino liquidcrystal 
//library do not match with used lcd module
//so line 175 of liquidcrystal.cpp which is availabel in
//arduino installation folder should be changed to
//int row_offsets[] = { 0x00, 0x40, 0x10, 0x50 };
#include <LiquidCrystal.h>
LiquidCrystal lcd(7,6, 5, 4, 3, 2);
void setup() {
  // put your setup code here, to run once:
   lcd.begin(16,4);
    lcd.print("this is 0th row");

}

void loop() {
  // put your main code here, to run repeatedly:
   lcd.setCursor(0, 1);
   lcd.print("this is 1st row");
   lcd.setCursor(0,2);
   lcd.print("this is 2nd row");
   lcd.setCursor(0,3);
   lcd.print("this is 3rd row");

}

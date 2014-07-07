package com.lakshadeep.imu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;




import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Display_data extends Activity implements OnClickListener{

BluetoothAdapter mBluetoothAdapter;
BluetoothSocket mmSocket;
BluetoothDevice mmDevice;
OutputStream mmOutputStream;
InputStream mmInputStream;
Thread workerThread;
byte[] readBuffer;
int readBufferPosition;
int counter;
volatile boolean stopWorker;
String got_device_name,data_to_be_sent;
Boolean first_run = true;

Button stop_bt;
TextView rolltv,pitchtv,headtv,accxtv,accytv,accztv,lattv,lngtv;
MediaPlayer mp,mpx,mpy,mpz;

int accxp_t,accxn_t,accyp_t,accyn_t,acczp_t,acczn_t;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
	    Bundle gotbasket = getIntent().getExtras();
		got_device_name = gotbasket.getString("device_name");
		
		rolltv = (TextView)findViewById(R.id.roll);
		pitchtv = (TextView)findViewById(R.id.pitch);
		headtv = (TextView)findViewById(R.id.heading);
		lattv = (TextView)findViewById(R.id.lat);
		lngtv = (TextView)findViewById(R.id.lng);
		accxtv = (TextView)findViewById(R.id.acclx);
		accytv = (TextView)findViewById(R.id.accly);
		accztv = (TextView)findViewById(R.id.acclz);
		
		stop_bt = (Button)findViewById(R.id.stopbt);
		stop_bt.setOnClickListener(this);
		
		Db entry = new Db(Display_data.this);
		entry.open();
		accxp_t = entry.getaccxp(1);
		accxn_t = entry.getaccxn(1);
		accyp_t = entry.getaccyp(1);
		accyn_t = entry.getaccyn(1);
		acczp_t = entry.getacczp(1);
		acczn_t = entry.getacczn(1);
		
		
		boolean diditwork = true;
		findBT();
		
		try {
			openBT();
		} catch (IOException e) {
			
				diditwork = false;
				// TODO: handle exception
			}finally{
				if (diditwork) {
					
					boolean diditwork1 = true;
					

					try {
						start_conn();
					} catch (IOException e) {
					
						diditwork1 = false;
						// TODO: handle exception
					}finally{
						if (diditwork1) {
							
							
							boolean diditwork2 = true;	
						    try {
								beginListenForData();
							} catch (IOException e) {
								
								diditwork2 = false;
								// TODO: handle exception
							}finally{
								if (diditwork2) {
									
								}
								else{
									
									Bundle send_name = new Bundle();
									send_name.putString("device_name",got_device_name);
									
									Intent a  = new Intent(Display_data.this,Home.class);
									a.putExtras(send_name);
									startActivity(a);
									
									
								}
								
							}
							
							
							
							
							
						}
						else{
							
							
							Bundle send_name = new Bundle();
							send_name.putString("device_name",got_device_name);
							send_name.putInt("msg_type",2);
							
							Intent a  = new Intent(Display_data.this,Home.class);
							a.putExtras(send_name);
							startActivity(a);
							
							
						}
						
					}
					
					
				}
				else{
					
					
					 
					Bundle send_name = new Bundle();
					send_name.putString("device_name",got_device_name);
					send_name.putInt("msg_type",2);
					
					Intent a  = new Intent(Display_data.this,Home.class);
					a.putExtras(send_name);
					startActivity(a);
					
					
				}
				
			}
		
		
			
		
		
		
		
		
		
		
	}
	
	
void findBT() 
{
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if(mBluetoothAdapter == null)
    {
        //conntv.setText("No bluetooth adapter available");
    }

    if(!mBluetoothAdapter.isEnabled())
    {
        Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetooth, 0);
    }

    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    if(pairedDevices.size() > 0)
    {
        for(BluetoothDevice device : pairedDevices)
        {
            if(device.getName().equals(got_device_name)) 
            {
                mmDevice = device;
                break;
            }
        }
    }
    //tvReceived_data.setText("Bluetooth Device Found");

}
void openBT() throws IOException
{
    
	
	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);        
    
    boolean test = false;
    while(!mmSocket.isConnected()){
    try{
    mmSocket.connect();
    }catch(IOException e2){
    	 test = false;
    }finally{
    	if(test){
    		break;
    	}
    	
    }
    
    }
    
    mmOutputStream = mmSocket.getOutputStream();
    mmInputStream = mmSocket.getInputStream();
    
    
   Toast.makeText(Display_data.this, "Connected to device", Toast.LENGTH_LONG).show();
}



void beginListenForData() throws IOException
{
    final Handler handler = new Handler(); 
    final byte delimiter = 10; //This is the ASCII code for a newline character
    
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

   
    

    stopWorker = false;
    readBufferPosition = 0;
    readBuffer = new byte[1024];
    workerThread = new Thread(new Runnable()
    {
       
    	public void run()
        {                
        	

    	File direct = new File(Environment.getExternalStorageDirectory() + "/NIO_project");
      
    
    	    
    	if (!direct.exists()) {
    	 File projectDirectory = new File(Environment.getExternalStorageDirectory() + "/NIO_project");
          projectDirectory.mkdirs();
        }
        	
       
         File myFile = new File(new File(Environment.getExternalStorageDirectory() + "/NIO_project"), currentTimeStamp+".txt");
        // mp = MediaPlayer.create(getApplicationContext(), R.raw.beep); 
         
         mpx = MediaPlayer.create(getApplicationContext(), R.raw.button1); 
         mpy = MediaPlayer.create(getApplicationContext(), R.raw.button2); 
         mpz = MediaPlayer.create(getApplicationContext(), R.raw.button3); 
         
         
        	try {
       		myFile.createNewFile();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
       		e.printStackTrace();
         	}
              FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(myFile);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             final OutputStreamWriter myOutWriter = 
             new OutputStreamWriter(fOut);

        	
           while(!Thread.currentThread().isInterrupted() && !stopWorker)
           {
                try 
                {
                    int bytesAvailable = mmInputStream.available();                        
                    if(bytesAvailable > 0)
                    {
                        byte[] packetBytes = new byte[bytesAvailable];
                        mmInputStream.read(packetBytes);
                        for(int i=0;i<bytesAvailable;i++)
                        {
                            byte b = packetBytes[i];
                            if(b == delimiter)
                            {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                               final String verify = data.substring(0,3);
                               readBufferPosition = 0;
                               
                               
                                if(verify.equals("IMU")){
                                
                                
                                
                                
                                final String[] separated = data.split(",");
                                separated[1] = separated[1].trim(); // this will contain roll
                                separated[2] = separated[2].trim(); // this will contain pitch
                                separated[3] = separated[3].trim(); // this will contain heading
                                separated[4] = separated[4].trim(); // this will contain acc x
                                separated[5] = separated[5].trim(); // this will contain acc y
                                separated[6] = separated[6].trim(); // this will contain acc z
                                separated[7] = separated[7].trim(); // this will contain time
                                separated[8] = separated[8].trim(); // this will contain latitude
                                separated[9] = separated[9].trim(); // this will contain longitude
                                separated[10] = separated[10].trim(); // this will contain speed
                                separated[11] = separated[11].trim(); // this will contain coarse
                                separated[12] = separated[12].trim(); // this will contain gyro x
                                separated[13] = separated[13].trim(); // this will contain gyro y
                                separated[14] = separated[14].trim(); // this will contain gyro z
                                
                                
                                float accx = Float.parseFloat(separated[4].toString());
                                float accy = Float.parseFloat(separated[5].toString());
                                float accz = Float.parseFloat(separated[6].toString());
                                
                               
                               if(accx>accxp_t ||  accx<accxn_t){
                            	   if(!mpx.isPlaying() && !mpy.isPlaying() && !mpz.isPlaying()){
                                  	 mpx.start();
                                    }   
                               }else if(accy>accyp_t || accy<accyn_t){
                            	   if(!mpx.isPlaying() && !mpy.isPlaying() && !mpz.isPlaying()){
                                  	 mpy.start();
                                       } 
                            	   
                               }else if(accz>acczp_t  || accz<acczn_t ){
                            	   if(!mpx.isPlaying() && !mpy.isPlaying() && !mpz.isPlaying()){
                                  	 mpz.start();
                                    } 
                               }
                      
                                
                                
                                
                              /*  
                                if(accx>accxp_t || accx<accxn_t || accy>accyp_t || accy<accyn_t ||accz>acczp_t 
                            		   || accz<acczn_t ){
                           
                            	
                            	 
                            	 if(!mp.isPlaying()){
                            	 mp.start();
                                 }                           
                               
                               
                                }
                                */
                            
                               
                               
                                
                                try {
                              		myOutWriter.append(data);
                              		myOutWriter.append("\r\n");
                              		
                              	} catch (IOException e) {
                              		// TODO Auto-generated catch block
                              		e.printStackTrace();
                               	}
                                
                                
                                
                                handler.post(new Runnable()
                                {
                                    public void run()
                                    {
                                    	//rolltv.setText(verify);
                                 
                                    	rolltv.setText(separated[1]);
                                       pitchtv.setText(separated[2]);
                                       headtv.setText(separated[3]);
                                       accxtv.setText(separated[4]);
                                       accytv.setText(separated[5]);
                                       accztv.setText(separated[6]);
                                       lattv.setText(separated[8]);
                                       lngtv.setText(separated[9]);
                                       
                              
                                      
                                    }
                                });
                                
                                }else{
                                    
                                	 handler.post(new Runnable()
                                     {
                                         public void run()
                                         {
                                	
                                	rolltv.setText("NA");
                                    pitchtv.setText("NA");
                                    headtv.setText("NA");
                                    accxtv.setText("NA");
                                    accytv.setText("NA");
                                    accztv.setText("NA");
                                    lattv.setText("NA");
                                    lngtv.setText("NA");
                                	
                                   
                                      }
                                  });
                                    
                                }
                                
                               
                                
                                
                            }
                            else
                            {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                    }
                } 
                catch (IOException ex) 
                {
                    stopWorker = true;
                }
           }

       }
    });
    
    
    workerThread.start();
}



void start_conn() throws IOException
{
   String msg = "A";
    msg += "\n";
    mmOutputStream.write(msg.getBytes());
    //mmOutputStream.write('A');
   // conntv.setText("Data successfully Sent");
   // conntv.setTextColor(Color.GREEN);
}

void stop_conn() throws IOException
{
   String msg = "S";
    msg += "\n";
    mmOutputStream.write(msg.getBytes());
    //mmOutputStream.write('A');
   // conntv.setText("Data successfully Sent");
   // conntv.setTextColor(Color.GREEN);
}

void closeBT() throws IOException
{
    stopWorker = true;
    mmOutputStream.close();
    mmInputStream.close();
    mmSocket.close();
   // conntv.setText("Bluetooth Closed");
   // conntv.setTextColor(Color.RED);
}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
  switch (v.getId()) {
	case R.id.stopbt:
	boolean test = true;	
	try {
		stop_conn();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		
		test = false;
	}finally{
		
		if(test){
		try {
			closeBT();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Bundle basket = new Bundle();
		basket.putString("device_name",got_device_name);
		basket.putInt("msg_type",2);
		Intent a  = new Intent(Display_data.this,Home.class);
		a.putExtras(basket);
		startActivity(a);
		
	        
		}else{
			Dialog d = new Dialog(this);
			d.setTitle("Connection unsuccessful");
			TextView tv = new TextView(this);
			tv.setText("");
			d.setContentView(tv);
			d.show();
			
			
			
		}
		
	}
	
		break;
	
	default:
		break;
	}
	
}


@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	finish();
}


}

package com.lakshadeep.imu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Config extends Activity implements OnClickListener {
Integer accxp,accxn,accyp,accyn,acczp,acczn;
EditText accxp_et,accxn_et,accyp_et,accyn_et,acczp_et,acczn_et;
Button confirm_bt;
String got_device_name;
RadioGroup frequency_group;
RadioButton frequency_bt,default_freq;
String frequency;


BluetoothAdapter mBluetoothAdapter;
BluetoothSocket mmSocket;
BluetoothDevice mmDevice;
OutputStream mmOutputStream;
InputStream mmInputStream;
volatile boolean stopWorker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		accxp_et = (EditText)findViewById(R.id.accxp_et);
		accxn_et = (EditText)findViewById(R.id.accxn_et);
		accyp_et = (EditText)findViewById(R.id.accyp_et);
		accyn_et = (EditText)findViewById(R.id.accyn_et);
		acczp_et = (EditText)findViewById(R.id.acczp_et);
		acczn_et = (EditText)findViewById(R.id.acczn_et);
		
		confirm_bt = (Button)findViewById(R.id.configbt);
		confirm_bt.setOnClickListener(this);
		
		
		Bundle gotbasket = getIntent().getExtras();
		got_device_name = gotbasket.getString("device_name");
		
		
		findBT();
		
		boolean diditwork = true;
		
		 try {
			openBT();
		} catch (IOException e) {
			
			diditwork = false;
			// TODO: handle exception
		}finally{
			if (diditwork) {
				/*
				Dialog d = new Dialog(this);
				d.setTitle("Connection successful");
				TextView tv = new TextView(this);
				tv.setText("");
				d.setContentView(tv);
				d.show();
				*/
			}
			else{
				/*
				Dialog d = new Dialog(this);
				d.setTitle("Connection unsuccessful");
				TextView tv = new TextView(this);
				tv.setText("");
				d.setContentView(tv);
				d.show();
				 */
				 
				Bundle send_name = new Bundle();
				send_name.putString("device_name",got_device_name);
				send_name.putInt("msg_type",2);
				Intent a  = new Intent(Config.this,Home.class);
				a.putExtras(send_name);
				startActivity(a);
				
				
			}
			
		}
		
		
		frequency_group = (RadioGroup)findViewById(R.id.frequency_select);
		
		default_freq = (RadioButton)findViewById(R.id.radioButton3);
		default_freq.setChecked(true);
		
		
		
		
		
		
		Db entry = new Db(Config.this);
		entry.open();
		long row_count = entry.get_row_count();
		
		if(row_count == 0){
		entry.createEntry(5,-5,5,-5,12,-12);
		}
		
		
		
		accxp = entry.getaccxp(1);
		accxn = entry.getaccxn(1);
		accyp = entry.getaccyp(1);
		accyn = entry.getaccyn(1);
		acczp = entry.getacczp(1);
		acczn = entry.getacczn(1);
		
		entry.close();
		
		
		accxp_et.setText(""+accxp);
		accxn_et.setText(""+accxn);
		accyp_et.setText(""+accyp);
		accyn_et.setText(""+accyn);
		acczp_et.setText(""+acczp);
		acczn_et.setText(""+acczn);
		
		
		
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.configbt:
			Boolean diditwork = true;
			 accxp = Integer.valueOf(accxp_et.getText().toString());
			 accxn = Integer.valueOf(accxn_et.getText().toString());
			 accyp = Integer.valueOf(accyp_et.getText().toString());
			 accyn = Integer.valueOf(accyn_et.getText().toString());
			 acczp = Integer.valueOf(acczp_et.getText().toString());
			 acczn = Integer.valueOf(acczn_et.getText().toString());
			 
			 int selectedId = frequency_group.getCheckedRadioButtonId();
			 frequency_bt = (RadioButton) findViewById(selectedId);
			 frequency = frequency_bt.getText().toString();
			 
			
			boolean diditwork1 = true;
			 try {
				set_frequency();
			}catch (IOException e) {

				diditwork1 = false;
				// TODO: handle exception
			
			}finally{
				if (diditwork1) {
					
					 try {
							closeBT();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					 
					 try {
				      		
				          	
				          	
				          	Db entry = new Db(Config.this);
				          	entry.open();
				          	entry.updateEntry(accxp, accxn, accyp, accyn, acczp, acczn,1);
				          
				          	entry.close();
								
							} catch (Exception e) {
								diditwork = false;
								// TODO: handle exception
							}finally{
								if (diditwork) {
									Dialog d = new Dialog(this);
									d.setTitle("New threshold values successfully set");
									TextView tv = new TextView(this);
									tv.setText("");
									d.setContentView(tv);
									d.show();
								}
								else{
									Dialog d = new Dialog(this);
									d.setTitle("Error!Please try again");
									TextView tv = new TextView(this);
									tv.setText("");
									d.setContentView(tv);
									d.show();
									
									
								}
							}
				          
				          Bundle basket = new Bundle();
				  		  basket.putString("device_name",got_device_name);
				  		  basket.putInt("msg_type",2);
				  		
				  		  Intent a  = new Intent(Config.this,Home.class);
				  		  a.putExtras(basket);
				  		  startActivity(a);
					 
					 
				}
				else{
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
	
	
	
	void findBT()
	{
	    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    if(mBluetoothAdapter == null)
	    {
	        //msgbox.setText("No bluetooth adapter available");
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

	  
	}

	void set_frequency() throws IOException
	{
		String msg = "2";
		
		String freq_str = frequency;
		if(freq_str.equalsIgnoreCase("50Hz")){
			msg = "5";
		}else if(freq_str.equalsIgnoreCase("40Hz")){
			msg = "4";
		}else if(freq_str.equalsIgnoreCase("20Hz")){
			msg = "3";
		}else if(freq_str.equalsIgnoreCase("10Hz")){
			msg = "2";
		}else if(freq_str.equalsIgnoreCase("1Hz")){
			msg = "1";
		}
			
		
		
		
	    msg += "\n";
	    mmOutputStream.write(msg.getBytes());
	   
	}

	void closeBT() throws IOException
	{
	    stopWorker = true;
	    mmOutputStream.close();
	    mmInputStream.close();
	    mmSocket.close();
	    //msgbox.setText("Connection Closed");
	   // msgbox.setTextColor(Color.RED);
	}
	
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	
	

}

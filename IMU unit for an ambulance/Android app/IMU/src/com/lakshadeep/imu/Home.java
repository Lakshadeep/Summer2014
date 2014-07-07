package com.lakshadeep.imu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity implements android.view.View.OnClickListener {

	Button connectbt,selectbt,closebt,configbt,calibratebt,startbt;
	TextView msgbox;
	
	String name = "--------";
	String message = "--------";
	Integer msg_type = 0;
	
	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket mmSocket;
	BluetoothDevice mmDevice;
	OutputStream mmOutputStream;
	InputStream mmInputStream;
	volatile boolean stopWorker;
	Boolean connect_check = false;;
	Boolean select_check = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		connectbt = (Button)findViewById(R.id.connectbt);
		connectbt.setOnClickListener(this);
		startbt = (Button)findViewById(R.id.startbt);
		startbt.setOnClickListener(this);
		selectbt = (Button)findViewById(R.id.selectbt);
		selectbt.setOnClickListener(this);
		configbt = (Button)findViewById(R.id.configbt);
		configbt.setOnClickListener(this);
		calibratebt = (Button)findViewById(R.id.calibratebt);
		calibratebt.setOnClickListener(this);
		closebt = (Button)findViewById(R.id.closebt);
		closebt.setOnClickListener(this);
		msgbox = (TextView)findViewById(R.id.messagetv);
		
		
		Bundle getname = getIntent().getExtras();
		
		if(getname!=null){
		name = getname.getString("device_name");
		msg_type = getname.getInt("msg_type");
		}
		
		switch (msg_type){
		case 1:
			msgbox.setText("Selected device:"+name);
			findBT();
			
			break;
		case 2:	
			findBT();
			try {
				openBT();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msgbox.setText("Connected to device:"+name);
			
			break;
		case 3:
			msgbox.setText("Couldn't connect to device "+name);
			
		    break;
		case 4:
			
			findBT();
			try {
				openBT();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msgbox.setText("Device successfully configured");
			
			break;
		case 5:	
			findBT();
			try {
				openBT();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msgbox.setText("Connected to device:"+name);
			
            break;
		default:
			
			break;
		}
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.selectbt:
			Intent startActivity = new Intent("com.lakshadeep.imu.Select_device");
			startActivity(startActivity);
			break;
			
		case R.id.connectbt:
			if(select_check){
			boolean diditwork = true;
			try {
				openBT();
			}catch (IOException e) {

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
					
					//Intent a  = new Intent(Home.this,Display_data.class);
					//a.putExtras(send_name);
					//startActivity(a);
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
			}else{
				msgbox.setText("You are not connected to any device");
				msgbox.setTextColor(Color.RED);
				
			}
			break;
		
		case R.id.closebt:
			if(select_check && connect_check){
			boolean diditwork3 = true;
			try {
				closeBT();
			}catch (IOException e) {

				diditwork3 = false;
				// TODO: handle exception
			
			}finally{
				if (diditwork3) {
					
					
				}
				else{
					Dialog d = new Dialog(this);
					d.setTitle("Couldn't close the connection");
					TextView tv = new TextView(this);
					tv.setText("");
					d.setContentView(tv);
					d.show();
					
					
				}
			}
			}else{
				msgbox.setText("You are not connected to any device");
				msgbox.setTextColor(Color.RED);
				
			}
            break;
		case R.id.startbt:
			if(select_check && connect_check){
			Bundle send_name = new Bundle();
			send_name.putString("device_name",name);
			boolean diditwork1 = true;
			try {
				closeBT();
				
			}catch (IOException e) {

				diditwork1 = false;
				// TODO: handle exception
			
			}finally{
				if (diditwork1) {
					
					/*
					Dialog d = new Dialog(this);
					d.setTitle("Connection successful");
					TextView tv = new TextView(this);
					tv.setText("");
					d.setContentView(tv);
					d.show();
					
					*/
					
					Intent a  = new Intent(Home.this,Display_data.class);
					a.putExtras(send_name);
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
		
			
			}else{
				msgbox.setText("You are not connected to any device");
				msgbox.setTextColor(Color.RED);
				
			}
			
			break;
			
		case R.id.configbt:
			//Toast.makeText(this,""+connect_check+select_check,Toast.LENGTH_LONG).show();
			if(select_check && connect_check){
			boolean diditwork4 = true;
			try {
				config_device();
			}catch (IOException e) {

				diditwork4 = false;
				// TODO: handle exception
			
			}finally{
				if (diditwork4) {
					
					/*
					Dialog d = new Dialog(this);
					d.setTitle("Connection successful");
					TextView tv = new TextView(this);
					tv.setText("");
					d.setContentView(tv);
					d.show();
					
					*/
					try {
						closeBT();
						connect_check = true;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					Intent a2  = new Intent(Home.this,Config.class);
					Bundle send_name2 = new Bundle();
					send_name2.putString("device_name",name);
			        a2.putExtras(send_name2);
					startActivity(a2);
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
			
			}else{
				msgbox.setText("You are not connected to any device");
				msgbox.setTextColor(Color.RED);
				
			}
			break;
			
		case R.id.calibratebt:
			if(select_check && connect_check){
			try {
				resetdevice();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}else{
				msgbox.setText("You are not connected to any device");
				msgbox.setTextColor(Color.RED);
				
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
	        msgbox.setText("No bluetooth adapter available");
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
	            if(device.getName().equals(name)) 
	            {
	                mmDevice = device;
	                select_check = true;
	                break;
	            }
	        }
	    }
	    //tvReceived_data.setText("Bluetooth Device Found");

	}
	void openBT() throws IOException
	{
	    if(select_check){
		if(!connect_check){
		
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
	    		connect_check =true;
	    		break;
	    	}
	    	
	    }
	    
	    }
	    
	    if(mmSocket.isConnected()){
	    	connect_check =true;
	    	
	    }
	    
	
	 
	    mmOutputStream = mmSocket.getOutputStream();
	    mmInputStream = mmSocket.getInputStream();

	   msgbox.setText("Connection succcessfully opened");
	   msgbox.setTextColor(Color.GREEN);
	   
	    }
	    }
	   
	}

	void resetdevice() throws IOException
	{
		String msg = "C";
	    msg += "\n";
	    mmOutputStream.write(msg.getBytes());
	    msgbox.setText("Reset initialised...");
	    msgbox.setTextColor(Color.GREEN);
	}
	
	void config_device() throws IOException
	{
		String msg = "F";
	  //  msg += "\n";
	    mmOutputStream.write(msg.getBytes());
	    msgbox.setText("Reset initialised...");
	    msgbox.setTextColor(Color.GREEN);
	}

	void closeBT() throws IOException
	{
	    stopWorker = true;
	    mmOutputStream.close();
	    mmInputStream.close();
	    mmSocket.close();
	    msgbox.setText("Connection Closed");
	    msgbox.setTextColor(Color.RED);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}


}

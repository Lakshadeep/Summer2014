package com.lakshadeep.imu;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Select_device extends ListActivity{

	private static final int REQUEST_ENABLE_BT = 1;
	List<String> devices = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	//	setContentView(R.layout.activity_home);
		
		
		
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		
		
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			Toast.makeText(Select_device.this, "Your Device doesn't support the bluetooth", Toast.LENGTH_LONG).show();
		}
		else{
		
		//Toast.makeText(Home.this, "Your Device supports the bluetooth", Toast.LENGTH_LONG).show();
		
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		    Toast.makeText(Select_device.this, "Go back and Start again!", Toast.LENGTH_LONG).show();
			 
		}    
		    
		 //if(mBluetoothAdapter.isEnabled()){   
		    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			// If there are paired devices
			if (pairedDevices.size() > 0) {
			    // Loop through paired devices
			    for (BluetoothDevice device : pairedDevices) {
			        // Add the name and address to an array adapter to show in a ListView
			        devices.add(device.getName());
			           }
			   
			}
			
			
			
					
			
		//}
		
		
	
		
		}
		 setListAdapter(new ArrayAdapter<String>(Select_device.this, android.R.layout.simple_list_item_1, devices));
			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Bundle basket = new Bundle();
		basket.putString("device_name",devices.get(position));
		basket.putInt("msg_type",1);
		
		Intent a  = new Intent(Select_device.this,Home.class);
		a.putExtras(basket);
		startActivity(a);
		
		
	}
	
	/*
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	*/
		
	

}

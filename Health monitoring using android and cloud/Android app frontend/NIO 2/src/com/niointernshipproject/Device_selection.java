package com.niointernshipproject;

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

public class Device_selection extends ListActivity{

	private static final int REQUEST_ENABLE_BT = 1;
	List<String> devices = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	//	setContentView(R.layout.activity_home);
		
		
		
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		
		
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			Toast.makeText(Device_selection.this, "Your Device doesn't support the bluetooth", Toast.LENGTH_LONG).show();
		}
		else{
		
		//Toast.makeText(Home.this, "Your Device supports the bluetooth", Toast.LENGTH_LONG).show();
		
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		    Toast.makeText(Device_selection.this, "Go back and Start again!", Toast.LENGTH_LONG).show();
			 
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
		 setListAdapter(new ArrayAdapter<String>(Device_selection.this, android.R.layout.simple_list_item_1, devices));
			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Bundle infoBundle = getIntent().getExtras();
		String user_id = infoBundle.getString("user_id");
		String sensor_id = infoBundle.getString("sensor_id");
		
		//Toast.makeText(Device_selection.this,user_id+" "+sensor_id, Toast.LENGTH_LONG).show();
		
		
		
		Bundle basket = new Bundle();
		basket.putString("device_name",devices.get(position));
		basket.putString("user_id",user_id);
		basket.putString("sensor_id",sensor_id);
		Intent a  = new Intent(Device_selection.this,Submit.class);
		a.putExtras(basket);
		startActivity(a);
		
		
	}
	
	
	

	
		
	

}

package com.niointernshipproject;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Sensor_selection extends ListActivity{
	List<String> sensors = new ArrayList<String>();
	List<String> sensors_ids = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		sensors.add("Sensor 1"+"\nTemperature Sensor\n");
		sensors.add("Sensor 2"+ "\nBlood Pressure sensor\n");
		sensors.add("Sensor 3"+"\n\n");
		sensors.add("Sensor 4"+"\n\n");
		
		
		sensors_ids.add("sensor1");
		sensors_ids.add("sensor2");
		sensors_ids.add("sensor3");
		sensors_ids.add("sensor4");
		
		setListAdapter(new ArrayAdapter<String>(Sensor_selection.this, android.R.layout.simple_list_item_1,sensors));
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  //      R.layout.custom_list_view_sensors, R.id.heading,sensors);
	 //setListAdapter(adapter);
		
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Bundle user_idBundle = getIntent().getExtras();
		String user_id = user_idBundle.getString("user_id");
		
		//Toast.makeText(Sensor_selection.this,user_id, Toast.LENGTH_LONG).show();
		
		Bundle basket = new Bundle();
		basket.putString("user_id",user_id);
		basket.putString("sensor_id", sensors_ids.get(position));
		
		Intent a = new  Intent(Sensor_selection.this,Device_selection.class);
		a.putExtras(basket);
		startActivity(a);
		
		
	}
	
	
	

}

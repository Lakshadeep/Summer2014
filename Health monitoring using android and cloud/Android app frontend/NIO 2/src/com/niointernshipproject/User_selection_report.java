package com.niointernshipproject;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class User_selection_report extends Activity{

	List<String> users = new ArrayList<String>();
	List<String> user_idArray = new ArrayList<String>();
	List<String> user_name = new ArrayList<String>();
	ListView lv;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        
 
		
		
		
		String gotbread = null;
		//Bundle gotbasket = getIntent().getExtras();
		//gotbread = gotbasket.getString("key");
		
		
		if(gotbread!= null){
			long l = Long.parseLong(gotbread);
			Db_General_info name = new Db_General_info(this);
			name.open();
			String returnstring = name.getName(l);
			
			
		}else{
		
		
		Db_General_info info = new Db_General_info(this);
		info.open();
		users = info.getData();
		
		user_idArray = info.getID();
		
		user_name = info.getName_list();
		info.close();
		
		
		
	    }
		
		// setListAdapter(new ArrayAdapter<String>(User_selection.this, android.R.layout.simple_list_item_1,users));
		 //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		//	        R.layout.custom_list_view, R.id.heading, users);
		 //setListAdapter(adapter);
		
		context=this;
		 
        lv=(ListView) findViewById(R.id.listView);
		
		lv.setAdapter(new Custom_user_selection_report(this, users,user_idArray,user_name));
		
	}
	
	


}

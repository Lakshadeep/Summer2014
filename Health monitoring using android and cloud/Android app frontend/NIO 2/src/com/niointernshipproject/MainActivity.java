package com.niointernshipproject;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity{
	List<String> form_items = new ArrayList<String>();
	String form_links[] = {"form_selection_class","User_selection","User_selection_report"
			,"User_selection_history","User_selection_edit",""};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		form_items.add("\nRegister new patient"+"\n");
		form_items.add("\nUpdate Readings"+ "\n");
		form_items.add("\nView Patient Report"+"\n");
		form_items.add("\nView Patient history"+"\n");
		form_items.add("\nEdit patient info"+"\n");
		form_items.add("\nSync"+"\n");
		
		
        
		//form_links.add("Basic_details_class");
		//form_links.add("Physical_exam_class");
		//form_links.add("General_health_class");
		//form_links.add("Practitioners_details_class");
		
		setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,form_items));
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  //      R.layout.activity_main, R.id.heading,form_items,R.id.imageView1,image_list);
	 //setListAdapter(adapter);
		
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		
		
		try {
			Class ourclass = Class.forName("com.niointernshipproject."+form_links[position]);
			Intent a = new  Intent(MainActivity.this,ourclass);
			
			startActivity(a);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	

}
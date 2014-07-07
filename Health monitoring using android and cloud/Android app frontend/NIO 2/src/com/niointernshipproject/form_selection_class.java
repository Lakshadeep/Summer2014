package com.niointernshipproject;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class form_selection_class extends ListActivity{
	List<String> form_items = new ArrayList<String>();
	String form_links[] = {"Basic_details_class","Physical_exam_class","General_health_class","Practitioners_details_class"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		form_items.add("\nBasic details"+"\n");
		form_items.add("\nPhysical examination"+ "\n");
		form_items.add("\nGeneral health"+"\n");
		form_items.add("\nPractitioners details"+"\n");
		
        
		//form_links.add("Basic_details_class");
		//form_links.add("Physical_exam_class");
		//form_links.add("General_health_class");
		//form_links.add("Practitioners_details_class");
		
		setListAdapter(new ArrayAdapter<String>(form_selection_class.this, android.R.layout.simple_list_item_1,form_items));
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
			Intent a = new  Intent(form_selection_class.this,ourclass);
			
			startActivity(a);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	

}
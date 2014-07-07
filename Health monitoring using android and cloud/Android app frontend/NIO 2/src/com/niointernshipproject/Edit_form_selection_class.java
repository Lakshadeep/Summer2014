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

public class Edit_form_selection_class extends ListActivity{
	List<String> form_items = new ArrayList<String>();
	String form_links[] = {"Edit_basic_details_class","Edit_physical_exam_class","Edit_general_health_class","Edit_practitioners_details_class"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		form_items.add("\nEdit Basic details"+"\n");
		form_items.add("\nEdit Physical examination"+ "\n");
		form_items.add("\nEdit General health"+"\n");
		form_items.add("\nEdit Practitioners details"+"\n");
		
	
		
		
        
		//form_links.add("Basic_details_class");
		//form_links.add("Physical_exam_class");
		//form_links.add("General_health_class");
		//form_links.add("Practitioners_details_class");
		
		setListAdapter(new ArrayAdapter<String>(Edit_form_selection_class.this, android.R.layout.simple_list_item_1,form_items));
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  //      R.layout.activity_main, R.id.heading,form_items,R.id.imageView1,image_list);
	 //setListAdapter(adapter);
		
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		
		Bundle user_idBundle = getIntent().getExtras();
		String user_id = user_idBundle.getString("user_id");
		
		Bundle send_user_id = new Bundle();
		send_user_id.putCharSequence("User_id",user_id);
		
		
		try {
			Class ourclass = Class.forName("com.niointernshipproject."+form_links[position]);
			Intent a = new  Intent(Edit_form_selection_class.this,ourclass);
			a.putExtras(send_user_id);
			startActivity(a);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	

}
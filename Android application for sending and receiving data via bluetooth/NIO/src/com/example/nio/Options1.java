package com.example.nio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Options1 extends Activity implements OnClickListener {
    
	Button new_userButton,update_userButton,record_Button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		//button declarations
		new_userButton = (Button)findViewById(R.id.add_new_user);
		new_userButton.setOnClickListener(this);
		new_userButton.setBackgroundColor(Color.BLACK);
		new_userButton.setTextColor(Color.WHITE);
		
		update_userButton = (Button)findViewById(R.id.update_data);
		update_userButton.setOnClickListener(this);
		update_userButton.setBackgroundColor(Color.BLACK);
		update_userButton.setTextColor(Color.WHITE);
		
		record_Button = (Button)findViewById(R.id.check_the_record);
		record_Button.setOnClickListener(this);
		record_Button.setBackgroundColor(Color.BLACK);
		record_Button.setTextColor(Color.WHITE);
		
		
	}

	@Override
	public void onClick(View v) {
		Bundle basket = new Bundle();

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_new_user:
			basket.putString("id","");
			Intent a1 = new Intent(Options1.this,   .class);
			startActivity(a1);
			break;
		case R.id.update_data:
			
			basket.putString("id","");
			Intent a2 = new Intent(Options1.this,   .class);
			startActivity(a2);
			break;
			
		case R.id.check_the_record:
			
			basket.putString("id","");
			Intent a3 = new Intent(Options1.this,   .class);
			startActivity(a3);
			break;

		default:
			break;
		}
		
		
	}

}

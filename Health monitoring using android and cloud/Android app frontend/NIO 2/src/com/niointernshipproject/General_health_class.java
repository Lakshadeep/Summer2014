package com.niointernshipproject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class General_health_class extends Activity implements OnClickListener{
    EditText impressionEditText,natureEditText,if_yesEditText,commentsEditText;
    RadioGroup yes;
    RadioButton yes_no;
    Button submitButton,syncButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general_health_layout);
		
		impressionEditText = (EditText)findViewById(R.id.impression_et);
		//String impression = impressionEditText.getText().toString();
		
		natureEditText = (EditText)findViewById(R.id.nature_of_problems_et);
		//String nature = natureEditText.getText().toString();
		
		if_yesEditText = (EditText)findViewById(R.id.if_yes_et);
		//String if_yes = if_yesEditText.toString();
		
		commentsEditText = (EditText)findViewById(R.id.comments_et);
		//String comments = commentsEditText.getText().toString();
		
		yes = (RadioGroup)findViewById(R.id.yes_group);
		int selectedId = yes.getCheckedRadioButtonId();
        yes_no = (RadioButton) findViewById(selectedId);
        //String yesString = yes_no.getText().toString();
        
        submitButton = (Button)findViewById(R.id.general_health_sync);
        submitButton.setOnClickListener(this);
        
		syncButton = (Button)findViewById(R.id.general_health_submit);
		syncButton.setOnClickListener(this);
				
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.general_health_sync:
			
			break;
        
		case R.id.general_health_submit:
			int selectedId = yes.getCheckedRadioButtonId();
	        yes_no = (RadioButton) findViewById(selectedId);
	        Boolean yesno;
	        Boolean diditwork = true;
	        
	        String impression = impressionEditText.getText().toString();
	        String nature = natureEditText.getText().toString();
	        String if_yes = if_yesEditText.getText().toString();
	        String comment = commentsEditText.getText().toString();
	        
	        String yes_noString = yes_no.getText().toString();
	        if(yes_noString.equals("Yes")){
	        	yesno = true;
	        }
	        else{
	        	yesno = false;
	        }
	        
            try {
        		
            	
            	
            	Db_General_Report entry = new Db_General_Report(General_health_class.this);
            	entry.open();
            	entry.createEntry(impression,nature,yesno,if_yes,comment,true);
            	
            	entry.close();
				
			} catch (Exception e) {
				diditwork = false;
				// TODO: handle exception
			}finally{
				if (diditwork) {
					Dialog d = new Dialog(this);
					d.setTitle("User info successfully added");
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
	        
	        
			
			
			break;
		default:
			break;
		}
	}

}
package com.niointernshipproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class Practitioners_details_class extends Activity implements OnClickListener{

	EditText pnameEditText,pphoneEditText,paddressEditText,pdocEditText;
	Button submitButton,syncButton;
	Calendar myCalendar;
	DatePickerDialog.OnDateSetListener date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.practitioner_details_layout);
		
		pnameEditText =(EditText)findViewById(R.id.p_name_et);
		//String  pname = pnameEditText.getText().toString();
		
		pphoneEditText = (EditText)findViewById(R.id.p_phone_et);
		//String pphone = pphoneEditText.getText().toString();
		
		paddressEditText = (EditText)findViewById(R.id.p_address_et);
		//String paddress = paddressEditText.getText().toString();
		
		submitButton = (Button)findViewById(R.id.practitioner_details_submit);
		submitButton.setOnClickListener(this);
		
		syncButton = (Button)findViewById(R.id.practitioner_details_sync);
		syncButton.setOnClickListener(this);
		
		
		pdocEditText = (EditText)findViewById(R.id.p_doc_et);
		myCalendar = Calendar.getInstance();

		 date = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateLabel();
		    }

		};
		
		
		pdocEditText.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            new DatePickerDialog(Practitioners_details_class.this, date, myCalendar
	                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	        }
	    });
		
		
		
		
				
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.practitioner_details_submit:
			String  pname = pnameEditText.getText().toString();
			String pphone = pphoneEditText.getText().toString();
			String paddress = paddressEditText.getText().toString();
			String doc = pdocEditText.getText().toString();
			Boolean diditwork = true;
			
         try {
        		
            	
            	
            	Db_Practitioner_details entry = new Db_Practitioner_details(Practitioners_details_class.this);
            	entry.open();
            	entry.createEntry(pname,pphone,paddress,doc,true);
            	
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
			
		case R.id.practitioner_details_sync:
			
            break;
		default:
			break;
		}
		
	}
	
	private void updateLabel() {

	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

	    pdocEditText.setText(sdf.format(myCalendar.getTime()));
	    }

}
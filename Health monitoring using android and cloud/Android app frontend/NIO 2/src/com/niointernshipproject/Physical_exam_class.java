package com.niointernshipproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Physical_exam_class extends Activity implements OnClickListener{
    EditText tempEditText,sysbpEditText,diabpEditText,sugarEditText,ecgEditText,pulseEditText;
    Button submitButton,syncButton;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.physical_exam_layout);
		
		tempEditText = (EditText)findViewById(R.id.temp_et);
		//String stemp = tempEditText.getText().toString();
		//Integer temp = Integer.valueOf(stemp);
		
		sysbpEditText = (EditText)findViewById(R.id.systollic_pressure_et);
		//String ssysbp = sysbpEditText.getText().toString();
		//Integer sysbp = Integer.valueOf(ssysbp);
		
		diabpEditText = (EditText)findViewById(R.id.diastollic_pressure_et);
		//String sdiabp = diabpEditText.getText().toString();
		//Integer diabp = Integer.valueOf(sdiabp);
		
		sugarEditText = (EditText)findViewById(R.id.sugar_et);
		//String ssugar = sugarEditText.getText().toString();
		//Integer sugar = Integer.valueOf(ssugar);
		
		ecgEditText = (EditText)findViewById(R.id.ecg_et);
		//String secg = ecgEditText.getText().toString();
		//Integer ecg = Integer.valueOf(secg);
		
		pulseEditText = (EditText)findViewById(R.id.pulse_et);
		//String spulse = pulseEditText.getText().toString();
		//Integer pulse = Integer.valueOf(spulse);
		
		submitButton = (Button)findViewById(R.id.physical_exam_submit);
		submitButton.setOnClickListener(this);
		
		syncButton = (Button)findViewById(R.id.physical_exam_sync);
		syncButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.physical_exam_submit:
			
			break;
			
		case R.id.physical_exam_sync:	
            
			break;
		default:
			break;
		}
	}

}
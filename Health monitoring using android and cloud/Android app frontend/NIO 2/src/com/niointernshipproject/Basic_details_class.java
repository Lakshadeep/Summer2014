package com.niointernshipproject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Basic_details_class extends Activity implements OnClickListener{

	Button take_photoButton,submitButton,syncButton;
	ImageView photoImageView;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	EditText dobEditText,nameEditText,addEditText,aadharEditText;
	Calendar myCalendar;
	DatePickerDialog.OnDateSetListener date;
	RadioGroup gender_Group;
	RadioButton gender_Button;
	String name,address,aadhar,gender,dob;
	Bitmap imageBitmap;
	
	private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    
    // url to create new product
 	private static String url_new_user_basic_info = "http://nio-internship-project.appspot.com/create_new_user/";

 	// JSON Node names
 	private static final String TAG_SUCCESS = "success";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_details_layout);
		
		take_photoButton = (Button)findViewById(R.id.take_photo);
		take_photoButton.setOnClickListener(this);
		
		submitButton = (Button)findViewById(R.id.basic_details_submit);
		submitButton.setOnClickListener(this);
		
		syncButton = (Button)findViewById(R.id.basic_details_sync);
		syncButton.setOnClickListener(this);
		
		photoImageView = (ImageView)findViewById(R.id.photo_view);
		
		dobEditText = (EditText)findViewById(R.id.dob_et);
		
		
		//datepicker start
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
		
		
		dobEditText.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            new DatePickerDialog(Basic_details_class.this, date, myCalendar
	                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	        }
	    });

	    //datepicker end
		
		nameEditText = (EditText)findViewById(R.id.name_et);
		//String name = nameEditText.getText().toString();
		
		addEditText = (EditText)findViewById(R.id.address_et);
		//String address = addEditText.getText().toString();
		
		aadharEditText = (EditText)findViewById(R.id.aadhar_et);
 		//String aadhar = aadharEditText.getText().toString();
		
		gender_Group = (RadioGroup)findViewById(R.id.yes_group);
		
		//int selectedId = gender_Group.getCheckedRadioButtonId();
        //gender_Button = (RadioButton) findViewById(selectedId);
        //String gender = gender_Button.getText().toString();
        
        //String dob = dobEditText.getText().toString();
        
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.take_photo:
			dispatchTakePictureIntent();
			break;
			
		case R.id.basic_details_submit:
			Boolean diditwork = true;
			 name = nameEditText.getText().toString();
			 address = addEditText.getText().toString();
			 aadhar = aadharEditText.getText().toString();
			
			 int selectedId = gender_Group.getCheckedRadioButtonId();
			 gender_Button = (RadioButton) findViewById(selectedId);
			 gender = gender_Button.getText().toString();
			 dob = dobEditText.getText().toString();
			//Toast.makeText(this, name + " " + address+ " "+aadhar+ " "+gender+" "+dob,
		      //      Toast.LENGTH_LONG).show();
            try {
        		
            	
            	
            	Db_General_info entry = new Db_General_info(Basic_details_class.this);
            	entry.open();
            	entry.createEntry(name,dob,address,aadhar,gender,true);
            	Integer id_photo = entry.getID_for_sync();
            	createDirectoryAndSaveFile(imageBitmap,"img"+id_photo) ;
            	entry.close();
				
			} catch (Exception e) {
				diditwork = false;
				// TODO: handle exception
			}finally{
				if (diditwork) {
					Dialog d = new Dialog(this);
					d.setTitle("User successfully added");
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
        	
		case R.id.basic_details_sync:
			new CreateNewProduct().execute();
             break;
		default:
			break;
		}
		
		
	}
	
	
	private void updateLabel() {

	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

	    dobEditText.setText(sdf.format(myCalendar.getTime()));
	    }
	
	
	//function for taking photo
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	
	//function for viewing thumbnail
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        imageBitmap = (Bitmap) extras.get("data");
	        photoImageView.setImageBitmap(imageBitmap);
	    }
	}
	
	
	
	private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

	    File direct = new File(Environment.getExternalStorageDirectory() + "/NIOApp");

	    if (!direct.exists()) {
	        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/NIOApp");
	        wallpaperDirectory.mkdirs();
	      }

	        File file = new File(new File(Environment.getExternalStorageDirectory() + "/NIOApp"), fileName);
	        if (file.exists())
	            file.delete();
	        try {
	            FileOutputStream out = new FileOutputStream(file);
	            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
	            out.flush();
	            out.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}
	
	
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Basic_details_class.this);
			pDialog.setMessage("Adding new user..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			//String name = nameEditText.getText().toString();
			//String address = addEditText.getText().toString();
			//String aadhar = aadharEditText.getText().toString();
			
		//	int selectedId = gender_Group.getCheckedRadioButtonId();
			//gender_Button = (RadioButton) findViewById(selectedId);
			//String gender = gender_Button.getText().toString();
			//String dob = dobEditText.getText().toString();
			Boolean diditwork = true;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("address", address));
			params.add(new BasicNameValuePair("aadhar", aadhar));
			params.add(new BasicNameValuePair("gender", gender));
			params.add(new BasicNameValuePair("dob", dob));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_new_user_basic_info,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					int online_id = json.getInt("online_id");
					
					
					
					try {
		        		
		            	
		            	
		            	Db_General_info entry = new Db_General_info(Basic_details_class.this);
		            	entry.open();
		            	int offline_id = entry.getID_for_sync();
		            	entry.update_online_id(offline_id, online_id);
		           
		           
		            	entry.close();
						
					} catch (Exception e) {
						diditwork = false;
						// TODO: handle exception
					}
					
					
					
					Intent i = new Intent(Basic_details_class.this,form_selection_class.class);
					startActivity(i);
					// closing this screen
					finish();
				} else {
					// failed to create product
					Intent i = new Intent(getApplicationContext(),Basic_details_class.class);
					startActivity(i);
				}
			} catch (JSONException e) {
				//e.printStackTrace();
				diditwork = false;
				
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
	
	
	
	
	

}

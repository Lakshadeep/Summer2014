package com.niointernshipproject;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Submit extends Activity implements OnClickListener,LocationListener{
TextView tvReceived_data,tv_connection_msg,tv_location,tv_accept;
BluetoothAdapter mBluetoothAdapter;
BluetoothSocket mmSocket;
BluetoothDevice mmDevice;
OutputStream mmOutputStream;
InputStream mmInputStream;
Thread workerThread;
byte[] readBuffer;
int readBufferPosition;
int counter;
volatile boolean stopWorker;
String got_device_name,data_to_be_sent;
Button open_bt,accept_bt,close_bt;
EditText send_data;

private LocationManager locationManager;
private String provider;
double lat,lng;
String public_data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive);
		
		
		//location 
	    	LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
	        boolean enabledGPS = service
	                .isProviderEnabled(LocationManager.GPS_PROVIDER);
	        boolean enabledWiFi = service
	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	        
	        if (!enabledGPS) {
	            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
	            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	            startActivity(intent);
	        }

	        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        
	        Criteria criteria = new Criteria();
	        provider = locationManager.getBestProvider(criteria, false);
	        Location location = locationManager.getLastKnownLocation(provider);

	        // Initialize the location fields
	        if (location != null) {
	            Toast.makeText(this, "Selected Provider " + provider,
	                    Toast.LENGTH_SHORT).show();
	            onLocationChanged(location);
	        } else {

	            //do something
	        }
	        
	        //location end
		
		
		
		
		Bundle gotbasket = getIntent().getExtras();
		got_device_name = gotbasket.getString("device_name");
		
		tvReceived_data = (TextView)findViewById(R.id.received_value);
		tvReceived_data.setText("Yet to receive any data...");
		tvReceived_data.setTextSize(28);
		tvReceived_data.setTextColor(Color.BLUE);
		
		tv_connection_msg = (TextView)findViewById(R.id.connection_msgs);
		tv_connection_msg.setText("Connected to "+got_device_name);
		tv_connection_msg.setTextColor(Color.GREEN);
		
		tv_location = (TextView)findViewById(R.id.location_txt);
		tv_location.setText("Latitude:"+lat+", Longitude:"+lng);
		
		tv_accept = (TextView)findViewById(R.id.textView5);
		
		
	
		findBT();
		
		open_bt = (Button)findViewById(R.id.open_bt);
		open_bt.setOnClickListener(this);
		open_bt.setBackgroundColor(Color.BLACK);
		open_bt.setTextColor(Color.WHITE);
		
		accept_bt = (Button)findViewById(R.id.accept_value);
		accept_bt.setOnClickListener(this);
		accept_bt.setBackgroundColor(Color.BLACK);
		accept_bt.setTextColor(Color.WHITE);
		
		
		close_bt = (Button)findViewById(R.id.close_connection);
		close_bt.setOnClickListener(this);
		close_bt.setBackgroundColor(Color.BLACK);
		close_bt.setTextColor(Color.WHITE);
		
		
	//	send_data = (EditText)findViewById(R.id.name);
	//	data_to_be_sent = send_data.getText().toString();
		
      
		
		
	}
	
	
void findBT()
{
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if(mBluetoothAdapter == null)
    {
        tvReceived_data.setText("No bluetooth adapter available");
    }

    if(!mBluetoothAdapter.isEnabled())
    {
        Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetooth, 0);
    }

    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    if(pairedDevices.size() > 0)
    {
        for(BluetoothDevice device : pairedDevices)
        {
            if(device.getName().equals(got_device_name)) 
            {
                mmDevice = device;
                break;
            }
        }
    }
    //tvReceived_data.setText("Bluetooth Device Found");

}
void openBT() throws IOException
{
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);        
    mmSocket.connect();
    mmOutputStream = mmSocket.getOutputStream();
    mmInputStream = mmSocket.getInputStream();

    //beginListenForData();

   tv_connection_msg.setText("Connection succcessfully opened");
   tv_connection_msg.setTextColor(Color.GREEN);
    Toast.makeText(Submit.this, "Device connection opened!", Toast.LENGTH_LONG).show();
}



void beginListenForData()
{
    final Handler handler = new Handler(); 
    final byte delimiter = 10; //This is the ASCII code for a newline character

    stopWorker = false;
    readBufferPosition = 0;
    readBuffer = new byte[1024];
    workerThread = new Thread(new Runnable()
    {
        public void run()
        {                
           while(!Thread.currentThread().isInterrupted() && !stopWorker)
           {
                try 
                {
                    int bytesAvailable = mmInputStream.available();                        
                    if(bytesAvailable > 0)
                    {
                        byte[] packetBytes = new byte[bytesAvailable];
                        mmInputStream.read(packetBytes);
                        for(int i=0;i<bytesAvailable;i++)
                        {
                            byte b = packetBytes[i];
                            if(b == delimiter)
                            {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                                readBufferPosition = 0;

                                handler.post(new Runnable()
                                {
                                    public void run()
                                    {
                                        tvReceived_data.setText(data);
                                        public_data = data;
                                    }
                                });
                            }
                            else
                            {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                    }
                } 
                catch (IOException ex) 
                {
                    stopWorker = true;
                }
           }
        }
    });

    workerThread.start();
}

void sendData() throws IOException
{
    String msg = send_data.getText().toString();
    msg += "\n";
    mmOutputStream.write(msg.getBytes());
    //mmOutputStream.write('A');
    tv_connection_msg.setText("Data successfully Sent");
    tv_connection_msg.setTextColor(Color.GREEN);
}

void closeBT() throws IOException
{
    stopWorker = true;
    mmOutputStream.close();
    mmInputStream.close();
    mmSocket.close();
    tv_connection_msg.setText("Bluetooth Closed");
    tv_connection_msg.setTextColor(Color.RED);
}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.open_bt:
		
		try {
			findBT();
			openBT();
			//tvReceived_data.setText("success");
			beginListenForData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tv_connection_msg.setText("Error:Couldn't open the port");
			tv_connection_msg.setTextColor(Color.RED);
			
		}
		break;
	case R.id.accept_value:
		try {
			sendData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tv_connection_msg.setText("Value successfully accepted");
			tv_connection_msg.setTextColor(Color.GREEN);
			tv_accept.setText(public_data);
			
		}
		break;
	case R.id.close_connection:
		try {
			closeBT();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	default:
		break;
	}
	
}


@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	 lat =  location.getLatitude();
     lng = location.getLongitude();
    Toast.makeText(this, "Location " + lat+","+lng,
            Toast.LENGTH_LONG).show();
   
}


@Override
public void onProviderDisabled(String arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void onProviderEnabled(String arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	// TODO Auto-generated method stub
	
}


}

package com.niointernshipproject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.DateTimeKeyListener;

public class Db_Practitioner_details {
	public static final String KEY_ROWID = "P_id";
	public static final String KEY_NAME = "P_name";
	public static final String KEY_PHONE = "P_phone";
	public static final String KEY_ADDRESS = "P_address";
	public static final String KEY_CHECKUP = "Check_up_date";
	public static final String KEY_SYNC_STATUS= "Sync_status";
	
	
	
	
	private static final String DATABASE_NAME = "NIO_internship";
	private static final String DATABASE_TABLE = "General_info";
	private static final int DATABASE_VERSION = 1;
	
	private   DbHelper ourHelper;
	private final Context ourContext;
	private   SQLiteDatabase ourDatabase;
	 
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null , DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE  " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
			KEY_NAME + " TEXT ," + KEY_PHONE + " TEXT ," + KEY_ADDRESS +
			" TEXT NOT NULL," + KEY_CHECKUP + " TEXT," + KEY_SYNC_STATUS + 
			" BOOLEAN DEFAULT FALSE);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
			onCreate(db);
			
		}
	}
		public Db_Practitioner_details(Context c) throws SQLException{
			ourContext = c;
		}
		
		public Db_Practitioner_details open(){
			ourHelper = new DbHelper(ourContext);
			ourDatabase = ourHelper.getWritableDatabase();
			return this;
		}
		
		public void close(){
			ourHelper.close();
		}

		public long createEntry( String name, String phone ,String address,String doc,Boolean sync_status) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_NAME, name);
			cv.put(KEY_PHONE,phone);
			cv.put(KEY_ADDRESS, address);
			cv.put(KEY_SYNC_STATUS, sync_status);
			
			cv.put(KEY_CHECKUP,doc);
			
			return ourDatabase.insert(DATABASE_TABLE, null,cv);
			
		}     
		
		
		public long updateEntry( String name, String phone ,String address,String doc,Boolean sync_status,
				Integer id) {
			
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_NAME, name);
			cv.put(KEY_PHONE,phone);
			cv.put(KEY_ADDRESS, address);
			cv.put(KEY_SYNC_STATUS, sync_status);
			
			cv.put(KEY_CHECKUP,doc);
			return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID+"="+id,null);
			
		}    

		
		
		

		public String getName(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_NAME};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				return name;
			}
			
			
			return null;
		}
		
		
		public String getPhone(long l) {
			// TODO Auto-generated method stub
						String[] columns = new String[]{KEY_PHONE};
						Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
						
						if(c!=null){
							c.moveToFirst();
							String name = c.getString(0);
							return name;
						}
						
						
						return null;
		}
		
		public String getAddress(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ADDRESS};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				return name;
			}
			
			
			return null;
		}
		
		public String getDOC(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_CHECKUP};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				return name;
			}
			
			
			return null;
		}
		
				

}

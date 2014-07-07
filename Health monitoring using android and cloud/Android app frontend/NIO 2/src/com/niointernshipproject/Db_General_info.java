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

public class Db_General_info {
	public static final String KEY_ROWID = "Id";
	public static final String KEY_NAME = "Name";
	public static final String KEY_DOB = "DOB";
	public static final String KEY_ADDRESS = "Address";
	public static final String KEY_AADHAR_NO = "Aadhar_no";
	public static final String KEY_SEX= "Sex";
	public static final String KEY_TIME= "Time";
	public static final String KEY_SYNC_STATUS= "Sync_status";
	public static final String KEY_ONLINE_ID= "Online_id";
	
	
	
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
			KEY_NAME + " TEXT NOT NULL," + KEY_DOB + " TEXT NOT NULL," + KEY_ADDRESS +
			" TEXT NOT NULL," + KEY_AADHAR_NO + " TEXT," + KEY_SEX + " TEXT NOT NULL," 
			+ KEY_TIME+ " TIMESTAMP,"+ KEY_SYNC_STATUS + " BOOLEAN DEFAULT FALSE,"+KEY_ONLINE_ID+ " INTEGER UNSIGNED);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
			onCreate(db);
			
		}
	}
		public Db_General_info(Context c) throws SQLException{
			ourContext = c;
		}
		
		public Db_General_info open(){
			ourHelper = new DbHelper(ourContext);
			ourDatabase = ourHelper.getWritableDatabase();
			return this;
		}
		
		public void close(){
			ourHelper.close();
		}

		public long createEntry( String name, String dob ,String address,String aadhar_no,String 
				sex,Boolean sync_status) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_NAME, name);
			cv.put(KEY_DOB,dob);
			cv.put(KEY_ADDRESS, address);
			cv.put(KEY_AADHAR_NO,aadhar_no);
			cv.put(KEY_SEX,sex);
			cv.put(KEY_SYNC_STATUS, sync_status);
			
			long yourmilliseconds = java.lang.System.currentTimeMillis();
	        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

	        Date resultdate = new Date(yourmilliseconds);
	        //System.out.println(sdf.format(resultdate));
			String final_date = sdf.format(resultdate);
			
			cv.put(KEY_TIME,final_date);
			
			
			return ourDatabase.insert(DATABASE_TABLE, null,cv);
			//ourDatabase.execSQL("Insert into " + DATABASE_TABLE + " (" + KEY_NAME + "," + KEY_HOTNESS + ")"+ " " +
				//	"values  ("+ name +"," + hotness +");");
			
			
		}     
		
		
		public long updateEntry( String name, String dob ,String address,String aadhar_no,String 
				sex,Boolean sync_status,Integer id) {
			
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_NAME, name);
			cv.put(KEY_DOB,dob);
			cv.put(KEY_ADDRESS, address);
			cv.put(KEY_AADHAR_NO,aadhar_no);
			cv.put(KEY_SEX,sex);
			cv.put(KEY_SYNC_STATUS, sync_status);
			
			long yourmilliseconds = java.lang.System.currentTimeMillis();
	        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

	        Date resultdate = new Date(yourmilliseconds);
	        //System.out.println(sdf.format(resultdate));
			String final_date = sdf.format(resultdate);
			
			cv.put(KEY_TIME,final_date);
			return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID+"="+id,null);
			//ourDatabase.execSQL("Insert into " + DATABASE_TABLE + " (" + KEY_NAME + "," + KEY_HOTNESS + ")"+ " " +
				//	"values  ("+ name +"," + hotness +");");
			
			
			
			
		}    

		public List<String> getData() {
			// TODO Auto-generated method stub
			
			String[] columns = new String[]{KEY_NAME,KEY_ADDRESS,KEY_DOB,KEY_ROWID,KEY_TIME,KEY_SYNC_STATUS
					,KEY_TIME,KEY_AADHAR_NO,KEY_ONLINE_ID};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,null, null, null, null, null);
			//String[] result;
			
			List<String> results1 = new ArrayList<String>();
			int iRow = c.getColumnIndex(KEY_ROWID);
			int iName = c.getColumnIndex(KEY_NAME);
			int iAddress = c.getColumnIndex(KEY_ADDRESS);
			int idob = c.getColumnIndex(KEY_DOB);
			int isex = c.getColumnIndex(KEY_SEX);
			int itime = c.getColumnIndex(KEY_TIME);
			int iaadhar = c.getColumnIndex(KEY_AADHAR_NO);
			int isync = c.getColumnIndex(KEY_SYNC_STATUS);
			int ionline_id =c.getColumnIndex(KEY_ONLINE_ID);
			
			
			//int i = 0;
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			
				results1.add("DOB:"+c.getString(idob)+ "   Address:"+c.getString(iAddress)+"\n");
	            //i++;		
			}
			
			
			return results1;
		}
		
		
		
		public List<String> getName_list() {
			// TODO Auto-generated method stub
			
			String[] columns = new String[]{KEY_NAME,KEY_ADDRESS,KEY_DOB,KEY_ROWID,KEY_TIME,KEY_SYNC_STATUS
					,KEY_TIME,KEY_AADHAR_NO,KEY_ONLINE_ID};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,null, null, null, null, null);
			//String[] result;
			
			List<String> results1 = new ArrayList<String>();
			int iRow = c.getColumnIndex(KEY_ROWID);
			int iName = c.getColumnIndex(KEY_NAME);
			int iAddress = c.getColumnIndex(KEY_ADDRESS);
			int idob = c.getColumnIndex(KEY_DOB);
			int isex = c.getColumnIndex(KEY_SEX);
			int itime = c.getColumnIndex(KEY_TIME);
			int iaadhar = c.getColumnIndex(KEY_AADHAR_NO);
			int isync = c.getColumnIndex(KEY_SYNC_STATUS);
			int ionline_id =c.getColumnIndex(KEY_ONLINE_ID);
			
			
			//int i = 0;
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			
				results1.add(c.getString(iName));
	            //i++;		
			}
			
			
			return results1;
		}
		
		
		public List<String> getID() {
			// TODO Auto-generated method stub
			
			String[] columns = new String[]{KEY_ROWID};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,null, null, null, null, null);
			//String[] result;
			
			List<String> results1 = new ArrayList<String>();
			int iRow = c.getColumnIndex(KEY_ROWID);
			//int iName = c.getColumnIndex(KEY_NAME);
			//int iAddress = c.getColumnIndex(KEY_ADDRESS);
			//int iAge = c.getColumnIndex(KEY_AGE);
			
			//int i = 0;
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			
				results1.add(""+c.getInt(iRow));
	            //i++;		
			}
			
			
			return results1;
		}
		
		
		public Integer getID_for_sync() {
			// TODO Auto-generated method stub
			
			String[] columns = new String[]{KEY_ROWID};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,null, null, null, null, null);
			//String[] result;
			
			Integer results1 = 0;
			int iRow = c.getColumnIndex(KEY_ROWID);
			//int iName = c.getColumnIndex(KEY_NAME);
			//int iAddress = c.getColumnIndex(KEY_ADDRESS);
			//int iAge = c.getColumnIndex(KEY_AGE);
			
			//int i = 0;
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			
				results1 = c.getInt(iRow);
	            //i++;		
			}
			
			
			return results1;
		}
		
		public long update_online_id(Integer id,Integer online_id) {
			
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			
			cv.put(KEY_SYNC_STATUS, true);
			cv.put(KEY_ONLINE_ID, online_id);
			
			
			return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID+"="+id,null);
			//ourDatabase.execSQL("Insert into " + DATABASE_TABLE + " (" + KEY_NAME + "," + KEY_HOTNESS + ")"+ " " +
				//	"values  ("+ name +"," + hotness +");");
			
			
			
			
		}    
		
		

		public String getName(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_ADDRESS,KEY_NAME,KEY_DOB};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(2);
				return name;
			}
			
			
			return null;
		}
		
		
		public String getDOB(long l) {
			// TODO Auto-generated method stub
						String[] columns = new String[]{KEY_ROWID,KEY_ADDRESS,KEY_NAME,KEY_DOB};
						Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
						
						if(c!=null){
							c.moveToFirst();
							String name = c.getString(3);
							return name;
						}
						
						
						return null;
		}
		
		public String getAddress(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_ADDRESS,KEY_NAME,KEY_DOB};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(1);
				return name;
			}
			
			
			return null;
		}
		
		public String getAadhar(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_ADDRESS,KEY_NAME,KEY_AADHAR_NO};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(3);
				return name;
			}
			
			
			return null;
		}
		
		public String getSex(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_ADDRESS,KEY_NAME,KEY_SEX};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(3);
				return name;
			}
			
			
			return null;
		}
		
		
		
		
		
		
		public String gettime(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_TIME};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(1);
				return name;
			}
			
			
			return null;
		}
			
		
	
		

}

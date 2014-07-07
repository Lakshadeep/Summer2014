package com.lakshadeep.imu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Db{
	public static final String KEY_ROWID = "Id";
	public static final String KEY_ACC_X_P = "accxp";
	public static final String KEY_ACC_X_N = "accxn";
	public static final String KEY_ACC_Y_P = "accyp";
	public static final String KEY_ACC_Y_N = "accyn";
	public static final String KEY_ACC_Z_P= "acczp";
	public static final String KEY_ACC_Z_N= "acczn";
	
	private static final String DATABASE_NAME = "IMU";
	private static final String DATABASE_TABLE = "Threshold";
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
			KEY_ACC_X_P + " INTEGER NOT NULL," + KEY_ACC_X_N + " INTEGER NOT NULL," +
			KEY_ACC_Y_P + " INTEGER NOT NULL," +KEY_ACC_Y_N + " INTEGER NOT NULL," +
			KEY_ACC_Z_P + " INTEGER NOT NULL," +KEY_ACC_Z_N +  " INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);
			
			
		}
	}
		public Db(Context c) throws SQLException{
			ourContext = c;
		}
		
		public Db open(){
			ourHelper = new DbHelper(ourContext);
			ourDatabase = ourHelper.getWritableDatabase();
			return this;
		}
		
		public void close(){
			ourHelper.close();
		}

		public long createEntry(Integer accxp,Integer accxn,Integer accyp,Integer accyn,Integer
				acczp,Integer acczn) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_ACC_X_P,accxp);
			cv.put(KEY_ACC_X_N,accxn);
			cv.put(KEY_ACC_Y_P, accyp);
			cv.put(KEY_ACC_Y_N,accyn);
			cv.put(KEY_ACC_Z_P,acczp);
			cv.put(KEY_ACC_Z_N,acczn);
			return ourDatabase.insert(DATABASE_TABLE, null,cv);
			
		}     
		
		
		public long updateEntry(Integer accxp,Integer accxn,Integer accyp,Integer accyn,Integer
				acczp,Integer acczn,long id){
			
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_ACC_X_P,accxp);
			cv.put(KEY_ACC_X_N,accxn);
			cv.put(KEY_ACC_Y_P, accyp);
			cv.put(KEY_ACC_Y_N,accyn);
			cv.put(KEY_ACC_Z_P,acczp);
			cv.put(KEY_ACC_Z_N,acczn);
			
		
			return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID+"="+id,null);
		}    

		
		
		
	    public Integer getaccxp(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ACC_X_P};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				Integer acc_value = c.getInt(0);
				return acc_value;
			}
			
			
			return null;
		}
	    
	    public Integer getaccxn(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ACC_X_N};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				Integer acc_value = c.getInt(0);
				return acc_value;
			}
			
			
			return null;
		}
	    
	    public Integer getaccyp(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ACC_Y_P};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				Integer acc_value = c.getInt(0);
				return acc_value;
			}
			
			
			return null;
		}
		
	    public Integer getaccyn(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ACC_Y_N};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				Integer acc_value = c.getInt(0);
				return acc_value;
			}
			
			
			return null;
		}
	    
	    public Integer getacczp(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ACC_Z_P};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				Integer acc_value = c.getInt(0);
				return acc_value;
			}
			
			
			return null;
		}
			
	    public Integer getacczn(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ACC_Z_N};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				Integer acc_value = c.getInt(0);
				return acc_value;
			}
			
			
			return null;
		}
	    
	    public long get_row_count(){
	    	
	    
	    	
	    	SQLiteStatement s = ourDatabase.compileStatement("Select count(*) from Threshold");

	    	long count = s.simpleQueryForLong();
	    	
	    	return count;
	    	
	    	
	    }
	
		

}

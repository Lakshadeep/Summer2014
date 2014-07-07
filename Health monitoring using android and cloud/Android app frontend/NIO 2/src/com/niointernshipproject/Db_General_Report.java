package com.niointernshipproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db_General_Report {
	public static final String KEY_ROWID = "Gr_id";
	public static final String KEY_IMPRESSIONS = "General_impression";
	public static final String KEY_PROBLEMS = "Nature_of_problems";
	public static final String KEY_MEDICATIONS = "Regular_medications";
	public static final String KEY_STATE_NATURE = "State_nature";
	public static final String KEY_COMMENTS = "Aaditional_comments";
	public static final String KEY_SYNC= "Sync_status";
	//public static final String KEY_ID= "Id";
	
	
	
	
	private static final String DATABASE_NAME = "NIO_internship";
	private static final String DATABASE_TABLE = "General_report";
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
			KEY_IMPRESSIONS + " TEXT," + KEY_PROBLEMS + " TEXT ," + KEY_MEDICATIONS +
			" BOOLEAN DEFAULT FALSE," + KEY_STATE_NATURE + " TEXT,"+KEY_COMMENTS + " TEXT,"+ KEY_SYNC + 
			" BOOLEAN DEFAULT FALSE);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
			onCreate(db);
			
		}
	}
		public Db_General_Report(Context c) throws SQLException{
			ourContext = c;
		}
		
		public Db_General_Report open(){
			ourHelper = new DbHelper(ourContext);
			ourDatabase = ourHelper.getWritableDatabase();
			return this;
		}
		
		public void close(){
			ourHelper.close();
		}

		public long createEntry( String impression, String problems ,Boolean medications,String nature,String comments,
				Boolean sync_status) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_IMPRESSIONS,impression);
			cv.put(KEY_PROBLEMS,problems);
			cv.put(KEY_MEDICATIONS,medications);
			cv.put(KEY_STATE_NATURE,nature);
			cv.put(KEY_COMMENTS,comments);
			cv.put(KEY_SYNC, sync_status);
			
			return ourDatabase.insert(DATABASE_TABLE, null,cv);
			
			
			
		}     
		
		
		public long updateEntry( String impression, String problems ,Boolean medications,String nature,String comments,
				Boolean sync_status,Integer id) {
			
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_IMPRESSIONS,impression);
			cv.put(KEY_PROBLEMS,problems);
			cv.put(KEY_MEDICATIONS,medications);
			cv.put(KEY_STATE_NATURE,nature);
			cv.put(KEY_COMMENTS,comments);
			cv.put(KEY_SYNC, sync_status);
			
			
			return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID+"="+id,null);
			
			
		}    

		
		public String getImpression(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_IMPRESSIONS};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				return name;
			}
			
			
			return null;
		}
		
		
		public String getProblems(long l) {
			// TODO Auto-generated method stub
						String[] columns = new String[]{KEY_PROBLEMS};
						Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
						
						if(c!=null){
							c.moveToFirst();
							String name = c.getString(0);
							return name;
						}
						
						
						return null;
		}
		
		public String getMedications(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_MEDICATIONS};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				
				return name;
			}
			
			
			return null;
		}
		
		public String getComments(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_COMMENTS};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				return name;
			}
			
			
			return null;
		}
		
		public String getNature(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_STATE_NATURE};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String name = c.getString(0);
				return name;
			}
			
			
			return null;
		}
		
		

}

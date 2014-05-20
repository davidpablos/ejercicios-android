package com.davidpablos.earthquakes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EarthQuakeDBOpenHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "EarthQuakes.db";
	public static final String DATABASE_TABLE = "EarthQuake";
	public static final int DATABASE_VERSION = 1;
	
	// SQL Statement to create a new database.
	private static final String DATABASE_CREATE =
	        "create table " + DATABASE_TABLE + "(_id integer primary key autoincrement, "
	            + "place text not null,"
	            + "time datetime not null UNIQUE,"
	            + "detail text not null,"
	            + "magnitude real not null,"
	            + "lat real not null,"
	            + "long real not null,"
	            + "url text not null,"
	            + "created_at datetime not null,"
	            + "updated_at datetime not null"
	            +");";
	
	public static final String KEY_ID = "_id";
	public static final String PLACE = "place";
	public static final String TIME = "time";
	public static final String DETAIL = "detail";
	public static final String MAGNITUDE = "magnitude";
	public static final String LAT = "lat";
	public static final String LONG = "long";
	public static final String URL = "url";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	
	
	public static final String[] RESULT_COLUMNS = new String[] { KEY_ID, PLACE, TIME, DETAIL, 
								MAGNITUDE, LAT, LONG, URL, CREATED_AT, UPDATED_AT };
	
	public EarthQuakeDBOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	// Called when no db exists in disk
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("TAG", DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Simplest case is to drop the old table and create a new one.
		  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		  // Create a new one.
		  onCreate(db);
	}

}

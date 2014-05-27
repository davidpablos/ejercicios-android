package com.davidpablos.earthquakes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
	
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	
	public static final String KEY_ID = "_id";
	public static final String ID_STR = "id_str";
	public static final String PLACE = "place";
	public static final String TIME = "time";
	public static final String DETAIL = "detail";
	public static final String MAGNITUDE = "magnitude";
	public static final String LAT = "lat";
	public static final String LONG = "long";
	public static final String URL = "url";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	
	public static final String[] KEYS_ALL = { KEY_ID, ID_STR, PLACE, TIME, DETAIL,
							MAGNITUDE, LAT, LONG, URL };
	
	private EarthQuakeDBOpenHelper earthQuakeDBOpenHelper;
	
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.davidpablos.provider.earthquakecontentprovider/earthquakes");
	
	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.davidpablos.provider.earthquakecontentprovider",
				"earthquakes", ALLROWS);
		uriMatcher.addURI("com.davidpablos.provider.earthquakecontentprovider",
				"earthquakes/0", SINGLE_ROW);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		Log.d("TAG", "getType()");
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			Log.d("TAG", "getType1()");
			return "vnd.android.cursor.dir/vnd.davidpablos.provider.earthquakecontentprovider";
		case SINGLE_ROW:
			Log.d("TAG", "getType2()");
			return "vnd.android.cursor.item/vnd.davidpablos.provider.earthquakecontentprovider";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db;
		try {
			  db = earthQuakeDBOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			  db = earthQuakeDBOpenHelper.getReadableDatabase();
		}

		String nullColumnHack = null;

		long id = db.insert(EarthQuakeDBOpenHelper.DATABASE_TABLE, nullColumnHack,
				values);

		if (id > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);
			return insertedId;
		} else
			return null;
	}

	@Override
	public boolean onCreate() {
		earthQuakeDBOpenHelper = new EarthQuakeDBOpenHelper(getContext(),
				EarthQuakeDBOpenHelper.DATABASE_NAME, null,
				EarthQuakeDBOpenHelper.DATABASE_VERSION);

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d("TAG", "query()");
		SQLiteDatabase db;
		String groupBy = null;
		String having = null;

		try {
			db = earthQuakeDBOpenHelper.getWritableDatabase();

			SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

			switch (uriMatcher.match(uri)) {
			case SINGLE_ROW:
				String rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "="
						+ rowID);
			default:
				break;
			}

			queryBuilder.setTables(EarthQuakeDBOpenHelper.DATABASE_TABLE);

			Cursor cursor = queryBuilder.query(db, projection, selection,
					selectionArgs, groupBy, having, sortOrder);
			
			cursor.setNotificationUri(getContext().getContentResolver(), uri);

			return cursor;

		} catch (SQLiteException ex) {
			db = earthQuakeDBOpenHelper.getReadableDatabase();
		}

		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class EarthQuakeDBOpenHelper extends SQLiteOpenHelper {
		
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
		
		
//		public static final String[] RESULT_COLUMNS = new String[] { KEY_ID, PLACE, TIME, DETAIL, 
//									MAGNITUDE, LAT, LONG, URL, CREATED_AT, UPDATED_AT };
		
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
}


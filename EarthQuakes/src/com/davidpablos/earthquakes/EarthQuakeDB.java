package com.davidpablos.earthquakes;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class EarthQuakeDB {

	private EarthQuakeDBOpenHelper dbHelper;
	private SQLiteDatabase db;

	public EarthQuakeDB(Context context) {
		dbHelper = new EarthQuakeDBOpenHelper(context,
				EarthQuakeDBOpenHelper.DATABASE_NAME, null,
				EarthQuakeDBOpenHelper.DATABASE_VERSION);
		db = this.open();
	}

	public SQLiteDatabase open() {
		try {
			return dbHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			return dbHelper.getReadableDatabase();
		}
	}

	public void close() {
		dbHelper.close();
	}

	public ArrayList query(Double magnitude) {
		// Specify the where clause that will limit our results.
		String where = EarthQuakeDBOpenHelper.MAGNITUDE + " > ?";
		String whereArgs[] = { magnitude.toString() };
		String groupBy = null;
		String having = null;
		String order = null;
		Cursor cursor = db.query(EarthQuakeDBOpenHelper.DATABASE_TABLE,EarthQuakeDBOpenHelper.RESULT_COLUMNS, where,
				whereArgs, groupBy, having, order);
		
		ArrayList queryResult = fromCursorToArrayList(cursor);

		return queryResult;
	}
	
	private ArrayList<EarthQuake> fromCursorToArrayList(Cursor cursor) {
		ArrayList<EarthQuake> result = new ArrayList<EarthQuake>();
		
		int PLACE_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.PLACE);
		int TIME_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.TIME);
		int DETAIL_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.DETAIL);
		int MAGNITUDE_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.MAGNITUDE);
		int LAT_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.LAT);
		int LONG_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.LONG);
		int URL_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.URL);
		int CREATED_AT_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.CREATED_AT);
		int UPDATED_AT_INDEX = cursor.getColumnIndexOrThrow(EarthQuakeDBOpenHelper.UPDATED_AT);
		
		while (cursor.moveToNext()) {
			EarthQuake earthquake = new EarthQuake(cursor.getString(PLACE_INDEX), 
					cursor.getLong(TIME_INDEX), 
					cursor.getString(DETAIL_INDEX), 
					cursor.getDouble(MAGNITUDE_INDEX), 
					cursor.getDouble(LAT_INDEX), 
					cursor.getDouble(LONG_INDEX), 
					cursor.getString(URL_INDEX));
			result.add(earthquake);
		}
		return result;
	}

	public void insert(EarthQuake earthquake) {
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();

		Date date = new Date();
		long time = date.getTime();
		
		// Assign values for each row.
		newValues.put(EarthQuakeDBOpenHelper.PLACE, earthquake.getPlace());
		newValues.put(EarthQuakeDBOpenHelper.TIME, earthquake.getTime().getTime());
		newValues.put(EarthQuakeDBOpenHelper.DETAIL, earthquake.getDetail());
		newValues.put(EarthQuakeDBOpenHelper.MAGNITUDE, earthquake.getMagnitude());
		newValues.put(EarthQuakeDBOpenHelper.LAT, earthquake.getLat());
		newValues.put(EarthQuakeDBOpenHelper.LONG, earthquake.getLng());
		newValues.put(EarthQuakeDBOpenHelper.URL, earthquake.getUrl());
		newValues.put(EarthQuakeDBOpenHelper.CREATED_AT, time);
		newValues.put(EarthQuakeDBOpenHelper.UPDATED_AT, time);
		
		// Insert the row into your table
		db.insert(EarthQuakeDBOpenHelper.DATABASE_TABLE, null, newValues);
	}

	public void update(int earthquakeId, EarthQuake earthquake) {
		// Create the updated row Content Values.
		ContentValues updatedValues = new ContentValues();

		Date date = new Date();
		long time = date.getTime();
		// Assign values for each row.
		updatedValues.put(EarthQuakeDBOpenHelper.PLACE, earthquake.getPlace());
		updatedValues.put(EarthQuakeDBOpenHelper.TIME, earthquake.getTime().getTime());
		updatedValues.put(EarthQuakeDBOpenHelper.DETAIL, earthquake.getDetail());
		updatedValues.put(EarthQuakeDBOpenHelper.MAGNITUDE, earthquake.getMagnitude());
		updatedValues.put(EarthQuakeDBOpenHelper.LAT, earthquake.getLat());
		updatedValues.put(EarthQuakeDBOpenHelper.LONG, earthquake.getLng());
		updatedValues.put(EarthQuakeDBOpenHelper.URL, earthquake.getUrl());
		updatedValues.put(EarthQuakeDBOpenHelper.UPDATED_AT, time);

		// Specify a where clause the defines which rows should be // updated.
		// Specify where arguments as necessary.
		String where = EarthQuakeDBOpenHelper.KEY_ID + " = ?";
		String whereArgs[] = { ((Integer)earthquakeId).toString() };
		// Update the row with the specified index with the new values.
		db.update(EarthQuakeDBOpenHelper.DATABASE_TABLE, updatedValues, where,
				whereArgs);
	}

	public void delete(int earthquakeId) {
		String where = EarthQuakeDBOpenHelper.KEY_ID + " = ?";
		String whereArgs[] = { ((Integer)earthquakeId).toString() };
		db.delete(EarthQuakeDBOpenHelper.DATABASE_TABLE, where,
				whereArgs);
	}
}

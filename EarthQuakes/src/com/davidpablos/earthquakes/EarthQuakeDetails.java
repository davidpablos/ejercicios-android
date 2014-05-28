package com.davidpablos.earthquakes;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EarthQuakeDetails extends Activity  implements LoaderCallbacks<Cursor> {
	
	private LoaderCallbacks<Cursor> mCallbacks;

	private static final int LOADER_ID = 2;
	private long earthquake_id = 0;
	
	private static String ID = "ID";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		earthquake_id = getIntent().getLongExtra(ID, 0);

		mCallbacks = this;

		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.d("TAG", "onCreateLoader()");
		Uri rowAddress = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, earthquake_id);

		CursorLoader loader = new CursorLoader(this,
				rowAddress, MyContentProvider.KEYS_ALL,
				null, null, null);

		return loader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int place_idx = cursor.getColumnIndex(MyContentProvider.PLACE);
		int mag_idx = cursor.getColumnIndex(MyContentProvider.MAGNITUDE);
		int time_idx = cursor.getColumnIndex(MyContentProvider.TIME);
		int detail_idx = cursor.getColumnIndex(MyContentProvider.DETAIL);
		int lat_idx = cursor.getColumnIndex(MyContentProvider.LAT);
		int lng_idx = cursor.getColumnIndex(MyContentProvider.LONG);
		int url_idx = cursor.getColumnIndex(MyContentProvider.URL);
		
		TextView mag = (TextView)findViewById(R.id.details_mag);
		TextView place = (TextView)findViewById(R.id.details_place);
		TextView time = (TextView)findViewById(R.id.details_time);
		TextView detail = (TextView)findViewById(R.id.details_detail);
		TextView lat = (TextView)findViewById(R.id.details_lat);
		TextView lng = (TextView)findViewById(R.id.details_lng);
		TextView url = (TextView)findViewById(R.id.details_url);
		
		Log.d("TAG", "onLoadFinished()");
		if(cursor.moveToFirst()) {
			place.setText(cursor.getString(place_idx));
			mag.setText(cursor.getString(mag_idx));
			time.setText(cursor.getString(time_idx));
			detail.setText(cursor.getString(detail_idx));
			lat.setText(cursor.getString(lat_idx));
			lng.setText(cursor.getString(lng_idx));
			url.setText(cursor.getString(url_idx));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		
		
	}
}
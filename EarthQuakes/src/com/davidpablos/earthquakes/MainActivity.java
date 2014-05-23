package com.davidpablos.earthquakes;

import com.davidpablos.preferences.SettingsActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private static final int SHOW_PREFERENCES = 0;
	private static final String TAG = "EARTHQUAKES";
	private static final String EARTHQUAKES_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

	private long myDownloadReference;
	private DownloadManager downloadManager;
	String serviceString = Context.DOWNLOAD_SERVICE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("TAG", "FRAGMENT");
		
		if(savedInstanceState == null) {
			Log.d("TAG", "FRAGMENT");
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.container, new EarthQuakeList(), "earthquakeList");
			fragmentTransaction.commit();
			Log.d("TAG", "FRAGMENT");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

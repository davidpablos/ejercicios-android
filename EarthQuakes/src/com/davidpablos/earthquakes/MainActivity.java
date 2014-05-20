package com.davidpablos.earthquakes;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EarthQuakeDB db = new EarthQuakeDB(this);
		EarthQuake earthquake = new EarthQuake("Errenteria", Long.valueOf("1400580039851"), "detail", 9.0, 1.0, 2.0, "http://www.arkaitzgarro.com/");
		db.insert(earthquake);
		earthquake.setMagnitude(100.0);
		db.update(1, earthquake);
		ArrayList<Object> result = db.query(9.0);
		Log.d("TAG", result.get(0).toString());
		Log.d("TAG", result.get(1).toString());
		Log.d("TAG", result.get(2).toString());
		Log.d("TAG", result.get(3).toString());
		Log.d("TAG", result.get(4).toString());
		Log.d("TAG", result.get(5).toString());
		Log.d("TAG", result.get(6).toString());
		Log.d("TAG", result.get(7).toString());
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

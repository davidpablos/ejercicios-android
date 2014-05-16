package com.davidpablos.persistencia;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final int INTENT_SETTINGS = 0; 

	
	private SharedPreferences mySharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("TAG", "onCreate() MainActivity");
		getPreferences();
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
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, INTENT_SETTINGS);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("TAG", "onActivityResult()");
		
		switch(requestCode) {
		case INTENT_SETTINGS:
			
			if (resultCode == Activity.RESULT_CANCELED) {
				Log.d("TAG", "BACK BUTTON");
				getPreferences();
			}
			      
			break;
		}
	}
	
	public void getPreferences() {
		
		mySharedPreferences =	getSharedPreferences(SettingsActivity.MY_PREFS,	
				Activity.MODE_PRIVATE);
		
		TextView refreshValue = (TextView) findViewById(R.id.refreshValue);
		Boolean autorefresh = (Boolean)mySharedPreferences.getBoolean(SettingsActivity.AUTOREFRESH, false);
		refreshValue.setText(autorefresh.toString());
		
		Integer intervalIndex = mySharedPreferences.getInt(SettingsActivity.INTERVAL, 0);
		TextView intervalTv = (TextView) findViewById(R.id.intervalValue);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.intervals_array, android.R.layout.simple_spinner_item);
		String intervalValue = (String) adapter.getItem(intervalIndex);
		intervalTv.setText(intervalValue);
		
	}

}

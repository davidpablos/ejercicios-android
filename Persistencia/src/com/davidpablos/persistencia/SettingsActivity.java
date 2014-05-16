package com.davidpablos.persistencia;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.os.Build;

public class SettingsActivity extends Activity {
	
	public static final String MY_PREFS = "My_preferences";
	public static final String AUTOREFRESH = "autorefresh";
	public static final String INTERVAL = "interval";
	
	private SharedPreferences mySharedPreferences;
	private Spinner spinner;
	private Switch switchAutorefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_settings);
		
		spinner = (Spinner) findViewById(R.id.spinner_interval);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.intervals_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		mySharedPreferences =	getSharedPreferences(MY_PREFS,	
				Activity.MODE_PRIVATE);

		boolean autorefresh = mySharedPreferences.getBoolean(AUTOREFRESH, false);
		switchAutorefresh = (Switch) findViewById(R.id.switchAutorefresh);
		switchAutorefresh.setChecked(autorefresh);
		
		int interval = mySharedPreferences.getInt(INTERVAL, 0);
		spinner.setSelection(interval);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences.Editor editor	= mySharedPreferences.edit();
		editor.putBoolean(AUTOREFRESH, switchAutorefresh.isChecked());
		editor.putInt(INTERVAL, spinner.getSelectedItemPosition());
		editor.apply();	
	}

}

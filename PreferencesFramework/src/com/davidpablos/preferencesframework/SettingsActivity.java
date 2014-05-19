package com.davidpablos.preferencesframework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends Activity implements OnSharedPreferenceChangeListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new FragmentSettingsActivity())
			.commit();
		// Register this OnSharedPreferenceChangeListener
		   SharedPreferences prefs =
		       PreferenceManager.getDefaultSharedPreferences(this);
		   prefs.registerOnSharedPreferenceChangeListener(this);
		   
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs,
			String key) {
		// TODO Auto-generated method stub
		Log.d("TAG", key);
		
		if(key == getResources().getString(R.string.autorefresh_switch_key)) {
			Log.d("TAG", "Evento autorefresh");
			boolean autorefresh = prefs.getBoolean(key, true);
			if(autorefresh) {
				//Start
			} else {
				//Pause
			}
		} else if(key == getResources().getString(R.string.interval_list_key)) {
			Log.d("TAG", prefs.getString(key, "60"));
		}
	}
}

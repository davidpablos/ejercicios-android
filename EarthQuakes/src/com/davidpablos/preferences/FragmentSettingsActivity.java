package com.davidpablos.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.davidpablos.earthquakes.R;

public class FragmentSettingsActivity extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
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
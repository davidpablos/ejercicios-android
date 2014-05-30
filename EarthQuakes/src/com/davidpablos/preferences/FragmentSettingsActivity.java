package com.davidpablos.preferences;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.davidpablos.earthquakes.R;

public class FragmentSettingsActivity extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	
	public final String REFRESH_ALARM = "com.davidpablos.earthquakes.alarmrefresh";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		// TODO Auto-generated method stub
		Log.d("TAG", key);

		if (key == getResources().getString(R.string.autorefresh_switch_key)) {
			Log.d("TAG", "Evento autorefresh");
			boolean autorefresh = prefs.getBoolean(key, true);
			if (autorefresh) {
				// Start
				setInexactRepeatingAlarm(prefs);
			} else {
				// Delete alarm
				cancelRepeatingAlarm();
			}
		} else if (key == getResources().getString(R.string.interval_list_key)) {
			Log.d("TAG", prefs.getString(key, "60"));
			// set Alarm
			setInexactRepeatingAlarm(prefs);
		}
	}

	private void setInexactRepeatingAlarm(SharedPreferences sharedPreferences) {
		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);
		int alarmType = AlarmManager.RTC;
		String key = getResources().getString(R.string.interval_list_key);
		String timeStr = sharedPreferences.getString(key, "1");
		long timeOfWait = Long.valueOf(timeStr) * 5 * 1000;
		Intent intentToFire = new Intent(REFRESH_ALARM);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),
				0, intentToFire, 0);
		Log.d("TAG", "FragmentSettings => alarm set to " + timeOfWait
				+ " milliseconds");
		alarmManager.setInexactRepeating(alarmType, timeOfWait, timeOfWait,
				alarmIntent);

	}
	
	private void cancelRepeatingAlarm() {
		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);

		Intent intentToFire = new Intent(REFRESH_ALARM);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),
				0, intentToFire, 0);
		alarmManager.cancel(alarmIntent);
	}
}
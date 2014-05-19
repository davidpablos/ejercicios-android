package com.davidpablos.preferencesframework;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

public class FragmentSettingsActivity extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}

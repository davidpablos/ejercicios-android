package com.davidpablos.earthquakes;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class EarthQuakeViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor c, int idx) {
		int time_idxz = c.getColumnIndex(MyContentProvider.TIME);

		if(time_idxz == idx) {
			SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
			String date = df.format(c.getLong(time_idxz));

			((TextView)view.findViewById(R.id.time)).setText(date);

			return true;
		}

		return false;
	}
}
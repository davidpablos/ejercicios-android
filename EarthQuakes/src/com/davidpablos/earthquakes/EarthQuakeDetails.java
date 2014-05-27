package com.davidpablos.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EarthQuakeDetails extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		TextView text = (TextView) findViewById(R.id.textViewDetails);
		Intent intent = getIntent();
		Log.d("TAG", "Intent: "+intent);
		String texto = null;
		if (intent != null){
			Log.d("TAG", "id: "+intent.getLongExtra("id", 0));
			texto = String.valueOf(intent.getLongExtra("id", 0));
		}
		text.setText(texto);
	}
}
package com.davidpablos.ciclovida;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ActivityC extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("TAG", "ActivityC.onPause()");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("TAG", "ActivityC.onResume()");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("TAG", "ActivityC.onStop()");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("TAG", "ActivityC.onDestroy()");
	}
	
	public void startActivityA(View v) {
		Intent intent = new Intent(this, ActivityA.class);
		startActivity(intent);
		
		Log.d("TAG", "ActivityC.startActivityB()");
	}
	
	public void startActivityB(View v) {
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
		
		Log.d("TAG", "ActivityC.startActivityB()");
	}
	
	public void close(View v) {
		Log.d("TAG", "ActivityC.finish()");
		finish();
	}
}

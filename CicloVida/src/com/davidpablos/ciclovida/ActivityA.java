package com.davidpablos.ciclovida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		
		Log.d("TAG", "ActivityA.onCreate()");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("TAG", "ActivityA.onPause()");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("TAG", "ActivityA.onResume()");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("TAG", "ActivityA.onStop()");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("TAG", "ActivityA.onDestroy()");
	}
	
	public void startActivityB(View v) {
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
		
		Log.d("TAG", "ActivityA.startActivityB()");
	}
	
	public void startActivityC(View v) {
		Intent intent = new Intent(this, ActivityC.class);
		startActivity(intent);
		
		Log.d("TAG", "ActivityA.startActivityC()");
	}
	
	public void close(View v) {
		Log.d("TAG", "ActivityA.finish()");
		finish();
	}
}

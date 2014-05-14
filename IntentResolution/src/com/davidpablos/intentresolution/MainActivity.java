package com.davidpablos.intentresolution;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidpablos.intentresolution.MainFragment.IIntentResolution;

public class MainActivity extends Activity implements IIntentResolution {
	
	MainFragment mf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mf = new MainFragment();
		
		if(savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.main_container, mf, "MainFragment");
			fragmentTransaction.commit();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG", "onActivityResult() activity ");
		
		((MainFragment)getFragmentManager().findFragmentByTag("MainFragment")).onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
//		
//		String texto = savedInstanceState.getString(TEXTVIEW);
//		TextView tv = (TextView)findViewById(R.id.textViewMain);
//		tv.setText(texto);
//		Bundle bundle = savedInstanceState.getBundle("BUNDLE");
//		Bitmap imageBitmap = (Bitmap) bundle.get("data");
//		ImageView iv = (ImageView)findViewById(R.id.imageViewMain);
//        iv.setImageBitmap(imageBitmap);
	}
	
//	@Override
//	 public void onWindowFocusChanged(boolean hasFocus) {
//	  // TODO Auto-generated method stub
//	  super.onWindowFocusChanged(hasFocus);
//	  //Here you can get the size!
//	  mf.setPic();
//	 }

}

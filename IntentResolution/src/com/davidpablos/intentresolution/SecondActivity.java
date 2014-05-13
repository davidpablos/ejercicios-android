package com.davidpablos.intentresolution;

import com.davidpablos.intentresolution.SecondFragment.IIntentResolution;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

public class SecondActivity extends Activity implements IIntentResolution {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		SecondFragment sf = new SecondFragment();
		sf.getData(getIntent());
		
		if(savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.second_container, sf);
			fragmentTransaction.commit();
		}
	}
	
	public void closeOK(String info) {
		Intent result = new Intent();
		result.putExtra(SecondFragment.INFO_EDITTEXTSECOND, info);
		setResult(Activity.RESULT_OK, result);
		finish();
	}
	
	public void closeBack() {
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
}

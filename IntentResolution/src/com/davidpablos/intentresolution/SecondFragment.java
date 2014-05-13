package com.davidpablos.intentresolution;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	
	public static final String INFO_EDITTEXTSECOND = "INFO_EDITTEXTSECOND";
	public static final int OK = 0;
	public static final int BACK = 1;
	
	
	private View v;
	private Intent intent;
	
	public interface IIntentResolution {
		public void closeOK(String info);
		public void closeBack();
	}
	
	private IIntentResolution activity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.activity = (IIntentResolution)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " debe implementar la interfaz IIntentResolution");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create, or inflate the Fragment's UI, and return it.
		// If this Fragment has no UI then return null.
		
		this.v = inflater.inflate(R.layout.fragment_second, container, false);
		String texto = this.intent.getStringExtra(MainFragment.INFO_EDITTEXTMAIN);
		TextView tv = (TextView)this.v.findViewById(R.id.textViewSecond);
		tv.setText(texto);
		
		Button botonOK = (Button) v.findViewById(R.id.buttonOK);
		botonOK.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View viewButton) {
				Log.d("TAG", "onClick() OK");
				EditText tv = (EditText) v.findViewById(R.id.editTextSecond);
				String info = tv.getText().toString();
				
				activity.closeOK(info);
			}
		});
		
		Button botonBack = (Button) v.findViewById(R.id.buttonBack);
		botonBack.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View viewButton) {
				Log.d("TAG", "onClick() Back");
				activity.closeBack();
			}
		});
		
		return v;
	}
	
	public void getData(Intent intent) {
		this.intent = intent;
	}
}

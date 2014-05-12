package com.davidpablos.todolist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.davidpablos.todolist.R;

public class AnadirFragment extends Fragment {
	
	public interface IAnadirLista {
		public void anadir(String text);
	}
	
	private IAnadirLista activity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.activity = (IAnadirLista)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " debe implementar la interfaz IAnadirLista");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create, or inflate the Fragment's UI, and return it.
		// If this Fragment has no UI then return null.
		
		View v = inflater.inflate(R.layout.anadir, container, false);
		
		final TextView tv = (TextView) v.findViewById(R.id.anadir);
		
		tv.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER
							|| keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
						
						activity.anadir(tv.getText().toString());
						tv.setText("");
						return true;
					}
				}
				return false;
			}
		});
		
		final Button boton = (Button) v.findViewById(R.id.button1);
		boton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String txt = tv.getText().toString();
				activity.anadir(txt);
			}
		});

		
		return v;
	}
}

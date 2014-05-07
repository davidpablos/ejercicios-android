package com.davidpablos.calculadora;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	private Calculadora calc;
	private TextView textView, resultView;
	
	private String op = "";
	private double op1 = 0;
	private double op2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calc = new Calculadora();
		resultView = (TextView)findViewById(R.id.display);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void pulsarDigito(View v) {
		Log.d("TAG", "MainActivity.pulsarDigito()");
		Button btn = (Button) v;
		String num = btn.getText().toString();
		
		String result = this.resultView.getText().toString() + num;
		this.resultView.setText(result); 
	}

	public void pulsarOperador(View v) {
		Log.d("TAG", "MainActivity.pulsarOperador()");
		Button btn = (Button) v;
		this.op1 = Double.parseDouble(this.resultView.getText().toString());
		this.op = btn.getText().toString();
	}
	
	public void pulsarIgual(View v) {
		this.op2 = Double.parseDouble(this.resultView.getText().toString());
		this.calc.realizarOperacion(this.op1, this.op2, this.op);
	}
}
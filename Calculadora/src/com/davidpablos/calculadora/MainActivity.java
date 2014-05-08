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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calc = new Calculadora();
		resultView = (TextView) findViewById(R.id.display);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     // Save UI state changes to the savedInstanceState.
	     // This bundle will be passed to onCreate and
	     // onRestoreInstanceState if the process is
	     // killed and restarted by the run time.
		savedInstanceState.putString("operador", this.calc.getOp());
		savedInstanceState.putDouble("op1", this.calc.getOp1());
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
	     // Restore UI state from the savedInstanceState.
	     // This bundle has also been passed to onCreate.
	     String operador = savedInstanceState.getString("operador");
	     double op1 = savedInstanceState.getDouble("op1");
	     this.calc.pulsarOperador(op1, operador);
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
		double op1 = Double.parseDouble(this.resultView.getText().toString());
		Log.d("TAG", btn.getText().toString());
		this.calc.pulsarOperador(op1, btn.getText().toString());

		this.resultView.setText("");
	}

	public void pulsarIgual(View v) {
		Log.d("TAG", "MainActivity.pulsarIgual()");
		String result = this.calc.realizarOperacion(this.resultView.getText()
				.toString());
		Log.d("TAG", result);
		this.resultView.setText(result);
	}
}
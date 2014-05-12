package com.davidpablos.todolist;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.davidpablos.todolist.AnadirFragment.IAnadirLista;

public class MainActivity extends Activity implements IAnadirLista {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		if(savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.anadir_container, new AnadirFragment());
			fragmentTransaction.add(R.id.lista_container, new ListaTodo(), "list");
			fragmentTransaction.commit();
		}
	}
	
	public void anadir(String txt) {
		Log.d("TAG", "anadir()");
		((ListaTodo)getFragmentManager().findFragmentByTag("list")).anadir(txt);
	}

}

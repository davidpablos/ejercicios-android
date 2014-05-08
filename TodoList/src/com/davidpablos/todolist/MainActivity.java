package com.davidpablos.todolist;

import java.util.ArrayList;

import com.davidpablos.todolist.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	private ArrayList<String> mainList;
	private ArrayAdapter<String> adapter;
	private TextView textView;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("TAG", "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.mainList = new ArrayList<String>();
		this.adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, this.mainList);
		this.textView = (TextView) findViewById(R.id.anadir);
		this.listView = (ListView) findViewById(R.id.listaTodo);
		this.listView.setAdapter(adapter);
		
		this.textView.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if(keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
						mainList.add(0, textView.getText().toString());
						adapter.notifyDataSetChanged();
						textView.setText("");
					}
				}
				// TODO Auto-generated method stub
				return false;
			}
		});
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     // Save UI state changes to the savedInstanceState.
	     // This bundle will be passed to onCreate and
	     // onRestoreInstanceState if the process is
	     // killed and restarted by the run time.
		savedInstanceState.putStringArrayList("mainList", this.mainList);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
	     // Restore UI state from the savedInstanceState.
	     // This bundle has also been passed to onCreate.
	     this.mainList.addAll(savedInstanceState.getStringArrayList("mainList"));
	     this.adapter.notifyDataSetChanged();
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
	
	public void anadir(View v) {
		Log.d("TAG", "anadir()");
		this.mainList.add(this.textView.getText().toString());
		this.adapter.notifyDataSetChanged();
	}

}

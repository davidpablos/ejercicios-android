package com.davidpablos.earthquakes;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.davidpablos.preferences.SettingsActivity;

public class MainActivity extends Activity {

	private static final String TAG = "EARTHQUAKES";

	String serviceString = Context.DOWNLOAD_SERVICE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("EarthQuakes");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tabList = actionBar.newTab();
		tabList.setText("LIST")
				.setTabListener(new TabListener<EarthQuakeList>
						(this, R.id.container, EarthQuakeList.class));
		
		actionBar.addTab(tabList);
		
		Tab tabMap = actionBar.newTab();
		tabMap.setText("MAP")
				.setTabListener(new TabListener<MyMapFragment>
						(this, R.id.container, MyMapFragment.class));
		
		actionBar.addTab(tabMap);
		
//		if(savedInstanceState == null) {
//			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//			fragmentTransaction.add(R.id.container, new EarthQuakeList(), "earthquakeList");
//			fragmentTransaction.commit();
//		}
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
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		} else {
			Log.d(TAG, ((Integer)id).toString());
			getFragmentManager().findFragmentByTag("fragmentMap");
		}
		return super.onOptionsItemSelected(item);
	}
	
	private static class TabListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment fragment;
		private Activity activity;
		private Class<T> fragmentClass;
		private int fragmentContainer;

		public TabListener(Activity activity, int fragmentContainer,
				Class<T> fragmentClass) {
			Log.d("TAG", "new TabListener()");
			
			this.activity = activity;
			this.fragmentContainer = fragmentContainer;
			this.fragmentClass = fragmentClass;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null)
			    ft.attach(fragment);

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (fragment == null) {
				String fragmentName = fragmentClass.getName();
				fragment = Fragment.instantiate(activity, fragmentName);
				ft.add(fragmentContainer, fragment, fragmentName);
			} else
				ft.attach(fragment);

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null)
			    ft.detach(fragment);

		}

	}
}

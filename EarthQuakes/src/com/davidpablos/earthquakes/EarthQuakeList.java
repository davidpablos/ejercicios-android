package com.davidpablos.earthquakes;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EarthQuakeList extends ListFragment implements LoaderCallbacks<Cursor> {
	
	public static final String EARTHQUAKES_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	
//	private ArrayList<EarthQuake> earthquakeList;
	private SimpleCursorAdapter adapter;
	
	private final int LOADER_ID = 1;
	private LoaderCallbacks<Cursor> mCallbacks;
	
	private String[] from = { MyContentProvider.MAGNITUDE, MyContentProvider.PLACE, MyContentProvider.TIME,
			MyContentProvider.KEY_ID };
	private int[] to = { R.id.magnitude, R.id.place, R.id.time };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_row, null, from, to, 0);
		adapter.setViewBinder(new EarthQuakeViewBinder());
		setListAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mCallbacks = this;
		getLoaderManager().initLoader(LOADER_ID, null, this);
		
//		new DownloadEarthQuakes(getActivity()).execute(EARTHQUAKES_URL);
		Intent service = new Intent(getActivity(), MyService.class);
		getActivity().startService(service);
	}

	@Override
	public void onResume() {
		super.onResume();
		getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
				getString(getActivity().getString(R.string.magnitude_list_key), "0");
		
		String where = MyContentProvider.MAGNITUDE + " >= ?";
		String whereArgs[] = { mag };
		String order = "";
		
		CursorLoader loader = new CursorLoader(getActivity(),
			    MyContentProvider.CONTENT_URI, from, where, whereArgs, order);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
		Log.d("TAG", "onLoadFinished()");
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		adapter.swapCursor(null);
		Log.d("TAG", "onLoadReset()");
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Log.d("TAG", "onListItemClick");
		Intent intent = new Intent(getActivity(), EarthQuakeDetails.class);
		intent.putExtra("ID", id);
		Log.d("TAG", "onListItemClick id:" + id);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
	
}

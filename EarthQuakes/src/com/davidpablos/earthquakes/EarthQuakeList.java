package com.davidpablos.earthquakes;

import java.util.ArrayList;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class EarthQuakeList extends ListFragment implements LoaderCallbacks<Cursor> {
	
	private static final String EARTHQUAKES_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	
//	private ArrayList<EarthQuake> earthquakeList;
	private SimpleCursorAdapter adapter;
	
	private final int LOADER_ID = 1;
	
	private String[] from = { MyContentProvider.MAGNITUDE, MyContentProvider.PLACE, MyContentProvider.TIME,
			MyContentProvider.KEY_ID };
	private int[] to = { R.id.magnitude, R.id.place, R.id.time };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
//		this.earthquakeList = new ArrayList<EarthQuake>();
//		adapter = new LazyAdapter(getActivity(), earthquakeList);
//		setListAdapter(this.adapter);
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
		
		getLoaderManager().initLoader(LOADER_ID, null, this);
		
//		db = EarthQuakeDB.getInstance(getActivity());
//		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
//						getString(getActivity().getString(R.string.magnitude_list_key), "0");
//		earthquakeList.addAll(db.query(Double.parseDouble(mag)));
//		adapter.notifyDataSetChanged();
//		
//		new DownloadEarthQuakes(getActivity(), this).execute(EARTHQUAKES_URL);
		
	}

	@Override
	public void onResume() {
		super.onResume();
//		Log.d("TAG", "Earthquake filter");
//		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
//				getString(getActivity().getString(R.string.magnitude_list_key), "0");
//		earthquakeList.addAll(db.query(Double.parseDouble(mag)));
//		adapter.notifyDataSetChanged();
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

//	@Override
//	public void refreshEarthquakesList(ArrayList<EarthQuake> earthquakes) {
//		// TODO Auto-generated method stub
//		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
//				getString(getActivity().getString(R.string.magnitude_list_key), "0");
//
//		for(EarthQuake earthquake: earthquakes) {
//			
//			if(earthquake.getMagnitude() > Double.parseDouble(mag)) {
//				earthquakeList.add(0, earthquake);
//			}
//		}
//		
//		adapter.notifyDataSetChanged();
//		Log.d("TAG", "REFRESH");
//	}
	
}

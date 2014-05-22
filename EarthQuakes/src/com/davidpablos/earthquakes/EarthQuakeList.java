package com.davidpablos.earthquakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.davidpablos.earthquakes.DownloadEarthQuakes.ICallback;

import android.app.ListFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EarthQuakeList extends ListFragment implements ICallback {
	
	private static final String EARTHQUAKES_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	
	private ArrayList<EarthQuake> earthquakeList;
	private ArrayAdapter<EarthQuake> adapter;
	private EarthQuakeDB db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.earthquakeList = new ArrayList<EarthQuake>();
		adapter = new ArrayAdapter<EarthQuake>(inflater.getContext(), android.R.layout.simple_list_item_1, earthquakeList);
		setListAdapter(this.adapter);
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
		
		db = new EarthQuakeDB(getActivity());
		earthquakeList.addAll(db.query(0.0));
		adapter.notifyDataSetChanged();
		
		new DownloadEarthQuakes(getActivity(), this).execute(EARTHQUAKES_URL);
		
//		if(savedInstanceState != null) {
//			// Meter datos en la lista de terremotos
//			//Falta
//			earthquakeList.addAll(db.query(0.0));
//			adapter.notifyDataSetChanged();
//		} else {
//			getEarthQuakes();
//		}
	}
	
//	private void getEarthQuakes() {
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				descargarJSON();
//			}
//		});
//		t.start();
//	}
	

	
//	private void updateList() {
//		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.magnitude_list_key), "0");
//		Log.d("TAG", mag);
//		earthquakeList.clear();
////		earthquakeList.addAll(db.query(Double.parseDouble(mag)));
//		adapter.notifyDataSetChanged();
//	}

	@Override
	public void onResume() {
		super.onResume();
//		updateList();
	}

	@Override
	public void refreshEarthquakesList(ArrayList<EarthQuake> earthquakes) {
		// TODO Auto-generated method stub
		earthquakeList.addAll(earthquakes);
		adapter.notifyDataSetChanged();
		Log.d("TAG", "REFRESH");
	}
	
}

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
	private LazyAdapter adapter;
	private EarthQuakeDB db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.earthquakeList = new ArrayList<EarthQuake>();
		adapter = new LazyAdapter(getActivity(), earthquakeList);
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
		
		db = EarthQuakeDB.getInstance(getActivity());
		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
						getString(getActivity().getString(R.string.magnitude_list_key), "0");
		earthquakeList.addAll(db.query(Double.parseDouble(mag)));
		adapter.notifyDataSetChanged();
		
		new DownloadEarthQuakes(getActivity(), this).execute(EARTHQUAKES_URL);

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("TAG", "Earthquake filter");
		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
				getString(getActivity().getString(R.string.magnitude_list_key), "0");
		earthquakeList.addAll(db.query(Double.parseDouble(mag)));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void refreshEarthquakesList(ArrayList<EarthQuake> earthquakes) {
		// TODO Auto-generated method stub
		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).
				getString(getActivity().getString(R.string.magnitude_list_key), "0");

		for(EarthQuake earthquake: earthquakes) {
			
			if(earthquake.getMagnitude() > Double.parseDouble(mag)) {
				earthquakeList.add(0, earthquake);
			}
		}
		
		adapter.notifyDataSetChanged();
		Log.d("TAG", "REFRESH");
	}
	
}

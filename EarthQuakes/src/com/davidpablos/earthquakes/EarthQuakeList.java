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

import android.app.ListFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EarthQuakeList extends ListFragment {
	
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
		
//		if(savedInstanceState != null) {
//			// Meter datos en la lista de terremotos
//			//Falta
//			earthquakeList.addAll(db.query(0.0));
//			adapter.notifyDataSetChanged();
//		} else {
//			getEarthQuakes();
//		}
	}
	
	private void getEarthQuakes() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				descargarJSON();
			}
		});
		t.start();
	}
	
	private void descargarJSON() {
		JSONObject json;
		Log.d("TAG", "DOWNLOAD");
		try {
			URL url = new URL(EARTHQUAKES_URL);

			// Create a new HTTP URL connection
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try {
					BufferedReader streamReader = new BufferedReader(
							new InputStreamReader(
									httpConnection.getInputStream(), "UTF-8"));
					StringBuilder responseStrBuilder = new StringBuilder();

					String inputStr;
					while ((inputStr = streamReader.readLine()) != null)
						responseStrBuilder.append(inputStr);

					json = new JSONObject(responseStrBuilder.toString());
					processJSON(json);
				} catch (JSONException e) {
					Log.e("500PX",
							"Error al leer el fichero JSON: " + e.getMessage());
				}
			}
		} catch (MalformedURLException e) {
			Log.d("500PX", "Malformed URL Exception.", e);
		} catch (IOException e) {
			Log.d("500PX", "IO Exception.", e);
		}
	}

	private void processJSON(JSONObject json) {
		try {
			JSONArray terremotos = json.getJSONArray("features");
			
			for (int i = 0; i < terremotos.length(); i++) {
				
				EarthQuake earthquake = new EarthQuake();
				JSONObject earthquakeProperties = terremotos.getJSONObject(i).getJSONObject("properties");
				JSONArray earthquakeCoordinates = terremotos.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
				
				earthquake.setPlace(earthquakeProperties.getString("place"));
				earthquake.setTime(earthquakeProperties.getInt("time"));
				earthquake.setDetail(earthquakeProperties.getString("detail"));
				earthquake.setMagnitude(earthquakeProperties.getDouble("mag"));
				earthquake.setLat(earthquakeCoordinates.getDouble(0));
				earthquake.setLng(earthquakeCoordinates.getDouble(1));
				earthquake.setUrl(earthquakeProperties.getString("url"));

				db.insert(earthquake);
			}
			
			ArrayList<EarthQuake> result = db.query(0.0);
			
			for(EarthQuake eq: result) {
				Log.d("TAG", eq.getPlace());
			}
			
		} catch (JSONException e) {
			Log.e("DOWNLOADS", e.getMessage());
		}
	}
	
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
	
}

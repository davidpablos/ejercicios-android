package com.davidpablos.earthquakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("TAG", "Starting service");
		downloadEarthQuakes();
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				Log.d("TAG", "RUN");
//				downloadJSON(EarthQuakeList.EARTHQUAKES_URL);
//				stopSelf();
//			}
//
//		});
		return  Service.START_NOT_STICKY;
	}
	
	private void downloadEarthQuakes() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Log.d("TAG", "RUN");
				downloadJSON(EarthQuakeList.EARTHQUAKES_URL);
				stopSelf();
			}

		});
		t.start();
	}
	
	private ArrayList<EarthQuake> downloadJSON(String stringUrl) {
		ArrayList<EarthQuake> earthquakeList = new ArrayList<EarthQuake>();
		JSONObject json;
		Log.d("TAG", "DOWNLOAD");
		try {
			URL url = new URL(stringUrl);

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
					earthquakeList = processJSON(json);
				} catch (JSONException e) {
					Log.e("EarthQuakes",
							"Error al leer el fichero JSON: " + e.getMessage());
				}
			}
		} catch (MalformedURLException e) {
			Log.d("EarthQuakes", "Malformed URL Exception.", e);
		} catch (IOException e) {
			Log.d("EarthQuakes", "IO Exception.", e);
		}
//		Log.d("TAG", ((Integer)earthquakeList.size()).toString());
		return earthquakeList;
	}

	private ArrayList<EarthQuake> processJSON(JSONObject json) {
		
		ArrayList<EarthQuake> earthquakeList = new ArrayList<EarthQuake>();
		
		try {
			JSONArray earthquakes = json.getJSONArray("features");
			
			for (int i = earthquakes.length()-1; i >= 0; i--) {
				
				EarthQuake earthquake = new EarthQuake();
				JSONObject earthquakeProperties = earthquakes.getJSONObject(i).getJSONObject("properties");
				JSONArray earthquakeCoordinates = earthquakes.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
				
				earthquake.setIdStr(earthquakes.getJSONObject(i).getString("id"));
				earthquake.setPlace(earthquakeProperties.getString("place"));
				earthquake.setTime(earthquakeProperties.getInt("time"));
				earthquake.setDetail(earthquakeProperties.getString("detail"));
				earthquake.setMagnitude(earthquakeProperties.getDouble("mag"));
				earthquake.setLat(earthquakeCoordinates.getDouble(0));
				earthquake.setLng(earthquakeCoordinates.getDouble(1));
				earthquake.setUrl(earthquakeProperties.getString("url"));

				insertEarthQuake(earthquake);
			}

			for(EarthQuake eq: earthquakeList) {
				Log.d("TAG", eq.getPlace());
			}
			
		} catch (JSONException e) {
			Log.e("Exception processing JSON: ", e.getMessage());
		}
		return earthquakeList;
	}
	
	public void insertEarthQuake(EarthQuake earthquake) {
		ContentValues newValues = new ContentValues();

		Date currentDate = new Date();

		newValues.put(MyContentProvider.KEY_ID, earthquake.getId());
		newValues.put(MyContentProvider.ID_STR, earthquake.getIdStr());
		newValues.put(MyContentProvider.PLACE, earthquake.getPlace());
		newValues.put(MyContentProvider.TIME, earthquake.getTime().getTime());
		newValues.put(MyContentProvider.DETAIL, earthquake.getDetail());
		newValues.put(MyContentProvider.MAGNITUDE, earthquake.getMagnitude());
		newValues.put(MyContentProvider.LAT, earthquake.getLat());
		newValues.put(MyContentProvider.LONG, earthquake.getLng());
		newValues.put(MyContentProvider.URL, earthquake.getUrl());
		newValues.put(MyContentProvider.CREATED_AT, currentDate.getTime());
		newValues.put(MyContentProvider.UPDATED_AT, currentDate.getTime());
		
		ContentResolver cr = getContentResolver();
		cr.insert(MyContentProvider.CONTENT_URI, newValues);
	}

}

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadEarthQuakes extends AsyncTask<String, Integer, ArrayList<EarthQuake>> {
	
	public interface ICallback {
		public void refreshEarthquakesList(ArrayList<EarthQuake> earthQuake);
	}
	
//	private static final String EARTHQUAKES_URL = 
//			"http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	private Context context;
	private EarthQuakeDBOpenHelper dbHelper;
	private EarthQuakeDB db;
	private ICallback callback;
	
	public DownloadEarthQuakes(Context context, ICallback callback) {
		this.context = context;
		this.callback = callback;
		dbHelper = new EarthQuakeDBOpenHelper(context,
				EarthQuakeDBOpenHelper.DATABASE_NAME, null,
				EarthQuakeDBOpenHelper.DATABASE_VERSION);
		db = new EarthQuakeDB(context);
	}

	@Override
	protected ArrayList<EarthQuake> doInBackground(String... urls) {
		ArrayList<EarthQuake> earthquakeList = new ArrayList<EarthQuake>();
		int count = urls.length;
		for (int i = 0; i < count; i++) {
			earthquakeList = downloadJSON(urls[i]);
		}
		
		return earthquakeList;
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
		
		return earthquakeList;
	}

	private ArrayList<EarthQuake> processJSON(JSONObject json) {
		
		ArrayList<EarthQuake> earthquakeList = new ArrayList<EarthQuake>();
		
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

				long id = db.insert(earthquake);
				if(id != -1)
					earthquakeList.add(earthquake);
				
//				db.insert(earthquake);
			}
			
//			earthquakeList = db.query(0.0);
			
			for(EarthQuake eq: earthquakeList) {
				Log.d("TAG", eq.getPlace());
			}
			
		} catch (JSONException e) {
			Log.e("DOWNLOADS", e.getMessage());
		}
		return earthquakeList;
	}
	
	protected void onPostExecute(ArrayList<EarthQuake> earthquakeList) {
//		for(EarthQuake eq: earthquakeList) {
//			long id = db.insert(eq);
//			
//			
//			Log.d("TAG", "Terremoto: " + eq.getPlace() + " insertado");
//		}
		
		callback.refreshEarthquakesList(earthquakeList);
	}
}

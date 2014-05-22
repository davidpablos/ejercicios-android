package com.davidpablos.earthquakes;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private static final String TAG = "EARTHQUAKES";
	private static final String EARTHQUAKES_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

	private long myDownloadReference;
	private DownloadManager downloadManager;
	String serviceString = Context.DOWNLOAD_SERVICE;
	EarthQuakeDB db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

//		db = new EarthQuakeDB(this);
		// EarthQuake earthquake = new EarthQuake("Errenteria",
		// Long.valueOf("1400580039851"), "detail", 9.0, 1.0, 2.0,
		// "http://www.arkaitzgarro.com/");
		// db.insert(earthquake);
		// earthquake.setMagnitude(100.0);
		// db.update(1, earthquake);
		// ArrayList<EarthQuake> result = db.query(9.0);
		// Log.d("TAG", result.get(0).getPlace());
		// // Log.d("TAG", result.get(0).getTime().getTime());
		// Log.d("TAG", result.get(0).getDetail());
		// Log.d("TAG", result.get(0).getMagnitude().toString());
		// Log.d("TAG", result.get(0).getLat().toString());
		// Log.d("TAG", result.get(0).getLng().toString());
		// Log.d("TAG", result.get(0).getUrl());

//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				descargarJSON();
//			}
//		});
//		t.start();
		
		Log.d("TAG", "FRAGMENT");
		
		if(savedInstanceState == null) {
			Log.d("TAG", "FRAGMENT");
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.container, new EarthQuakeList(), "earthquakeList");
			fragmentTransaction.commit();
			Log.d("TAG", "FRAGMENT");
		}
	}

//	private void descargarJSON() {
//		JSONObject json;
//		Log.d("TAG", "DOWNLOAD");
//		try {
//			URL url = new URL(EARTHQUAKES_URL);
//
//			// Create a new HTTP URL connection
//			URLConnection connection = url.openConnection();
//			HttpURLConnection httpConnection = (HttpURLConnection) connection;
//
//			int responseCode = httpConnection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				try {
//					BufferedReader streamReader = new BufferedReader(
//							new InputStreamReader(
//									httpConnection.getInputStream(), "UTF-8"));
//					StringBuilder responseStrBuilder = new StringBuilder();
//
//					String inputStr;
//					while ((inputStr = streamReader.readLine()) != null)
//						responseStrBuilder.append(inputStr);
//
//					json = new JSONObject(responseStrBuilder.toString());
//					processJSON(json);
//				} catch (JSONException e) {
//					Log.e("500PX",
//							"Error al leer el fichero JSON: " + e.getMessage());
//				}
//			}
//		} catch (MalformedURLException e) {
//			Log.d("500PX", "Malformed URL Exception.", e);
//		} catch (IOException e) {
//			Log.d("500PX", "IO Exception.", e);
//		}
//	}
//
//	private void processJSON(JSONObject json) {
//		try {
//			JSONArray terremotos = json.getJSONArray("features");
//			
//			for (int i = 0; i < terremotos.length(); i++) {
//				
//				EarthQuake earthquake = new EarthQuake();
//				JSONObject earthquakeProperties = terremotos.getJSONObject(i).getJSONObject("properties");
//				JSONArray earthquakeCoordinates = terremotos.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
//				
//				earthquake.setPlace(earthquakeProperties.getString("place"));
//				earthquake.setTime(earthquakeProperties.getInt("time"));
//				earthquake.setDetail(earthquakeProperties.getString("detail"));
//				earthquake.setMagnitude(earthquakeProperties.getDouble("mag"));
//				earthquake.setLat(earthquakeCoordinates.getDouble(0));
//				earthquake.setLng(earthquakeCoordinates.getDouble(1));
//				earthquake.setUrl(earthquakeProperties.getString("url"));
//
//				db.insert(earthquake);
//			}
//			
//			ArrayList<EarthQuake> result = db.query(0.0);
//			
//			for(EarthQuake eq: result) {
//				Log.d("TAG", eq.getPlace());
//			}
//			
//		} catch (JSONException e) {
//			Log.e("DOWNLOADS", e.getMessage());
//		}
//	}

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
}

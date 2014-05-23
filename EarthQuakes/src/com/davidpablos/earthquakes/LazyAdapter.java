package com.davidpablos.earthquakes;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<EarthQuake> arrayQuakes;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity a, ArrayList<EarthQuake> arrayQuakes) {
        activity = a;
        this.arrayQuakes=arrayQuakes;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		return arrayQuakes.size();
	}

	@Override
	public EarthQuake getItem(int position) {
		return arrayQuakes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi=convertView;
        if(convertView==null){
            vi = inflater.inflate(R.layout.list_row, null);
        }    
 
        TextView mag = (TextView)vi.findViewById(R.id.magnitude); 
        TextView place = (TextView)vi.findViewById(R.id.place); 
        TextView time = (TextView)vi.findViewById(R.id.time); 
 
        EarthQuake quake = arrayQuakes.get(position);
 
        // Setting all values in listview
        mag.setText(String.valueOf(quake.getMagnitude()));
        place.setText(quake.getPlace());
//        time.setText(quake.getTimeFormated());
        
        return vi;		

	}

}
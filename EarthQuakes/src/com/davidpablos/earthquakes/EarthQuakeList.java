package com.davidpablos.earthquakes;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EarthQuakeList extends ListFragment {
	
	private ArrayList<String> earthquakeList;
	private ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.earthquakeList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, earthquakeList);
		setListAdapter(this.adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	public void anadir(String txt) {
		earthquakeList.add(0, txt);
		adapter.notifyDataSetChanged();
	}
}

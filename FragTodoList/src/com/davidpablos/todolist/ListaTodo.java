package com.davidpablos.todolist;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ListaTodo extends ListFragment {
	private ArrayList<String> mainList;
	private ArrayAdapter<String> adapter;
	
	/**
	 * onCreateView
	 * onActivityCreated
	 * onSaveInstanceState
	 * 
	 * @param txt
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.mainList = new ArrayList<String>();
		this.adapter = new ArrayAdapter<String>(context, textViewResourceId);
		setListAdapter(this.adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	public void anadir(String txt) {
		mainList.add(0, txt);
		adapter.notifyDataSetChanged();
	}
}
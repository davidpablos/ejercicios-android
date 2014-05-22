package com.davidpablos.earthquakes;

import java.util.Date;

import android.text.format.DateFormat;

public class EarthQuake {
	
	private String place;
	private Date time;
	private String detail;
	private Double magnitude;
	private Double lat;
	private Double lng;
	private String url;
	
	
	public EarthQuake() {
	}
	
	public EarthQuake(String place, long time, String detail, Double magnitude,
			Double lat, Double lng, String url) {
		this.place = place;
		this.time = new Date(time);
		this.detail = detail;
		this.magnitude = magnitude;
		this.lat = lat;
		this.lng = lng;
		this.url = url;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = new Date(time);
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(Double magnitude) {
		this.magnitude = magnitude;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Mag: " + magnitude + "\nTime: " + DateFormat.format("yyyy-MM-dd hh:mm:ss", time) + "\nPlace: " + place;
	}
}

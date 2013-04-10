package com.example.plansearch;

import java.util.*;

import com.example.plansearch.Transmit.Position;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.os.Bundle;

public class BackgroundWorker implements LocationListener {
	private LocationManager locationManager = null;
	private Timer timer;
	private BackgroundWorker(){
		
	}
	public static BackgroundWorker Instance = new BackgroundWorker();
	
	
	public void Start(Activity act){
		locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
	    
	    timer = new Timer("network-looper", true);
		timer.scheduleAtFixedRate(new NetworkLooper(this), 1000, 10000);
	}
	public void Stop()
	{
		locationManager.removeUpdates(this);
		timer.cancel();
	}
	private List<LocationChangedListener> locChangeListeners = new ArrayList<LocationChangedListener>();
	private List<PulseListener> pulseListeners = new ArrayList<PulseListener>();
	public void AddLocationChangeListener(LocationChangedListener listener)
	{
		locChangeListeners.add(listener);
	}
	public void RemoveLocationChangeListener(LocationChangedListener listener)
	{
		locChangeListeners.remove(listener);
	}
	public void AddPulseListener(PulseListener listener)
	{
		pulseListeners.add(listener);
	}
	public void RemovePulseListener(PulseListener listener)
	{
		pulseListeners.remove(listener);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
		for (LocationChangedListener listener: locChangeListeners)
		{
			listener.LocationChanged(pos);
		}
		synchronized (Transmit.lock)
		{
			Transmit.positionsQueue.add(new Position(0, Transmit.userID, true, pos.latitude, pos.longitude, (int)(System.currentTimeMillis() / 1000L)));
		}
	}
	
	
	private class NetworkLooper extends TimerTask
	{
		private BackgroundWorker _ref;
		public NetworkLooper(BackgroundWorker ref)
		{
			_ref = ref;
		}
		@Override
		public void run()
		{
			synchronized(Transmit.lock)
			{
				Transmit.transmitLogs();
				Transmit.receiveLogs();
			}
			for (PulseListener listener: _ref.pulseListeners)
			{
				listener.Pulse();
			}
			//runOnUiThread(_ref);
			//TODO
		}
		
	}
	
	
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}

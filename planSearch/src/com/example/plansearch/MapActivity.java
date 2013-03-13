package com.example.plansearch;

import com.example.plansearch.*;
import com.example.plansearch.Coordinates.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;

public class MapActivity extends FragmentActivity {

	GoogleMap map = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		
		Button btn = (Button) findViewById(R.id.mapTypeBtn);  
        registerForContextMenu(btn);  
		
		SupportMapFragment mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		
		map = mapFrag.getMap();
		PolygonOptions polygonOptions = new PolygonOptions();
		polygonOptions.add(new LatLng(59.9494,10.7564),new LatLng(63.4400, 10.4000), new LatLng(60.3800, 5.3400));
		polygonOptions.fillColor(0x66000000);
		polygonOptions.strokeColor(0x00C0FFEE);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
		PolygonOptions[] polys = CreateLines(10,0x55FF0000,
				new LatLng(63.4329,10.3917),
				new LatLng(63.4264,10.3933),
				new LatLng(63.4230,10.3943),
				new LatLng(63.4242,10.3867),
				new LatLng(63.4243,10.3865),
				new LatLng(63.4276,10.3848));
		for(PolygonOptions pol: polys)
		{
			map.addPolygon(pol);
		}
		map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(63.44,10.4)));
		map.moveCamera(CameraUpdateFactory.zoomBy(12f));
	}
	
	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Map Type"); 
        for (int i = 0; i < mapTypes.length;i++)
        {
        	MapType mt = mapTypes[i];
        	menu.add(0, mt.MapType, 0, mt.Name);
        }
        //menu.add(0, (int)MapType.Satellite, 0, "Satellite");  
    } 
	private final MapType[] mapTypes = new MapType[]{new MapType("Street", GoogleMap.MAP_TYPE_NORMAL),
													new MapType("Satellite", GoogleMap.MAP_TYPE_SATELLITE),
													new MapType("None", GoogleMap.MAP_TYPE_NONE),
													new MapType("Terrain", GoogleMap.MAP_TYPE_TERRAIN),
													new MapType("Hybrid", GoogleMap.MAP_TYPE_HYBRID)};
	private class MapType
	{
		public MapType(String name, int mapType)
		{
			MapType = mapType;
			Name = name;
		}
		public int MapType;
		public String Name;
	}
	public void mapTypeMenu(View v)
	{
		this.openContextMenu(v);
	}
	
	@Override  
    public boolean onContextItemSelected(MenuItem item) {  
		int mapType = item.getItemId();
		map.setMapType(mapType);
        return true;  
    }  
	

	public static PolygonOptions[] CreateLines(double width, int color, LatLng... pos)
	{
		
		PolygonOptions[] polys = new PolygonOptions[pos.length-1];
		for (int i = 0; i < pos.length-1;i++)
		{
			polys[i] = CreateLine(pos[i],pos[i+1],width, color);
		}
		return polys;
	}
	public static PolygonOptions CreateLine(LatLng start, LatLng end, double width, int color)
	{
		PolygonOptions poly = new PolygonOptions();
		poly.strokeColor(0x00C0FFEE);
		poly.fillColor(color);
		
		CoordinateConversion conv = new CoordinateConversion();
		UTM utmStart = conv.latLon2UTM(start.latitude, start.longitude);
		UTM utmEnd = conv.latLon2UTM(end.latitude, end.longitude);
		
		double dEast = utmEnd.Easting - utmStart.Easting;
		double dNorth = utmEnd.Northing - utmStart.Northing;
		double length = Math.sqrt(dEast*dEast+dNorth*dNorth);
		double normEast = dEast/length;
		double normNorth = dNorth/length;
		
		double eastDist = normEast*width/2;
		double northDist = -normNorth*width/2;
		
		poly.add(conv.utm2LatLon(utmStart.AddDistance(-northDist, -eastDist)),
				conv.utm2LatLon(utmEnd.AddDistance(-northDist, -eastDist)),
				conv.utm2LatLon(utmEnd.AddDistance(northDist, eastDist)),
				conv.utm2LatLon(utmStart.AddDistance(northDist, eastDist)));
				
		return poly;
	}
	
}

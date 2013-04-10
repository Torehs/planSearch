package com.example.plansearch;

import java.util.*;

import com.example.plansearch.*;
import com.example.plansearch.Transmit.Position;
import com.example.plansearch.Coordinates.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;

public class MapActivity extends FragmentActivity implements LocationChangedListener, PulseListener, Runnable {
	private GoogleMap map = null;
	private boolean followOnMap = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		
		Button btn = (Button) findViewById(R.id.mapTypeBtn);  
        registerForContextMenu(btn);
        
        CheckBox followChk = (CheckBox)findViewById(R.id.followChk);
        followChk.setChecked(true);
        
        
		SupportMapFragment mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		
		map = mapFrag.getMap();
		PolygonOptions polygonOptions = new PolygonOptions();
		polygonOptions.add(new LatLng(59.9494,10.7564),new LatLng(63.4400, 10.4000), new LatLng(60.3800, 5.3400));
		polygonOptions.fillColor(0x66000000);
		polygonOptions.strokeColor(0x00C0FFEE);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.setMyLocationEnabled(true);
		        
		
		
//		DrawLines(10,0x55FF0000,
//				new LatLng(63.4329,10.3917),
//				new LatLng(63.4264,10.3933),
//				new LatLng(63.4230,10.3943),
//				new LatLng(63.4242,10.3867),
//				new LatLng(63.4243,10.3865),
//				new LatLng(63.4276,10.3848));
		map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(63.44,10.4)));
		map.moveCamera(CameraUpdateFactory.zoomBy(12f));
		
		//NewDraw(30);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		BackgroundWorker.Instance.AddLocationChangeListener(this);
		BackgroundWorker.Instance.AddPulseListener(this);
	}
	@Override
	protected void onPause() {
		super.onPause();
		BackgroundWorker.Instance.RemoveLocationChangeListener(this);
		BackgroundWorker.Instance.RemovePulseListener(this);
	}
	
	private void NewDraw(double width)
	{
		
		LatLng latLng = new LatLng(63.44,10.4);
		
		UTM pos1 = conv.latLon2UTM(latLng);
		
		UTM pos2 = pos1.AddDistance(100, -100);
		//UTM pos2 = pos1.AddDistance(-100, -100);
		//UTM pos3 = pos1.AddDistance(0, -200);
		UTM pos3 = pos1.AddDistance(200, 0);
		//UTM pos3 = pos1.AddDistance(500, -200);
		
		PolygonOptions poly = StartRoute(pos1, pos2,width/2.0);
		AddCorner(pos1, pos2, pos3, width/2.0, poly, true);
		Turn(pos2,pos3,width/2.0,poly);
		AddCorner(pos3, pos2, pos1, width/2.0, poly,false);
		
		//poly.strokeColor(0x00C0FFEE);
		poly.fillColor(0x550000FF);
		
		map.addPolygon(poly);
	}
	
	private PolygonOptions StartRoute(UTM pos, UTM next, double width)
	{
		PolygonOptions poly = new PolygonOptions();
		double[] perp = GetPerpendicularVector(pos, next);
		
		poly.add(conv.utm2LatLon(pos.AddDistance(-perp[1]*width, -perp[0]*width)));
		poly.add(conv.utm2LatLon(pos.AddDistance(perp[1]*width, perp[0]*width)));
		
		return poly;
	}
	
	private void AddCorner(UTM prev, UTM pos, UTM next, double width, PolygonOptions poly, boolean swtch)
	{
		double[] perp1 = GetPerpendicularVector(prev, pos);
		//double[] perp2 = GetPerpendicularVector(next, pos);
		double[] perp2 = GetPerpendicularVector(pos, next);
		/*double easting = (perp1[1]+perp2[1])*width;
		double northing = (perp1[0]+perp2[0])*width;
		
		
		if (perp1[0] > 0)
			easting = -easting;
		if (perp1[1] > 0)
			northing = -northing;*/
		//double easting = 0.0;
		//double northing = 0.0;
		
		Coeff c1 = new Coeff(prev,pos);
		Coeff c2 = new Coeff(pos,next);
		//System.out.println(String.format("c1: E: %.2f, N: %.2f \tc2: E %.2f, N: %.2f",c1.E,c1.N,c2.E,c2.N));
		//int swapR = c2.N < 0 ? -1 : 1;
		//int swapR = 1;
		//int swapP = 1;
		//int swapR = next.Easting < pos.Easting && next.Northing > pos.Northing ? -1 : 1;
		//int swapP = prev.Easting < pos.Easting && prev.Northing < pos.Northing ? -1 : 1;
		//int swapR = next.Northing > pos.Northing ? -1 : 1;
		//int swapP = prev.Northing < pos.Northing ? -1 : 1;
		double angle = angleBetween2Lines(prev, pos, pos, next);
		System.out.println("Angle: " + angle);
		
		int swapR = angle < 0.0 ? -1 : 1;
		int swapP = swapR;
		
		UTM p1 = prev.AddDistance(swapP*perp1[1]*width, swapP*perp1[0]*width);
		UTM p2 = pos.AddDistance(swapP*perp1[1]*width, swapP*perp1[0]*width);
		Coeff pc = new Coeff(p1, p2);
		
		UTM r1 = next.AddDistance(swapR*-perp2[1]*width, swapR*-perp2[0]*width);
		UTM r2 = pos.AddDistance(swapR*-perp2[1]*width, swapR*-perp2[0]*width);
		Coeff rc = new Coeff(r1, r2);
		
		
		UTM intr = GetIntersection(p1, r1, pc, rc);
		if (swtch)
		{
			AddMarker(p1, "p1");
			AddMarker(p2, "p2");
			AddMarker(r1, "r1");
			AddMarker(r2, "r2");
			AddMarker(intr, "intr");
		}
		poly.add(conv.utm2LatLon(intr));
		
		//System.out.println(String.format("perp1: 0: %.2f, 1: %.2f \tperp2: 0 %.2f, 1: %.2f",perp1[0],perp1[1],perp2[0],perp2[1]));
		//System.out.println(String.format("pc: E: %.2f, N: %.2f \trc: E %.2f, N: %.2f",pc.E,pc.N,rc.E,rc.N));
		//poly.add(conv.utm2LatLon(pos.AddDistance(easting, northing)));
		
	}
	
	
	public static double angleBetween2Lines(UTM line1Start, UTM line1End, UTM line2Start, UTM line2End)
    {
        double angle1 = Math.atan2(line1Start.Northing - line1End.Northing,
                                   line1Start.Easting - line1End.Easting);
        double angle2 = Math.atan2(line2Start.Northing - line2End.Northing,
                					line2Start.Easting - line2End.Easting);
        double angle = angle1-angle2;
        if (angle > Math.PI)
        	angle -= 2*Math.PI;
        else if (angle < -Math.PI)
        	angle += 2*Math.PI;
        return angle;
    }
	
	
	private void AddMarker(UTM pos,String text)
	{
		MarkerOptions opt = new MarkerOptions();
		opt.position(conv.utm2LatLon(pos));
		opt.title(text);
		map.addMarker(opt);
	}
	
	private void Turn(UTM prev, UTM pos, double width, PolygonOptions poly)
	{
		double[] perp = GetPerpendicularVector(prev, pos);
		poly.add(conv.utm2LatLon(pos.AddDistance(-perp[1]*width, -perp[0]*width)));
		poly.add(conv.utm2LatLon(pos.AddDistance(perp[1]*width, perp[0]*width)));
	}
	
	private UTM GetIntersection(UTM pos1, UTM pos2, Coeff ce1, Coeff ce2)
	{
		double r = (pos2.Easting - pos1.Easting + ce2.E/ce2.N*(pos1.Northing - pos2.Northing))/(ce1.E - ce1.N*ce2.E/ce2.N);
        return pos1.AddDistance(ce1.E*r, ce1.N*r);
	}
	
	private class Coeff
	{
		public Coeff(double easting, double northing)
		{
			E = easting;//X
			N = northing;//Y
		}
		public Coeff(UTM pos1, UTM pos2)
		{
			E = pos1.Easting - pos2.Easting;
			N = pos1.Northing - pos2.Northing;
		}
		public double E;
		public double N;
	}
	private static CoordinateConversion conv = new CoordinateConversion();
	private static LatLng AddDistance(LatLng pos, double easting, double northing)
	{
		UTM utm = conv.latLon2UTM(pos.latitude, pos.longitude);
		return conv.utm2LatLon(utm.AddDistance(easting, northing));
	}
	//private static double[] GetNormal(LatLng)
	
	
	
	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Map Type");
        int curType = map.getMapType();
        for (int i = 0; i < mapTypes.length;i++)
        {
        	MapType mt = mapTypes[i];
        	MenuItem item = menu.add(0, mt.MapType, 0, mt.Name);
        	
        	if (curType == mt.MapType)
        	{
        		item.setCheckable(true);
        		item.setChecked(true);
        	}
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
	
	
	public void callBtnClick(View v)
	{
		String phno="tel:47027635";

	    Intent i=new Intent(Intent.ACTION_CALL,Uri.parse(phno));
	    startActivity(i);
	}
	
	public void infoBtnClick(View v)
	{
		Intent i = new Intent(this,Menu.class);
		startActivity(i);
	}
	
	@Override  
    public boolean onContextItemSelected(MenuItem item) {  
		int mapType = item.getItemId();
		map.setMapType(mapType);
        return true;  
    }  
	public void DrawLines(double width, int color, LatLng... pos)
	{
		PolygonOptions[] lines = CreateLines(width, color, pos);
		for(int i = 0; i < lines.length;i++)
		{
			map.addPolygon(lines[i]);
		}
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
	
	private static double[] GetPerpendicularVector(UTM start, UTM end)
	{
		double dEast = end.Easting - start.Easting;
		double dNorth = end.Northing - start.Northing;
		double length = Math.sqrt(dEast*dEast+dNorth*dNorth);
		double normEast = dEast/length;
		double normNorth = dNorth/length;
		return new double[]{normNorth, -normEast};
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
	
	
	public void followPositionClick(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    followOnMap = checked;
	}
	
	@Override
	public void LocationChanged(LatLng pos) {
		if (followOnMap)
		{
			CameraPosition cam = map.getCameraPosition();
			map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(pos, cam.zoom, cam.tilt, cam.bearing)));
		}
	}
	
	private SparseArray<Paths> userPaths = new SparseArray<Paths>();
	private int nextIndex = 0;
	private class Paths
	{
		List<Path> Paths = new ArrayList<Path>();
		boolean StartNew = false;
		Marker Marker = null;
	}
	private class Path
	{
		public List<LatLng> positions = new ArrayList<LatLng>();
		public int LastDrawnTo = 0;
	}
	
	private void DrawFromPaths()
	{
		for(int i = 0; i < userPaths.size();i++)
		{
			LatLng pos = null;
			Paths paths = userPaths.valueAt(i);
			int uid = userPaths.keyAt(i);
			boolean isSelf = uid == Transmit.userID;
			int color = isSelf ? 0x66FF0000 : 0x660000FF; 
			for (Path path: paths.Paths)
			{
				for (;path.LastDrawnTo < path.positions.size()-1;path.LastDrawnTo++)
				{
					DrawLines(10, color, path.positions.get(path.LastDrawnTo), path.positions.get(path.LastDrawnTo+1));
				}
				pos = path.positions.get(path.positions.size()-1);
			}
			if (pos != null && !isSelf)
			{
				if (paths.Marker == null)
				{
					MarkerOptions mrk = new MarkerOptions();
					mrk.position(pos);
					mrk.title(Transmit.getUserName(uid));
					paths.Marker = map.addMarker(mrk);
				}
				paths.Marker.setPosition(pos);
				
			}
		}
	}

	@Override
	public void Pulse() {
		runOnUiThread(this);
	}
	//To update map with new coordinates from users
	@Override
	public void run() {
		synchronized(Transmit.positions)
		{
			List<Position> pos = Transmit.positions;
			for (;nextIndex < pos.size();nextIndex++)
			{
				Position step = pos.get(nextIndex);
				Paths paths = userPaths.get(step.userID);
				if (paths == null)
					paths = new Paths();
				userPaths.append(step.userID, paths);
				if (!step.logType)
				{
					paths.StartNew = true;
				}
				else
				{
					Path path;
					if (paths.StartNew || paths.Paths.size() == 0)
					{
						path = new Path();
						paths.Paths.add(path);
						paths.StartNew = false;
					}
					else
						path = paths.Paths.get(paths.Paths.size()-1);
					path.positions.add(new LatLng(step.latitude,step.longitude));
				}
			}
		}
		DrawFromPaths();
	}
}

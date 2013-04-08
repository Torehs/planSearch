package com.example.plansearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	
		if(newUser())
		{
			System.out.println("no nr registered");
			Intent i = new Intent(this,RegUser.class); 
			startActivity(i);
		}
        
    }
	
	public void createClick(View view)
	{
		System.out.println("create click");
		Intent i = new Intent(this,Create.class); 
		startActivity(i); 
	}
	
	public void joinClick(View view)
	{
		System.out.println("join click");
		Intent i = new Intent(this,Join.class); 
		startActivity(i); 
	}

	public void mapClick(View view)
	{
		System.out.println("map click");
		Intent i = new Intent(this,MapActivity.class); 
		startActivity(i); 
	}
	
	public boolean newUser()
	{	
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		
		int phoneNr = prefs.getInt("phoneNr", 0);
		
		if(phoneNr == 0)
			return true;
		else
			return false;
	}
}

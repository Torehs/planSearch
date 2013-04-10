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
			Intent i = new Intent(this,RegUser.class); 
			startActivity(i);
		}
        
    }
	
	public void createClick(View view)
	{
		Intent i = new Intent(this,Create.class); 
		startActivity(i); 
	}
	
	public void joinClick(View view)
	{
		Intent i = new Intent(this,Join.class); 
		startActivity(i); 
	}
	
	public boolean newUser()
	{	
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		
		int userID = prefs.getInt("userID", 0);
		String userPassword = prefs.getString("userPassword", "");
		
		if(userID == 0)
			return true;
		else
			Transmit.login(userID, userPassword);
			return false;
	}
}

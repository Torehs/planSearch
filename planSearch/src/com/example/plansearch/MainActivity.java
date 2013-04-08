package com.example.plansearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
		if(getMyPhoneNumber() != null)
		{
			super.onCreate(savedInstanceState);
        	setContentView(R.layout.activity_main);
		}
		else
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.reg_user_main);
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
	
	private String getMyPhoneNumber(){
	    TelephonyManager mTelephonyMgr;
	    mTelephonyMgr = (TelephonyManager)getSystemService(MainActivity.TELEPHONY_SERVICE); 
	    return mTelephonyMgr.getLine1Number();
	}
}

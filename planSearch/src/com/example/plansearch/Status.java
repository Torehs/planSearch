package com.example.plansearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Status extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_status, menu);
		return true;
	}
	
	public void clickHeading(View view)
	{
		String error = Transmit.setMemberStatus("Heading to search area");
		
		if ("ok".equals(error))
		{
			Toast.makeText(getApplicationContext(), "Status updated", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void clickSearching(View view)
	{
		String error = Transmit.setMemberStatus("Searching area");
		
		if ("ok".equals(error))
		{
			Toast.makeText(getApplicationContext(), "Status updated", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void clickDone(View view)
	{
		String error = Transmit.setMemberStatus("Done searching area");
		
		if ("ok".equals(error))
		{
			Toast.makeText(getApplicationContext(), "Status updated", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void clickMedical(View view)
	{
		String error = Transmit.setMemberStatus("Need medical assistance!");
		
		if ("ok".equals(error))
		{
			Toast.makeText(getApplicationContext(), "Status updated", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
		}
		
	}

}

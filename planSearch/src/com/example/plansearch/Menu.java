package com.example.plansearch;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}
	
	public void infoClick(View view)
	{
		Intent i = new Intent(this,Info.class); 
		startActivity(i); 
	}

	public void statusClick(View view)
	{
		Intent i = new Intent(this,Status.class); 
		startActivity(i); 
	}
}

package com.example.plansearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Create extends Activity {
	

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_main);
	}
	
	public void createClick(View view)
	{
		if (Transmit.createOperation("Navn paa leteaksjon", "mitthemmeligepassord")) {
			System.out.println("create click");
			Intent i = new Intent(this,Info.class); 
			startActivity(i);
		}
		
	}
}

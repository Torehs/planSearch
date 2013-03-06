package com.example.plansearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Join extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_main);

	}
	
	public void joinClick(View view)
	{
		Intent i = new Intent(this,Menu.class); 
		startActivity(i); 
	}

}

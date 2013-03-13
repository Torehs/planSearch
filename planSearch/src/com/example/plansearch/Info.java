package com.example.plansearch;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class Info extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main);
        

	}
	
	public void saveClick(View view)
	{
		Intent i = new Intent(this,Menu.class); 
		startActivity(i); 
	}

}

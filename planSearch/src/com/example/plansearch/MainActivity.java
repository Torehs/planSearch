package com.example.plansearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
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

}

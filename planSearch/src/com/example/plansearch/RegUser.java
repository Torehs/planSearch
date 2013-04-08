package com.example.plansearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegUser extends Activity {
	
	EditText name;
	EditText tlf;
	EditText tilhor;

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user_main);

	}
	
	public void saveClick(View view)
	{
		name = (EditText)findViewById(R.id.name);
		tlf = (EditText)findViewById(R.id.tlf);
		tilhor = (EditText)findViewById(R.id.tilhor);
		
		saveNr(Integer.parseInt(tlf.getText().toString()));
		
		System.out.println("save click");
		Intent i = new Intent(this,MainActivity.class); 
		startActivity(i);
		
	}
	
	public void saveNr(int phoneNr)
	{
		
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		prefs.edit().putInt("phoneNr", phoneNr).commit();
	}
	

}

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
	EditText password;
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
		password = (EditText)findViewById(R.id.password);
		
		String error = Transmit.createUser(name.getText().toString(), password.getText().toString(), tlf.getText().toString(), tilhor.getText().toString());
		
		if ("".equals(error))
		{
			saveNr(Integer.parseInt(tlf.getText().toString()));
			savePass(password.getText().toString());
			
			Intent i = new Intent(this,MainActivity.class); 
			startActivity(i);
		}
		
	}
	
	public void saveNr(int phoneNr)
	{
		
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		prefs.edit().putInt("phoneNr", phoneNr).commit();
	}
	
	public void savePass(String pass)
	{
		
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		prefs.edit().putString("pass", pass).commit();
	}
	

}

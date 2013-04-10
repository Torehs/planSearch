package com.example.plansearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegUser extends Activity {
	
	EditText name;
	EditText phone;
	EditText organization;
	EditText password;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user_main);

	}
	
	public void saveClick(View view)
	{
		name = (EditText)findViewById(R.id.name);
		phone = (EditText)findViewById(R.id.phone);
		organization = (EditText)findViewById(R.id.organization);
		password = (EditText)findViewById(R.id.password);
		
		if (name.getText().toString().trim().equals("") || phone.getText().toString().trim().equals("") || organization.getText().toString().trim().equals("") || password.getText().toString().trim().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String error = Transmit.createUser(name.getText().toString(), phone.getText().toString(), organization.getText().toString(), password.getText().toString());
			
			if ("".equals(error))
			{
				saveUserID(Transmit.userID);
				saveUserPassword(Transmit.userPassword);
				
				Intent i = new Intent(this,MainActivity.class); 
				startActivity(i);
				finish();
			}
			else
			{
				Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public void saveUserID(int userID)
	{
		
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		prefs.edit().putInt("userID", userID).commit();
	}
	
	public void saveUserPassword(String userPassword)
	{
		
		SharedPreferences prefs = this.getSharedPreferences("com.example.plansearch", Context.MODE_PRIVATE);
		prefs.edit().putString("userPassword", userPassword).commit();
	}
	

}

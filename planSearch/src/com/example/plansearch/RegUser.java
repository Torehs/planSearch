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
	EditText phone;
	EditText organization;

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
		
		String error = Transmit.createUser(name.getText().toString(), password.getText().toString(), phone.getText().toString(), organization.getText().toString());
		
		if ("".equals(error))
		{
			saveUserID(Transmit.userID);
			saveUserPassword(Transmit.userPassword);
			
			Intent i = new Intent(this,MainActivity.class); 
			startActivity(i);
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

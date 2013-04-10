package com.example.plansearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Create extends Activity {
	
	EditText name;
	EditText pw;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_main);
	}
	
	public void createClick(View view)
	{
		name = (EditText)findViewById(R.id.editText1);
		pw = (EditText)findViewById(R.id.editText2);
		
		if (name.getText().toString().trim().equals("") || pw.getText().toString().trim().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String error = Transmit.createOperation(name.getText().toString(), pw.getText().toString());
			
			if ("".equals(error))
			{
				Intent i = new Intent(this,Info.class); 
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
			}
		}

	}
}

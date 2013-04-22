package com.example.plansearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_main);

	}
	
	public void joinClick(View view)
	{
		EditText id = (EditText)findViewById(R.id.join);
		EditText pw = (EditText)findViewById(R.id.password);
		
		if (id.getText().toString().trim().equals("") || pw.getText().toString().trim().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String error = Transmit.joinOperation(Integer.parseInt(id.getText().toString()), pw.getText().toString());
			
			if ("ok".equals(error))
			{
				Intent i = new Intent(this,Menu.class);
            	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
			}	
		}
		
	}

}

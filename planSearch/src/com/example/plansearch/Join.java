package com.example.plansearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
		
		
		if(Transmit.joinOperation(Integer.parseInt(id.getText().toString()), pw.getText().toString()))
		{
			Intent i = new Intent(this,Menu.class); 
			startActivity(i); 
		}
		else
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Join.this);
			
			alertDialogBuilder.setTitle("Wrong pw");
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			alertDialog.show();
		}
		
//		if(Transmit.operationID == Integer.parseInt(id.getText().toString()) && Transmit.operationPassword == pw.getText().toString())
//		{
//			Intent i = new Intent(this,Menu.class); 
//			startActivity(i); 
//		}
//		else
//		{
//			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Join.this);
//			
//			alertDialogBuilder.setTitle("Wrong pw");
//			
//			AlertDialog alertDialog = alertDialogBuilder.create();
//			
//			alertDialog.show();
//			//FYFY
//		}
		
//		Intent i = new Intent(this,Menu.class); 
//		startActivity(i); 
	}

}

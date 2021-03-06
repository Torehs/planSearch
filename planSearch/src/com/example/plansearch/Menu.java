package com.example.plansearch;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		BackgroundWorker.Instance.Start(this);
	}

	public void infoClick(View view)
	{
		Intent i = new Intent(this,Info.class);
		startActivity(i);
	}

	public void membersClick(View view)
	{
		Intent i = new Intent(this,Members.class);
		startActivity(i);
	}

	public void statusClick(View view)
	{
		Intent i = new Intent(this,Status.class);
		startActivity(i);
	}

	public void mapClick(View view)
	{
		System.out.println("map click");
		Intent i = new Intent(this,MapActivity.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle("Exit operation?")
	        .setMessage("Are you sure you want to exit?")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	            	BackgroundWorker.Instance.Stop();
	            	Transmit.onExit();
	            	finish();
	            	Intent iMainActivity = new Intent(Menu.this, MainActivity.class);
	            	iMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	            	iMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(iMainActivity);

	            }
	        }).create().show();
	}
}

package com.example.plansearch;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class Members extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_main);
        
        Transmit.members.clear();
        String error = Transmit.getOperationMembers(Transmit.operationID);
        
        TextView operationMembers = (TextView)findViewById(R.id.textView2);
        
        String membersString = "";
        
        if ("ok".equals(error)) {
            for (int i = 0; i < Transmit.members.size(); i++)
            {
            	membersString = Transmit.members.get(i).memberName+"\n"+
            	Transmit.members.get(i).memberPhone+"\n"+Transmit.members.get(i).memberOrganization+"\n"+
            	Transmit.members.get(i).memberStatus+"\n\n"+membersString;
            }
        }
		else
		{
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
		}
        
		operationMembers.setText(membersString);
        
	}

}

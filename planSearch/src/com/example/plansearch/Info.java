package com.example.plansearch;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class Info extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main);
        
        EditText opName = (EditText)findViewById(R.id.opName);
        TextView opID = (TextView)findViewById(R.id.opID);
        EditText opPW = (EditText)findViewById(R.id.opPW);
        EditText startP = (EditText)findViewById(R.id.startingPoint);
        EditText missingP = (EditText)findViewById(R.id.missingPerson);
        EditText opLastSeen = (EditText)findViewById(R.id.lastSeen);
        
        String name = Transmit.operationName;
        int id = Transmit.operationID;
        String pw = Transmit.operationPassword;
        String startingPoint = Transmit.operationStartingPoint;
        String missingPerson = Transmit.operationMissingPerson;
        String lastSeen = Transmit.operationLastSeen;
        
		opName.setText(name);
		opID.setText("" + id);
		opPW.setText(pw);
		startP.setText(startingPoint);
		missingP.setText(missingPerson);
		opLastSeen.setText(lastSeen);
        

	}
	
	public void saveClick(View view)
	{
		EditText opName = (EditText)findViewById(R.id.opName);
        //TextView opID = (TextView)findViewById(R.id.opID);
        EditText opPW = (EditText)findViewById(R.id.opPW);
        EditText startP = (EditText)findViewById(R.id.startingPoint);
        EditText missingP = (EditText)findViewById(R.id.missingPerson);
        EditText opLastSeen = (EditText)findViewById(R.id.lastSeen);
        
		EditText pw = (EditText)findViewById(R.id.password);
		
		if (opName.getText().toString().trim().equals("") || opPW.getText().toString().trim().equals("")|| startP.getText().toString().trim().equals("")|| missingP.getText().toString().trim().equals("")|| opLastSeen.getText().toString().trim().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String error = Transmit.updateOperation(opName.getText().toString(), opPW.getText().toString(), "fdas", startP.getText().toString(), missingP.getText().toString(), opLastSeen.getText().toString());
			
			if ("ok".equals(error))
			{
				Intent i = new Intent(this,Menu.class); 
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
			}
		}
		
	}

}

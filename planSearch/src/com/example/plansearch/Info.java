package com.example.plansearch;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
		
		Transmit.updateOperation(opName.getText().toString(), opPW.getText().toString(), "", startP.getText().toString(), missingP.getText().toString(), opLastSeen.getText().toString());
		
		Intent i = new Intent(this,Menu.class); 
		startActivity(i); 
	}

}

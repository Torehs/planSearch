	package com.example.plansearch;


	import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

	import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

	public class Transmit {
		static Object lock = new Object();
		// Stored data
		// Operation
		static int operationID = 0;
		static String operationName;
		static String operationPassword;
		static String operationDescription;
		static String operationStartingPoint;
		static String operationMissingPerson;
		static String operationLastSeen;
		
		// User
		static int userID = 5;
		static String userName;
		static String userPassword;
		static String userPhone;
		static String userOrganization;
		static int userRole; // 0 = no team, 1 = team member, 2 = administrator 
		static int userTeam;
		
		// Logs
		static int lastLogID;
		static class Position {
			int ID;
			double latitude;
			double longitude;
			int userID;
			boolean searched;
			
			public Position(double latitude, double longitude, int userID, boolean searched) {
				this.latitude = latitude;
				this.longitude = longitude;
				this.userID = userID;
				this.searched = searched;
			}
		}
		static ArrayList<Position> positions = new ArrayList<Position>();
		
		// Events
		static int lastEventID;
		static ArrayList<String> events = new ArrayList<String>();
		
		// CREATE OPERATION
		static public String createOperation(String operationName, String operationPassword) {
			String error = "Error!";
			int tempOperationID = 0;
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("userID", Transmit.userID);
				jsonT.put("operationName", operationName);
				jsonT.put("operationPassword", operationPassword);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Build JSONObject Receive
			JSONObject jsonR = new JSONObject();
			
			try {
				jsonR = new Connect().execute(jsonT).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Decode JSONObject Receive
			try {
				error = jsonR.getString("error");
				tempOperationID = jsonR.getInt("operationID");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				Transmit.operationID = tempOperationID;
				Transmit.operationName = operationName;
				Transmit.operationPassword = operationPassword;
				Transmit.userRole = 2;	
			}

			return error;
			
		}
		
		// CREATE USER
		static public String createUser(String userName, String userPassword, String userPhone, String userOrganization) {
			String error = "Error!";
			int tempUserID = 0;
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("userName", userName);
				jsonT.put("userPassword", userPassword);
				jsonT.put("userPhone", userPhone);
				jsonT.put("userPassworOrganization", userOrganization);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Build JSONObject Receive
			JSONObject jsonR = new JSONObject();
			
			try {
				jsonR = new Connect().execute(jsonT).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Decode JSONObject Receive
			try {
				error = jsonR.getString("error");
				tempUserID = jsonR.getInt("userID");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				Transmit.userID = tempUserID;
				Transmit.userName = userName;
				Transmit.userPassword = userPassword;
				Transmit.userPhone = userPhone;
				Transmit.userOrganization = userOrganization;
				Transmit.userRole = 0;
				Transmit.userTeam = 0;
			}
			
			return error;
		}
		
		// JOIN OPERATION
		static public String joinOperation(int operationID, String operationPassword) {
			String error = "Error!";
			String tempOperationName = "";
			String tempOperationDescription = "";
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("operationID", operationID);
				jsonT.put("operationPassword", operationPassword);
				jsonT.put("userID", Transmit.userID);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Build JSONObject Receive
			JSONObject jsonR = new JSONObject();
			
			try {
				jsonR = new Connect().execute(jsonT).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Decode JSONObject Receive
			try {
				error = jsonR.getString("error");
				tempOperationName = jsonR.getString("operationName");
				tempOperationDescription = jsonR.getString("operationDescription");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				Transmit.operationID = operationID;
				Transmit.operationName = tempOperationName;
				Transmit.operationPassword = operationPassword;
				Transmit.operationDescription = tempOperationDescription;
				Transmit.operationStartingPoint = "Oppmøte depot Trondheim Røde Kors";
				Transmit.operationMissingPerson = "64 år gammel mann fra Melhus. Dårlig kledd, men i god form. Rød anorakk, sort bukse, rød lue og hvit sekk.";
				Transmit.operationLastSeen = "Vassfjell-kapellet kl. 14:45 onsdag.";
			}
			
			return error;
		}
		
		// LOGIN
		static public String login(int userID, String userPassword) {
			String error = "Error!";
			String tempUserName = "";
			String tempUserPhone = "";
			String tempUserOrganization = "";
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("userID", userID);
				jsonT.put("userPassword", userPassword);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Build JSONObject Receive
			JSONObject jsonR = new JSONObject();
			
			try {
				jsonR = new Connect().execute(jsonT).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Decode JSONObject Receive
			try {
				error = jsonR.getString("error");
				tempUserName = jsonR.getString("userName");
				tempUserPhone = jsonR.getString("userPhone");
				tempUserOrganization = jsonR.getString("userOrganization");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				Transmit.userID = userID;
				Transmit.userName = tempUserName;
				Transmit.userPassword = userPassword;
				Transmit.userPhone= tempUserPhone;
				Transmit.userOrganization = tempUserOrganization;
			}
			
			return error;
		}
		
		static public boolean receiveLogs() {
			boolean result = false;
			boolean transmitted = true;
			
			if (transmitted) {
				// Not finished
				positions.add(new Position(10.425342, 55.1313541, 12, true));
				positions.add(new Position(10.425353, 55.1313521, 12, true));
				Transmit.lastLogID = 721;
				
				result = true;
			}
			
			return result;
		}
		
		static public boolean transmitLogs() {
			boolean result = false;
			boolean transmitted = true;
			
			if (transmitted) {
				
				result = true;
			}
			
			return result;
		}
		
		static public String updateOperation(String operationName, String operationPassword, String operationDescription, String operationStartingPoint, String operationMissingPerson, String operationLastSeen) {
			String error = "Error!";
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("operationID", operationID);
				jsonT.put("operationName", operationName);
				jsonT.put("operationPassword", operationPassword);
				jsonT.put("operationDescription", operationDescription);
				jsonT.put("operationStartingPoint", operationStartingPoint);
				jsonT.put("operationMissingPerson", operationMissingPerson);
				jsonT.put("operationLastSeen", operationLastSeen);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Build JSONObject Receive
			JSONObject jsonR = new JSONObject();
			
			try {
				jsonR = new Connect().execute(jsonT).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Decode JSONObject Receive
			try {
				error = jsonR.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				
			}
			
			return error;
		}

	}

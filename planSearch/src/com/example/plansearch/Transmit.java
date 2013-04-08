	package com.example.plansearch;


	import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
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
		static int lastLogID = 0;
		static class Position {
			int ID;
			int userID;
			boolean logType;
			double latitude;
			double longitude;
			int positionTime;
			
			public Position(int ID, int userID, boolean logType, double latitude, double longitude, int positionTime) {
				this.ID = ID;
				this.userID = userID;
				this.logType = logType;
				this.latitude = latitude;
				this.longitude = longitude;
				this.positionTime = positionTime;
			}
		}
		static ArrayList<Position> positions = new ArrayList<Position>();
		static ArrayList<Position> positionsQueue = new ArrayList<Position>();
		
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
		static public String login(int userPhone) {
			String error = "Error!";
			int tempUserID = 0;
			String tempUserName = "";
			String tempUserPhone = "";
			String tempUserOrganization = "";
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("userPhone", userPhone);
				
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
				//Transmit.userPhone= userPhone;
				Transmit.userOrganization = tempUserOrganization;
			}
			
			return error;
		}
		
		// RECEIVE LOGS
		static public String receiveLogs() {
			String error = "Error!";
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			JSONArray jsonPos = new JSONArray();
			try {
//				jsonT.put("lastLogID", Transmit.lastLogID);
//				jsonT.put("operationID", Transmit.operationID);
				jsonT.put("lastLogID", 700);
				jsonT.put("operationID", 1);	
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
				jsonPos = jsonR.getJSONArray("positions");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				for (int i = 0; i<jsonPos.length(); i++) {
					try {
						JSONObject tmp = jsonPos.getJSONObject(i);
						positions.add(new Position(tmp.getInt("ID"), tmp.getInt("userID"), (tmp.getInt("logType") != 0), tmp.getDouble("latitude"), tmp.getDouble("longitude"), tmp.getInt("positionTime")));
						Transmit.lastLogID = tmp.getInt("ID");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return error;
		}
		
		static public boolean transmitLogs() {
			boolean result = false;
			boolean transmitted = true;
			
			if (transmitted) {
				
				result = true;
			}
			
			return result;
		}
		
		// UPDATE OPERATION
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

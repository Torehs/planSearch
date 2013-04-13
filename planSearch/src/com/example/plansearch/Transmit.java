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
		static int operationID;
		static String operationName;
		static String operationPassword;
		static String operationDescription;
		static String operationStartingPoint;
		static String operationMissingPerson;
		static String operationLastSeen;
		
		// User
		static int userID;
		static String userName;
		static String userPassword;
		static String userPhone;
		static String userOrganization;
		static int userRole; // 0 = no team, 1 = team member, 2 = administrator 
		static int userTeam;
		
		// Logs
		static int lastLogID = 1;
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
		
		// Operation members
		static class Member {
			int memberID;
			String memberName;
			String memberPhone;
			String memberOrganization;
			String memberStatus;
			
			public Member(int memberID, String memberName, String memberPhone, String memberOrganization, String memberStatus) {
				this.memberID = memberID;
				this.memberName = memberName;
				this.memberPhone = memberPhone;
				this.memberOrganization = memberOrganization;
				this.memberStatus = memberStatus;
			}
		}
		static ArrayList<Member> members = new ArrayList<Member>();
		
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
		static public String createUser(String userName, String userPhone, String userOrganization, String userPassword) {
			String error = "Error!";
			int tempUserID = 0;
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("userName", userName);
				jsonT.put("userPassword", userPassword);
				jsonT.put("userPhone", userPhone);
				jsonT.put("userOrganization", userOrganization);
				
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
		
		// GET OPERATION MEMBERS
		static public String getOperationMembers(int operationID) {
			String error = "Error!";
			JSONArray jsonMem = new JSONArray();
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("operationID", operationID);
				jsonT.put("getOperationMembers", 1);
				
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
				jsonMem = jsonR.getJSONArray("operationMembers");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				for (int i = 0; i<jsonMem.length(); i++) {
					try {
						JSONObject tmp = jsonMem.getJSONObject(i);
						members.add(new Member(tmp.getInt("memberID"), tmp.getString("memberName"), tmp.getString("memberPhone"), tmp.getString("memberOrganization"), tmp.getString("memberStatus")));
						Transmit.lastLogID = tmp.getInt("ID");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return error;
			
		}
		
		// GET USER NAME
		static public String getUserName(int userID) {
			String error = "Error!";
			String tempUserName = "";
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("getUserID", userID);
				
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				error = tempUserName;
			}

			return error;
			
		}
		
		// JOIN OPERATION
		static public String joinOperation(int operationID, String operationPassword) {
			String error = "Error!";
			String tempOperationName = "";
			String tempOperationDescription = "";
			String tempOperationStartingPoint = "";
			String tempOperationMissingPerson = "";
			String tempOperationLastSeen = "";
			
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
				tempOperationStartingPoint = jsonR.getString("startingPoint");
				tempOperationMissingPerson = jsonR.getString("missingPerson");
				tempOperationLastSeen = jsonR.getString("lastSeen");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				Transmit.operationID = operationID;
				Transmit.operationName = tempOperationName;
				Transmit.operationPassword = operationPassword;
				Transmit.operationDescription = tempOperationDescription;
				Transmit.operationStartingPoint = tempOperationStartingPoint;
				Transmit.operationMissingPerson = tempOperationMissingPerson;
				Transmit.operationLastSeen = tempOperationLastSeen;
			}
			
			return error;
		}
		
		// LOGIN
		static public String login(int userID, String userPassword) {
			String error = "Error!";
			int tempUserID = 0;
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
				tempUserID = jsonR.getInt("userID");
				tempUserName = jsonR.getString("userName");
				tempUserPhone = jsonR.getString("userPhone");
				tempUserOrganization = jsonR.getString("userOrganization");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( "".equals(error) ) {
				Transmit.userID = tempUserID;
				Transmit.userName = tempUserName;
				Transmit.userPhone = tempUserPhone;
				Transmit.userPassword = userPassword;
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
				jsonT.put("lastLogID", Transmit.lastLogID);
				jsonT.put("operationID", Transmit.operationID);	
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
		
		// TRANSMIT LOGS
		static public String transmitLogs() {
			String error = "Error!";
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			
			for (int i = 0; i < positionsQueue.size(); i++) {
				try {
					JSONObject tmp = new JSONObject();
					tmp.put("userID", positionsQueue.get(i).userID);
					tmp.put("longitude", positionsQueue.get(i).longitude);
					tmp.put("latitude", positionsQueue.get(i).latitude);
					tmp.put("logType", positionsQueue.get(i).logType ? 1 : 0);
					tmp.put("positionTime", positionsQueue.get(i).positionTime);
					jsonT.put("operationID", Transmit.operationID);
					jsonT.accumulate("positions", tmp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				positionsQueue.clear();
			}

			return error;
		}
		
		// UPDATE OPERATION
		static public String updateOperation(String operationName, String operationPassword, String operationDescription, String operationStartingPoint, String operationMissingPerson, String operationLastSeen) {
			String error = "Error!";
			
			// Build JSONObject Transmit
			JSONObject jsonT = new JSONObject();
			try {
				jsonT.put("operationID", Transmit.operationID);
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
				Transmit.operationName = operationName;
				Transmit.operationPassword = operationPassword;
				Transmit.operationDescription = operationDescription;
				Transmit.operationStartingPoint = operationStartingPoint;
				Transmit.operationMissingPerson = operationMissingPerson;
				Transmit.operationLastSeen = operationLastSeen;
			}
			
			return error;
		}
		
		static public void onExit()
		{
			Transmit.positions.clear();
			Transmit.positionsQueue.clear();
			Transmit.lastLogID = 1;
			Transmit.operationID = 0;
			Transmit.operationName = "";
			Transmit.operationPassword = "";
			Transmit.operationDescription = "";
			Transmit.operationStartingPoint = "";
			Transmit.operationMissingPerson = "";
			Transmit.operationLastSeen = "";
		}

	}

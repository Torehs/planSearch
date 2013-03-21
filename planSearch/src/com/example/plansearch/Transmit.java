package com.example.plansearch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.plansearch.Transmit.Position;

public class Transmit {
	
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
	static int lastLogID;
	static class Position {
		int ID;
		double latitude;
		double longitude;
		int userID;
		boolean searched;
		
		public Position(int ID, double latitude, double longitude, int userID, boolean searched) {
			this.ID = ID;
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
	
	static public boolean createOperation(String operationName, String operationPassword) {
		boolean result = false;
		boolean transmitted = true;
		
		if (transmitted) {
			Transmit.operationID = 15;
			Transmit.operationName = operationName;
			Transmit.operationPassword = operationPassword;
			Transmit.userRole = 2;
			
			result = true;
		}
		
		// FUNGERE IKKE ENDA, MÅ LEGGE INN ASYNC()
//		// Build JSONObject Transmit
//		JSONObject jsonT = new JSONObject();
//		try {
//			jsonT.put("operationName", operationName);
//			jsonT.put("operationPassword", operationPassword);
//			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		// Build JSONObject Receive
//		JSONObject jsonR = new JSONObject();
//		
//		// Transmit and Receive
//		try {
//			jsonR = transmitAndReceive(jsonT);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		// Decode JSONObject
//		try {
//			Transmit.operationID = jsonR.getInt("operationID");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return result;
		
	}
	
	static public boolean createUser(String userName, String userPassword, String userPhone, String userOrganization) {
		boolean result = false;
		boolean transmitted = true;
		
		if (transmitted) {
			userID = 12;
			Transmit.userName = userName;
			Transmit.userPassword = userPassword;
			Transmit.userPhone = userPhone;
			Transmit.userOrganization = userOrganization;
			Transmit.userRole = 0;
			Transmit.userTeam = 0;
			
			result = true;
		}
		
		return result;
	}
	
	static public boolean joinOperation(int operationID, String operationPassword) {
		boolean result = false;
		boolean transmitted = true;
		
		if (transmitted) {
			Transmit.operationID = 42;
			Transmit.operationName = "Leteaksjon Vassfjellet";
			Transmit.operationPassword = "passord";
			Transmit.operationDescription = "Leteaksjon etter dement mann i Vassfjell-området.";
			Transmit.operationStartingPoint = "Oppmøte depot Trondheim Røde Kors";
			Transmit.operationMissingPerson = "64 år gammel mann fra Melhus. Dårlig kledd, men i god form. Rød anorakk, sort bukse, rød lue og hvit sekk.";
			Transmit.operationLastSeen = "Vassfjell-kapellet kl. 14:45 onsdag.";
			
			result = true;
		}
		
		return result;	
	}
	
	static public boolean login(String userName, String userPassword) {
		boolean result = false;
		boolean transmitted = true;
		
		if (transmitted) {
			Transmit.userID = 12;
			Transmit.userName = "Fornavn Etternavn";
			Transmit.userPassword = "passord";
			Transmit.userPhone = "+4712345678";
			Transmit.userOrganization = "Trondheim Røde Kors hjelpekorps";
			Transmit.userRole = 0;
			Transmit.userTeam = 0;
			
			result = true;
		}
		
		return result;
	}
	
	static public boolean receiveLogs() {
		boolean result = false;
		boolean transmitted = true;
		
		if (transmitted) {
			// Not finished
			positions.add(new Position(720, 10.425342, 55.1313541, 12, true));
			positions.add(new Position(721, 10.425353, 55.1313521, 12, true));
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
	
	static public boolean updateOperation(String operationName, String operationPassword, String operationDescription, String operationStartingPoint, String operationMissingPerson, String operationLastSeen) {
		boolean result = false;
		boolean transmitted = true;
		
		if (transmitted) {
			Transmit.operationID = 15;
			Transmit.operationName = operationName;
			Transmit.operationPassword = operationPassword;
			Transmit.operationDescription = operationDescription;
			Transmit.operationStartingPoint = operationStartingPoint;
			Transmit.operationMissingPerson = operationMissingPerson;
			Transmit.operationLastSeen = operationLastSeen;
			
			result = true;
		}
		
		return result;
	}
	
	static public JSONObject transmitAndReceive(JSONObject jsonT) throws ClientProtocolException, IOException {
		String myUri = "http://folk.ntnu.no/torehavs/transfer.php";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(myUri);
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("data", jsonT.toString()));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HttpResponse response = httpClient.execute(httppost);

		String bodyHtml = EntityUtils.toString(response.getEntity());
		
		JSONObject jsonR = new JSONObject();;
		try {
			jsonR = new JSONObject(bodyHtml);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonR;
	}

}

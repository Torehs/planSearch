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

public class Transmit {
	
	public int createOperation(String operationName, String operationPassword) {
		// Build JSONObject Transmit
		JSONObject jsonT = new JSONObject();
		try {
			jsonT.put("operationName", operationName);
			jsonT.put("operationPassword", operationPassword);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Build JSONObject Receive
		JSONObject jsonR = new JSONObject();
		
		try {
			jsonR = transmitAndReceive(jsonT);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Decode JSONObject
		int operationID = 0;
		try {
			operationID = jsonR.getInt("operationID");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return operationID;
	}
	
	public JSONObject transmitAndReceive(JSONObject jsonT) throws ClientProtocolException, IOException {
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

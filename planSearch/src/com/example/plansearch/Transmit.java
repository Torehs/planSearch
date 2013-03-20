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
	
	
	
	public String transmitAndReceive(JSONObject jsonT) throws ClientProtocolException, IOException {
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
		
		JSONObject jsonR;
		try {
			jsonR = new JSONObject(bodyHtml);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bodyHtml;
	}

}

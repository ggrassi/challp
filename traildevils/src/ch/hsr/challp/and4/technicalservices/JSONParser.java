package ch.hsr.challp.and4.technicalservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import ch.hsr.challp.and4.domain.Trail;

public class JSONParser extends Thread {
	String url;
	Handler handler;

	public JSONParser(String url, Handler handler) {
		this.url = url;
		this.handler = handler;
	}

	@Override
	public void run() {
		sendMessage(1000);
		parse();
		sendMessage(3000);
	}

	private void parse() {
		try {
			String readedFeed = readFeed();
			JSONArray jsonArray = new JSONArray(readedFeed);
			
			sendMessage(2000);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				if (i % (jsonArray.length() / 10) == 0) {
					if((i / (jsonArray.length() / 10) * 10) <= 100) {
						sendMessage(2000, (i / (jsonArray.length() / 10) * 10));
					}
				}
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				@SuppressWarnings("unused")
				Trail tmpTrail = new Trail(jsonObject);
			}
		} catch (Exception e) {
			//TODO: Handle Excpetion
			e.printStackTrace();
		}
	}

	private String readFeed() {
		sendMessage(1100);
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Content-Type", "application/json");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				// TODO: Handle Failure
			}
		} catch (ClientProtocolException e) {
			// TODO: Handle Exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: Handle Exception
			e.printStackTrace();
		}
		return builder.toString();
	}

	private void sendMessage(int arg1) {
		sendMessage(arg1, 0);
	}

	private void sendMessage(int arg1, int arg2) {
		Message msg = handler.obtainMessage();
		msg.arg1 = arg1;
		msg.arg2 = arg2;
		handler.sendMessage(msg);
	}

}

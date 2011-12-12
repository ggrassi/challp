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

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import ch.hsr.challp.and4.domain.Trail;

public class JSONParser extends Thread {
	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	String url;
	Handler handler;
	Context ctx;

	public JSONParser(String url, Handler handler) {
		this.url = url;
		this.handler = handler;
	}

	@Override
	public void run() {
		sendMessage("Start Syncing...");
		parse();
		sendMessage("Launching Trail-Browser...");
	}

	private void parse() {
		try {
			String readedFeed = readFeed();
			JSONArray jsonArray = new JSONArray(readedFeed);
			
			sendMessage("Parsing...");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				if (i % (jsonArray.length() / 10) == 0) {
					if((i / (jsonArray.length() / 10) * 10) <= 100) {
						sendMessage("Parsing... ( " + (i / (jsonArray.length() / 10) * 10) + " % )");
					}
				}
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				@SuppressWarnings("unused")
				Trail tmpTrail = new Trail(jsonObject);
			}
			Trail.serialize();
		} catch (Exception e) {
			//TODO: Handle Excpetion
			e.printStackTrace();
		}
	}

	private String readFeed() {
		sendMessage("Download...");
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

	private void sendMessage(String text) {
		Message msg = handler.obtainMessage();
		msg.obj = text;
		handler.sendMessage(msg);
	}
}

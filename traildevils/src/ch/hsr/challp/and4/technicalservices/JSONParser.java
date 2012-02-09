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

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.domain.TrailController;

public class JSONParser extends Thread {

	private String url;
	private Handler handler;
	private Context ctx;
	private TrailController trailController;

	public JSONParser(String url, Handler handler) {
		this.url = url;
		this.handler = handler;
	}

	public JSONParser(String url, Handler handler,
			TrailController trailController) {
		this.url = url;
		this.handler = handler;
		this.trailController = trailController;
	}

	public JSONParser(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		sendMessage("Start Syncing...");
		parse();
		sendMessage("Launching Trail-Browser...");
	}

	public Context getCtx() {
		return ctx;
	}
	
	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}
	
	private void parse() {
		try {
			String readedFeed = readFeed();
			JSONArray jsonArray = new JSONArray(readedFeed);

			sendMessage("Parsing...");
			for (int i = 0; i < jsonArray.length(); i++) {
				if (i % (jsonArray.length() / 10) == 0) {
					if ((i / (jsonArray.length() / 10) * 10) <= 100) {
						sendMessage("Parsing... ( "
								+ (i / (jsonArray.length() / 10) * 10) + " % )");
					}
				}
				final Trail tmpTrail = new Trail(jsonArray.getJSONObject(i));
				trailController.getTrails().add(tmpTrail);
			}
			trailController.serialize();
		} catch (Exception e) {
			Log.e(this.getClass().getName(), e.toString());
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
				reader.close();
			} else {
				Log.w(this.getClass().getName(), "Failure in HTTP-Request");

			}
		} catch (ClientProtocolException e) {
			Log.e(this.getClass().getName(), e.toString());

		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.toString());

		} 
		return builder.toString();
	}

	private void sendMessage(String text) {
		if (handler != null) {
			final Message msg = handler.obtainMessage();
			msg.obj = text;
			handler.sendMessage(msg);
		}
	}
}

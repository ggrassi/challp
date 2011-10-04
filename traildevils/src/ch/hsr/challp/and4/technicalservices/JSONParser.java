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

import ch.hsr.challp.and4.domain.Trail;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class JSONParser extends IntentService {
	
	String url;
	
	public JSONParser() {
		this("JSONParser");
	}

	public JSONParser(String name) {
		super(name);
	}
	
	@Override
	public void onCreate() {
		url = getString(ch.hsr.challp.and4.R.string.JSONUrl);
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.d("tag", "filtrino: " + "json-url:" + url);
		parse();
	}
	
	private void parse() {
		try {
			String readedFeed = readFeed();
			JSONArray jsonArray = new JSONArray(readedFeed);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Trail tmpTrail = new Trail(jsonObject);
				Log.d("tag", "filtrino: " + "json-obj:" + tmpTrail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("tag", "filtrino: " + "Exception:" + e.toString());
		}
	}
	
	private String readFeed() {
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
				Log.d("tag", "line-start");
				while ((line = reader.readLine()) != null) {
					builder.append(line);
					Log.d("tag", "line");
				}
				Log.d("tag", "line-stop");
			} else {
				Log.e("tag", "filtrino: " + "Failure:"
						+ "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d("tag", "filtrino: " + "Exception:" + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("tag", "filtrino: " + "Exception:" + e.toString());
		}
		Log.d("tag", "filtrino: " + "builder:" + builder.toString());
		return builder.toString();
	}

}

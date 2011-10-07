package ch.hsr.challp.and4.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import ch.hsr.challp.and4.R;
import ch.hsr.challp.and4.technicalservices.JSONParser;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;

public class StartScreen extends Activity {
	private boolean shouldStartTabContainer = true;
	private Controller controller;
	private TextView textViewToChange;

	public void goToGPSView(View v) {
		shouldStartTabContainer = false;

		Intent ac = new Intent(".activities.GPSTest");
		startActivity(ac);

		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_screen);

		setWaitText("Getting Position...");
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new UserLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);

		controller = new Controller(handler);
		controller.start();
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String message;
			switch (msg.arg1) {
			case 1000:
				message = "Start Syncing...";
				break;
			case 1100:
				message = "Download...";
				break;
			case 2000:
				message = "Parsing... ( " + msg.arg2 + " % )";
				break;
			case 3000:
				message = "Launching Trail-Browser...";
				break;
			default:
				message = "Error! (no such message)";
				break;
			}
			setWaitText(message);
		}
	};

	private void setWaitText(String WaitText) {
		textViewToChange = (TextView) findViewById(R.id.wait_text);
		textViewToChange.setText(WaitText);
	}

	class Controller extends Thread {
		Handler myH;

		public Controller(Handler h) {
			myH = h;
		}

		public void run() {
			try {

				JSONParser parser = new JSONParser(getString(R.string.JSONUrl),
						myH);
				parser.start();
				parser.join();

				if (shouldStartTabContainer) {
					Intent ac = new Intent(".activities.TabContainer");
					startActivity(ac);
				}
			} catch (Exception e) {
				Log.d("tag", "filtrino: " + "error:"
						+ "Exception in zZzZ-Thread on StartScreen");
				Log.d("tag", "filtrino: " + "error:" + e.toString());
			} finally {
				finish();
			}
		};
	};
}
package ch.hsr.challp.and4.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.and4.technicalservices.JSONParser;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;
import ch.hsr.challp.android4.R;

public class StartScreen extends LicenseCheckActivity {
	private static Object locationService = null;

	private Controller controller;

	private TextView textViewToChange;

	public static Object getLocationService() {
		return locationService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_screen);

		textViewToChange = (TextView) findViewById(R.id.wait_text);

		setWaitText("Getting Position...");
		Object tmpLocationService = getSystemService(Context.LOCATION_SERVICE);
		locationService = tmpLocationService;
		((TrailDevils) getApplication())
				.setUserLocation(new UserLocationListener());

		setWaitText("Checking Application License...");
		checkLicense();
	}

	@Override
	protected void goOn() {
		controller = new Controller(handler);
		controller.start();
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void setWaitText(String waitText) {
		textViewToChange.setText(waitText);
	}

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				textViewToChange.setText((String) msg.obj);
			} catch (Exception e) {
				// Do nothing
			}
		}
	};

	class Controller extends Thread {
		Handler myH;

		public Controller(Handler h) {
			myH = h;
		}

		@Override
		public void run() {
			try {

				final TrailController trailController = ((TrailDevils) getApplication())
						.getTrailController();
				trailController.getTrails().clear();
				if (!trailController.serializationExists()) {
					final JSONParser parser = new JSONParser(
							getString(R.string.JSONUrl), myH, trailController);
					parser.setCtx(getBaseContext());
					parser.start();
					parser.join();
				} else {
					trailController.deserialize();
				}

				final Intent ac = new Intent(".activities.TabContainer");
				startActivity(ac);

			} catch (Exception e) {
				Log.e(this.getClass().getName(), e.toString());
			} finally {
				finish();
			}
		};
	}
}
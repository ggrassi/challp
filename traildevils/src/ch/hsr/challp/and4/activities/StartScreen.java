package ch.hsr.challp.and4.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import ch.hsr.challp.and.R;
import ch.hsr.challp.and4.technicalservices.JSONParser;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;
import ch.hsr.challp.and4.technicalservices.database.TrailData;

public class StartScreen extends LicenseCheckActivity {
	private static Object locationService = null;

	private boolean shouldStartTabContainer = true;
	private Controller controller;
	private TextView textViewToChange;

	public static Object getLocationService() {
		return locationService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_screen);


        Toast.makeText(this, "Checking Application License", Toast.LENGTH_SHORT).show();
        // Check the license
        checkLicense();
		
		
		setWaitText("Getting Position...");
		Object tmpLocationService = getSystemService(Context.LOCATION_SERVICE);
		locationService = tmpLocationService;
		UserLocationListener.getInstance();

		controller = new Controller(handler);
		controller.start();
		
	}
	
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

	final Handler handler = new Handler() {
		@Override
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

		@Override
		public void run() {
			try {
				TrailData.initializeTrailData(getBaseContext());
				if (TrailData.getInstance().isEmpty()){
					JSONParser parser = new JSONParser(getString(R.string.JSONUrl),
							myH);
					parser.setCtx(getBaseContext());
					parser.start();
					parser.join();
				}
				TrailData.getInstance().close();
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
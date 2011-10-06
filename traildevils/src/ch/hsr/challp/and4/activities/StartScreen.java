package ch.hsr.challp.and4.activities;

import ch.hsr.challp.and4.R;
import ch.hsr.challp.and4.technicalservices.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class StartScreen extends Activity {
	private TextView textViewToChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_screen);

		Controller controller = new Controller(handler);
		controller.start();
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String message;
			switch (msg.arg1) {
			case 1000:
				message = "Start...";
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
				JSONParser parser = new JSONParser(
						getString(ch.hsr.challp.and4.R.string.JSONUrl), myH);
				parser.start();
				parser.join();

				Intent ac = new Intent(".activities.TabHead");
				startActivity(ac);
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
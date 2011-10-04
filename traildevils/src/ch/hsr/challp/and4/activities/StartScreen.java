package ch.hsr.challp.and4.activities;

import ch.hsr.challp.and4.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class StartScreen extends Activity {
	private TextView textViewToChange;
	
	String[] texts = { "Coffee...", "Smoke...",
			"Just wasting time...", "Have a beer..." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_screen2);

		Thread zZzZz = new ZzZzZz(handler);

		zZzZz.start();

	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			setWaitText(texts[msg.arg1]);

		}
	};

	private void setWaitText(String WaitText) {
		textViewToChange = (TextView) findViewById(R.id.wait_text);
		textViewToChange.setText(WaitText);
	}

	class ZzZzZz extends Thread {
		Handler myH;
		
		public ZzZzZz(Handler h) {
			myH = h;
		}

		public void run() {
			try {
				Intent svc = new Intent(".technicalservices.JSONParser");
				startService(svc);

				sleep(3000);
				for (int i = 0; i < texts.length; i++) {
	                Message msg = myH.obtainMessage();
	                msg.arg1 = i;
	                myH.sendMessage(msg);
					sleep(3000);
				}

				Intent ac = new Intent(".activities.BrowserActivity");
				startService(ac);
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

package ch.hsr.challp.and4.activities;

import ch.hsr.challp.and4.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.start_screen);
		
        Intent svc = new Intent(".technicalservices.JSONParser");
        startService(svc);
        Intent s = new Intent(".activities.BrowserTabActivity");
        startActivity(s);
	}

}

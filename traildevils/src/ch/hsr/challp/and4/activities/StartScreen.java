package ch.hsr.challp.and4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        Intent svc = new Intent("ch.hsr.challp.and4.technicalservices.JSONParser");
        startService(svc);
	}

}

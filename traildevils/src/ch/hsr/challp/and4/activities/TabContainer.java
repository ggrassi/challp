package ch.hsr.challp.and4.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabContainer extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(ch.hsr.challp.and4.R.layout.tab);
		Bundle bundle = getIntent().getExtras();

		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, TrailBrowserTab.class);
		spec = tabHost.newTabSpec("some_things").setIndicator("Trails")
				.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, MapTab.class);
		spec = tabHost.newTabSpec("dif_things").setIndicator("map")
				.setContent(intent);
		tabHost.addTab(spec);
	}

}
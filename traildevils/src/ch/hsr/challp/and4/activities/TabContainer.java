package ch.hsr.challp.and4.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import ch.hsr.challp.and4.R;

public class TabContainer extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		
		intent = new Intent().setClass(this, TrailBrowserTab.class);
		spec = tabHost.newTabSpec("some_things").setIndicator(this.getString(R.string.trail_browser_tab_title), getResources().getDrawable(R.drawable.ic_tab_trailbrowser))
				.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, MapTab.class);
		spec = tabHost.newTabSpec("dif_things").setIndicator(this.getString(R.string.map_tab_title), getResources().getDrawable(R.drawable.ic_tab_map))
				.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, MapTab.class);
		spec = tabHost.newTabSpec("dif_things").setIndicator(this.getString(R.string.favorites_tab_title), getResources().getDrawable(R.drawable.ic_tab_favorites))
				.setContent(intent);
		tabHost.addTab(spec);
	}

}

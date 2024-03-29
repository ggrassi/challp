package ch.hsr.challp.and4.activities;

import java.util.Observable;
import java.util.Observer;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.billing.Billing;
import ch.hsr.challp.android4.R;

public class TabContainer extends TabActivity implements Observer {
	private TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		// do klöpts classcastexception wege downcasting
		TabHost.TabSpec spec;
		tabHost = initializeTabHost();
		Intent intent;

		intent = new Intent().setClass(this, TrailBrowserTab.class);
		spec = tabHost
				.newTabSpec("trail_things")
				.setIndicator(
						this.getString(R.string.trail_browser_tab_title),
						getResources().getDrawable(
								R.drawable.ic_tab_trailbrowser))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this.getApplicationContext(),
				MapTab.class);
		spec = tabHost
				.newTabSpec("map_things")
				.setIndicator(this.getString(R.string.map_tab_title),
						getResources().getDrawable(R.drawable.ic_tab_map))
				.setContent(intent);
		tabHost.addTab(spec);
		tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);

		intent = new Intent().setClass(this, FavoritesTab.class);
		spec = tabHost
				.newTabSpec("favorites_tab")
				.setIndicator(this.getString(R.string.favorites_tab_title),
						getResources().getDrawable(R.drawable.ic_tab_favorites))
				.setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		tabHost.addTab(spec);

		intent = new Intent().setClass(this.getApplicationContext(),
				Billing.class);
		spec = tabHost
				.newTabSpec("inappbilling_tab")
				.setIndicator(this.getString(R.string.buy_tab_title),
						getResources().getDrawable(R.drawable.ic_tab_billing))
				.setContent(intent);
		tabHost.addTab(spec);
	}
	private TabHost initializeTabHost() {
			((TrailDevils) getApplication()).setTabContainer(this);
			return getTabHost();
	}
	public void update(Observable observable, Object data) {
		tabHost.getTabWidget().getChildTabViewAt(1).setEnabled((Boolean) data);
	}
}
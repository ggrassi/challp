package ch.hsr.challp.and4.application;

import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;
import ch.hsr.challp.and4.technicalservices.favorites.Favorites;
import android.app.Application;
import android.widget.TabHost;

public class TrailDevils extends Application {

	private TrailController trails = new TrailController(this);
	private Favorites favorites = new Favorites(this, trails);
	private UserLocationListener userLocation;
	private TabHost tabHost;

	public Favorites getFavorites() {
		return favorites;
	}

	public TrailController getTrailController() {
		return trails;
	}

	public UserLocationListener getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(UserLocationListener locationListener) {
		userLocation = locationListener;
	}

	public TabHost getTabHost() {
		return tabHost;
	}

	public void setTabHost(TabHost tabHost) {
		this.tabHost = tabHost;
	}

}
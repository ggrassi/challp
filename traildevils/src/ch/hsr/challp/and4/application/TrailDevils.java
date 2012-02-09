package ch.hsr.challp.and4.application;

import android.app.Application;
import ch.hsr.challp.and4.activities.TabContainer;
import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;
import ch.hsr.challp.and4.technicalservices.favorites.Favorites;

public class TrailDevils extends Application {

	private TrailController trails = new TrailController(this);
	private Favorites favorites = new Favorites(this, trails);
	private UserLocationListener userLocation;
	private TabContainer tabCont;

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

	public TabContainer getTabContainer() {
		return tabCont;
	}

	public void setTabContainer(TabContainer tabCont) {
		this.tabCont = tabCont;
	}

}
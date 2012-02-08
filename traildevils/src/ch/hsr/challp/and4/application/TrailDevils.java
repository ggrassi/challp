package ch.hsr.challp.and4.application;

import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.and4.technicalservices.favorites.Favorites;
import android.app.Application;

public class TrailDevils extends Application {

	private TrailController trails = new TrailController();
	private Favorites favorites = new Favorites(this, trails);

	public Favorites getFavorites() {
		return favorites;
	}

	public TrailController getTrailController() {
		return trails;
	}
}
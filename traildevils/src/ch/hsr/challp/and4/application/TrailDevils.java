package ch.hsr.challp.and4.application;

import ch.hsr.challp.and4.technicalservices.favorites.Favorites;
import android.app.Application;

public class TrailDevils extends Application {

	private Favorites favorites = new Favorites(this);

	public Favorites getFavorites() {
		return favorites;
	}
}
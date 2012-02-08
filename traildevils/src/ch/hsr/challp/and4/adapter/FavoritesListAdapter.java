package ch.hsr.challp.and4.adapter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.technicalservices.favorites.Favorites;

public class FavoritesListAdapter extends TrailListAdapter implements Observer {

	private TrailDevils app;

	public FavoritesListAdapter(Context context, int textViewResourceId,
			TrailDevils app) {
		super(context, textViewResourceId, app.getFavorites().getTrails());
		this.app = app;
		final Favorites favs = app.getFavorites();
		this.trails = favs.getTrails();
		favs.addObserver(this);
	}

	public void update(Observable observable, Object data) {
		loadNew();
	}

	@Override
	public void loadNew() {
		trails = app.getFavorites().getTrails();
		notifyDataSetChanged();
	}
}

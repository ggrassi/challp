package ch.hsr.challp.and4.technicalservices.favorites;

import java.util.ArrayList;

import android.content.Context;
import ch.hsr.challp.and4.domain.Trail;

public class Favorites {
	private FavoritesDBHelper helper = null;

	public Favorites(Context context) {
		helper = new FavoritesDBHelper(context);
	}

	public void addTrail(Trail trail) {
		helper.addId(trail.getTrailId());
	}

	public void removeTrail(Trail trail) {
		helper.removeId(trail.getTrailId());
	}

	public ArrayList<Trail> getTrails() {
		ArrayList<Trail> favorites = new ArrayList<Trail>();
		ArrayList<Trail> trails = Trail.getTrails();
		ArrayList<Integer> ids = helper.getIds();
		for (Integer id : ids) {
			for (Trail trail : trails) {
				if (id == trail.getTrailId()) {
					favorites.add(trail);
				}
			}
		}
		return favorites;
	}

	public boolean isFavorite(Trail trail) {
		return helper.isIdInDb(trail.getTrailId());
	}
}

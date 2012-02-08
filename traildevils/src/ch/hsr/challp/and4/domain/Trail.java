package ch.hsr.challp.and4.domain;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ch.hsr.challp.and4.domain.sortingStrategy.SortByName;
import ch.hsr.challp.and4.domain.sortingStrategy.SortStrategy;

public class Trail implements Serializable, Comparable<Trail> {

	/**
	 * This is the Trail POJO
	 */

	private int trail_id, trailDevils_id, distance, favorits;
	float gmapX;
	float gmapY;
	private String country, description, imageUrl120, imageUrl800, name,
			nextCity;
	private SortStrategy sortStrategy;

	public Trail(JSONObject Trail_NewJson) {
		try {
			converte(Trail_NewJson);
			sortStrategy=new SortByName();
		} catch (JSONException e) {
			
			Log.w(this.getClass().getName(), e.toString());
		}
	}

	private void converte(JSONObject trailJson) throws JSONException {
		trail_id = trailJson.getInt("Id");
		country = trailJson.getString("Country");
		description = trailJson.getString("Desc");
		gmapX = (float) (trailJson.getDouble("GmapX"));
		gmapY = (float) (trailJson.getDouble("GmapY"));
		imageUrl120 = trailJson.getString("ImageUrl120") == null ? "null" : trailJson.getString("ImageUrl120");
		imageUrl800 = trailJson.getString("ImageUrl800") == null ? "null" : trailJson.getString("ImageUrl800");
		name = (trailJson.getString("Name") == null ? "" : trailJson.getString("Name"));
		nextCity = trailJson.getString("NextCity");
		setTrailDevilsId(trailJson.getInt("TraildevilsId"));
		setFavorits(trailJson.getInt("Favorits"));
	}

	public float getGmapX() {
		return gmapX;
	}

	public float getGmapY() {
		return gmapY;
	}

	public int getTrailId() {
		return trail_id;
	}

	public String getCountry() {
		return country;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl120() {
		return imageUrl120;
	}

	public String getImageUrl800() {
		return imageUrl800;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = (name == null ? "" : name);
	}

	public String getNextCity() {
		return nextCity;
	}

	public int getTrailDevilsId() {
		return trailDevils_id;
	}

	public void setTrailDevilsId(int Trail_NewDevilsId) {
		this.trailDevils_id = Trail_NewDevilsId;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}


	public int getFavorits() {
		return favorits;
	}

	public void setFavorits(int favorits) {
		this.favorits = favorits;
	}

	public int compareTo(Trail another) {
		
		return this.sortStrategy.sortingAlgorithm(this, another);
	}

	public SortStrategy getSortStrategy() {
		return sortStrategy;
	}

	public void setSortStrategy(SortStrategy sortStrategy) {
		this.sortStrategy = sortStrategy;
	}

}
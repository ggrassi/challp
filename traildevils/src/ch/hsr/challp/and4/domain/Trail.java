package ch.hsr.challp.and4.domain;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Trail {
	@Override
	public String toString() {
		return "Trail [countryId=" + countryId + ", favorits=" + favorits
				+ ", gmapX=" + gmapX + ", gmapY=" + gmapY + ", trailId="
				+ trailId + ", creationDate=" + creationDate + ", country="
				+ country + ", description=" + description + ", imageUrl120="
				+ imageUrl120 + ", imageUrl800=" + imageUrl800 + ", info="
				+ info + ", journey=" + journey + ", name=" + name
				+ ", nextCity=" + nextCity + ", state=" + state + ", url="
				+ url + ", isCommercial=" + isCommercial + ", isOpen=" + isOpen
				+ "]";
	}

	public int getCountryId() {
		return countryId;
	}

	public int getFavorits() {
		return favorits;
	}

	public int getGmapX() {
		return gmapX;
	}

	public int getGmapY() {
		return gmapY;
	}

	public int getTrailId() {
		return trailId;
	}

	public Date getCreationDate() {
		return creationDate;
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

	public String getInfo() {
		return info;
	}

	public String getJourney() {
		return journey;
	}

	public String getName() {
		return name;
	}

	public String getNextCity() {
		return nextCity;
	}

	public String getState() {
		return state;
	}

	public String getUrl() {
		return url;
	}

	public boolean isCommercial() {
		return isCommercial;
	}

	public boolean isOpen() {
		return isOpen;
	}

	private static ArrayList<Trail> trails = new ArrayList<Trail>();
	private int countryId, favorits, gmapX, gmapY, trailId;
	private Date creationDate;
	private String country, description, imageUrl120, imageUrl800, info,
			journey, name, nextCity, state, url;
	private boolean isCommercial, isOpen;

	public Trail(JSONObject trailJson) {
		try {
			converte(trailJson);
			trails.add(this);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("tag",
					Thread.currentThread().getStackTrace()[3].getLineNumber()
							+ "");

		}
	}

	public static ArrayList<Trail> getTrails() {
		return trails;
	}

	private void converte(JSONObject trailJson) throws JSONException {
		country = trailJson.getString("Country");
		countryId = trailJson.getInt("CountryId");
		creationDate = createDateFromJson(trailJson.getString("CreatedDate"));
		description = trailJson.getString("Desc");
		favorits = trailJson.getInt("Favorits");
		gmapX = (int) (trailJson.getDouble("GmapX") * 1E6);
		gmapY = (int) (trailJson.getDouble("GmapY") * 1E6);
		imageUrl120 = trailJson.getString("ImageUrl120");
		imageUrl800 = trailJson.getString("ImageUrl800");
		info = trailJson.getString("Info");
		isCommercial = trailJson.getBoolean("IsCommercial");
		isOpen = trailJson.getBoolean("IsOpen");
		journey = trailJson.getString("Journey");
		name = trailJson.getString("Name");
		nextCity = trailJson.getString("NextCity");
		state = trailJson.getString("State");
		trailId = trailJson.getInt("TrailId");
		url = trailJson.getString("Url");
	}

	private Date createDateFromJson(String string) {
		String timestamp = string.substring(6, string.length() - 7);
		return new Date(Long.valueOf(timestamp));
	}
}
package ch.hsr.challp.and4.domain;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;
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

	public float getGmapX() {
		return gmapX;
	}

	public float getGmapY() {
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
		return Html.fromHtml(info).toString();
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
	private int countryId, favorits, trailId;
	float gmapX;
	float gmapY;
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
			Log.d("tag", "filtrino: " + e.toString() + "");
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
		gmapX = (float) (trailJson.getDouble("GmapX"));
		gmapY = (float) (trailJson.getDouble("GmapY"));
		imageUrl120 = trailJson.getString("ImageUrl120");
		imageUrl800 = trailJson.getString("ImageUrl800");
		info = trailJson.getString("Info");
		isCommercial = trailJson.getBoolean("IsCommercial");
		try {
			isOpen = trailJson.getBoolean("IsOpen");
		} catch (Exception e) {
			isOpen = false;
		}
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
package ch.hsr.challp.and4.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;
import android.util.Log;
import ch.hsr.challp.and4.technicalservices.database.TrailData;

public class Trail implements Serializable {

	public Trail() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Trail(int countryId, int favorits, int trailId, float gmapX,
			float gmapY, Date creationDate, Date lastModified, String country,
			String description, String imageUrl120, String imageUrl800,
			String info, String journey, String name, String nextCity,
			String state, String url, boolean isCommercial, boolean isOpen,
			TrailData trailData) {
		super();
		this.countryId = countryId;
		this.favorits = favorits;
		this.trailId = trailId;
		this.gmapX = gmapX;
		this.gmapY = gmapY;
		this.creationDate = creationDate;
		this.lastModified = lastModified;
		this.country = country;
		this.description = description;
		this.imageUrl120 = imageUrl120;
		this.imageUrl800 = imageUrl800;
		this.info = info;
		this.journey = journey;
		this.name = name;
		this.nextCity = nextCity;
		this.state = state;
		this.url = url;
		this.isCommercial = isCommercial;
		this.isOpen = isOpen;
	}

	public Trail(int trailId, Date creationDate, String name) {
		super();
		this.creationDate = creationDate;
		this.trailId = trailId;
		this.name = name;
	}

	private static HashMap<Integer, Trail> trails = new HashMap<Integer, Trail>();
	private int countryId, favorits, trailId;
	float gmapX;
	float gmapY;
	private Date creationDate, lastModified;
	private String country, description, imageUrl120, imageUrl800, info,
			journey, name, nextCity, state, url;
	private boolean isCommercial, isOpen;

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

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
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

	public Trail(JSONObject trailJson, boolean fromDB) {
		try {
			converte(trailJson);

			if (!fromDB) {
				TrailData.getInstance().add(this);
				TrailData.getInstance().close();
			}
		} catch (JSONException e) {
			Log.d("tag", "filtrino: " + e.toString() + "");
		}
	}

	public static ArrayList<Trail> getTrails() {
			trails = TrailData.getInstance().getAll();
		TrailData.getInstance().close();
		ArrayList<Trail> myTrail = new ArrayList<Trail>();
		for (Integer trail : new TreeSet<Integer>(trails.keySet())) {
			myTrail.add(trails.get(trail));
			Log.d("tag", "filtrino: " + trails.get(trail).getName()
					+ " hinzugefügt, neue Size: " + myTrail.size());

		}
		return myTrail;

	}

	private void converte(JSONObject trailJson) throws JSONException {
		trailId = trailJson.getInt("Id");
		country = trailJson.getString("Country");
		countryId = trailJson.getInt("CountryId");
		creationDate = createDateFromJson(trailJson.getString("CreatedDate"));
		setLastModified(createDateFromJson(trailJson.getString("ModifiedDate")));
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
		url = trailJson.getString("Url");
	}

	private Date createDateFromJson(String string) {
		String timestamp = string.substring(6, string.length() - 7);
		return new Date(Long.valueOf(timestamp));
	}
}
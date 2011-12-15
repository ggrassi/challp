package ch.hsr.challp.and4.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;

public class Trail implements Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SERIALIZE_PATH = "/data/data/ch.hsr.challp.and/files/trails";
	private static final String SERIALIZE_FILE_NAME = "trails.ser";
	private static final File SERIALIZE_FILE = new File(SERIALIZE_PATH,
			SERIALIZE_FILE_NAME);
	private static String sorting = "Trail-ID";
	private static ArrayList<Trail> trailsArrayList = new ArrayList<Trail>();
	private int trailId, trailDevilsId, entfernung;
	float gmapX;
	float gmapY;
	private String country, description, imageUrl120, imageUrl800, name,
			nextCity;

	public Trail(JSONObject trailJson) {
		try {
			converte(trailJson);
			trailsArrayList.add(this);
		} catch (JSONException e) {
			Log.d("tag", "filtrino: " + e.toString() + "");
		}
	}

	public static ArrayList<Trail> getTrails() {
		return trailsArrayList;
	}

	public synchronized static void deserialize() {
		if (serializationExists()) {
			FileInputStream fis = null;
			ObjectInputStream in = null;
			try {
				fis = new FileInputStream(SERIALIZE_FILE);
				in = new ObjectInputStream(fis);
				trailsArrayList = (ArrayList<Trail>) in.readObject();
				in.close();
			} catch (IOException ex) {
				Log.d("tag", "filtrino: " + ex.toString() + "");
			} catch (ClassNotFoundException e) {
				Log.d("tag", "filtrino: " + e.toString() + "");
			}
		}
	}

	public static void serialize() {
		try {
			if (serializationExists()
					|| (new File(SERIALIZE_PATH).mkdirs() && SERIALIZE_FILE
							.createNewFile()))
				;

		} catch (IOException e) {
			Log.d("tag", "filtrino: " + "error: " + e.toString());

		}
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(SERIALIZE_FILE);
			out = new ObjectOutputStream(fos);
			out.writeObject(trailsArrayList);
			out.close();
		} catch (IOException ex) {
			Log.d("tag", "filtrino: " + "error: " + ex.toString());

		}
	}

	public static boolean serializationExists() {
		return SERIALIZE_FILE.exists();
	}

	private void converte(JSONObject trailJson) throws JSONException {
		trailId = trailJson.getInt("Id");
		country = trailJson.getString("Country");
		description = trailJson.getString("Desc");
		gmapX = (float) (trailJson.getDouble("GmapX"));
		gmapY = (float) (trailJson.getDouble("GmapY"));
		imageUrl120 = trailJson.getString("ImageUrl120");
		imageUrl800 = trailJson.getString("ImageUrl800");
		name = trailJson.getString("Name");
		nextCity = trailJson.getString("NextCity");
		setTrailDevilsId(trailJson.getInt("TraildevilsId"));
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

	public String getNextCity() {
		return nextCity;
	}

	public int getTrailDevilsId() {
		return trailDevilsId;
	}

	public void setTrailDevilsId(int trailDevilsId) {
		this.trailDevilsId = trailDevilsId;
	}

	public int compareTo(Object another) {
		Trail temp = (Trail) another;
		if (sorting.equalsIgnoreCase("trail-id")) {
			return new Integer(getTrailId()).compareTo(new Integer(temp
					.getTrailId()));
		}
		if (sorting.equalsIgnoreCase("name")) {
			return this.name.compareTo(temp.getName());
		}

		if (temp.getEntfernung() == this.getEntfernung()) {
			return 0;
		}
		if (temp.getEntfernung() == 0) {
			return -1;
		}
		if (this.getEntfernung() == 0) {
			return 1;
		}
		if (this.getEntfernung() < temp.getEntfernung()) {
			return -1;
		} else {
			return 1;
		}

	}

	public static void deleteEveryThing() {
		deleteRecursive(new File("/data/data/ch.hsr.challp.and/files"));
		trailsArrayList.clear();
	}

	private static void deleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory()) {
			for (File child : fileOrDirectory.listFiles()) {
				deleteRecursive(child);
			}
		}
		fileOrDirectory.delete();
	}

	@SuppressWarnings("unchecked")
	public static void setSorting(String string) {
		sorting = string;
		Collections.sort(trailsArrayList);
	}

	public int getEntfernung() {
		return entfernung;
	}

	public void setEntfernung(int entfernung) {
		this.entfernung = entfernung;
	}

	public static void calculateEntfernungen() {
		for (Trail t : trailsArrayList) {
			if (t.getGmapX() > 0 && t.getGmapY() > 0) {
				float[] results = new float[3];
				Location.distanceBetween(t.getGmapX(), t.getGmapY(),
						UserLocationListener.getInstance().getLatitude(),
						UserLocationListener.getInstance().getLongitude(),
						results);
				t.setEntfernung(Math.round(results[0]));
			}
		}

	}

}
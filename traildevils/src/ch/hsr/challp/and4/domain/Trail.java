package ch.hsr.challp.and4.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Trail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SERIALIZE_PATH = "/data/data/ch.hsr.challp.and/files/trails";
	private static final String SERIALIZE_FILE_NAME = "trails.ser";
	private static final File SERIALIZE_FILE = new File(SERIALIZE_PATH,
			SERIALIZE_FILE_NAME);
	// private static HashMap<Integer, Trail> trails;
	private static ArrayList<Trail> trailsArrayList = new ArrayList<Trail>();
	private int trailId, trailDevilsId;
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
		if (trailsArrayList == null || trailsArrayList.size() == 0) {
			deserialize();
		}
		return trailsArrayList;
	}

	public static void deserialize() {
		if (serializationExists()) {
			Log.d("tag",
					"filtrino: " + "start deserialize: "
							+ System.currentTimeMillis());

			FileInputStream fis = null;
			ObjectInputStream in = null;
			try {
				fis = new FileInputStream(SERIALIZE_FILE);
				in = new ObjectInputStream(fis);
				trailsArrayList = (ArrayList<Trail>) in.readObject();
				in.close();
				Log.d("tag",
						"filtrino: " + "end deserialize: "
								+ System.currentTimeMillis());

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
		Log.d("tag",
				"filtrino: " + "start serialize: " + System.currentTimeMillis());

		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(SERIALIZE_FILE);
			out = new ObjectOutputStream(fos);
			out.writeObject(trailsArrayList);
			out.close();
			Log.d("tag",
					"filtrino: " + "end serialize: "
							+ System.currentTimeMillis());

		} catch (IOException ex) {
			Log.d("tag", "filtrino: " + "error: " + ex.toString());

		}
	}

	public static boolean serializationExists() {
		return SERIALIZE_FILE.exists();
	}

	// private static ArrayList<Trail> converteMapToArrayList() {
	// ArrayList<Trail> myTrail = new ArrayList<Trail>();
	// for (Integer trail : new TreeSet<Integer>(trails.keySet())) {
	// myTrail.add(trails.get(trail));
	// }
	// return myTrail;
	// }

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

}
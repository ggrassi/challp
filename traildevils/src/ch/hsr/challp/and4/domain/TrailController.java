package ch.hsr.challp.and4.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.sortingStrategy.SortStrategy;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;

public class TrailController {
	private final File TRAILDEVILS_HOMEDIRECTORY = new File(
			"/data/data/ch.hsr.challp.and/files");
	private ArrayList<Trail> trailsArrayList = new ArrayList<Trail>();
	private final String SERIALIZE_PATH = "/data/data/ch.hsr.challp.android4/files/trails";
	private final String SERIALIZE_FILE_NAME = "trails.ser";
	private final File SERIALIZE_FILE = new File(SERIALIZE_PATH,
			SERIALIZE_FILE_NAME);
	private Context ctx;

	public TrailController(Context ctx) {
		this.ctx = ctx;
	}

	public ArrayList<Trail> getTrails() {
		return trailsArrayList;
	}

	@SuppressWarnings("unchecked")
	public void deserialize() throws IOException {
		if (serializationExists()) {
			FileInputStream fis = null;
			ObjectInputStream in = null;
			try {
				fis = new FileInputStream(SERIALIZE_FILE);
				in = new ObjectInputStream(fis);
				trailsArrayList = (ArrayList<Trail>) in.readObject();
			} catch (IOException ex) {
				Log.e(this.getClass().getName(), ex.toString());
			} catch (ClassNotFoundException e) {
				Log.e(this.getClass().getName(), e.toString());
			} finally {
				in.close();
			}
		}
	}

	public void serialize() throws IOException {
		try {
			handleSerializeFiles();

		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.toString());
			return;
		}
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(SERIALIZE_FILE);
			out = new ObjectOutputStream(fos);
			out.writeObject(trailsArrayList);
		} catch (IOException ex) {
			Log.e(this.getClass().getName(), ex.toString());

		} finally {
			out.close();
		}
	}

	public void handleSerializeFiles() throws IOException {
		if (serializationExists()) {
			return;
		}
		new File(SERIALIZE_PATH).mkdirs();
		SERIALIZE_FILE.createNewFile();
	}

	public boolean serializationExists() {
		return SERIALIZE_FILE.exists();
	}

	public void deleteEveryThing() {
		deleteRecursive(TRAILDEVILS_HOMEDIRECTORY);
		trailsArrayList.clear();
	}

	private void deleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory()) {
			for (final File child : fileOrDirectory.listFiles()) {
				deleteRecursive(child);
			}
		}
		fileOrDirectory.delete();
	}

	public void setSortStrategy(SortStrategy sortStrategy) {
		for (final Trail trail : trailsArrayList) {
			trail.setSortStrategy(sortStrategy);
		}
		Collections.sort(trailsArrayList);
	}

	public void calculateDistances() {
		final UserLocationListener locationListener = ((TrailDevils) ctx
				.getApplicationContext()).getUserLocation();
		for (final Trail t : trailsArrayList) {
			if (t.getGmapX() > 0 && t.getGmapY() > 0) {
				float[] results = new float[3];
				Location.distanceBetween(t.getGmapX(), t.getGmapY(),
						locationListener.getLatitude(),
						locationListener.getLongitude(), results);
				t.setDistance(Math.round(results[0]));
			}
		}

	}
}
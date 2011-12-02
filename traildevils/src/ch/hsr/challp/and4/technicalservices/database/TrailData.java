/***
 * Excerpted from "Hello, Android! 3e",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
 ***/
package ch.hsr.challp.and4.technicalservices.database;

import static ch.hsr.challp.and4.technicalservices.database.Constants.TABLE_NAME;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import ch.hsr.challp.and4.domain.Trail;

public class TrailData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "traildevils2";
	private static final int DATABASE_VERSION = 9;
	private static TrailData instance;

	/** Create a helper object for the Events database */
	private TrailData(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static TrailData getInstance() {

		return instance;
	}

	public static void initializeTrailData(Context ctx) {
		if (instance == null) {
			instance = new TrailData(ctx);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "
				+ TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY, object BLOB, added_on TIMESTAMP NOT NULL DEFAULT current_timestamp);");
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public boolean isEmpty(){
		    String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
		    SQLiteStatement statement = getWritableDatabase().compileStatement(sql);
		    return 0 != statement.simpleQueryForLong();
		}
	
	public void delete(int trailId){
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(trailId)});
		db.close();
	}

	public void add(Trail trail) {
		delete(trail.getTrailId());
		SQLiteDatabase db = getWritableDatabase();
		ContentValues dbTrail = new ContentValues();
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(trail);
			out.close();
			byteOut.close();
		} catch (Exception e) {
			Log.d("tag", "filtrino: " + "error in adding to DB:" + e.toString());
		}
		byte[] trailString = byteOut.toByteArray();
		dbTrail.put("_id", trail.getTrailId());
		dbTrail.put("object", trailString);
		// dbTrail.put("favorits", trail.getFavorits());
		// dbTrail.put("gmapX", trail.getGmapX());
		// dbTrail.put("gmapY", trail.getGmapY());
		// dbTrail.put("creationDate", trail.getCreationDate().toString());
		// dbTrail.put("lastModified", trail.getLastModified().toString());
		// dbTrail.put("description", trail.getDescription());
		// dbTrail.put("imageUrl120", trail.getImageUrl120());
		// dbTrail.put("imageUrl800", trail.getImageUrl800());
		// dbTrail.put("info", trail.getInfo());
		// dbTrail.put("journey", trail.getJourney());
		// dbTrail.put("name", trail.getName());
		// dbTrail.put("nextCity", trail.getNextCity());
		// dbTrail.put("state", trail.getState());
		// dbTrail.put("url", trail.getUrl());
		// dbTrail.put("isCommercial", trail.isCommercial());
		// dbTrail.put("isOpen", trail.isOpen());
		db.insert(TABLE_NAME, null, dbTrail);
		db.close();
	}

	public Trail get(int id) {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_NAME + " where _id = ?",
				new String[] { String.valueOf(id) });

		cursor.moveToFirst();

		byte[] blob = cursor.getBlob(1);
		cursor.close();
		return generateTrailFromByteArray(blob);

	}

	public HashMap<Integer, Trail> getAll() {
		HashMap<Integer, Trail> trails = new HashMap<Integer, Trail>();
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_NAME, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			Trail temp = generateTrailFromByteArray(cursor.getBlob(1));
			Log.d("tag",
					"filtrino: " + "Trail getted from DB: " + temp.getName());

			trails.put(temp.getTrailId(), temp);
			cursor.moveToNext();
		}
		cursor.close();

		return trails;
	}

	private Trail generateTrailFromByteArray(byte[] blob) {
		ByteArrayInputStream byteIn;
		ObjectInputStream in;
		Trail temp = null;
		try {
			byteIn = new ByteArrayInputStream(blob);
			in = new ObjectInputStream(byteIn);
			temp = (Trail) in.readObject();
			byteIn.close();
			in.close();

		} catch (Exception e) {
			Log.d("tag", "filtrino: " + "error:" + e.toString());

		}
		return temp;

	}

	public void truncate() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_NAME);
		db.execSQL("VACUUM");
	}
}
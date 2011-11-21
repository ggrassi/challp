/***
 * Excerpted from "Hello, Android! 3e",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
 ***/
package ch.hsr.challp.and4.technicalservices.database;

import static android.provider.BaseColumns._ID;
import static ch.hsr.challp.and4.technicalservices.database.Constants.TABLE_NAME;
import static ch.hsr.challp.and4.technicalservices.database.Constants.TIME;
import static ch.hsr.challp.and4.technicalservices.database.Constants.TITLE;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ch.hsr.challp.and4.domain.Trail;

public class TrailData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "traildevils2";
	private static final int DATABASE_VERSION = 2;
	

	/** Create a helper object for the Events database */
	public TrailData(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY, " + TIME + " INTEGER," + TITLE
				+ " TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	public void add(SQLiteDatabase db,Trail trail){
		ContentValues dbTrail = new ContentValues();
		dbTrail.put(_ID, trail.getTrailId());
		dbTrail.put(TIME, trail.getCreationDate().toString());
		dbTrail.put(TITLE, trail.getName());
		db.insert(TABLE_NAME, null, dbTrail);
	}
}
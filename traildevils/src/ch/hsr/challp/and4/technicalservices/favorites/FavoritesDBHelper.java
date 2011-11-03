package ch.hsr.challp.and4.technicalservices.favorites;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "traildevils";
	private static final String TABLE_NAME = "favorites";
	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (ID INTEGER UNIQUE);";

	public FavoritesDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTable(db);
		onCreate(db);
	}

	protected void addId(int trailId) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("INSERT INTO " + TABLE_NAME + " values(" + trailId
					+ ");");
			db.close();
		} catch (SQLiteConstraintException e) {
			// Will be thrwon when an ID allready exists in the DB
		} finally {
			db.close();
		}
	}

	protected void removeId(int trailId) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID = " + trailId + ";");
		db.close();
	}

	protected ArrayList<Integer> getIds() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { "ID" }, null,
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		db.close();
		return list;
	}

	public boolean isIdInDb(int trailId) {
		boolean test = false;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { "ID" },
				"ID = " + trailId, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			test = true;
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		db.close();
		return test;
	}

	private void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
}
package com.calloutcolorado.android.calloutcolorado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.calloutcolorado.android.calloutcolorado.DatabaseContract.ChallengeEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TEXT_TYPE = " TEXT";
	public static final String DOUBLE_TYPE = " REAL";
	public static final String INT_TYPE = " INTEGER";
	public static final String COMMA_SEP = " , ";
	public static final String DATABASE_NAME = "challenges.db";
	public static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + DatabaseContract.ChallengeEntry.TABLE_NAME + " (" +
					ChallengeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
					ChallengeEntry.COLUMN_LAT + DOUBLE_TYPE + COMMA_SEP +
					ChallengeEntry.COLUMN_LNG + DOUBLE_TYPE + COMMA_SEP +
					ChallengeEntry.SHORT_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
					ChallengeEntry.LONG_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
					" )";
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + ChallengeEntry.TABLE_NAME;
	private final String LOG_TAG = DatabaseHelper.class.getSimpleName();

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(LOG_TAG, "Helper Initialized");
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
		Log.d(LOG_TAG, "New Database Created");

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}


	public Cursor getAll(SQLiteDatabase db){
		return db.query(
				ChallengeEntry.TABLE_NAME,
				new String[] {
						ChallengeEntry._ID,
						ChallengeEntry.COLUMN_LAT,
						ChallengeEntry.COLUMN_LNG,
						ChallengeEntry.SHORT_DESCRIPTION,
						ChallengeEntry.LONG_DESCRIPTION}
				, null, null, null, null, null);
	}

	public void add(Challenge challenge) {

		Log.d("Adding Challenge to DB", "Yep");

		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.ChallengeEntry.COLUMN_LAT, challenge.latitude);
		values.put(DatabaseContract.ChallengeEntry.COLUMN_LNG, challenge.longitude);
		values.put(DatabaseContract.ChallengeEntry.SHORT_DESCRIPTION, challenge.short_desc);
		values.put(DatabaseContract.ChallengeEntry.LONG_DESCRIPTION, challenge.long_desc);

		db.insert(
				DatabaseContract.ChallengeEntry.TABLE_NAME,
				null,
				values);
		db.close();
	}


/*
	public Challenge retrieve(int challengeid) {
		Fortune loadedFortune = new Fortune();

		SQLiteDatabase db = getReadableDatabase();
		Cursor cur = db.query(ChallengeEntry.TABLE_NAME, null, null, null, null, null, null);
		cur.moveToPosition(fortuneid);

		loadedFortune.fortune = cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_FORTUNE));
		loadedFortune.english = cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_ENGLISH));
		loadedFortune.chinese = cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_CHINESE));
		loadedFortune.pro = cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_PRO));
		loadedFortune.lat = cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LAT));
		loadedFortune.lon = cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LONG));

		String[] lotto = {
				cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LOTTO1)),
				cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LOTTO2)),
				cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LOTTO3)),
				cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LOTTO4)),
				cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LOTTO5)),
				cur.getString(cur.getColumnIndex(ChallengeEntry.COLUMN_NAME_LOTTO6))
		};

		loadedFortune.lotto = lotto;


		return loadedFortune;
	}

	public ArrayList<String> listSavedFortunes() {

		SQLiteDatabase db = getReadableDatabase();
		Log.d("Loaded Database", db.getPath());
		ArrayList<String> loadlist = new ArrayList<>();
		Cursor cur = db.query(ChallengeEntry.TABLE_NAME, null, null, null, null, null, null);
		cur.moveToFirst();
		while (!cur.isAfterLast()) {
			loadlist.add(cur.getString(1));
			Log.d("Retrived Fortune", cur.getString(1));
			cur.moveToNext();
		}
		db.close();
		cur.close();
		return loadlist;
	}
*/
}

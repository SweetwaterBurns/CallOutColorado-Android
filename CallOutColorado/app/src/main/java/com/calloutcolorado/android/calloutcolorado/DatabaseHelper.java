import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.calloutcolorado.android.calloutcolorado.DatabaseContract;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TEXT_TYPE = " TEXT";
	public static final String COMMA_SEP = " , ";
	public static final String DATABASE_NAME = "challenges.db";
	public static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + DatabaseContract.ChallengeEntry.TABLE_NAME + " (" +
					DatabaseContract.ChallengeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
					DatabaseContract.ChallengeEntry.COLUMN_LAT + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.ChallengeEntry.COLUMN_LNG + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.ChallengeEntry.COLUMN_ZOOM + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.ChallengeEntry.SHORT_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.ChallengeEntry.LONG_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
					" )";
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + DatabaseContract.ChallengeEntry.TABLE_NAME;
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
/*
	public void add(Challenge challenge) {

		Log.d("Adding Fortune to DB", "Yep");

		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ChallengeEntry.COLUMN_NAME_FORTUNE, fortune.fortune);
		values.put(ChallengeEntry.COLUMN_NAME_ENGLISH, fortune.english);
		values.put(ChallengeEntry.COLUMN_NAME_CHINESE, fortune.chinese);
		values.put(ChallengeEntry.COLUMN_NAME_PRO, fortune.pro);
		values.put(ChallengeEntry.COLUMN_NAME_LAT, fortune.lat);
		values.put(ChallengeEntry.COLUMN_NAME_LONG, fortune.lon);

		values.put(ChallengeEntry.COLUMN_NAME_LOTTO1, fortune.lotto[0]);
		values.put(ChallengeEntry.COLUMN_NAME_LOTTO2, fortune.lotto[1]);
		values.put(ChallengeEntry.COLUMN_NAME_LOTTO3, fortune.lotto[2]);
		values.put(ChallengeEntry.COLUMN_NAME_LOTTO4, fortune.lotto[3]);
		values.put(ChallengeEntry.COLUMN_NAME_LOTTO5, fortune.lotto[4]);
		values.put(ChallengeEntry.COLUMN_NAME_LOTTO6, fortune.lotto[5]);

		//Log.d("Stored Lotto", fortune.lotto.toString());

		db.insert(
				DatabaseContract.ChallengeEntry.TABLE_NAME,
				null,
				values);
		db.close();


	}


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

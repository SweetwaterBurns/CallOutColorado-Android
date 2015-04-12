package com.calloutcolorado.android.calloutcolorado;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

public class ChallengesContentProvider extends ContentProvider {
	public static final String PROVIDER_NAME = "com.calloutcolorado.android.calloutcolorado";
	public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/locations" );
	/** Constant to identify the requested operation */
	private static final int LOCATIONS = 1;
	private static final UriMatcher uriMatcher ;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "locations", LOCATIONS);
	}

	DatabaseHelper mChallengesDB;

	public boolean onCreate() {
		mChallengesDB = new DatabaseHelper(getContext());
		return true;
	}

	/** A callback method which is invoked when insert operation is requested on this content provider */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = mChallengesDB.add(values);
		Uri _uri=null;
		if(rowID>0){
			_uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
		}else {
			try {
				throw new SQLException("Failed to insert : " + uri);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return _uri;
	}

	/** A callback method which is invoked when delete operation is requested on this content provider */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int cnt = 1;
		mChallengesDB.del();
		return cnt;
	}

	/** A callback method which is invoked by default content uri */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		if(uriMatcher.match(uri)==LOCATIONS){
			return mChallengesDB.getAll();
		}
		return null;
	}

	//Stubs need to be implemented correctly
	@Override
	public int update(Uri uri, ContentValues values, String selection,
	                  String[] selectionArgs) {
		return 0;
	}
	@Override
	public String getType(Uri uri) {
		return null;
	}
}

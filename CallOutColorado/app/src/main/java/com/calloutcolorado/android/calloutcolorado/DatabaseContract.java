package com.calloutcolorado.android.calloutcolorado;

import android.provider.BaseColumns;

/**
 * Created by ianmitchell on 4/11/15.
 */
public class DatabaseContract {
	public DatabaseContract() {
	}

	public static abstract class ChallengeEntry implements BaseColumns {
		public static final String TABLE_NAME = "challenges";
		public static final String COLUMN_LAT = "latitude";
		public static final String COLUMN_LNG = "longitude";
		public static final String SHORT_DESCRIPTION = "short_desc";
		public static final String LONG_DESCRIPTION = "long_desc";
	}
}

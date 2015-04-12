package com.calloutcolorado.android.calloutcolorado;

import android.location.Location;

/**
 * Created by ianmitchell on 4/12/15.
 */
public class CreateChallenge {
	private Challenge challenge;

	public CreateChallenge(Location location, String short_desc, String long_desc, DatabaseHelper dbhelper){
		challenge.latitude = location.getLatitude();
		challenge.longitude = location.getLongitude();
		challenge.short_desc = short_desc;
		challenge.long_desc = long_desc;
		//dbhelper.add(challenge);
	}

}

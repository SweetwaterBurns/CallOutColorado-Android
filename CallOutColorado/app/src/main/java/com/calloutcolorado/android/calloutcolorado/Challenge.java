package com.calloutcolorado.android.calloutcolorado;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ianmitchell on 4/11/15.
 */
public class Challenge implements Parcelable{

	public Double latitude;
	public Double longitude;
	public int zoom;
	public String short_desc;
	public String long_desc;

	private GoogleApiClient mGoogleApiClient;


	public Challenge() {

	}

	public Challenge(Parcel in) {
		this.latitude = in.readDouble();
		this.longitude = in.readDouble();
		this.zoom = in.readInt();
		this.short_desc = in.readString();
		this.long_desc = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}
/*
	public void save(DatabaseHelper dbhelper) {
		ContentValues
		dbhelper.add(this);
	}
*/
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.latitude);
		dest.writeDouble(this.longitude);
		dest.writeInt(this.zoom);
		dest.writeString(this.short_desc);
		dest.writeString(this.long_desc);
	}
}

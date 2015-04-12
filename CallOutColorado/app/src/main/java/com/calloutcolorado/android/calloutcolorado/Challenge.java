package com.calloutcolorado.android.calloutcolorado;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ianmitchell on 4/11/15.
 */
public class Challenge implements Parcelable{

	public Float latitude;
	public Float longitude;
	public int zoom;
	public String short_desc;
	public String long_desc;

	private GoogleApiClient mGoogleApiClient;


	public Challenge() {

	}

	public Challenge(Parcel in) {
		this.latitude = in.readFloat();
		this.longitude = in.readFloat();
		this.zoom = in.readInt();
		this.short_desc = in.readString();
		this.long_desc = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void save(DatabaseHelper dbhelper) {
		dbhelper.add(this);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(this.latitude);
		dest.writeFloat(this.longitude);
		dest.writeInt(this.zoom);
		dest.writeString(this.short_desc);
		dest.writeString(this.long_desc);
	}
}

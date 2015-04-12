package com.calloutcolorado.android.calloutcolorado;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalChallengesActivity extends FragmentActivity {
	private GoogleMap mMap; // Might be null if Google Play services APK is not available.
	private UiSettings mapSettings;
	Challenge testChallenge = new Challenge();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_challenges);
		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();

		if (mMap != null) {
			mMap.setMyLocationEnabled(true);
			mapSettings = mMap.getUiSettings();
			mapSettings.setZoomControlsEnabled(true);
		}
	}

	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
	 * installed) and the map has not already been instantiated.. This will ensure that we only ever
	 * call {@link #setUpMap()} once when {@link #mMap} is not null.
	 * <p/>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
	 * install/update the Google Play services APK on their device.
	 * <p/>
	 * A user can return to this FragmentActivity after following the prompt and correctly
	 * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
	 * have been completely destroyed during this process (it is likely that it would only be
	 * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
	 * method in {@link #onResume()} to guarantee that it will be called.
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	 * just add a marker near Africa.
	 * <p/>
	 * This should only be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() {
		testChallenge.longitude = -104.7167;
		testChallenge.latitude = 40.4167;
		testChallenge.short_desc = "Greeley";
		testChallenge.long_desc = "The Home of Boredom";
		addMarker(testChallenge);
	}

	/**
	 * Initialises the mapview
	 */
	private void createMapView(){
		/**
		 * Catch the null pointer exception that
		 * may be thrown when initialising the map
		 */
		try {
			if(null == mMap){
				mMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				/**
				 * If the map is still null after attempted initialisation,
				 * show an error to the user
				 */
				if(null == mMap) {
					Toast.makeText(getApplicationContext(),
							"Error creating map", Toast.LENGTH_SHORT).show();
				}
			}
		} catch (NullPointerException exception){
			Log.e("mapApp", exception.toString());
		}
	}

	/**
	 * Adds a marker to the map
	 */
	private void addMarker(Challenge challenge){

		Double latitude = challenge.latitude;
		Double longitude = challenge.longitude;
		String title = challenge.short_desc;
		String snippet = challenge.long_desc;

		/** Make sure that the map has been initialised **/
		if(null != mMap){
			mMap.addMarker(new MarkerOptions()
							.position(new LatLng(latitude, longitude))
							.title(title)
							.snippet(snippet)
							.draggable(true)
			);
		}
	}
}

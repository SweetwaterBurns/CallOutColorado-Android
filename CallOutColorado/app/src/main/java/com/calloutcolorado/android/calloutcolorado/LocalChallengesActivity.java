package com.calloutcolorado.android.calloutcolorado;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalChallengesActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private GoogleMap mMap; // Might be null if Google Play services APK is not available.
	private UiSettings mapSettings;
	Challenge testChallenge = new Challenge();
	GoogleMap.InfoWindowAdapter infoWindowAdapter;

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
			//mMap.setInfoWindowAdapter(infoWindowAdapter);
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
		mMap.setMyLocationEnabled(true);
		mapSettings = mMap.getUiSettings();
		mapSettings.setZoomControlsEnabled(true);
		infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {

				// Getting view from the layout file info_window_layout
				View v = getLayoutInflater().inflate(R.layout.challenge_info_window, null);

				// Getting the position from the marker
				LatLng latLng = marker.getPosition();


				// Getting reference to the TextView to set latitude
				TextView short_desc = (TextView) v.findViewById(R.id.short_desc);

				// Getting reference to the TextView to set longitude
				TextView long_desc = (TextView) v.findViewById(R.id.long_desc);

				// Setting the latitude
				short_desc.setText("Latitude:" + latLng.latitude);

				// Setting the longitude
				long_desc.setText("Longitude:" + latLng.longitude);

				// Returning the view containing InfoWindow contents
				return v;

			}
		};

		mMap.setInfoWindowAdapter(infoWindowAdapter);

		mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {

				// Drawing marker on the map
				drawMarker(point);

				// Creating an instance of ContentValues
				ContentValues contentValues = new ContentValues();

				// Setting latitude in ContentValues
				contentValues.put(DatabaseContract.ChallengeEntry.COLUMN_LAT, point.latitude );

				// Setting longitude in ContentValues
				contentValues.put(DatabaseContract.ChallengeEntry.COLUMN_LNG, point.longitude);
				// Setting Title (short description) in ContentValues
				contentValues.put(DatabaseContract.ChallengeEntry.SHORT_DESCRIPTION, "This is Here");
				// Setting snippet (Long description) in ContentValues
				contentValues.put(DatabaseContract.ChallengeEntry.LONG_DESCRIPTION, "Now you know.");
				// Creating an instance of LocationInsertTask
				LocationInsertTask insertTask = new LocationInsertTask();

				// Storing the latitude, longitude and zoom level to SQLite database
				insertTask.execute(contentValues);

				Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
			}
		});

		mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng point) {

				// Removing all markers from the Google Map
				mMap.clear();

				// Creating an instance of ChallengesDeleteTask
				ChallengesDeleteTask deleteTask = new ChallengesDeleteTask();

				// Deleting all the rows from SQLite database table
				deleteTask.execute();

				Toast.makeText(getBaseContext(), "All markers are removed", Toast.LENGTH_LONG).show();
			}
		});

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

	private void drawMarker(LatLng point){
		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions();

		// Setting latitude and longitude for the marker
		markerOptions.position(point);

		// Adding marker on the Google Map
		mMap.addMarker(markerOptions);
	}

	private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void>{
		@Override
		protected Void doInBackground(ContentValues... contentValues) {

			/** Setting up values to insert the clicked location into SQLite database */
			getContentResolver().insert(ChallengesContentProvider.CONTENT_URI, contentValues[0]);
			return null;
		}
	}

	private class ChallengesDeleteTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			/** Deleting all the locations stored in SQLite database */
			getContentResolver().delete(ChallengesContentProvider.CONTENT_URI, null, null);
			return null;
		}
	}


	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Uri to the content provider LocationsContentProvider
		Uri uri = ChallengesContentProvider.CONTENT_URI;
		// Fetches all the rows from locations table
		return new CursorLoader(this, uri, null, null, null, null);
	}


	public void onLoadFinished(Loader<Cursor> arg0,
	                           Cursor arg1) {
		int locationCount;
		double lat = 0;
		double lng = 0;
		float zoom = 0;
		String short_desc;
		String long_desc;

		// Number of locations available in the SQLite database table
		locationCount = arg1.getCount();

		// Move the current record pointer to the first row of the table
		arg1.moveToFirst();

		for(int i=0;i<locationCount;i++){

			// Get the latitude
			lat = arg1.getDouble(arg1.getColumnIndex(DatabaseContract.ChallengeEntry.COLUMN_LAT));

			// Get the longitude
			lng = arg1.getDouble(arg1.getColumnIndex(DatabaseContract.ChallengeEntry.COLUMN_LNG));

			short_desc = arg1.getString(arg1.getColumnIndex(DatabaseContract.ChallengeEntry.SHORT_DESCRIPTION));

			long_desc = arg1.getString(arg1.getColumnIndex(DatabaseContract.ChallengeEntry.LONG_DESCRIPTION));

			// Get the zoom level
			zoom = 13;

			// Creating an instance of LatLng to plot the location in Google Maps
			LatLng location = new LatLng(lat, lng);

			// Drawing the marker in the Google Maps
			mMap.addMarker(new MarkerOptions()
					.position(location)
					.title(short_desc)
					.snippet(long_desc));

		// Traverse the pointer to the next row
			arg1.moveToNext();
		}

		if(locationCount>0){
			// Moving CameraPosition to last clicked position
			mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

			// Setting the zoom level in the map on last position  is clicked
			mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
	}

}

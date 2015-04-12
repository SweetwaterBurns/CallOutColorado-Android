package com.calloutcolorado.android.calloutcolorado;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalChallengesActivity extends FragmentActivity {
// http://wptrafficanalyzer.in/blog/storing-and-retrieving-locations-in-sqlite-from-google-maps-android-api-v2/
	private GoogleMap mMap; // Might be null if Google Play services APK is not available.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_challenges);
        Object value;
        setUpMapIfNeeded();
        Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"ConnectCO", "Events", "Profile"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {




            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Log.d("Hiya","Please work!");
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        //Intent intent = new Intent(getApplicationContext(), );
                        Log.d("Debug", "Debugging2");
                        break;
                    case 2:
                        Log.d("Debug", "Debugging");
                        Intent intent = new Intent(getApplicationContext(), profilePageActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }  Log.d("Debug", "Debugging3");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
		mMap.addMarker(new MarkerOptions().position(new LatLng(-104, 40)).title("Greeley"));
	}

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
}

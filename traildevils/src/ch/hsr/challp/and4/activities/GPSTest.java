package ch.hsr.challp.and4.activities;

import ch.hsr.challp.and4.R;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class GPSTest extends Activity {
	TextView textViewLatitude;
	TextView textViewLongitude;
	TextView textViewAdam;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.gps_test);
		
		textViewLatitude = (TextView) findViewById(R.id.latitud_value);
		textViewLongitude = (TextView) findViewById(R.id.longitud_value);
		textViewAdam = (TextView) findViewById(R.id.adam_value);
		
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
	}

	public class MyLocationListener implements LocationListener	{

		public void onLocationChanged(Location location) {
			textViewLatitude.setText(String.valueOf(location.getLatitude()));
			textViewLongitude.setText(String.valueOf(location.getLongitude()));
			
			//Amsterdam:
			float adamLatitude = 52.370197222222f;
			float adamgetLongitude = 4.8904444444445f;
			
			//http://developer.android.com/reference/android/location/Location.html
			float[] results = new float[3];
			Location.distanceBetween(location.getLatitude(), location.getLongitude(), adamLatitude, adamgetLongitude, results);
			
			try {
				textViewAdam.setText(String.valueOf(results[0]) + " m");
			} catch (Exception e) {
				textViewAdam.setText("Error: " + e.toString());
			}
			
			
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub	
		}
	}
}

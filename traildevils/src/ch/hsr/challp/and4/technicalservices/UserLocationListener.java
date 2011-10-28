package ch.hsr.challp.and4.technicalservices;

import java.util.Observable;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import ch.hsr.challp.and4.activities.StartScreen;

public class UserLocationListener extends Observable implements
		LocationListener {
	private static final int LEGAL_AGE_OF_LOCATION = 1000 * 60 * 2;

	private static UserLocationListener ref = null;
	private Location current = new Location("newOne");

	public static UserLocationListener getInstance() {
		if (ref == null) {
			ref = new UserLocationListener();
		}
		return ref;
	}

	private UserLocationListener() {
		LocationManager mlocManager = (LocationManager) StartScreen
				.getLocationService();
		LocationListener mlocListener = this;
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	public Location getLocation() {
		return current;
	}

	public double getLatitude() {
		return current.getLatitude();
	}

	public double getLongitude() {
		return current.getLongitude();
	}
	
	public void onLocationChanged(Location location) {
		Log.d("tag", "filtrino: " + "onLocationChanged()");
		if (isBetterLocation(location, current)) {
			this.current = location;
			this.setChanged();
			this.notifyObservers(location);
		}
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > LEGAL_AGE_OF_LOCATION;
		boolean isSignificantlyOlder = timeDelta < -LEGAL_AGE_OF_LOCATION;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	public void onProviderDisabled(String provider) {
		// ignore
	}

	public void onProviderEnabled(String provider) {
		// ignore
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// ignore
	}
}
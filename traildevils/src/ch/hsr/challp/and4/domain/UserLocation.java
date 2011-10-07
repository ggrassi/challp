package ch.hsr.challp.and4.domain;

import android.location.Location;


public class UserLocation {
	private static UserLocation ref = null;

	private double latitude = -1f;
	private double longitude = -1f;

	public static UserLocation getInstance() {
		if (ref == null) {
			ref = new UserLocation();
		}
		return ref;
	}

	private UserLocation() {

	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLocation(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}

}

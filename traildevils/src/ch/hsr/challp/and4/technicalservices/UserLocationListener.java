package ch.hsr.challp.and4.technicalservices;

import ch.hsr.challp.and4.domain.UserLocation;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class UserLocationListener implements LocationListener	{

	public void onLocationChanged(Location location) {
		UserLocation.getInstance().setLocation(location);
	}

	public void onProviderDisabled(String provider) {
		//ignore
	}

	public void onProviderEnabled(String provider) {
		//ignore
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		//ignore	
	}
}
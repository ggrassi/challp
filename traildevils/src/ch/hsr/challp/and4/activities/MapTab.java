package ch.hsr.challp.and4.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import ch.hsr.challp.and4.R;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MapTab extends MapActivity {
	private long geoKontaktId;
	
//	private GeoKontakt kontakt;
	private LinearLayout linLayout;
	private MapView mapView;
	private MapController mapCtrl;
	private MyLocationOverlay locationOverlay;
//	private GeoKontactOverlay mapViewOverlay;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		initMapView();
	}
	
	private void initMapView() {
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
	
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
package ch.hsr.challp.and4.activities;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.and4.technicalservices.TrailizedOverlay;
import ch.hsr.challp.android4.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapTab extends MapActivity implements Observer {
	private static final int zoomCorrection = 11;
	private MapView mapView;
	private MapController mapCtrl;
	private MyLocationOverlay myLocationOverlay;
	private List<Overlay> mapOverlay;
	private Drawable drawable;
	private TrailizedOverlay trailizedOverlay;
	private GeoPoint point;
	private OverlayItem overlayItem;
	private TrailController trailController;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		trailController = ((TrailDevils) getApplication()).getTrailController();
		initMapView();
		initMyLocationOverlay();
		initTrailOverlay();
	}

	private void initTrailOverlay() {
		mapOverlay = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.map_marker);
		trailizedOverlay = new TrailizedOverlay(drawable,
				getApplicationContext());
		int xCoor, yCoor;
		for (Trail trail : trailController.getTrails()) {
			xCoor = (int) (trail.getGmapX() * 1E6);
			yCoor = (int) (trail.getGmapY() * 1E6);
			point = new GeoPoint(xCoor, yCoor);
			overlayItem = new OverlayItem(point, trail.getName(),
					String.valueOf(trail.getTrailId()));
			trailizedOverlay.addOverlayItem(overlayItem);
		}

		mapOverlay.add(trailizedOverlay);
		trailizedOverlay.doPopulate();
	}

	private void initMyLocationOverlay() {
		myLocationOverlay = new MyLocationOverlay(this, mapView) {

			@Override
			public void onLocationChanged(Location newPosition) {
				super.onLocationChanged(newPosition);
				final GeoPoint myPosition = new GeoPoint(
						(int) (newPosition.getLatitude() * 1E6),
						(int) (newPosition.getLongitude() * 1E6));

				mapCtrl.animateTo(myPosition);
			}
		};

		mapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
		myLocationOverlay.runOnFirstFix(new Runnable() {

			public void run() {
				mapCtrl.animateTo(myLocationOverlay.getMyLocation());
			}
		});
	}

	private void initMapView() {
		mapView = (MapView) findViewById(R.id.mapview);
		mapCtrl = mapView.getController();

		final int maxZoomLevel = mapView.getMaxZoomLevel() - zoomCorrection;
		mapCtrl.setZoom(maxZoomLevel);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
	}

	@Override
	protected void onResume() {
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
		mapView.invalidate();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void update(Observable observable, Object data) {
		// TabContainer.tabHost.getTabWidget().getChildTabViewAt(1).setEnabled((Boolean)
		// data);

	}
}
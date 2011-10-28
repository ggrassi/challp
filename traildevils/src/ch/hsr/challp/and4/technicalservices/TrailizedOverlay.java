package ch.hsr.challp.and4.technicalservices;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class TrailizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
	
	public TrailizedOverlay(Drawable defaultMarker) {
		super(boundCenter((defaultMarker)));
	}
	
	public void addOverlayItem(OverlayItem overlayItem){
		overlayItems.add(overlayItem);
 	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlayItems.get(i);
	}

	@Override
	public int size() {
		return overlayItems.size();
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow){
		if(!shadow){
			super.draw(canvas, mapView, shadow);
		}
	}

	public void doPopulate() {
		populate();
		
	}

}

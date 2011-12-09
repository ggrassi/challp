package ch.hsr.challp.and4.technicalservices;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class TrailizedOverlay extends ItemizedOverlay<OverlayItem> {

	@Override
	protected boolean onTap(int index) {		
		Intent ac = new Intent(".activities.TrailDetail");
		ac.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ac.putExtra("key", Integer.parseInt((overlayItems.get(index)).getSnippet()));
		context.startActivity(ac);
		return true;
	}

	private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
	private Context context;
	
	public TrailizedOverlay(Drawable defaultMarker, Context c) {
		super(boundCenterBottom((defaultMarker)));
		context = c;
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

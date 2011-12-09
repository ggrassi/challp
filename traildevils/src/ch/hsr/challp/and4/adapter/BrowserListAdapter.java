package ch.hsr.challp.and4.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.hsr.challp.and.R;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.technicalservices.UserLocationListener;

public class BrowserListAdapter extends ArrayAdapter<Trail> implements Observer {

	private ArrayList<Trail> trails;
	private Context context;

	public BrowserListAdapter(Context context, int textViewResourceId,
			ArrayList<Trail> trails) {
		super(context, textViewResourceId, trails);
		this.trails = trails;
		this.context = context;
		UserLocationListener.getInstance().addObserver(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_entry, null);
		}

		Trail trail = trails.get(position);
		if (trail != null) {
			ImageView listImg = (ImageView) v.findViewById(R.id.status_icon);

			TextView parkname = (TextView) v.findViewById(R.id.list_parkname);
			TextView info = (TextView) v.findViewById(R.id.list_info);
			TextView date = (TextView) v.findViewById(R.id.list_date);

			Drawable trailDraw = null;

			if (new File(context.getFilesDir(), String.valueOf(trail
					.getTrailId()) + "_120.png").exists()) {
				String fname = new File(context.getFilesDir(),
						String.valueOf(trail.getTrailId()) + "_120.png")
						.getAbsolutePath();
				trailDraw = new BitmapDrawable(BitmapFactory.decodeFile(fname));
			} else {

				try {
					InputStream is = (InputStream) new URL(
							trail.getImageUrl120()).getContent();
					trailDraw = Drawable.createFromStream(is, "src name");
				} catch (Exception e) {
					System.out.println("Exc=" + e);
				}

				FileOutputStream fos;
				try {
					fos = context.openFileOutput(
							String.valueOf(trail.getTrailId()) + "_120.png",
							Context.MODE_PRIVATE);
					if (trailDraw != null) {
						((BitmapDrawable) trailDraw).getBitmap().compress(
								Bitmap.CompressFormat.PNG, 100, fos);
					}
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (trailDraw != null) {
				listImg.setImageDrawable(trailDraw);
			} else {
				listImg.setImageResource(R.drawable.icon);
			}

			if (parkname != null && info != null && date != null) {
				parkname.setText(trail.getName());
				info.setText(getDistanceText(trail));
				// info.setText("TX" + String.valueOf(trail.getGmapX()) + "TY" +
				// String.valueOf(trail.getGmapY()));
				date.setText(Html.fromHtml(trail.getDescription()).toString());
			}
		}
		return v;
	}

	private static String getDistanceText(Trail trail) {
		StringBuilder distance = new StringBuilder();
		try {
			// http://developer.android.com/reference/android/location/Location.html
			float[] results = new float[3];
			if (UserLocationListener.getInstance().getLatitude() > 0
					&& UserLocationListener.getInstance().getLongitude() > 0) {
				Location.distanceBetween(trail.getGmapX(), trail.getGmapY(),
						UserLocationListener.getInstance().getLatitude(),
						UserLocationListener.getInstance().getLongitude(),
						results);
				if (true) { // TODO: Check if value is realistic
					distance.append(Math.round(results[0] / 1000));
					distance.append(" km");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (trail.getNextCity() != null && !trail.getNextCity().equals("null")) {
			distance.append(", " + trail.getNextCity());
		}
		return distance.toString();
	}

	public void update(Observable observable, Object data) {
		this.clear();
		for(Trail trail : Trail.getTrails()){
			this.trails.add(trail);
		}
		Log.d("tag", "filtrino: " + "update()");
		notifyDataSetChanged();
	}

}

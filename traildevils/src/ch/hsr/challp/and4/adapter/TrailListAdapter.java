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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.android4.R;

public abstract class TrailListAdapter extends ArrayAdapter<Trail> implements
		Observer, Filterable {
	
	protected ArrayList<Trail> trails;
	private TrailFilter filter;
	protected TrailController trailController;
	private TrailDevils app;

	public TrailListAdapter(Context context, int textViewResourceId,
			ArrayList<Trail> trails) {
		super(context, textViewResourceId, trails);
		app = ((TrailDevils)context.getApplicationContext());
		trailController = app.getTrailController();
		this.trails=trails;
		app.getUserLocation().addObserver(this);
	}

	public abstract void update(Observable observable, Object data);

	public void loadNew() {
		trails = trailController.getTrails();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_entry, null);
		}

		Trail trail = trails.get(position);

		if (trail != null) {
			TextView parkname = (TextView) v.findViewById(R.id.list_parkname);
			TextView info = (TextView) v.findViewById(R.id.list_info);
			TextView date = (TextView) v.findViewById(R.id.list_date);

			if (parkname != null && info != null && date != null) {
				parkname.setText(trail.getName());
				info.setText(getDistanceText(trail));
				date.setText(Html.fromHtml(trail.getDescription()).toString());
			}
			setTrailIcon(trail, v);
			v.setId(trail.getTrailId());
		}
	
		return v;
	}

	private String getDistanceText(Trail trail) {
		StringBuilder distance = new StringBuilder();
		try {
			float[] results = new float[3];
			if (app.getUserLocation().getLatitude() > 0
					&& app.getUserLocation().getLongitude() > 0) {
				Location.distanceBetween(trail.getGmapX(), trail.getGmapY(),
						app.getUserLocation().getLatitude(),
						app.getUserLocation().getLongitude(),
						results);
				trailController.calculateDistances();
				distance.append(Math.round(results[0] / 1000) + " km");
				if (trail.getNextCity() != null
						&& !trail.getNextCity().equals("null")) {
					distance.append(", ");
				}
			}
		} catch (Exception e) {
			Log.e(this.getClass().getName(), e.toString());

		}

		if (trail.getNextCity() != null && !trail.getNextCity().equals("null")) {
			distance.append(trail.getNextCity());
		}
		return distance.toString();
	}

	public void setTrailIcon(Trail trail, View v) {
		try {
			loadImage(trail, (ImageView) v.findViewById(R.id.status_icon));
		} catch (Exception e) {
			Log.e(this.getClass().getName(), e.toString());

		}
		View iconContainer = v.findViewById(R.id.status_icon);
		iconContainer.setVisibility(View.VISIBLE);
	}

	private void loadImage(Trail trail, ImageView listImg) throws Exception {
		Drawable trailDraw = null;
		File imageFile = new File(getContext().getFilesDir(),
				String.valueOf(trail.getTrailId()) + "_120.png");
		if (imageFile.exists()) {
			trailDraw = new BitmapDrawable(BitmapFactory.decodeFile(imageFile
					.getAbsolutePath()));
		} else {
			if (trail.getImageUrl120() != null
					&& !"null".equals(trail.getImageUrl120())) {
				InputStream is = (InputStream) new URL(trail.getImageUrl120())
						.getContent();
				trailDraw = Drawable.createFromStream(is, "src name");
				saveDownloadedFile(trail, trailDraw);
			}
		}

		if (trailDraw != null) {
			listImg.setImageDrawable(trailDraw);
		} else {
			listImg.setImageResource(R.drawable.icon);
		}

	}

	private void saveDownloadedFile(Trail trail, Drawable trailDraw)
			throws FileNotFoundException, IOException {
		FileOutputStream fos;
		fos = getContext().openFileOutput(
				String.valueOf(trail.getTrailId()) + "_120.png",
				Context.MODE_PRIVATE);
		if (trailDraw != null) {
			((BitmapDrawable) trailDraw).getBitmap().compress(
					Bitmap.CompressFormat.PNG, 100, fos);
		}
		fos.flush();
		fos.close();
	}

	@Override
	public int getCount() {
		return trails.size();
	}

	@Override
	public Trail getItem(int position) {
		return trails.get(position);
	}
	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new TrailFilter();
		}
		return filter;
	}

	private class TrailFilter extends Filter {

		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			String filterString = constraint.toString().toLowerCase();

			final ArrayList<Trail> items = trailController.getTrails();
			final int count = items.size();
			final ArrayList<Trail> newItems = new ArrayList<Trail>(count);
			for (int i = 0; i < count; i++) {
				final Trail item = items.get(i);
				final String itemName = item.getName().toLowerCase();
				if (itemName.contains(filterString)) {
					newItems.add(item);
				}
				results.values = newItems;
				results.count = newItems.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			trails = (ArrayList<Trail>) results.values;
			// Let the adapter know about the updated list
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}
}

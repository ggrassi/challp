package ch.hsr.challp.and4.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.hsr.challp.and4.R;
import ch.hsr.challp.and4.domain.Trail;

public class BrowserListAdapter extends ArrayAdapter<Trail> {

	private ArrayList<Trail> trails;

	public BrowserListAdapter(Context context, int textViewResourceId,
			ArrayList<Trail> trails) {
		super(context, textViewResourceId, trails);
		this.trails = trails;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
//			LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			v = vi.inflate(ch.hsr.challp.and4.R.layout.list_entry, null);
		}

		Trail trail = trails.get(position);
		if (trail != null) {
			TextView parkname = (TextView) v
					.findViewById(R.id.list_parkname);
			TextView info = (TextView) v
					.findViewById(R.id.list_info);
			TextView date = (TextView) v
					.findViewById(R.id.list_date);

			if (parkname != null && info != null && date != null) {
				parkname.setText(trail.getName());
				info.setText(trail.getInfo());
				date.setText(trail.getCreationDate().toString());
			}
		}
		return v;

	}

}

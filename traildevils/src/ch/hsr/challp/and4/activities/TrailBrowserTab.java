package ch.hsr.challp.and4.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.hsr.challp.android4.R;
import ch.hsr.challp.and4.adapter.BrowserListAdapter;
import ch.hsr.challp.and4.domain.Trail;

public class TrailBrowserTab extends ListActivity {
	private BrowserListAdapter t_adapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		ArrayList<Trail> trails = Trail.getTrails();

		this.t_adapter = new BrowserListAdapter(this, R.layout.list_entry,
				trails);
		setListAdapter(this.t_adapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent ac = new Intent(".activities.TrailDetail");
				ac.putExtra("key", t_adapter.getItem(position).getTrailId());
				startActivity(ac);
			}
		});
	}
}

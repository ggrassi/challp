package ch.hsr.challp.and4.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.hsr.challp.and4.adapter.FavoritesListAdapter;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.android4.R;

public class FavoritesTab extends ListActivity {

	@Override
	protected void onResume() {
		((FavoritesListAdapter)getListAdapter()).loadNew();
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		TrailDevils app = ((TrailDevils) getApplication());

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(getOnItemClickListener());
		setListAdapter(new FavoritesListAdapter(this, R.layout.list_entry, app));

	}

	private OnItemClickListener getOnItemClickListener() {
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent ac = new Intent(".activities.TrailDetail");
				ac.putExtra("key",
						((Trail) parent.getAdapter().getItem(position))
								.getTrailId());
				startActivity(ac);
			}
		};
	}

	@Override
	public boolean onSearchRequested() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		return false;
	}
}

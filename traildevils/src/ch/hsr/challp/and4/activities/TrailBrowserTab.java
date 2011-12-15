package ch.hsr.challp.and4.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.hsr.challp.and4.adapter.BrowserListAdapter;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.android4.R;

public class TrailBrowserTab extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		ArrayList<Trail> trails = Trail.getTrails();

		setListAdapter(new BrowserListAdapter(this, R.layout.list_entry, trails));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent ac = new Intent(".activities.TrailDetail");

				ac.putExtra("key",
						((Trail) parent.getAdapter().getItem(position))
								.getTrailId());
				startActivity(ac);
			}
		});
	}

	@Override
	public boolean onSearchRequested() {
		Log.d("tag", "filtrino: Inner-SEARCH!!");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		return false;
	}

}

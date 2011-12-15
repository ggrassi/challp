package ch.hsr.challp.and4.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import ch.hsr.challp.and4.adapter.BrowserListAdapter;
import ch.hsr.challp.and4.adapter.TrailListAdapter;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.technicalservices.JSONParser;
import ch.hsr.challp.android4.R;

public class TrailBrowserTab extends ListActivity {
	final static int LOAD_NEW = 0, SORT_ID = 1, SORT_NAME = 2,
			SORT_FAVORITS = 3, SORT_DISTANCE = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		ArrayList<Trail> trails = Trail.getTrails();

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
		setListAdapter(new BrowserListAdapter(this, R.layout.list_entry, trails));

	}

	public class SortedOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Do nothing
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.list_header, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reload:
			reload();
			return true;
		case R.id.sort_Entfernung:
			sort("Entfernung");
			return true;
		case R.id.sort_ID:
			sort("trail-id");
			return true;
		case R.id.sort_Name:
			sort("name");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void sort(String string) {
		Trail.setSorting(string);
		((TrailListAdapter) getListAdapter()).loadNew();
	}

	@Override
	public boolean onSearchRequested() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		return false;
	}

	public void reload() {
		Trail.deleteEveryThing();
		JSONParser parser = new JSONParser(getString(R.string.JSONUrl));
		parser.start();
		try {
			parser.join();
		} catch (InterruptedException e) {
			Log.d("tag", "filtrino: " + e.toString());
		}
		((TrailListAdapter) getListAdapter()).loadNew();
	}
}

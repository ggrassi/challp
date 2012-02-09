package ch.hsr.challp.and4.activities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
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
import android.widget.ListView;
import ch.hsr.challp.and4.adapter.BrowserListAdapter;
import ch.hsr.challp.and4.adapter.TrailListAdapter;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.domain.TrailController;
import ch.hsr.challp.and4.domain.sortingStrategy.SortByDistance;
import ch.hsr.challp.and4.domain.sortingStrategy.SortByName;
import ch.hsr.challp.and4.domain.sortingStrategy.SortByPopularity;
import ch.hsr.challp.and4.domain.sortingStrategy.SortStrategy;
import ch.hsr.challp.and4.technicalservices.JSONParser;
import ch.hsr.challp.android4.R;

public class TrailBrowserTab extends ListActivity{

	private TrailController trailController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		trailController = ((TrailDevils) getApplication()).getTrailController();

		final ListView lv = getListView();
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
		setListAdapter(new BrowserListAdapter(this, R.layout.list_entry,
				trailController.getTrails()));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
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
			sort(new SortByDistance());
			return true;
		case R.id.sort_Favorits:
			sort(new SortByPopularity());
			return true;
		case R.id.sort_Name:
			sort(new SortByName());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void sort(SortStrategy sortStrategy) {
		trailController.setSortStrategy(sortStrategy);
		((TrailListAdapter) getListAdapter()).loadNew();
	}

	@Override
	public boolean onSearchRequested() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		return false;
	}

	public void reload() {
		trailController.deleteEveryThing();
		final JSONParser parser = new JSONParser(getString(R.string.JSONUrl),
				null, trailController);
		parser.start();
		try {
			parser.join();
		} catch (InterruptedException e) {
			Log.e(this.getClass().getName(), e.toString());
		}
		((TrailListAdapter) getListAdapter()).loadNew();
	}

	public void update(Observable observable, Object data) {
		
	}
}

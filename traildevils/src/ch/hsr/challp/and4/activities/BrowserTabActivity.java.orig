package ch.hsr.challp.and4.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import ch.hsr.challp.and4.R;
import ch.hsr.challp.and4.adapter.BrowserListAdapter;
import ch.hsr.challp.and4.domain.Trail;

public class BrowserTabActivity extends ListActivity {
	
	private ArrayList<Trail> trails = null;
	private BrowserListAdapter t_adapter = null;
	private Runnable viewTrails;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.browser_tab);
		trails = Trail.getTrails();
		this.t_adapter = new BrowserListAdapter(this, R.layout.list_entry, trails);
		setListAdapter(this.t_adapter);

	}
}

package ch.hsr.challp.and4.adapter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import ch.hsr.challp.and4.domain.Trail;

public class BrowserListAdapter extends TrailListAdapter implements Observer {

	public BrowserListAdapter(Context context, int textViewResourceId,
			ArrayList<Trail> trails) {
		super(context, textViewResourceId, trails);
	}

	public void update(Observable observable, Object data) {
		super.loadNew();
	}

	
}

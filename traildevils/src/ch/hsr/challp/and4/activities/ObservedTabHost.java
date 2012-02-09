package ch.hsr.challp.and4.activities;

import android.content.Context;
import ch.hsr.challp.and4.billing.Observer;

public class ObservedTabHost extends android.widget.TabHost implements Observer {

	public ObservedTabHost(Context context) {
		super(context);
	}

	public void handleEvent() {
		getTabWidget().getChildTabViewAt(1).setEnabled(true);
	}

}

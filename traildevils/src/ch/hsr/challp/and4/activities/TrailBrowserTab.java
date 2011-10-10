package ch.hsr.challp.and4.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.hsr.challp.and4.R;
import ch.hsr.challp.and4.adapter.BrowserListAdapter;
import ch.hsr.challp.and4.domain.Trail;

public class TrailBrowserTab extends ListActivity {
	private ArrayList<Trail> trails = null;
	private BrowserListAdapter t_adapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		trails = Trail.getTrails();
		
		this.t_adapter = new BrowserListAdapter(this,
				R.layout.list_entry, trails);
		setListAdapter(this.t_adapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent ac = new Intent(".activities.TrailDetail");
				ac.putExtra("key", trails.get(position).getTrailId());
				startActivity(ac);
			}
		});
	}
	/*

    SQLiteDatabase myDB = null;
    try{
        myDB = this.openOrCreateDatabase("TankPro2", MODE_PRIVATE, null);
        Cursor c = myDB.rawQuery("SELECT _id, parkname, " +
        		                        "wetterMorgen, " +
        		                        "wetterAbend, " +
        		                        "zustand " +
        		                        "FROM parktrail", null);        
           startManagingCursor(c);    

           getListView().setOnCreateContextMenuListener(this);
           
           final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_entry, 
                c,
                new String[] { "_id", "parkname", "wetterMorgen", "wetterAbend", "zustand" },
                new int[] { R.id.parkname, R.id.wetter_morgen, R.id.wetter_abend, R.id.status_icon }
           );
          
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                     
            public boolean setViewValue(View view, Cursor theCursor, int column) 
            {
            	switch(view.getId()) {
            	 case R.id.parkname: 
            		String ColName = theCursor.getString(1); //Name
                    ((TextView)view).setText(ColName);
          	        return true;  
            	 case R.id.wetter_morgen: 
             		final String ColWetterMorgen = theCursor.getString(2); //wetter am morgen
                     ((TextView)view).setText(ColWetterMorgen);
           	        return true;  
            	 case R.id.wetter_abend:
            		final String ColWetterAbend = theCursor.getString(3); //wetter am abend
                   	((TextView)view).setText(ColWetterAbend);  
                    return true;
            	 case R.id.status_icon:
            		 final int ColZustand = theCursor.getInt(4); //OFFEN ODER GESCHLOSSEN
            		 if(ColZustand == 0) {
            			 ((ImageView)view).setImageResource(R.drawable.offen);
            		 }
            		 else if(ColZustand == 1){
            			 ((ImageView)view).setImageResource(R.drawable.closed);
            		 }
            		 return true;                        
            	}
            	return false;
            }
        });
        
        this.setListAdapter(adapter);
    }finally {
        if (myDB != null)
            myDB.close();
    }
    */
}

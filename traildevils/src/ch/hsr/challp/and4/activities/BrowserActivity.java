package ch.hsr.challp.and4.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BrowserActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

//	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, PARKS ));
//
//	  ListView lv = getListView();
//	  lv.setTextFilterEnabled(true);
//
//	  lv.setOnItemClickListener(new OnItemClickListener() {
//	    public void onItemClick(AdapterView<?> parent, View view,
//	        int position, long id) {
//	      // When clicked, show a toast with the TextView text
//	      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//	          Toast.LENGTH_SHORT).show();
//	    }
//	  });
	}
	
	
	@Override
    protected void onStart() {
        super.onStart();
        
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
	

}
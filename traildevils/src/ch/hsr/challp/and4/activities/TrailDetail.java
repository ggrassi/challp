package ch.hsr.challp.and4.activities;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import ch.hsr.challp.and4.R;
import ch.hsr.challp.and4.domain.Trail;

public class TrailDetail extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_view);
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			Integer trailId = extras.getInt("key");
			Trail activeTrail = null;
			
			for(Trail t: Trail.getTrails()){
				if(t.getTrailId()==trailId){
					activeTrail = t;
				}
			}
			
			ImageView img = (ImageView)findViewById(R.id.trailImage);
			TextView name = (TextView)findViewById(R.id.detailTrailName);
			TextView country = (TextView)findViewById(R.id.detailTrailCountry);
			TextView description = (TextView)findViewById(R.id.detailTrailDescription);
			TextView info = (TextView)findViewById(R.id.detailTrailInfo);
			
			Drawable drawable = loadImage(activeTrail.getImageUrl800());
			
			name.setText(activeTrail.getName());
			country.setText(activeTrail.getCountry());
			description.setText(activeTrail.getDescription());
			info.setText(Html.fromHtml(activeTrail.getInfo()).toString());
			
		    img.setImageDrawable(drawable);
		}
	}

	private Drawable loadImage(String url)
    {
         try {
             InputStream is = (InputStream) new URL(url).getContent();
             Drawable d = Drawable.createFromStream(is, "src name");
             return d;
         }catch (Exception e) {
             System.out.println("Exc="+e);
             return null;
         }
    }
}

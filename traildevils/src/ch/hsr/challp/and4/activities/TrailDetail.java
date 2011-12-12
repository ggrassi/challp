package ch.hsr.challp.and4.activities;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ch.hsr.challp.and.R;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.technicalservices.favorites.Favorites;
import ch.hsr.challp.and4.technicalservices.weather.GoogleWeatherHandler;
import ch.hsr.challp.and4.technicalservices.weather.WeatherSet;

public class TrailDetail extends Activity{
	
	private String city;
	private String country;
	private Trail activeTrail;
	private Favorites favs;
	private Handler handler;
	private WeatherSet ws = null;
	private boolean weatherIsVisible = true;
	private ImageButton favBtn;
	private Drawable weather1Draw;
	private Drawable weather2Draw;
	private Drawable weather3Draw;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
		favs = ((TrailDevils) getApplication()).getFavorites();
		
		setContentView(R.layout.detail_view);
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			Integer trailId = extras.getInt("key");
			activeTrail = null;
			
			for(Trail t: Trail.getTrails()){
				if(t.getTrailId()==trailId){
					activeTrail = t;
				}
			}
			
			this.country = activeTrail.getCountry();
			this.city = activeTrail.getNextCity();
			
			ImageView img = (ImageView)findViewById(R.id.trailImage);
			TextView name = (TextView)findViewById(R.id.detailTrailName);
			TextView countryView = (TextView)findViewById(R.id.detailTrailCountry);
			TextView placeView = (TextView)findViewById(R.id.detailTrailPlace);
			TextView descriptionView = (TextView)findViewById(R.id.detailTrailDescription);
			
			Drawable trailDraw = loadImage(activeTrail.getImageUrl800());
			img.setImageDrawable(trailDraw);
			
			name.setText(activeTrail.getName());
			placeView.setText(activeTrail.getNextCity());
			countryView.setText(country);
			descriptionView.setText(activeTrail.getDescription());
			
			favBtn = (ImageButton)findViewById(R.id.fav_button);
			Button webBtn = (Button)findViewById(R.id.web_button);
			Button navigateBtn = (Button)findViewById(R.id.navigate_button);
			
			setFavoriteButtonIcon();
			
			favBtn.setOnClickListener(new OnClickListener() {	
				public void onClick(View v) {
					handleFavorite();
				}
			});	 
			
			webBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int trailIdWeb = activeTrail.getTrailId();
					trailIdWeb++;
					goToUrl("http://www.traildevils.ch/trail.php?tid="+trailIdWeb);
				}
			});
			
			navigateBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					navigateToTrail();
				}
			});
			
			startWeaterInitialization();
		}
	}
	
	private void setFavoriteButtonIcon(){
		if(favs.isFavorite(activeTrail)){
			favBtn.setImageResource(R.drawable.fav_remove_small);

		} else {
			favBtn.setImageResource(R.drawable.fav_add_small);
		}
	}
	
	private void handleFavorite(){
		if(favs.isFavorite(activeTrail)){
			favs.removeTrail(activeTrail);
		}else{
			favs.addTrail(activeTrail);
		}
		
		handler.post(new Runnable() {
			public void run() {
				setFavoriteButtonIcon();
			}
		});
	}
	
	private void navigateToTrail(){
		String cords = String.valueOf(activeTrail.getGmapX()) +","+String.valueOf(activeTrail.getGmapY());
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+cords));
		startActivity(i);
	}
	
	private void loadWeatherData(){
		URL imgURLTomorrow = getIconWeatherURL(0);
		URL imgURLAfterTomorrow = getIconWeatherURL(1);
		URL imgURLAfterAfterTomorrow = getIconWeatherURL(2);
		
		if(imgURLTomorrow != null && imgURLAfterTomorrow != null && imgURLAfterAfterTomorrow != null){
			weather1Draw = loadImage(imgURLTomorrow.toString());
			weather2Draw = loadImage(imgURLAfterTomorrow.toString());
			weather3Draw = loadImage(imgURLAfterAfterTomorrow.toString());
		}else{
			weatherIsVisible = false;
		}
	}
	
	private void setWeatherData() throws Exception{
		ImageView weather_1 = (ImageView)findViewById(R.id.weather1);
		ImageView weather_2 = (ImageView)findViewById(R.id.weather2);
		ImageView weather_3 = (ImageView)findViewById(R.id.weather3);
		
		weather_1.setImageDrawable(weather1Draw);
		weather_2.setImageDrawable(weather2Draw);
		weather_3.setImageDrawable(weather3Draw);
		
		TextView celcius_1 = (TextView)findViewById(R.id.celcius1);
		TextView celcius_2 = (TextView)findViewById(R.id.celcius2);
		TextView celcius_3 = (TextView)findViewById(R.id.celcius3);
		
		celcius_1.setText(" "+getDayOfWeek(0)+"\n "+getMinTemp(0)+"°/"+getMaxTemp(0)+"°");
		celcius_2.setText(" "+getDayOfWeek(1)+"\n "+getMinTemp(1)+"°/"+getMaxTemp(1)+"°");
		celcius_3.setText(" "+getDayOfWeek(2)+"\n "+getMinTemp(2)+"°/"+getMaxTemp(2)+"°");
	}

	private Drawable loadImage(String url)
    {
         try {
             InputStream is = (InputStream) new URL(url).getContent();
             Drawable d = Drawable.createFromStream(is, "src name");
             return d;
         }catch (Exception e) {
             return null;
         }
    }
	
	private URL getIconWeatherURL(int day){
		ws = getWeatherSet();
		try {
			return new URL("http://www.google.com" + ws.getWeatherForecastConditions().get(day).getIconURL());
		} catch (Exception e) {
			return null;
		}
	}
	
	private WeatherSet getWeatherSet(){
		try{
			String queryString = "http://www.google.com/ig/api?weather=" + city + "," + country;
			URL url = new URL(queryString.replace(" ", "%20"));
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			GoogleWeatherHandler gwh = new GoogleWeatherHandler();
			xr.setContentHandler(gwh);
			xr.parse(new InputSource(url.openStream()));			
			return gwh.getWeatherSet();
			
		}catch(Exception e){
			return null;
		}
	}
	
	private int getMaxTemp(int day){
		return getWeatherSet().getWeatherForecastConditions().get(day).getTempMaxCelsius();
	}
	
	private int getMinTemp(int day){
		return getWeatherSet().getWeatherForecastConditions().get(day).getTempMinCelsius();
	}
	
	private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

	
	private String getDayOfWeek(int day){
		String shortDayName = getWeatherSet().getWeatherForecastConditions().get(day).getDayofWeek();
		if(shortDayName.equals("Mon")){
			return "Monday";
		}
		if(shortDayName.equals("Tue")){
			return "Tuesday";
		}
		if(shortDayName.equals("Wed")){
			return "Wednesday";
		}
		if(shortDayName.equals("Thu")){
			return "Thursday";
		}
		if(shortDayName.equals("Fri")){
			return "Friday";
		}
		if(shortDayName.equals("Sat")){
			return "Saturday";
		}
		if(shortDayName.equals("Sun")){
			return "Sunday";
		}
		return "";
	}
	
	public void startWeaterInitialization() {
		Runnable runnable = new Runnable() {
			public void run() {
				loadWeatherData();
				handler.post(new Runnable() {
					public void run() {
						try {
							setWeatherData();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(weatherIsVisible){
							View weatherContainer = findViewById(R.id.weatherContainer);
							weatherContainer.setVisibility(View.VISIBLE);
						}
					}
				});
			}
		};
		new Thread(runnable).start();
	}
}

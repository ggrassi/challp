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
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ch.hsr.challp.android4.R;
import ch.hsr.challp.and4.application.TrailDevils;
import ch.hsr.challp.and4.domain.Trail;
import ch.hsr.challp.and4.technicalservices.favorites.Favorites;
import ch.hsr.challp.and4.technicalservices.weather.GoogleWeatherHandler;
import ch.hsr.challp.and4.technicalservices.weather.WeatherSet;

public class TrailDetail extends Activity{
	
	private static final String GOOGLE_NAVIGATION_INTENT = "google.navigation:q=";
	private static final String TRAIL_DEVILS_WEBSITE = "http://www.traildevils.ch/trail.php?tid=";
	private static final String TRAIL_NOT_AVAILABLE = "n.a.";
	private static final String TRAIL_INFO_REGEX = "\\<[^>]*>";
	
	private String city;
	private String country;
	private Trail activeTrail;
	private Favorites favorites;
	private Handler handler = new Handler();
	private WeatherSet ws = null;
	private boolean weatherIsAvailable = true;
	private ImageButton favoriteButton;
	private Drawable weather1Draw;
	private Drawable weather2Draw;
	private Drawable weather3Draw;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_view);
		favorites = ((TrailDevils) getApplication()).getFavorites();
		
		initActiveTrailInformation();
		initViews();
		initButtons();

		startWeaterInitialization();
	}

	private void initViews() {
		ImageView mainTrailImage = (ImageView) findViewById(R.id.trailImage);
		setTrailImage(mainTrailImage);
		
		TextView name = (TextView) findViewById(R.id.detailTrailName);
		name.setText(activeTrail.getName());
		
		TextView countryView = (TextView) findViewById(R.id.detailTrailCountry);
		countryView.setText(country);
		
		TextView placeView = (TextView) findViewById(R.id.detailTrailPlace);
		placeView.setText(city.equals("null") ? TRAIL_NOT_AVAILABLE : city);
		
		TextView descriptionView = (TextView) findViewById(R.id.detailTrailDescription);
		descriptionView.setMovementMethod(LinkMovementMethod.getInstance());
		descriptionView.setText(activeTrail.getDescription().replaceAll(TRAIL_INFO_REGEX, ""));
	}
	
	private void initButtons(){
		favoriteButton = (ImageButton) findViewById(R.id.fav_button);
		setFavoriteButtonIcon();
		favoriteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleFavorite();
			}
		});
		
		Button websiteButton = (Button) findViewById(R.id.web_button);
		websiteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int trailIdWeb = activeTrail.getTrailDevilsId();
				goToUrl(TRAIL_DEVILS_WEBSITE + trailIdWeb);
			}
		});
		
		Button navigationButton = (Button) findViewById(R.id.navigate_button);
		navigationButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigateToTrail();
			}
		});
	}

	private void setTrailImage(ImageView mainTrailImage) {
		if (activeTrail.getImageUrl800().equals("null")) {
			mainTrailImage.setImageResource(R.drawable.trail_dummy);
		} else {
			Drawable trailDraw = loadImage(activeTrail.getImageUrl800());
			mainTrailImage.setImageDrawable(trailDraw);
		}
	}

	private void initActiveTrailInformation() {
		Bundle intentInformation = getIntent().getExtras();
		if (intentInformation != null) {
			Integer trailId = intentInformation.getInt("key");
			activeTrail = null;
			for (Trail trail : Trail.getTrails()) {
				if (trail.getTrailId() == trailId) {
					activeTrail = trail;
				}
			}
		}
		country = activeTrail.getCountry();
		city = activeTrail.getNextCity();
	}
	
	private void setFavoriteButtonIcon(){
		favoriteButton.setImageResource(favorites.isFavorite(activeTrail)
				? R.drawable.fav_remove_small : R.drawable.fav_add_small);
	}
	
	private void handleFavorite(){
		if(favorites.isFavorite(activeTrail)){
			favorites.removeTrail(activeTrail);
		}else{
			favorites.addTrail(activeTrail);
		}
		
		handler.post(new Runnable() {
			public void run() {
				setFavoriteButtonIcon();
			}
		});
	}
	
	private void navigateToTrail(){
		String cords = String.valueOf(activeTrail.getGmapX()) +","+String.valueOf(activeTrail.getGmapY());
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_NAVIGATION_INTENT+cords));
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
			weatherIsAvailable = false;
		}
	}
	
	private void setWeatherData() {
		ImageView weather_1 = (ImageView) findViewById(R.id.weather1);
		ImageView weather_2 = (ImageView) findViewById(R.id.weather2);
		ImageView weather_3 = (ImageView) findViewById(R.id.weather3);

		weather_1.setImageDrawable(weather1Draw);
		weather_2.setImageDrawable(weather2Draw);
		weather_3.setImageDrawable(weather3Draw);

		TextView celcius_1 = (TextView) findViewById(R.id.celcius1);
		TextView celcius_2 = (TextView) findViewById(R.id.celcius2);
		TextView celcius_3 = (TextView) findViewById(R.id.celcius3);

		celcius_1.setText(" " + getDayOfWeek(0) + "\n " + getMinTemp(0)
				+ "\u00b0 /" + getMaxTemp(0) + "\u00b0");
		celcius_2.setText(" " + getDayOfWeek(1) + "\n " + getMinTemp(1)
				+ "\u00b0/" + getMaxTemp(1) + "\u00b0");
		celcius_3.setText(" " + getDayOfWeek(2) + "\n " + getMinTemp(2)
				+ "\u00b0/" + getMaxTemp(2) + "\u00b0");
	}

	private Drawable loadImage(String url)
    {
         try {
             InputStream stream = (InputStream) new URL(url).getContent();
             Drawable d = Drawable.createFromStream(stream, "src name");
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
			String urlCity = city.replaceAll("�", "ue");
			urlCity.replaceAll("�", "ae");
			urlCity.replaceAll("�","oe");
			String queryString = "http://www.google.com/ig/api?weather=" + urlCity + "," + country;
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
			return "Montag";
		}
		if(shortDayName.equals("Tue")){
			return "Dienstag";
		}
		if(shortDayName.equals("Wed")){
			return "Mittwoch";
		}
		if(shortDayName.equals("Thu")){
			return "Donnerstag";
		}
		if(shortDayName.equals("Fri")){
			return "Freitag";
		}
		if(shortDayName.equals("Sat")){
			return "Samstag";
		}
		if(shortDayName.equals("Sun")){
			return "Sonntag";
		}
		return "";
	}
	
	public void startWeaterInitialization() {
		Runnable runnable = new Runnable() {
			public void run() {
				loadWeatherData();
				handler.post(new Runnable() {
					public void run() {
						if(weatherIsAvailable){
							setWeatherData();
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

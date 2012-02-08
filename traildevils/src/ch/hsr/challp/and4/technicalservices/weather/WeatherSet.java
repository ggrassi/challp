/*
 * Original from:
 * http://www.anddev.org/android_weather_forecast_-_google_weather_api_-_description-t337.html
 */

package ch.hsr.challp.and4.technicalservices.weather;

import java.util.ArrayList;

/**
 * Combines one WeatherCurrentCondition with a List of
 * WeatherForecastConditions.
 */
public class WeatherSet {

	private WeatherCurrentCondition myCurrentCondition = null;
	private ArrayList<WeatherForecastCondition> myForecastConditions = 
		new ArrayList<WeatherForecastCondition>(4);

	public WeatherCurrentCondition getWeatherCurrentCondition() {
		return myCurrentCondition;
	}

	public void setWeatherCurrentCondition(
			WeatherCurrentCondition myCurrentWeather) {
		this.myCurrentCondition = myCurrentWeather;
	}

	public ArrayList<WeatherForecastCondition> getWeatherForecastConditions() {
		return this.myForecastConditions;
	}

	public WeatherForecastCondition getLastWeatherForecastCondition() {
		return this.myForecastConditions
				.get(this.myForecastConditions.size() - 1);
	}
}

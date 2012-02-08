/*
 * Original from:
 * http://www.anddev.org/android_weather_forecast_-_google_weather_api_-_description-t337.html
 */

package ch.hsr.challp.and4.technicalservices.weather;

/** Useful Utility in working with temperatures. (conversions). */
public class WeatherUtils {

	public static int fahrenheitToCelsius(int tFahrenheit) {
		return (int) ((5.0f / 9.0f) * (tFahrenheit - 32));
	}

	public static int celsiusToFahrenheit(int tCelsius) {
		return (int) ((9.0f / 5.0f) * tCelsius + 32);
	}
}

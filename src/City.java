import java.io.Serializable;

import com.google.code.geocoder.*;
import com.google.code.geocoder.model.*;

public class City implements Comparable, Serializable {

	private String name;
	private LatLng location;
	private int indexPos;
	static int cityCount = 0;
	/**
	 * Creates a City object with no parameters.
	 */
	City() {
		indexPos = cityCount;
		cityCount++;
	}
	/**
	 * Creates a City object with one parameters.
	 * @param name: name of the city
	 */
	City(String name) {
		this.name = name;
		location = new LatLng();
		try {
		    Geocoder geocoder = new Geocoder();
		    GeocoderRequest geocoderRequest;
		    GeocodeResponse geocodeResponse;
		    double lat;
		    double lng;

		    geocoderRequest = new GeocoderRequestBuilder().setAddress(name).getGeocoderRequest();
		    geocodeResponse = geocoder.geocode(geocoderRequest);
		    lat = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
		    lng = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
		    this.location.setLat(lat);
		    this.location.setLng(lng);
		} catch (Exception e) {
			System.out.println("City not found!");
		}
		indexPos = cityCount;
		cityCount++;
	}
	/**
	 * Sets the name of a city.
	 * @param name: preferred name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Sets the location of a city.
	 * @param location: preferred location
	 */
	public void setLocation(LatLng location) {
		this.location = location;
	}
	/**
	 * Retrieves the name of the city.
	 * @return the name of the city
	 */
	public String getName() {
		return name;
	}
	/**
	 * Retrieves the location of the city.
	 * @return the location of the city
	 */
	public LatLng getLocation() {
		return location;
	}
	/**
	 * Retrieves the index of the city.
	 * @return the index of the city
	 */
	public int getIndexPos() {
		return indexPos;
	}
	/**
	 * Retrieves the city count.
	 * @return the city count
	 */
	public int getCityCount() {
		return cityCount;
	}
	/**
	 * Compares two cities based on their index position.
	 * @return 0 if they are equal, 1 or -1 based on which has a greater index position
	 */
	public int compareTo(Object o) {
        City otherEmp = (City)o;
        if (this.indexPos == otherEmp.indexPos)
                return 0;
        else if (this.indexPos > otherEmp.indexPos)
                return 1;
        else
                return -1;
	}
	
}

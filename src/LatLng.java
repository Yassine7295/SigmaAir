import java.io.Serializable;



public class LatLng implements Serializable {
    /* represents the latitude and longitude of a location in degrees */
    private double lat;
    private double lng;
    
    /**
     * Default constructor LatLng. Defaults lat-lng to 0.
     */
    public LatLng(){
        lat = 0.0;
        lng = 0.0;
    }
    
    /**
     * Overloaded constructor LatLng. Accepts initial values for lat-lng
     * @param lat
     * Latitude of point in degrees
     * @param lng 
     * Longitude of point in degrees
     */
    public LatLng(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }
    
    /**
     * Getter method for lat
     * @return 
     * degree value of lat as a double
     */
    public double getLat(){
        return lat;
    }
    
    /**
     * Getter method for lng
     * @return 
     * degree value of lng as a double
     */
    public double getLng(){
        return lng;
    }
    
    /**
     * Setter method for lat
     * @param lat 
     * New value of lat
     */
    public void setLat(double lat){
        this.lat = lat;
    }
    
    /**
     * Setter method of lng
     * @param lng 
     * New value of lng
     */
    public void setLng(double lng){
        this.lng = lng;
    }
    
    /**
     * Calculates the distance between 2 given LatLng. This method assumes that 
     * the Earth is a perfect sphere.
     * @param src
     * First location
     * @param dest
     * Second location
     * @return 
     * Spherical distance between 2 points in kilometers
     */
    public static double calculateDistance(LatLng src, LatLng dest){        
        double srcLat = Math.toRadians(src.getLat());
        double destLat = Math.toRadians(dest.getLat());
        double deltaLat = Math.toRadians(dest.getLat() - src.getLat());
        double deltaLng = Math.toRadians(dest.getLng() - src.getLng());
        double meanRadius = 6371000.0;
        
        double a = Math.pow(Math.sin(deltaLat / 2), 2) 
                + Math.cos(srcLat) * Math.cos(destLat) * Math.pow(Math.sin(deltaLng / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = meanRadius * c;
        
        return distance;
    }
}

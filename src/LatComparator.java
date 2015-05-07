import java.io.Serializable;
import java.util.Comparator;

import latlng.LatLng;

public class LatComparator implements Comparator<City>, Serializable {
	/**
	 * Overrides the compare method.
	 * @param o1: the first city
	 * @param o2: the second city
	 * @return a comparison of the two cities based on the latitude of the cities
	 */
	public int compare(City o1, City o2) {
        City e1 = (City) o1;
        City e2 = (City) o2;
        if (e1.getLocation().getLat() == e2.getLocation().getLat()) //get latitude
            return 0;
        else if (e1.getLocation().getLat() > e2.getLocation().getLat())
            return 1;
        else
            return -1;
    }
}

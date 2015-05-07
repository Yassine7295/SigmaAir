import java.io.Serializable;
import java.util.Comparator;

public class LngComparator implements Comparator<City>, Serializable {
	/**
	 * Overrides the compare method.
	 * @param o1: the first city
	 * @param o2: the second city
	 * @return a comparison of the two cities based on the longitude of the cities
	 */
	public int compare(City o1, City o2) {
        City e1 = (City) o1;
        City e2 = (City) o2;
        if (e1.getLocation().getLng() == e2.getLocation().getLng()) //get longitude
            return 0;
        else if (e1.getLocation().getLng() > e2.getLocation().getLng())
            return 1;
        else
            return -1;
    }
}

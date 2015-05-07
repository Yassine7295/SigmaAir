import java.io.Serializable;
import java.util.Comparator;

public class NameComparator implements Comparator<City>, Serializable {
	/**
	 * Overrides the compare method.
	 * @param o1: the first city
	 * @param o2: the second city
	 * @return a comparison of the two cities based on the name of the cities
	 */
	public int compare(City o1, City o2) {
        City c1 = (City) o1;
        City c2 = (City) o2;
        return (c1.getName().compareTo(c2.getName()));
	}
}

import java.io.Serializable;

public class CityNotFoundException extends Exception implements Serializable {
	/**
	 * Creates an instance of the CityNotFoundException class.
	 */
	CityNotFoundException() {
		System.out.println("This city is not found in the database.");
	}
}

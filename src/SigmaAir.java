import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SigmaAir implements Serializable {

	ArrayList<City> cities;
	public static final int MAX_CITIES = 100;
	double[][] connections;
	private double[][] dist;
	private City[][] next;
	int i, j;
	Scanner s;
	File f;
	String input;
	City newCity;
	String[] pair;
	Boolean exist, existTo;
	/**
	 * Creates a SigmaAir object with no parameters.
	 */
	SigmaAir() {
		cities = new ArrayList<City>();
		connections = new double[MAX_CITIES][MAX_CITIES];
		for(int i = 0; i < MAX_CITIES; i++) {
			for(int j = 0; j < MAX_CITIES; j++) {
				connections[i][j] = 0;
			}
		}
	}
	/**
	 * Adds an object of type City to the ArrayList of the SigmaAir object.
	 * @param city: the City object to be added.
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The city has been added to the cities ArrayList of the SigmaAir object.
	 */
	public void addCity(City city) {
		cities.add(city);
		try {
		      FileOutputStream file = new FileOutputStream("citiesSaved.obj");
		      ObjectOutputStream fout = new ObjectOutputStream(file);
		      fout.writeObject(cities); 
		      fout.close();
		} catch (IOException e){
			
		}
	}
	/**
	 * Adds a connection from the cityFrom to the cityTo to the connection matrix in the SigmaAir object. The value is the
	 * distance between the two cities.
	 * @param cityFrom: the first city
	 * @param cityTo: the second city
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The connection has been added to the connection matrix in the SigmaAir object.
	 * @throws CityNotFoundException: If either city is not found in the database, the exception is thrown.
	 */
	public void addConnection(String cityFrom, String cityTo) throws CityNotFoundException {
		exist = false;
		existTo = false;
		for(int i = 0; i < cities.size(); i++) {
			if(cities.get(i).getName().equalsIgnoreCase(pair[0]))
				exist = true;
			if(cities.get(i).getName().equalsIgnoreCase(pair[1]))
				existTo = true;
		}
		if(exist && existTo) {
			City from = findCity(cityFrom);
			City to = findCity(cityTo);
			LatLng src = from.getLocation();
			LatLng dest = to.getLocation();
			connections[from.getIndexPos()][to.getIndexPos()] = calculateDistance(src, dest);
			connections[to.getIndexPos()][from.getIndexPos()] = calculateDistance(src, dest);
			System.out.println(cityFrom + " -> " + cityTo + " added: " + calculateDistance(src, dest));
			try {
			      FileOutputStream file = new FileOutputStream("connectionsSaved.obj");
			      ObjectOutputStream fout = new ObjectOutputStream(file);
			      fout.writeObject(connections); 
			      fout.close();
			} catch (IOException e){
				
			}
		}
		else
			throw new CityNotFoundException();
	}
	/**
	 * Removes a connection from the connection matrix of the SigmaAir object.
	 * @param cityFrom: the first city
	 * @param cityTo: the second city
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The connection has been removed from the connections matrix of the SigmaAir object.
	 * @throws CityNotFoundException: If either city is not found in the database, the exception is thrown.
	 */
	public void removeConnection(String cityFrom, String cityTo) throws CityNotFoundException {
		City from = findCity(cityFrom);
		City to = findCity(cityTo);
		if(from == (City) null || to == (City) null)
			throw new CityNotFoundException();
		connections[from.getIndexPos()][to.getIndexPos()] = Double.POSITIVE_INFINITY;;
		connections[to.getIndexPos()][from.getIndexPos()] = Double.POSITIVE_INFINITY;
		System.out.println("Connection from " + cityFrom + " to " + cityTo + " has been remove!");
		try {
		      FileOutputStream file = new FileOutputStream("connectionsSaved.obj");
		      ObjectOutputStream fout = new ObjectOutputStream(file);
		      fout.writeObject(connections); 
		      fout.close();
		} catch (IOException e){
			
		}
	}
	/**
	 * Prints all of the cities based on the sorting algorithm chosen from: by name, by latitude, by longitude.
	 * @param comp: the sorting algorithm
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 */
	public void printAllCities(Comparator comp) {
		Collections.sort(cities, comp);
		System.out.println("City Name            Latitude       Longitude      ");
		System.out.println("---------------------------------------------------");
		for(int i = 0; i < cities.size(); i++) {
			System.out.printf("%-21s%-15f%-15f", cities.get(i).getName(), cities.get(i).getLocation().getLat(), cities.get(i).getLocation().getLng());
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Prints all of the connections between the cities in the database, if the connections exist.
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 */
	public void printAllConnections() {
		System.out.println("Connections:");
		System.out.println("Route                         Distance      ");
		System.out.println("---------------------------------------------");
		String s;
		for(int i = 0; i < cities.size(); i++) {
			for(int j = 0; j < cities.size(); j++) {
				if(connections[i][j] > 0) {
					s = findCity(i).getName() + " -> " + findCity(j).getName();
						System.out.printf("%-30s%-15f", s, calculateDistance(findCity(i).getLocation(),findCity(j).getLocation()));
						System.out.println();
				}
			}
		}
	}
	/**
	 * Loads city names to be added to the SigmaAirobject. Invokes the addCity method after loading the file.
	 * @param filename: the name of the file to be loaded.
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The cities in the file have been added to the SigmaAir object.
	 * @throws FileNotFoundException: if the file does not exist.
	 */
	public void loadAllCities(String filename) throws FileNotFoundException {
		f = new File(filename);
		s = new Scanner(f);
		while(s.hasNextLine()) {
			input = s.nextLine();
			newCity = new City(input);
			addCity(newCity);
			System.out.println(input + " has been added: (" + newCity.getLocation().getLat() + ", " + newCity.getLocation().getLng() + ")");
		}
		f.exists();
		s.close();
	}
	/**
	 * Loads connections between cities to be added to the SigmaAirobject. Invokes the addConnection method after loading the file.
	 * @param filename: the name of the file to be loaded.
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The connection on the file have been added to the connections matrix of the SigmaAir object.
	 * @throws FileNotFoundException: if the file does not exist.
	 */
	public void loadAllConnections(String filename) throws FileNotFoundException {
		f = new File(filename);
		s = new Scanner(f);
		while(s.hasNextLine()) {
			input = s.nextLine();
			pair = input.split(",");
			exist = false;
			existTo = false;
			for(int i = 0; i < cities.size(); i++) {
				if(cities.get(i).getName().equalsIgnoreCase(pair[0]))
					exist = true;
				if(cities.get(i).getName().equalsIgnoreCase(pair[1]))
					existTo = true;
			}
			if(exist && existTo) {
				try {
					addConnection(pair[0], pair[1]);
				} catch(CityNotFoundException ex) {
					ex.getMessage();
				}
			}
		}
		f.exists();
		s.close();
	}
	/**
	 * Finds a city in a SigmaAir object based on the name of the cities.
	 * @param cityName: the name of the city to be found
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * @return the City object if found
	 */
	public City findCity(String cityName) {
		for(int i = 0; i < cities.size(); i++) {
			if(cities.get(i).getName().equalsIgnoreCase(cityName))
				return cities.get(i);
		}
		return null;
	}
	/**
	 * Finds a city in a SigmaAir object based on the index position of the cities.
	 * @param indexPos: the indexPos of the city to be found
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * @return the City object if found
	 */
	public City findCity(int indexPos) {
		for(int i = 0; i < cities.size(); i++) {
			if(cities.get(i).getIndexPos() == indexPos)
				return cities.get(i);
		}
		return null;
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
	/**
	 * Uses the Floyd Warshal algorithm to calculate the shortest path between two cities in the SigmaAir object.
	 * @param cityFrom: the first city
	 * @param cityTo: the second city
	 * <dt><b>Precondition:</b><dd> A SigmaAir object has been initialized.
	 * @return a String representation of the path from the first city to the second city
	 */
	public String shortestPath(String cityFrom, String cityTo) {
		City from = findCity(cityFrom);
		City to = findCity(cityTo);
		if(from == (City) null || to == (City) null)
			return "Shortest path from " + cityFrom + " to " + cityTo + " does not exist!";
		FloydWarshallWithPathReconstruction();
		String path = from.getName();
		double distance = 0;
		City ref = from;
		while(from != to) {
			from = next[from.getIndexPos()][to.getIndexPos()];
			if(from == (City) null)
				return "Shortest path from " + cityFrom + " to " + cityTo + " does not exist!";
			path += " -> " + from.getName();
			distance += calculateDistance(ref.getLocation(), from.getLocation());
			ref = from;
		}
		path += ": " + distance;
		return path;
	}
	/**
	 * The Floyd Warshal algorithm for calculating the shortest path.
	 */
	public void FloydWarshallWithPathReconstruction() {
		dist = new double[MAX_CITIES][MAX_CITIES];
		next = new City[MAX_CITIES][MAX_CITIES];
		
		for(int v = 0; v < MAX_CITIES; v++) {
            for (int w = 0; w < MAX_CITIES; w++) {
                dist[v][w] = Double.POSITIVE_INFINITY;
                next[v][w] = (City) null;
            }
        }
		for(int v = 0; v < MAX_CITIES; v++) {
			for (int w = 0; w < MAX_CITIES; w++) {
	            if(connections[v][w] > 0 && connections[v][w] != Double.POSITIVE_INFINITY) {
	            	dist[v][w] = connections[v][w];
	            	next[v][w] = findCity(w);
	            }
	        }
        }
		for(int i = 0; i < MAX_CITIES; i++) {
			for(int j = 0; j < MAX_CITIES; j++) {
				for(int k = 0; k < MAX_CITIES; k++) {
					if(dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}
	}
	
}

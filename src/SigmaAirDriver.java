import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class SigmaAirDriver implements Serializable {
	
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
		
		Scanner s = new Scanner(System.in);
		String input, inputSub;
		menu();
		input = s.nextLine();
		SigmaAir airline = new SigmaAir();
		City city;
		String src, dest;
		
		try {
		     FileInputStream file = new FileInputStream("citiesSaved.obj");
		     ObjectInputStream fin  = new ObjectInputStream(file);
		     airline.cities = (ArrayList<City>) fin.readObject(); 
		     fin.close();
		} catch(IOException e) {
			 System.out.println("citiesSaved.obj is not found. Using a new cities array.");
		}
		
		try {
		     FileInputStream file = new FileInputStream("connectionsSaved.obj");
		     ObjectInputStream fin  = new ObjectInputStream(file);
		     airline.connections = (double[][]) fin.readObject(); 
		     fin.close();
		} catch(IOException e) {
			 System.out.println("connectionsSaved.obj is not found. Using a new cities array.");
		}
		
		while(!input.equalsIgnoreCase("Q")) {
			if(input.equalsIgnoreCase("A")) {
				System.out.println("Enter the name of the city: ");
				input = s.nextLine();
				city = new City(input);
				airline.addCity(city);
				System.out.println(input + " has been added: (" + city.getLocation().getLat() + ", " + city.getLocation().getLng() + ")");
			}
			else if(input.equalsIgnoreCase("B")) {
				System.out.println("Enter source city: ");
				src = s.nextLine();
				System.out.println("Enter destination city: ");
				dest = s.nextLine();
				try{
					airline.addConnection(src, dest);
				} catch(CityNotFoundException ex) {
					ex.getMessage();
				}
			}
			else if(input.equalsIgnoreCase("C")) {
				System.out.println("Enter the file name: ");
				input = s.nextLine();
				airline.loadAllCities(input);
			}
			else if(input.equalsIgnoreCase("D")) {
				System.out.println("Enter the file name: ");
				input = s.nextLine();
				airline.loadAllConnections(input);
			}
			else if(input.equalsIgnoreCase("E")) {
				subMenu();
				inputSub = s.nextLine();
				while(!inputSub.equalsIgnoreCase("Q")) {
					if(inputSub.equalsIgnoreCase("EA")) { //sort by name and print
						airline.printAllCities(new NameComparator());
					}
					else if(inputSub.equalsIgnoreCase("EB")) { //sort by latitude and print
						airline.printAllCities(new LatComparator());
					}
					else if(inputSub.equalsIgnoreCase("EC")) { //sort by longitude and print
						airline.printAllCities(new LngComparator());
					}
					else {
						System.out.println("Invalid input");
					}
					subMenu();
					inputSub = s.nextLine();
				}
			}
			else if(input.equalsIgnoreCase("F")) {
				airline.printAllConnections();
			}
			else if(input.equalsIgnoreCase("G")) {
				System.out.println("Enter source city: ");
				src = s.nextLine();
				System.out.println("Enter destination city: ");
				dest = s.nextLine();
				try{
					airline.removeConnection(src, dest);
				} catch(CityNotFoundException ex) {
					ex.getMessage();
				}
			}
			else if(input.equalsIgnoreCase("H")) {
				System.out.println("Enter source city: ");
				src = s.nextLine();
				System.out.println("Enter destination city: ");
				dest = s.nextLine();
				System.out.println(airline.shortestPath(src, dest));
			}
			else {
				System.out.println("Invalid input.");
			}
			menu();
			input = s.nextLine();
		}
		System.out.println("Program terminating...");
	}
	
	public static void menu() {
		System.out.println("(A) Add City\n"
				+ "(B) Add Connection\n"
				+ "(C) Load all Cities\n"
				+ "(D) Load all Connections\n"
				+ "(E) Print all Cities\n"
				+ "(F) Print all Connections\n"
				+ "(G) Remove Connection\n"
				+ "(H) Find Shortest Path\n"
				+ "(Q) Quit\n"
				+ "Enter a selection: ");
	}
	
	public static void subMenu() {
		System.out.println("(EA) Sort by City Name\n"
				+ "(EB) Sort by Latitude\n"
				+ "(EC) Sort by Longitude\n"
				+ "(Q) Quit\n"
				+ "Enter a selection: ");
	}
	
}

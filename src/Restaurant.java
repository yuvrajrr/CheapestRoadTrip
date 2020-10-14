import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// This class represents a Restaurant object 
public class Restaurant {
	// Each restaurant has a name, latitude, longitude and an ArrayList of meal objects containing the two cheapest meals available at that restaurant
	private String name;
	private double lat;
	private double lon;
	private ArrayList<Meal> meals = new ArrayList<Meal>();
	
	// Constructor for the Restaurant object takes a Name, latitude, longitude, and an ArrayList of the cheapest two meals at every restaurant
	public Restaurant(String Name, double Lat, double Long, ArrayList<Meal> Meals) {
		name = Name;
		lat = Lat;
		lon = Long;
		// An ArrayList of the two cheapest meals available at every restaurant is passed through the constructor so this loop will add the two cheapest meals at the restaurant object that is being created and add those two meals to the meal ArrayList of that restaurant object
		for(Meal meal:Meals) {
			if(meal.getRestaurant().compareTo(name) == 0) {
				meals.add(meal);
			}
		}
		
	}
	
	
	// Getter returns the restaurant name
	public String getName() {
		return name;
	}
	
	// Getter returns the latitude
	public double getLat() {
		return lat;
	}
	
	// Getter returns the longitude
	public double getLong() {
		return lon;
	}
	
	// Getter returns the meal objects representing the cheapest two meals
	public ArrayList<Meal> getMeals(){
		return meals;
	}
	
	// This function will create an ArrayList of Restaurant objects by reading the corresponding restaurant files and storing the name, latitude, longitude, and two cheapest meals available
	public static ArrayList<Restaurant> RestArray(ArrayList<Meal> meal_array) throws IOException{
		BufferedReader m = new BufferedReader(new FileReader("data/mcdonalds.csv"));
		BufferedReader b = new BufferedReader (new FileReader("data/burgerking.csv"));
		BufferedReader w = new BufferedReader (new FileReader ("data/wendys.csv"));
		String mac_reader;
		String[] columns_mac;
		String bk_reader;
		String[] columns_bk;
		String wen_reader;
		String[] columns_wen;
		ArrayList<Restaurant> rest_array = new ArrayList<Restaurant>();
		
		// mcdonalds.csv is read and the McDonalds restaurant objects are stored in the restaurant ArrayList
		m.readLine();
		while((mac_reader = m.readLine()) != null) {
			columns_mac = mac_reader.split(",");
			rest_array.add(new Restaurant("McDonalds", Double.parseDouble(columns_mac[1]), Double.parseDouble(columns_mac[0]), meal_array));
		}
		
		// burgerking.csv is read and the Burger King restaurant objects are stored in the restaurant ArrayList
		b.readLine();
		while((bk_reader = b.readLine()) != null) {
			columns_bk = bk_reader.split(",");
			rest_array.add(new Restaurant("Burger King", Double.parseDouble(columns_bk[1]), Double.parseDouble(columns_bk[0]), meal_array));
		}
		
		// wendys.csv is read and the Wendy's restaurant objects are stored in the restaurant ArrayList
		w.readLine();
		while((wen_reader = w.readLine()) != null) {
			columns_wen = wen_reader.split(",");
			rest_array.add(new Restaurant("Wendy's", Double.parseDouble(columns_wen[1]), Double.parseDouble(columns_wen[0]), meal_array));
		}
		
		m.close();
		w.close();
		b.close();
		return rest_array;
	}
	
	
	public String toString() {
		return name;
	}
	
	
	
}

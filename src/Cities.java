import java.util.*;
import java.io.*;

// This class represents a City object
public class Cities {
	// Each city has a name, latitude, longitude 
	private String name;
	private double lat;
	private double lon;
	private ArrayList<Restaurant> restaurant_choice = new ArrayList<Restaurant>();
	
	// City object constructor
	public Cities(String Name, double Latitude, double Longitude, ArrayList<Restaurant> Restaurant_list){
		name = Name;
		lat = Latitude;
		lon = Longitude;
		
		ArrayList<Restaurant> restaurant = new ArrayList<Restaurant>();
		
		// If a restaurant is within the 0.5 degrees of the latitude and longitude it gets added to a restaurant ArrayList
		for(Restaurant searcher: Restaurant_list) {
			if((Math.abs(lat - searcher.getLat()) <= 0.5) && (Math.abs(lon - searcher.getLong()) <= 0.5 )) {
				restaurant.add(searcher);
			}
		}
		
		// the two cheapest restaurants in the restaurant ArrayList are found and stored into the restaurant_choice field of the city object
		double min1_price = restaurant.get(0).getMeals().get(0).getPrice();
		Restaurant min1_rest = restaurant.get(0);
		
		double min2_price = restaurant.get(0).getMeals().get(1).getPrice();
		Restaurant min2_rest = restaurant.get(0);
		
		if(min2_price < min1_price) {
			double temp_price = min1_price;
			min1_price = min2_price;
			min2_price = temp_price;
	
		}
		
		// This loop finds the two cheapest meals and identifies if they come from the same restaurant
		for(int i = 1; i< restaurant.size(); i++) {
			for(int j = 0; j < restaurant.get(i).getMeals().size() ; j++) {
				if(restaurant.get(i).getMeals().get(j).getPrice() < min1_price) {
					min2_price = min1_price;
					min2_rest = min1_rest;
					min1_price = restaurant.get(i).getMeals().get(j).getPrice();
					min1_rest = restaurant.get(i);
				}
				else if (restaurant.get(i).getMeals().get(j).getPrice() < min2_price) {
					min2_price = restaurant.get(i).getMeals().get(j).getPrice();
					min2_rest = restaurant.get(i);
				}
			}
			
		}
		// If the cheapest meals are the same restaurant, only one needs to be added to the restaurant_choice field of the city object
		if(min1_rest.getName().compareTo(min2_rest.getName()) == 0) {
			restaurant_choice.add(min1_rest);
		}
		
		// If the two cheapest meals are different restaurants, they both need to be added to the restaurant_choice field of the city object
		else {
			restaurant_choice.add(min1_rest);
			restaurant_choice.add(min2_rest);
		}
		
	}
	
	// Getter returns the name of the city
	public String getName() {
		return name;
	}
	
	// Getter returns the latitude of the city
	public double getLat() {
		return lat;
	}
	
	// Getter returns the longitude of the city
	public double getLong() {
		return lon;
	}

	
	// Getter returns the ArrayList of the restaurants with the 2 cheapest meals in the city
	public ArrayList<Restaurant> getRestaurant(){
		return restaurant_choice;
	}


	// This function reads the USCities.csv file and creates an ArrayList of city objects where each city has a name, latitude, longitude, and and ArrayList of restaurant objects that correspond to the two cheapest meals in that city
	public static ArrayList<Cities> CityArray(ArrayList<Restaurant> rest_array) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("data/USCities.csv"));
		String[] columns;
		String line_read;
		ArrayList<Cities> city_array = new ArrayList<Cities>();
		r.readLine();
		while((line_read = r.readLine()) != null) {
			columns = line_read.split(",");
			city_array.add(new Cities(columns[3], Double.parseDouble(columns[4]), Double.parseDouble(columns[5]), rest_array));
		}
		
		r.close();
		return city_array;
	}
	
	
	public String toString() {
		return name;
	}
	
	
}

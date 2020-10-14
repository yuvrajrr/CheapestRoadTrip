import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// This class represents a meal object 
public class Meal {
	// Each meal object has a string that represents the meal, the restaurant, and a double that represents the price of the meal
	private String meal;
	private String restaurant;
	private double price;
	
	// The constructor takes in a meal, restaurant and a price and sets the state variables
	public Meal(String meal_input, String restaurant_input, double price_input) {
		meal = meal_input;
		restaurant = restaurant_input;
		price = price_input;
	}
	
	// This getter returns the meal name
	public String getMeal() {
		return meal;
	}
	
	// This getter returns the restaurant name
	public String getRestaurant() {
		return restaurant;
	}
	
	// This getter returns the meal price
	public double getPrice() {
		return price;
	}
	
	// Creates an ArrayList of Meal objects that contains Meal objects representing the two cheapest meals from each restaurant 
	public static ArrayList<Meal> MealArray() throws IOException {
		// The menu.csv is read to create an ArrayList of each restaurant and a different meal and price
		BufferedReader r = new BufferedReader(new FileReader("data/menu.csv"));
		r.readLine();
		String column[];
		String line_read;
		
		// mac_meal is an ArrayList of Meal objects where each Meal object will have a different meal and price available at McDonalds
		ArrayList<Meal> mac_meal = new ArrayList<Meal>();
		// bk_meal is an ArrayList of Meal objects where each Meal object will have a different meal and price available at Burger King
		ArrayList<Meal> bk_meal = new ArrayList<Meal>();
		// wen_meal is an ArrayList of Meal objects where each Meal object will have a different meal and price available at Wendy's
		ArrayList<Meal> wen_meal = new ArrayList<Meal>();
		// meal_list is an ArrayList of Meal objects that will contain 6 Meal objects
		// Each restaurant will have a meal object corresponding to it's 2 cheapest meals
		// This is done because no same meal can be eaten in two consecutive cities so the two cheapest meals at each restaurant are stored
		ArrayList<Meal> meal_list = new ArrayList<Meal>();
		// This loop reads the menus and creates ArrayList of each restaurant with meal objects with each meal in the menu
		while((line_read = r.readLine()) != null) {
			column = line_read.split(",");
			
			if(column[0].compareTo("McDonald’s") == 0) {
				mac_meal.add(new Meal(column[1], "McDonalds",Double.parseDouble(column[2].substring(1))));
			}
			
			else if(column[0].compareTo("Burger King") == 0) {
				bk_meal.add(new Meal(column[1], column[0],Double.parseDouble(column[2].substring(1))));
			}
			
			else {
				wen_meal.add(new Meal(column[1], "Wendy's",Double.parseDouble(column[2].substring(1))));
			}
		}
		
		// The two cheapest meals at McDonalds are found by traversing the mac_meal ArrayList
		double min_mac_num = mac_meal.get(0).getPrice();
		double min_mac2_num = mac_meal.get(1).getPrice();
		Meal min1_mac_obj = mac_meal.get(0);
		Meal min2_mac_obj = mac_meal.get(1);
			if(min_mac2_num < min_mac_num) {
				double temp = min_mac_num;
				Meal temp_obj = min1_mac_obj;
				min1_mac_obj = min2_mac_obj;
				min2_mac_obj = temp_obj;
				min_mac_num = min_mac2_num;
				min_mac2_num = temp;
			}
			
		for(int i = 2; i<mac_meal.size() ; i++) {
			if(mac_meal.get(i).getPrice() < min_mac_num) {
				min_mac2_num = min_mac_num;
				min2_mac_obj = min1_mac_obj;
				min_mac_num = mac_meal.get(i).getPrice();
				min1_mac_obj = mac_meal.get(i);
			}
			else if (mac_meal.get(i).getPrice() < min_mac2_num) {
				min_mac2_num = mac_meal.get(i).getPrice();
				min2_mac_obj = mac_meal.get(i);
			}
		}
		// The two cheapest meals at McDonalds are stored in the meal_list ArrayList
		meal_list.add(min1_mac_obj);
		meal_list.add(min2_mac_obj);
		
		
		
		// The two cheapest meals at Burger King are found by traversing the bk_meal ArrayList
		double min_bk_num = bk_meal.get(0).getPrice();
		double min_bk2_num = bk_meal.get(1).getPrice();
		Meal min1_bk_obj = bk_meal.get(0);
		Meal min2_bk_obj = bk_meal.get(1);
			if(min_bk2_num < min_bk_num) {
				double temp = min_bk_num;
				Meal temp_obj = min1_bk_obj;
				min1_bk_obj = min2_bk_obj;
				min2_bk_obj = temp_obj;
				min_bk_num = min_bk2_num;
				min_bk_num = temp;
			}
			
		for(int i = 2; i<bk_meal.size() ; i++) {
			if(bk_meal.get(i).getPrice() < min_bk_num) {
				min_bk2_num = min_bk_num;
				min2_bk_obj = min1_bk_obj;
				min_bk_num = bk_meal.get(i).getPrice();
				min1_bk_obj = bk_meal.get(i);
			}
			else if (bk_meal.get(i).getPrice() < min_bk2_num) {
				min_bk2_num = bk_meal.get(i).getPrice();
				min2_bk_obj = bk_meal.get(i);
			}
		}
		// The two cheapest meals at Burger King are stored in the meal_list ArrayList
		meal_list.add(min1_bk_obj);
		meal_list.add(min2_bk_obj);
		
	
		// The two cheapest meals at Wendy's are found by traversing the wen_meal ArrayList
		double min_wen_num = wen_meal.get(0).getPrice();
		double min_wen2_num = wen_meal.get(1).getPrice();
		Meal min1_wen_obj = wen_meal.get(0);
		Meal min2_wen_obj = wen_meal.get(1);
			if(min_wen2_num < min_wen_num) {
				double temp = min_wen_num;
				Meal temp_obj = min1_wen_obj;
				min1_wen_obj = min2_wen_obj;
				min2_wen_obj = temp_obj;
				min_wen_num = min_wen2_num;
				min_wen2_num = temp;
			}
			
		for(int i = 2; i<wen_meal.size() ; i++) {
			if(wen_meal.get(i).getPrice() < min_wen_num) {
				min_wen2_num = min_wen_num;
				min2_wen_obj = min1_wen_obj;
				min_wen_num = wen_meal.get(i).getPrice();
				min1_wen_obj = wen_meal.get(i);
			}
			else if (wen_meal.get(i).getPrice() < min_wen2_num) {
				min_wen2_num = wen_meal.get(i).getPrice();
				min2_wen_obj = wen_meal.get(i);
			}
		}
		// The two cheapest meals at Wendy's are stored in the meal_list ArrayList
		meal_list.add(min1_wen_obj);
		meal_list.add(min2_wen_obj);
		
		
		r.close();
		return meal_list;
	}
	
	
	public String toString() {
		return meal;
	}
	


}

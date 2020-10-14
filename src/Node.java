// This class represents a Node object
public class Node {
	private String city;
	private String meal;
	private double price;
	private String restaurant;

	// The constructor for a Node object 
	public Node(String City, String Meal, double Price, String RestaurantName) {
		// The state variables are assigned their respective values
		city = City;
		meal = Meal;
		price = Price;
		restaurant = RestaurantName;
		
	}
	
	
	// The getter returns the city name 
	public String getCity() {
		return city;
	}
	
	// The getter returns the meal name
	public String getMeal() {
		return meal;
	}
	
	// The getter returns the price of the meal
	public double getPrice() {
		return price;
	}
	
	// The getter returns the name of the restaurant
	public String getRestaurant() {
		return restaurant;
	}
	
	// This function changes the price field of the Node object
	public void changePrice(double newPrice) {
		price = newPrice;
	}
	
	// This function determines if two nodes are equal by comparing every field
	public boolean EqualsNode(Node compare) {
		if(compare.getMeal().compareTo(meal) == 0 && compare.getPrice() == price && compare.getRestaurant().compareTo(restaurant)==0) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	
}

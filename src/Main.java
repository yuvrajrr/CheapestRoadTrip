import java.io.IOException;

// The main class runs the other classes
public class Main {

	public static void main(String[] args) throws IOException {
		// The BFS and DFS from a start and end city are found
		Graph test = new Graph("data/out.txt");
		test.bfs("Boston", "Minneapolis");
		test.dfs("Boston", "Minneapolis");
		  
		// The cheapest path from a start and end city are found
		Weighted test2 = new Weighted("data/out.txt");
		test2.city_mapper(Cities.CityArray(Restaurant.RestArray(Meal.MealArray())));
		test2.weighted_list("Boston", "Minneapolis");
		  
	}
	
}

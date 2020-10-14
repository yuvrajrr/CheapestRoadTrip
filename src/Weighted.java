import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// This class creates a weighted graph
public class Weighted {
	private static HashMap<String, Node> city_map = new HashMap<String, Node>();
	private HashMap<String, ArrayList<Node>> final_map = new HashMap<String, ArrayList<Node>>();
	private String filename;
	
	// The constructor assigns the filename field a filename that was passed through
	public Weighted(String File) {
		filename = File;
	}
	
	// This function takes an ArrayList of city objects and maps a city (string) to it's corresponding Node
	public HashMap<String, Node> city_mapper(ArrayList<Cities> city_arraylist){
		// Each city in the list is iterated over and the name of the city is the key, and the node is the value
		for(Cities city: city_arraylist) {
			// A 0 and a 1 is added to the city name
			// Each city will have two different objects and two different strings representing it, the city with a 0 at the end will represent the Node that has the cheapest meal of that city and the city with a 1 at the end of it will represent the Node that has the second cheapest meal of that city
			city_map.put(city.getName() + "0", new Node(city.getName() + "0", city.getRestaurant().get(0).getMeals().get(0).getMeal(), city.getRestaurant().get(0).getMeals().get(0).getPrice(), city.getRestaurant().get(0).getName()));
			if(city.getRestaurant().size() > 1) {
				city_map.put(city.getName() + "1", new Node(city.getName() + "1", city.getRestaurant().get(1).getMeals().get(0).getMeal(), city.getRestaurant().get(1).getMeals().get(0).getPrice(), city.getRestaurant().get(1).getName()));
			}
			else {
				city_map.put(city.getName() + "1", new Node(city.getName() + "1", city.getRestaurant().get(0).getMeals().get(1).getMeal(), city.getRestaurant().get(0).getMeals().get(1).getPrice(), city.getRestaurant().get(0).getName()));
			}
		}
		return city_map;
	}
	

	
	// The weighted list is created in this function
	public HashMap<String, ArrayList<Node>> weighted_list(String Root, String Goal) throws IOException{
		BufferedReader read = new BufferedReader(new FileReader("data/connectedCities.txt"));
		String[] edge;
		String line_read;
		
		// This calls a function that creates an auxiliary node
		AuxNode(Root);
		
		// The while loop runs till the file connectedCities.txt has been read
		while((line_read = read.readLine()) != null) {
			edge = line_read.split(", ");
			String key_string0 = (edge[0]+"0").toUpperCase() ;
			String key_string1 = (edge[0] +"1").toUpperCase();
			String value_string0 = (edge[1] + "0").toUpperCase();
			String value_string1 = (edge[1] + "1").toUpperCase();
			
			// key_node0 represents the cheapest Node of the city that is in the first column of connectedCities.txt 
			Node key_node0 = city_map.get(key_string0);
			// key_node1 represents the second cheapest Node of the city that is in the first column of connectedCities.txt 
			Node key_node1 = city_map.get(key_string1);
			// value_node0 represents the cheapest Node of the city that is in the second column of connectedCities.txt 			
			Node value_node0 = city_map.get(value_string0);
			// value_node1 represents the second cheapest Node of the city that is in the second column of connectedCities.txt 
			Node value_node1 = city_map.get(value_string1);
			
			// The two Nodes that represent the start of the path that we are trying to find the cheapest cost of will be hashed with all the Nodes that represent the adjacent nodes (since there are now two nodes per city)
			if(key_string0.compareTo((Root+"0").toUpperCase())==0 && final_map.get(key_string0)==null) {
				ArrayList<Node> root_values = new ArrayList<Node>();
				root_values.add(value_node0);
				root_values.add(value_node1);
				final_map.put(key_string0, root_values);
				final_map.put(key_string1, root_values);
			}
			
			// If the root Node already has a value hashed to it, the new values will be added to the final_map field (ArrayList of String to an ArrayList of Nodes)
			else if(key_string0.compareTo((Root+"0").toUpperCase())==0 && final_map.get(key_string0) != null) {
				final_map.get(key_string0).add(value_node0);
				final_map.get(key_string1).add(value_node1);
			}
			
			// If the Node that is being read in the file is not a root node this block of code will run
			else {
			// If the current Node (Node representing the city that is in the first column (before the "," in the connectedCities.txt) has not already been hashed it will hash it to the corresponding values 
			if(final_map.get(key_string0) == null) {
				// If the current Node0 is equal to the adjacent node (the city that is after the comma in the connectedCities.txt), then the current Node will be hashed with the second cheapest value Node (Node representing the city that is in the second column (after the ",") 
				if(key_node0.EqualsNode(value_node0)) {
					
					ArrayList<Node> node_array1 = new ArrayList<Node>();
					node_array1.add(value_node1);
					final_map.put(key_string0, node_array1);
					
					ArrayList<Node> node_array0 = new ArrayList<Node>();
					node_array0.add(value_node0);
					final_map.put(key_string1, node_array0);	
				}
				
				// If the current Node1 is equal to the value Node0, the current Node0 will be hashed with the value Node0 and the current Node1 will be hashed with the value Node1 
				else if(key_node1.EqualsNode(value_node0)){
					
					ArrayList<Node> node_array1 = new ArrayList<Node>();
					node_array1.add(value_node1);
					final_map.put(key_string1, node_array1);
					
					ArrayList<Node> node_array0 = new ArrayList<Node>();
					node_array0.add(value_node0);
					final_map.put(key_string0, node_array0);
				}
				else {
					
					ArrayList<Node> node_array1 = new ArrayList<Node>();
					node_array1.add(value_node0);
					final_map.put(key_string1, node_array1);
					final_map.put(key_string0, node_array1);
				}
			}
			
			// If the current Node already has a value hashed to it, the value Nodes will be added to the ArrayList at these Nodes in the HashMap
			else {
				if(key_node0.EqualsNode(value_node0)) {
					
					final_map.get(key_string0).add(value_node1);
					final_map.get(key_string1).add(value_node0);
				}
				
				else if(key_node1.EqualsNode(value_node0)) {
					final_map.get(key_string0).add(value_node0);
					final_map.get(key_string1).add(value_node1);
				}
				
				else {
					final_map.get(key_string1).add(value_node0);
					final_map.get(key_string0).add(value_node0);
				}
			}

		}
		}
		read.close();

		// The weighted_search function is called
		weighted_search(Root, Goal);
		return final_map;
	}

	// This function creates an auxiliary node and changes the price (weight) of the root Nodes to 0
	public void AuxNode(String Root) {
		Node root0 = city_map.get((Root+"0").toUpperCase());
		root0.changePrice(0);
		
		Node root1 = city_map.get((Root+"1").toUpperCase());
		root1.changePrice(0);
		
		city_map.put((Root+"0").toUpperCase(), root0);
		city_map.put((Root+"1").toUpperCase(), root1);
		
		ArrayList<Node> source = new ArrayList<Node>();
		source.add(root0);
		source.add(root1);
		String aux = "Aux";
		
		final_map.put(aux, source);
		city_map.put(aux, new Node(aux, aux, 0.0, aux));
		
	}
	

	// This function creates a Dijkstra object and runs the algorithm to find the cheapest path as well as write it to the output file
	public HashMap<String, String> weighted_search(String Root, String Goal) throws IOException{
		Dijkstra test = new Dijkstra(city_map, final_map, filename);
		// This will search for the shortest path
		test.search();
		// This write the path to the output file
		test.pathCreate(Root, Goal);
		return test.getPath();
	}
	

	
}

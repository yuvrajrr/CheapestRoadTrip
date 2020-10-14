import java.util.*;
import java.io.*;

// This class represents a Dijkstra object
public class Dijkstra {
	// Maps a city to its corresponding node
	private HashMap<String, Node> city_map = new HashMap<String, Node>();
	// Maps a city to an ArrayList of adjacent Nodes
	private HashMap<String, ArrayList<Node>> final_map = new HashMap<String, ArrayList<Node>>();
	
	// Keeps track of all the visited Nodes
	private ArrayList<Node> visited = new ArrayList<Node>();
	// HashMap that stores the distance of each city from the root node
	private HashMap<String,Double> distance = new HashMap<String, Double>();
	// HashMap that stores the shortest path of every Node
	private HashMap<String, String> path = new HashMap<String, String>();
	
	private String filename;
	
	// The constructor will assign the values passed through to the state variables
	public Dijkstra(HashMap<String, Node> City_map,	HashMap<String, ArrayList<Node>> Final_map, String File) {
		city_map = City_map;
		final_map = Final_map;
		filename = File;
		// The HashMap that represents the distance of each node is initially set to the maximum value a double can hold
		for(String s: city_map.keySet()) {
			distance.put(s, Double.MAX_VALUE);
		}
		// The distance of the auxiliary node is initially set to 0
		distance.put("Aux", 0.0);
		
		
	}
	
	// Getter that returns the visited ArrayList of Nodes
	public ArrayList<Node> getVisited(){
		return visited;
	}
	
	// Getter that returns the HashMap of the path
	public HashMap<String, String> getPath(){
		return path;
	}
	
	// Getter that returns the HashMap of the distance of each node
	public HashMap<String, Double> getDistance(){
		return distance;
	}
	
	// Function that returns a boolean true or false after checking if a Node has already been visited
	public boolean Visit(String key_string) {
		for(int i = 0; i<visited.size() ; i++) {
			if(key_string.compareTo(visited.get(i).getCity()) == 0) {
				return true;
			}
		}
		
		return false;
			
	}
	
	// Function that finds the node with the smallest distance 
	public String min_node() {
		String min = null;
		double min_val = Double.MAX_VALUE;
		
		for(String key: distance.keySet()) {
				if( Visit(key) == false && distance.get(key)<min_val) {
					min_val = distance.get(key);
					min = key;
				}
			}
		
		return min;
	}
	
	
	// Function that runs the algorithm to find the shortest path
	public void search() {
		// The initial minimum string is set to the Auxiliary string 
		String minimum = "Aux";
		ArrayList<Node> connected_nodes = new ArrayList<Node>();
		// The while loop runs until all nodes have been visited
		while((visited.size()*2+1) != city_map.size()) {
			// Adjacent nodes of the Node with the smallest distance from the root are stored in connected_nodes
			connected_nodes = final_map.get(minimum);
			// Every Node that is adjacent to the Node with the smallest distance from the root Node is traversed 
			for(Node current:connected_nodes) {
				// If the price (weight) of the adjacent node + the distance is less than the current distance of the node, the distance is updated
				// The path of the Node that has been updated is stored with the minimum Node being set as the parent Node
				if(current.getPrice() + distance.get(minimum) < distance.get(current.getCity())) {
					distance.put(current.getCity(), current.getPrice() + distance.get(minimum));
					path.put(current.getCity(), minimum);

					// If the adjacent node has no outgoing nodes it is set as visited 
					if(final_map.get(current.getCity()) == null) {
						visited.add(current);
					}
				}
			}
			// The minimum Node is set as visited
			visited.add(city_map.get(minimum));
			// A new minimum node is found using the min_node() function
			minimum = min_node();
		}

	}
	
	// The path from the start city to the end city is found 
	public void pathCreate(String start, String end) throws IOException {
		String start0 = (start+"0").toUpperCase();
		String start1 = (start+"1").toUpperCase();
		String end0 = (end+"0").toUpperCase();
		String end1 = (end+"1").toUpperCase();
		
		String current0 = end0;
		String current1 = end1;
		
		ArrayList<Node> path0 = new ArrayList<Node>();
		ArrayList<Node> path1 = new ArrayList<Node>();
		
		// Since each city is represented by 2 Nodes, Node0 and Node1, both paths are found by using the path HashMap and tracing it back to the starting Node
		// This while loop runs until the path of Node0 (cheapest Node of the end city) has been found
		while(!(current0.compareTo(start0) == 0) && !(current0.compareTo(start1) == 0)) {
			path0.add(city_map.get(current0));
			current0 = path.get(current0);

		}
		path0.add(city_map.get(start0));
		
		// This while loop runs until the path of Node1 (second cheapest Node of the end city) has been found
		while(current1.compareTo(start0) != 0 && current1.compareTo(start1) != 0) {
			path1.add(city_map.get(current1));
			current1 = path.get(current1);
		}
		
		path1.add(city_map.get(start1));
		
		double total0 = 0.0;
		double total1 = 0.0;
		
		// The total cost of the path for Node0 is found
		for(int i = 0; i<path0.size() ; i++) {
			total0 += path0.get(i).getPrice();
		}
		
		// The total cost of the path for Node1 is found
		for(int i = 0; i<path1.size() ; i++) {
			total1 += path1.get(i).getPrice();
		}
		
		
		ArrayList<Node> reverse = new ArrayList<Node>();
		double finalPrice;
		// The cheaper path array is reversed since it was stored in the reverse order
		if(total0 < total1) {
			for(int j = (path0.size() - 1) ; j > -1 ; j--) {
				reverse.add(path0.get(j));	
			}
			finalPrice = total0;
		}
		
		else {
			for(int j = (path1.size() - 1) ; j > -1 ; j--) {
				reverse.add(path1.get(j));	
			}
			finalPrice = total1;
		}
	
		// The reversed array is passed through with the cost of that path (cheapest cost) to be written to the output file
		pathWrite(reverse, finalPrice);
		
	}
	
	// The cheapest cost path is written to the output file
	public void pathWrite(ArrayList<Node> finalPath, double finalPrice) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter(filename, true));
		w.write("\n");
		w.write("\n");
		// The headings are written in first
		w.write(String.format("%-20s | %-30s | %-20s \r\n", "City", "Meal Choice", "Cost of Meal"));
		w.write("------------------------------------------------------------------------");
		w.write("\n");
		
		// The first city is written in seperately since the meal will says "No Meal"
		w.write(String.format("%-20s | %-30s | %-20s \r\n", finalPath.get(0).getCity().substring(0, finalPath.get(0).getCity().length() - 1), "No Meal", "$0.00"));
		w.write("------------------------------------------------------------------------");
		w.write("\n");
		
		// The loop will run for the size of the remaining path and write the City, Meal, and Cost in a table 
		for(int i = 1; i < finalPath.size() ; i++) {
			w.write(String.format("%-20s | %-30s | %-20s \r\n", finalPath.get(i).getCity().substring(0, finalPath.get(i).getCity().length() - 1), finalPath.get(i).getMeal(), "$"+finalPath.get(i).getPrice()));
			w.write("------------------------------------------------------------------------");
			w.write("\n");
		}
		w.write("Total Cost: $" + Double.toString(finalPrice));
		
		w.close();
	}	
	
}

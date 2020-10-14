import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

// This class creates an adjacency list and runs a breadth-first search and depth-first search to find the path from one city to another
public class Graph {
	
	// Adjacency list initialized as a HasMap of strings to an ArrayList of strings
	private HashMap<String, ArrayList<String>> adj_list = new HashMap<String, ArrayList<String>>();
	// filename is the string that represents what the output file is going to be named
	// It is initialized on creation of a graph object
	private String filename;
	
	// Graph class constructor
	// Constructor will run the AdjacentList() function 
	// Constructor will give a value to the state variable filename
	public Graph(String File) throws IOException {
		AdjacentList();
		filename = File;
	}
	
	// This class creates an adjacency list by reading from the connectedCities.txt file
	public void AdjacentList() throws IOException {
		BufferedReader read = new BufferedReader(new FileReader("data/connectedCities.txt"));
		String[] edge;
		String line_read;
		
		// Each line is read and split at the ", "
		while((line_read = read.readLine()) != null) {
			edge = line_read.split(", ");
			
			// If the first city (key) does not contain a value, an ArrayList will be created containing the value (the second city in the line)
			// The ArrayList will then be the associated value at the key (first city in the line)
			if(adj_list.get(edge[0]) == null) {
				ArrayList<String> value = new ArrayList<String>();
				value.add(edge[1]);
				adj_list.put(edge[0], value);
				}
			
			// If the first city does have a value (existing ArrayList with a city or several cities) then the value (second city in the line) will be added to that ArrayList
			else {
				adj_list.get(edge[0]).add(edge[1]);
				}
		}
		read.close();
	}


	
	// This function runs the breadth-first search and creates a HashMap that hashes every node to its parent node
	// The function takes a string of the starting city and the ending city
	public void bfs(String start, String end) throws IOException{
		
		// If the start city is the same as the end city the bfs_same_path function is called
		if(start.compareTo(end) == 0) {
			bfs_same_path(start);
		}
		
		// If the start city and the end city are different, the breadth first search begins
		else {
			// This HashMap stores a node and its parent node
			HashMap<String, String> path = new HashMap<String,String>();
			// This HashSet is used to track which nodes have been visited
			HashSet<String> visited = new HashSet<String>();
			// This Queue is used to store the nodes that have not yet been visited but were connected to a visited node
			Queue<String> store = new LinkedList<String>();
			String current;
		
			// The starting city is added to the queue
			store.add(start);
		
			// This loop runs until the the queue is empty indicating that the breadth first search has been completed
			while(!store.isEmpty()) {
				// The current city being looked at is pulled from the front of the queue and stored in the string current
				current = store.poll();
				// The current city is marked as visited
				visited.add(current);
			
				// If the current city does not have any adjacent cities, the loop is skipped
				if(adj_list.get(current) == null) {
					continue;
				}
			
				// The adjacency list is checked for the all the adjacent nodes of the current node
				// Iterating over the ArrayList of the adjacent nodes, every adjacent node is added to the queue only if the adjacent node has not been visited before and it is not currently in the queue
				// Every adjacent node is also hashed to the path HashMap with the value being the current node and the key being the adjacent node that is being iterated over
				for(int i = 0; i < adj_list.get(current).size(); i++) {
					if(!visited.contains(adj_list.get(current).get(i)) && !store.contains(adj_list.get(current).get(i))){
						store.add(adj_list.get(current).get(i));
						path.put(adj_list.get(current).get(i), current);
					}
				}
			}
		
			// The bfs_path_result function is called with the path HashMap, the start city and the end city
			bfs_path_result(path, start, end);	
		}
		
	}

	
	
	// This function writes the start city in the new file as the path when the start and the end city are the same
	public void bfs_same_path(String start) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter(filename));
		w.write("BFS: "+start);
		w.write("\n");
		w.close();
	}
	
	// This function finds the path using the path HashMap that was created in the bfs function
	public void bfs_path_result(HashMap<String, String> path, String start, String end) throws IOException {
		// The Result is stored in an array of strings
		ArrayList<String> result_array = new ArrayList<String>();
		
		// Since the keys in the path HashMap were the cities and the value was the parent node of that city, the path will be traced backwards
		// The current location is therefore set as the end city
		String current_location = end;
		// The parent node (In this block of code I will be referring to it as a node because in a graph it would be a node, it is however a string in this block of code) of that city is found by using the HashMap and the current_location string
		String parent = path.get(current_location);
		
		// The current_location string (end city) is added to the result array 
		result_array.add(current_location);
		
		// The while loop runs until the last entry in the array is equal to the start city (indicating that the path has been found)
		while(result_array.get(result_array.size()-1) != start) {
			// The parent is found using the current location 
			parent = path.get(current_location);
			
			
			// If the parent node is null, that means that there is no valid path and the loop breaks
			if(parent == null) {
				break;
			}
			
			// The parent node is added to the array 
			// The current location is now set as the parent for the next iteration
			result_array.add(parent);
			current_location = parent;
		}
		
		// Since the code would break if the parent was null, it will then reach this point and call the function bfs_null()
		if(parent == null) {
			bfs_null();
		}
		
		
		// If the parent is not null, a path exists and has been found
		// The ArrayList that stored the path is backwards so it is reversed in this for loop
		else {
		ArrayList<String> result_array_flipped = new ArrayList<String>();
		for(int j = result_array.size() - 1; j > -1; j--) {
			result_array_flipped.add(result_array.get(j));
		}
		
		// The bfsOut function is called and the flipped array is passed through
		bfsOut(result_array_flipped);
		}
		
	}
	
	
	// This function creates the file using filename
	// Nothing is written because this function is only called if no path existed
	public void bfs_null() throws IOException {
		BufferedWriter w = new BufferedWriter(new FileWriter(filename));
		w.close();
	}
	
	
	// This function writes the path of the bfs in a new file
	public void bfsOut(ArrayList<String> result) throws IOException {
		BufferedWriter w = new BufferedWriter(new FileWriter(filename));
		w.write("BFS: ");
		for(int i = 0; i < result.size(); i++) {
			w.write(result.get(i));
			if(i != result.size() - 1) {
			w.write(", ");
			}
		}
		w.write("\n");
		w.close();
	}
	
	
	// This function sets up the correct variables needed before running the DFS
	public void dfs(String start, String end) throws IOException {
		// A HashSet is used to track the visited "nodes" (nodes refers to the nodes in terms of the graph, not the nodes from the Node class
		HashSet<String> visited = new HashSet<String>();
		// A HashMap tracks the path of the DFS
		HashMap<String, String> path = new HashMap<String, String>();
		// If the start city is the same as the end city, the dfs_same_path function is called and the DFS is skipped
		if(start.compareTo(end) == 0) {
			dfs_same_path(start);
		}
		// If there is a different start city to end city, the dfs_start function is called 
		else {
			dfs_start(start, start, end, visited, path);
		}
	
	}
	
	// This function writes the start city in the new file as the path when the start and the end city are the same
	public void dfs_same_path(String start) throws IOException {
		BufferedWriter w = new BufferedWriter (new FileWriter(filename, true));
		w.write("DFS: " + start);
		w.close();
	}
	
	// This function runs the DFS
	public void dfs_start(String current, String start, String end, HashSet<String> visited, HashMap<String, String> path) throws IOException {
		// The current "node" (of type string) is hashed to the visited HashSet
		visited.add(current);
		// This condition checks if the current string is equal to the destination city and will return because the DFS is now complete
		if(current.compareTo(end) == 0 ) {
			dfs_path(start, end, path);
			return;
		}

		// If the current "node" has no adjacent nodes it will return, this is because it must trace back to a "node" that has adjacent nodes in order to find the end city
		if(adj_list.get(current) == null) {
			return;
		}

		// The adjacent nodes are traversed and will be added to the path if they have not been visited
		// Recursively each adjacent "node" will call this function until it either reached the end city or it reaches a point where it has no more adjacent "nodes"
		for(int i = 0; i < adj_list.get(current).size(); i++) {
			if(!visited.contains(adj_list.get(current).get(i))) {
				path.put(adj_list.get(current).get(i), current);
		
				dfs_start(adj_list.get(current).get(i), start, end, visited, path);
				}
		}
		
		return;
	
	}
	
	
	// This function traces back the path using the HashMap
	public void dfs_path(String start, String end, HashMap<String, String> path) throws IOException {
		ArrayList<String> result_array = new ArrayList<String>();
		String current_location = end;
		String parent;
		result_array.add(current_location);
		while(result_array.get(result_array.size()-1) != start) {
			parent = path.get(current_location);
			result_array.add(parent);
			current_location = parent;
		}
		
		// The resulting array of the path is reversed since it was in reverse order 
		ArrayList<String> result_array_flipped = new ArrayList<String>();
		for(int j = result_array.size() - 1; j > -1; j--) {
			result_array_flipped.add(result_array.get(j));
		}
		
		// The function to write the results is called
		dfsOut(result_array_flipped);
		
	}
	
	// The result array is passed through and the path is written to the output file
	public void dfsOut(ArrayList<String> result) throws IOException {
		BufferedWriter w = new BufferedWriter(new FileWriter(filename, true));
		w.write("DFS: ");
		for(int i = 0; i < result.size(); i++) {
			w.write(result.get(i));
			if(i != result.size() - 1) {
			w.write(", ");
			}
		}
		w.close();
	}
	
}

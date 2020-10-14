import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// This class tests the BFS and DFS
class JUnit {
	// The Graph object is a set as a state variable
	Graph Test;
	BufferedReader w;
	String bfs_path;
	String dfs_path;
	
	// A new graph object is created before every test
	@BeforeEach
	void setup() throws IOException {
		Test = new Graph("data/out.txt");
	}
	
	// The path from Boston to Minneapolis is tested for BFS and DFS
	@Test
	void testBostonMinneapolis() throws IOException {
		Test.bfs("Boston", "Minneapolis");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path.compareTo("BFS: Boston, New York City, Pittsburgh, Columbus, Chicago, Minneapolis") == 0);
		
		Test.dfs("Boston", "Minneapolis");
		dfs_path = w.readLine();
		assertTrue(dfs_path.compareTo("DFS: Boston, New York City, Philadelphia, Baltimore, Washington, Charlotte, Atlanta, Nashville, Columbus, Indianapolis, St. Louis, Kansas City, Denver, Salt Lake City, Las Vegas, Los Angeles, San Francisco, Portland, Seattle, Minneapolis") == 0);
	}
	
	// The path from New York City to Columbus is tested for BFS and DFS
	@Test
	void testNYCColumbus() throws IOException {
		Test.bfs("New York City", "Columbus");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path.compareTo("BFS: New York City, Pittsburgh, Columbus") == 0);
		
		Test.dfs("New York City", "Columbus");
		dfs_path = w.readLine();
		assertTrue(dfs_path.compareTo("DFS: New York City, Philadelphia, Baltimore, Washington, Charlotte, Atlanta, Nashville, Columbus") == 0);
	}
	
	// The path from Las Vegas to Phoenix is tested for BFS and DFS
	@Test
	void testLasVegasPhoenix() throws IOException {
		Test.bfs("Las Vegas", "Phoenix");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path.compareTo("BFS: Las Vegas, Phoenix") == 0);
		
		Test.dfs("Las Vegas", "Phoenix");
		dfs_path = w.readLine();
		assertTrue(dfs_path.compareTo("DFS: Las Vegas, Los Angeles, Phoenix") == 0);
	}
		
	// The path from St. Louis to Dallas is tested for BFS and DFS
	@Test
	void testStLouisDallas() throws IOException {
		Test.bfs("St. Louis", "Dallas");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path.compareTo("BFS: St. Louis, Kansas City, Oklahoma City, Dallas") == 0);
		
		Test.dfs("St. Louis", "Dallas");
		dfs_path = w.readLine();
		assertTrue(dfs_path.compareTo("DFS: St. Louis, Kansas City, Denver, Salt Lake City, Las Vegas, Los Angeles, Phoenix, Albuquerque, Dallas") == 0);
	}
		
	// This case tests a path where the start city and end city are the same for BFS and DFS
	@Test
	void testBostonNoPath() throws IOException {
		Test.bfs("Boston", "Boston");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path.compareTo("BFS: Boston") == 0);
		
		Test.dfs("Boston", "Boston");
		dfs_path = w.readLine();
		assertTrue(dfs_path.compareTo("DFS: Boston") == 0);
	}
	
	// This case tests a path where the start city and end city are the same for BFS and DFS
	@Test
	void testNYCNoPath() throws IOException {
		Test.bfs("New York City", "New York City");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path.compareTo("BFS: New York City") == 0);
		
		Test.dfs("New York City", "New York City");
		dfs_path = w.readLine();
		assertTrue(dfs_path.compareTo("DFS: New York City") == 0);
	}
	
	
	// This case test a non existent path for a BFS and DFS and proves true if the paths return null since there is no path
	@Test
	void testNonExistentPath() throws IOException {
		Test.bfs("New York City", "Boston");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path == null);
		
		Test.dfs("New York City", "Boston");
		dfs_path = w.readLine();
		assertTrue(dfs_path == null);
	}
	

	// This case test a non existent path for a BFS and DFS and proves true if the paths return null since there is no path
	@Test
	void testNonExistentPath2() throws IOException {
		Test.bfs("Philadelphia", "Boston");
		w = new BufferedReader(new FileReader("data/out.txt"));
		bfs_path = w.readLine();
		assertTrue(bfs_path == null);
		
		Test.dfs("Philadelphia", "Boston");
		dfs_path = w.readLine();
		assertTrue(dfs_path == null);
	}

}

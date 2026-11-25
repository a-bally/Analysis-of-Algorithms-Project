package project;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("=========================================");
		System.out.println(" Amazon Delivery Route Optimization Tool ");
		System.out.println("=========================================");
		
		// Algorithm Menu
		System.out.println("\nSelect an Algorithm to Run:");
		System.out.println("1. Brute Force");
		System.out.println("2. Dynamic Programming");
		System.out.println("3. Greedy");
		System.out.println("4. 2-Opt");
		System.out.println("Enter your choice (1-4): ");
		int algorithmChoice = scanner.nextInt();
		
		// Parameter / Input Size Selection Menu
		System.out.println("n/Select Input Size (Number of Delivery Stops - N): ");
		System.out.println("1. Small Test (20 stops)");
		System.out.println("2. Medium Test (200 stops)");
		System.out.println("3. Large Test ( 1,000 stops");
		System.out.println("4. X-Large Test ( 10,000 stops");
		System.out.println("Enter your choice (1-4): ");
		int sizeChoice = scanner.nextInt();
		
		// Map size choice
		int n = 10;
		switch (sizeChoice) {
			case 1: n = 10; break;
			case 2: n = 200; break;
			case 3: n = 1000; break;
			case 4: n = 10000; break;
			default:
				n = 100;
		}
		
		System.out.println("\n--------------------------------------------------------");
		System.out.println("Initializing dataset and running test for N = " + n + "...");
		System.out.println("----------------------------------------------------------");
		
		String csvPath = "amazon_delivery.csv";

		List<Graph.Location> locations = Graph.loadLocationsFromCSV(csvPath);
		if (locations.isEmpty()) {
			System.out.println("No locations loaded. Exiting.");
			scanner.close();
			return;
		}
		
		if (locations.size() > n) {
			locations = new ArrayList<>(locations.subList(0, n));
		}
		
		double[][] matrix = Graph.createDistanceMatrix(locations);
		Graph graph = new Graph(locations, matrix);
		
		long startTime = System.nanoTime();
		List<Integer> route = null;
		
		switch(algorithmChoice) {
			case 1:
				System.out.println("Executing Brute Force...");
				route = Algorithms.bruteForce(graph);
				break;
			case 2:
				System.out.println("Executing Dynamic Programming...");
				route = Algorithms.dynamicProgramming(graph);
				break;
			case 3:
				System.out.println("Executing Greedy...");
				route = Algorithms.greedyNearestNeighbor(graph);
				break;
			case 4:
				System.out.println("Executing 2-Opt...");
				List<Integer> initialRoute = Algorithms.greedyNearestNeighbor(graph);
				route = Algorithms.twoOpt(graph, initialRoute);
				break;
			default:
				System.out.println("Invalid algorithm selection.");
				scanner.close();
				return;
		}
		
		
		
		// End timing
		long endTime = System.nanoTime();
		double durationMs = (endTime - startTime) / 1_000_000.0;
		double durationSeconds = durationMs / 1000.0;
		
		System.out.println("\n=====================================================================");
		System.out.println("==========================Benchmark Results============================");
		System.out.println("Execution Time: " + durationMs + " ms (" + durationSeconds + " seconds)");
		System.out.println("=======================================================================");
		
		scanner.close();
	}
}
		
		
		
		
		
		
		/* Example dummy distance matrix for testing (replace with real dataset loader later)
		double[][] sampleDistances = {
				{0, 10, 15, 20},
				{10, 0, 35, 25},
				{15, 35, 0, 30},
				{20, 25, 30, 0}
		};
		
		Graph graph = new Graph(sampleDistances);
		
		System.out.println("Running Route Optimization Algorthims...");
		
		// Test Greedy Algorithm & time it
		long startTime = System.nanoTime();
		List<Integer> greedyRoute = Algorithms.greedyNearestNeighbor(graph);
		long endTime = System.nanoTime();
		
		System.out.println("Greedy Route Execution Time (nano-seconds): " + (endTime - startTime));
	}
}
*/
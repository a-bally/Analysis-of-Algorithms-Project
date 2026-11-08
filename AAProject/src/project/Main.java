package project;

import java.util.List;
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
		System.out.println("3. Greedy Nearest Neighbor");
		System.out.println("4. 2-Opt");
		System.out.println("Enter your choice (1-4): ");
		int algorithmChoice = scanner.nextInt();
		
		// Parameter / Input Size Selection Menu
		System.out.println("n/Select Input Size (Number of Delivery Stops - N): ");
		System.out.println("1. 1,000 stops");
		System.out.println("2. 10,000 stops");
		System.out.println("3. 100,000 stops");
		System.out.println("4. 1,000,000 stops");
		System.out.println("Enter your choice (1-4): ");
		int sizeChoice = scanner.nextInt();
		
		// Map size choice
		int n = 1000;
		switch (sizeChoice) {
			case 1: n = 1000; break;
			case 2; n = 10000; break;
			case 3; n = 1000000; break;
			case 4; n = 1000000; break;
			default:
				System.out.println("Invalid selection. Defaulting to 1,000 stops.");
				n = 1000;
		}
		
		System.out.println("\n--------------------------------------------------------");
		System.out.println("Initializing dataset and running test for N = " + n + "...");
		System.out.println("----------------------------------------------------------");
		
		long startTime = System.nanoTime();
		
		switch(algorithmChoice) {
			case 1:
				System.out.println("Executing Brute Force...");
				//
				break;
			case 2:
				System.out.println("Executing Brute Force...");
				//
				break;
			case 3:
				System.out.println("Executing Brute Force...");
				//
				break;
			case 4:
				System.out.println("Executing Brute Force...");
				//
				break;
			default:
				System.out.println("Invalid algorithm selection.");
				scanner.close();
				return;
		}
		
		// End timing
		long endTime = System.nanoTime();
		double durationMs = (endTime - startTime) / 1_000_000.0;
		double durationSeconds = durationMS / 1000.0;
		
		System.out.println("\n=====================================================================");
		System.out.println("==========================Benchmark Results============================");
		System.out.println("Execution Time: " + durationMS + " ms (" + durationSeconds + " seconds)");
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
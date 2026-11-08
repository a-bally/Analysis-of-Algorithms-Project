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
		System.out.println("1. Brute Force (Small tests only: N <= 10");
		System.out.println("2. Dynamic Programming (Small/Medium tests: N <= 20");
		System.out.println("3. Greedy Nearest Neighbor (Small, Medium, Large)");
		System.out.println("4. 2-Opt (Small, Medium, Large)");
		System.out.println("Enter your choice (1-4): ");
		int algorithmChoice = scanner.nextInt();
		
		// Parameter / Input Size Selection Menu
		System.out.println("n/Select Input Size (Number of Delivery Stops - N): ");
		System.out.println("1. Small Test (5 to 20 stops)");
		System.out.println("2. Medium Test (25 to 200 stops)");
		System.out.println("3. Large Test ( 1,000 to 10,000 stops");
		System.out.println("Enter your choice (1-3): ");
		int sizeChoice = scanner.nextInt();
		
		// Map size choice
		int n = 10;
		switch (sizeChoice) {
			case 1: n = 10; break;
			case 2: n = 50; break;
			case 3: n = 10000; break;
			default:
				n = 100;
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
				System.out.println("Executing Dynamic Force...");
				//
				break;
			case 3:
				System.out.println("Executing Greedy...");
				//
				break;
			case 4:
				System.out.println("Executing 2-Opt...");
				//
				break;
			default:
				System.out.println("Invalid algorithm selection.");
				scanner.close();
				return;
		}
		
		// Validation for exact algorithm choice (large sets)
		if ((algorithmChoice == 1 || algorithmChoice == 2) && n > 20) {
			System.out.println("\n[Warning] Brute Force and DP are too slow for N > 20! Switching N to 10 for safety.");
			n = 10;
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
package project;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		// Example dummy distance matrix for testing (replace with real dataset loader later)
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

package project;

import java.util.List;
import java.util.ArrayList;

public class Algorithms {

	// 1. Brute Force (Baseline) - O(n!)[cite: 2]
	public static List<Integer> bruteForce(Graph graph) {
		//TODO: Implement permutation generation to find ablsolute shortest route
		List<Integer> bestRoute = new ArrayList<>();
		return bestRoute;
	}
	
	// 2. Dynamic Programming (Held-Karp) - O(n^2 * 2^n)[cite: 2]
	public static List<Integer> dynamicProgramming(Graph graph) {
		//TODO: Implement memoized subset generation
		List<Integer> bestRoute = new ArrayList<>();
		return bestRoute;
	}
	
	// 3. Greedy (Nearest Neighbor) - O(n^2)[cite: 2]
	public static List<Integer> greedyNearestNeighbor(Graph graph) {
		List<Integer> route = new ArrayList<>();
		// TODO: Always pick the closest unvisited city
		return route;
	}
	
	// 4. 2-opt Heuristic - Iterative improvement on greedy route[cite: 2]
	public static List<Integer> twoOpt(Graph graph, List<Integer> initialRoute){
		List<Integer> optimizedRoute = new ArrayList<>(initialRoute);
		// TODO: Swap edges to eliminate path crossings
		return optimizedRoute;
	}
}

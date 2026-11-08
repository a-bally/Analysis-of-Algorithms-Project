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
		int n = graph.size(); // Number of delivery stops
        boolean[] visited = new boolean[n];

        // Start at node 0 (the depot / first stop)
        int current = 0;
        visited[current] = true;
        route.add(current);

        // Find the nearest unvisited neighbor for every step
        for (int count = 1; count < n; count++) {
            int nearest = -1;
            double minDistance = Double.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    // Fetch distance from current node to node i using the Graph object
                    double dist = graph.createDistanceMatrix(current, i); 
                    if (dist < minDistance) {
                        minDistance = dist;
                        nearest = i;
                    }
                }
            }

            if (nearest != -1) {
                visited[nearest] = true;
                route.add(nearest);
                current = nearest; // Move to the chosen neighbor
            }
        }

        // Optional: Complete the TSP cycle by returning to the start node
        route.add(route.get(0)); 

        return route;
    }


	// 4. 2-opt Heuristic - Iterative improvement on greedy route[cite: 2]
	public static List<Integer> twoOpt(Graph graph, List<Integer> initialRoute){
		List<Integer> optimizedRoute = new ArrayList<>(initialRoute);
		// TODO: Swap edges to eliminate path crossings
		return optimizedRoute;
	}
}

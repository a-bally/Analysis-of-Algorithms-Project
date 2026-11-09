package project;

import java.util.List; 
import java.util.ArrayList;

public class Algorithms {

    // 1. Brute Force (Baseline) - O(n!)
    // Tries every possible order of stops (always starting at stop 0)
    // and keeps the shortest round trip.
    public static List<Integer> bruteForce(Graph graph) {
        int n = graph.getNumLocations();
        List<Integer> bestRoute = new ArrayList<>();
        if (n == 0) {
            return bestRoute;
        }

        // Stop 0 is always the start; every other stop gets arranged after it.
        // This skips checking the same loop starting from different places.
        List<Integer> current = new ArrayList<>();
        current.add(0);
        boolean[] visited = new boolean[n];
        visited[0] = true;

        double[] bestDistance = { Double.MAX_VALUE };
        bruteForceSearch(graph, current, visited, 0.0, bestRoute, bestDistance);
        return bestRoute;
    }

    // Helper for bruteForce: recursively builds every possible order of the remaining stops.
    private static void bruteForceSearch(Graph graph, List<Integer> current, boolean[] visited,
                                         double distanceSoFar, List<Integer> bestRoute, double[] bestDistance) {
        int n = graph.getNumLocations();

        // Every stop is in the route: add the trip back to the start and compare
        if (current.size() == n) {
            int last = current.get(n - 1);
            double total = distanceSoFar + graph.getDistance(last, 0);
            if (total < bestDistance[0]) {
                bestDistance[0] = total;
                bestRoute.clear();
                bestRoute.addAll(current);
            }
            return;
        }

        // Try each unvisited stop as the next one, then undo and try the next option
        int last = current.get(current.size() - 1);
        for (int next = 0; next < n; next++) {
            if (!visited[next]) {
                visited[next] = true;
                current.add(next);
                bruteForceSearch(graph, current, visited,
                                 distanceSoFar + graph.getDistance(last, next), bestRoute, bestDistance);
                current.remove(current.size() - 1);
                visited[next] = false;
            }
        }
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

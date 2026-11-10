package project;

import java.util.List; 
import java.util.ArrayList;
import java.util.Arrays;

public class Algorithms {

    // 1. Brute Force (Baseline) - O(n!)
    // Tries every possible order of stops (always starting at stop 0)
    // and keeps the shortest round trip.
    public static List<Integer> bruteForce(Graph graph) {
        int n = graph.size();
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
        int n = graph.size();

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
	
	// 2. Dynamic Programming (Held-Karp) - O(n^2 * 2^n) time, O(n * 2^n) space
	// Finds the shortest round trip starting at stop 0. Like brute force,
	// the returned route lists each stop once; the trip back to 0 is implied.
	public static List<Integer> dynamicProgramming(Graph graph) {
		int n = graph.size();
		List<Integer> bestRoute = new ArrayList<>();
		if (n == 0) {
			return bestRoute;
		}
		if (n > 20) {
			throw new IllegalArgumentException("Dynamic programming supports at most 20 stops.");
		}
		bestRoute.add(0);
		if (n == 1) {
			return bestRoute;
		}

		// A bit in mask means that stop (bit + 1) has been visited.
		// Stop 0 is not in the mask because every route starts there.
		int otherStops = n - 1;
		int subsetCount = 1 << otherStops;
		int fullMask = subsetCount - 1;

		// dp[mask, last] = shortest distance from 0, visiting exactly
		// the stops in mask, and finishing at stop (last + 1).
		// Flat arrays keep memory usage lower for inputs near 20 stops.
		double[] dp = new double[subsetCount * otherStops];
		int[] parent = new int[subsetCount * otherStops];
		Arrays.fill(dp, Double.POSITIVE_INFINITY);
		Arrays.fill(parent, -1);

		for (int mask = 1; mask < subsetCount; mask++) {
			for (int ends = mask; ends != 0; ends &= ends - 1) {
				int last = Integer.numberOfTrailingZeros(ends);
				int previousMask = mask & ~(1 << last);
				int state = mask * otherStops + last;

				if (previousMask == 0) {
					// First delivery: travel directly from depot 0.
					dp[state] = graph.getDistance(0, last + 1);
				} else {
					// Try every possible previous stop and keep the best one.
					for (int starts = previousMask; starts != 0; starts &= starts - 1) {
						int previous = Integer.numberOfTrailingZeros(starts);
						double distance = dp[previousMask * otherStops + previous]
								+ graph.getDistance(previous + 1, last + 1);
						if (distance < dp[state]) {
							dp[state] = distance;
							parent[state] = previous;
						}
					}
				}
			}
		}

		// Include the last edge back to the depot when choosing the route.
		double shortestTrip = Double.POSITIVE_INFINITY;
		int lastStop = -1;
		for (int last = 0; last < otherStops; last++) {
			double total = dp[fullMask * otherStops + last]
					+ graph.getDistance(last + 1, 0);
			if (total < shortestTrip) {
				shortestTrip = total;
				lastStop = last;
			}
		}
		if (lastStop == -1) {
			throw new IllegalStateException("No complete delivery route was found.");
		}

		// Follow saved previous stops backwards to rebuild the best route.
		int[] order = new int[otherStops];
		int mask = fullMask;
		for (int position = otherStops - 1; position >= 0; position--) {
			order[position] = lastStop + 1;
			int previous = parent[mask * otherStops + lastStop];
			mask &= ~(1 << lastStop);
			lastStop = previous;
		}
		for (int stop : order) {
			bestRoute.add(stop);
		}
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
                    double dist = graph.getDistance(current, i);
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


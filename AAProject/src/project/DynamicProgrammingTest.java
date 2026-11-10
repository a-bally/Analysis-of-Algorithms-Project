package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Tests for the Held-Karp dynamic programming algorithm.
// Run this file as a Java application to check the results.
public class DynamicProgrammingTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testEmptyGraph();
        testOneStop();
        testTwoStops();
        testFourStopSample();
        testRouteVisitsEveryStop();
        testMatchesBruteForce();
        testTwentyStops();
        testTooManyStops();

        System.out.println("\nPassed: " + passed + "   Failed: " + failed);
        if (failed > 0) {
            throw new AssertionError(failed + " dynamic programming test(s) failed.");
        }
    }

    private static void testEmptyGraph() {
        Graph g = graphFromMatrix(new double[0][0]);
        check("Empty graph gives empty route",
                Algorithms.dynamicProgramming(g).isEmpty());
    }

    private static void testOneStop() {
        Graph g = graphFromMatrix(new double[][] {{0}});
        check("One stop gives [0]",
                Algorithms.dynamicProgramming(g).equals(List.of(0)));
    }

    private static void testTwoStops() {
        Graph g = graphFromMatrix(new double[][] {
                {0, 7},
                {7, 0}
        });
        List<Integer> route = Algorithms.dynamicProgramming(g);
        check("Two stops gives [0, 1]", route.equals(List.of(0, 1)));
        checkClose("Two stops: round trip distance is 14", 14, routeLength(g, route));
    }

    private static void testFourStopSample() {
        Graph g = graphFromMatrix(new double[][] {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        });
        checkClose("Four-stop sample: best distance is 80", 80,
                routeLength(g, Algorithms.dynamicProgramming(g)));
    }

    private static void testRouteVisitsEveryStop() {
        Graph g = graphFromPoints(10, 42);
        check("Ten stops: starts at 0 and visits each stop once",
                validRoute(Algorithms.dynamicProgramming(g), 10));
    }

    private static void testMatchesBruteForce() {
        for (int n = 3; n <= 9; n++) {
            for (int seed = 1; seed <= 3; seed++) {
                Graph g = graphFromPoints(n, seed);
                double expected = routeLength(g, Algorithms.bruteForce(g));
                double actual = routeLength(g, Algorithms.dynamicProgramming(g));
                checkClose(n + " stops, random graph #" + seed
                        + ": matches brute force", expected, actual);
            }
        }
    }

    private static void testTwentyStops() {
        Graph g = graphFromPoints(20, 25);
        List<Integer> route = Algorithms.dynamicProgramming(g);
        check("Twenty stops: produces a complete valid route", validRoute(route, 20));
        check("Twenty stops: finite total distance",
                Double.isFinite(routeLength(g, route)));
    }

    private static void testTooManyStops() {
        Graph g = graphFromPoints(21, 1);
        boolean rejected = false;
        try {
            Algorithms.dynamicProgramming(g);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        check("More than 20 stops is rejected safely", rejected);
    }

    private static Graph graphFromMatrix(double[][] matrix) {
        List<Graph.Location> locations = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            locations.add(new Graph.Location("Stop " + i, 0, 0));
        }
        return new Graph(locations, matrix);
    }

    private static Graph graphFromPoints(int n, int seed) {
        Random rand = new Random(seed);
        List<Graph.Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Graph.Location("Stop " + i,
                    rand.nextDouble() * 100, rand.nextDouble() * 100));
        }
        return new Graph(locations, Graph.createDistanceMatrix(locations));
    }

    private static boolean validRoute(List<Integer> route, int n) {
        if (route.size() != n || route.get(0) != 0) {
            return false;
        }
        boolean[] seen = new boolean[n];
        for (int stop : route) {
            if (stop < 0 || stop >= n || seen[stop]) {
                return false;
            }
            seen[stop] = true;
        }
        return true;
    }

    // The last edge back to stop 0 is included in the distance.
    private static double routeLength(Graph g, List<Integer> route) {
        if (route.size() < 2) {
            return 0;
        }
        double total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += g.getDistance(route.get(i), route.get(i + 1));
        }
        return total + g.getDistance(route.get(route.size() - 1), 0);
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name);
        }
    }

    private static void checkClose(String name, double expected, double actual) {
        boolean equal = Math.abs(expected - actual) < 1e-8;
        check(name + (equal ? "" : " (expected " + expected + ", got " + actual + ")"),
                equal);
    }
}

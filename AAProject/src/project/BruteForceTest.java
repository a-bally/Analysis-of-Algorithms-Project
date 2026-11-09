package project;

import java.util.ArrayList;
import java.util.List;

// Tests for Algorithms.bruteForce.
// Run it: right-click this file -> Run As -> Java Application.
public class BruteForceTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testEmptyGraph();
        testOneStop();
        testTwoStops();
        testFourStopSample();
        testSquareOutOfOrder();
        testRouteVisitsEveryStopOnce();
        testMatchesSlowCheckOnRandomGraphs();

        System.out.println();
        System.out.println("Passed: " + passed + "   Failed: " + failed);
    }

    // ---------- the tests ----------

    // No stops -> empty route
    static void testEmptyGraph() {
        Graph g = graphFromMatrix(new double[0][0]);
        List<Integer> route = Algorithms.bruteForce(g);
        check("Empty graph gives empty route", route.isEmpty());
    }

    // One stop -> route is just [0], distance 0
    static void testOneStop() {
        Graph g = graphFromMatrix(new double[][] { { 0 } });
        List<Integer> route = Algorithms.bruteForce(g);
        check("One stop gives [0]", route.equals(List.of(0)));
    }

    // Two stops 7 km apart -> there and back = 14
    static void testTwoStops() {
        Graph g = graphFromMatrix(new double[][] {
            { 0, 7 },
            { 7, 0 }
        });
        List<Integer> route = Algorithms.bruteForce(g);
        checkClose("Two stops: distance is 14", 14.0, routeLength(g, route));
    }

    // The 4-stop sample from Main.java. Worked out by hand, the best round trip is 80
    // (0 -> 1 -> 3 -> 2 -> 0 = 10 + 25 + 30 + 15).
    static void testFourStopSample() {
        Graph g = graphFromMatrix(new double[][] {
            { 0, 10, 15, 20 },
            { 10, 0, 35, 25 },
            { 15, 35, 0, 30 },
            { 20, 25, 30, 0 }
        });
        List<Integer> route = Algorithms.bruteForce(g);
        checkClose("4-stop sample: best distance is 80", 80.0, routeLength(g, route));
    }

    // Four corners of a 1x1 square, numbered so that visiting them in order 0,1,2,3
    // zig-zags across the diagonals. The best route goes around the edge: distance 4.
    static void testSquareOutOfOrder() {
        double[][] corners = { { 0, 0 }, { 1, 1 }, { 1, 0 }, { 0, 1 } };
        Graph g = graphFromPoints(corners);
        List<Integer> route = Algorithms.bruteForce(g);
        checkClose("Square: best route goes around the edge (4)", 4.0, routeLength(g, route));
    }

    // The route must start at 0 and contain every stop exactly once
    static void testRouteVisitsEveryStopOnce() {
        Graph g = graphFromPoints(randomPoints(8, 42));
        List<Integer> route = Algorithms.bruteForce(g);

        boolean valid = route.size() == 8 && route.get(0) == 0;
        boolean[] seen = new boolean[8];
        for (int stop : route) {
            if (stop < 0 || stop >= 8 || seen[stop]) {
                valid = false;
                break;
            }
            seen[stop] = true;
        }
        check("8 stops: route starts at 0 and visits each stop once", valid);
    }

    // Compare against a second, simpler brute force (swap-based permutations)
    // on several random graphs. Both must find the same shortest distance.
    static void testMatchesSlowCheckOnRandomGraphs() {
        for (int seed = 1; seed <= 5; seed++) {
            Graph g = graphFromPoints(randomPoints(7, seed));
            double expected = shortestBySwapping(g);
            double actual = routeLength(g, Algorithms.bruteForce(g));
            checkClose("Random 7-stop graph #" + seed + " matches independent check", expected, actual);
        }
    }

    // ---------- helpers ----------

    // Total distance of a round trip that follows the route and returns to the start
    static double routeLength(Graph g, List<Integer> route) {
        if (route.size() < 2) {
            return 0;
        }
        double total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += g.getDistance(route.get(i), route.get(i + 1));
        }
        total += g.getDistance(route.get(route.size() - 1), route.get(0));
        return total;
    }

    // Builds a Graph from a hand-written distance table.
    // The stops get placeholder names and coordinates; only the distances matter here.
    static Graph graphFromMatrix(double[][] matrix) {
        List<Graph.Location> locations = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            locations.add(new Graph.Location("Stop " + i, 0, 0));
        }
        return new Graph(locations, matrix);
    }

    // Builds a Graph from (x, y) points, using the team's Graph.createDistanceMatrix
    static Graph graphFromPoints(double[][] points) {
        List<Graph.Location> locations = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            locations.add(new Graph.Location("Stop " + i, points[i][0], points[i][1]));
        }
        return new Graph(locations, Graph.createDistanceMatrix(locations));
    }

    // Same seed -> same points every run, so results are repeatable
    static double[][] randomPoints(int n, long seed) {
        java.util.Random rand = new java.util.Random(seed);
        double[][] pts = new double[n][2];
        for (int i = 0; i < n; i++) {
            pts[i][0] = rand.nextDouble() * 100;
            pts[i][1] = rand.nextDouble() * 100;
        }
        return pts;
    }

    // A second brute force written a different way, used only to double-check the real one
    static double shortestBySwapping(Graph g) {
        int n = g.size();
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            order.add(i);
        }
        double[] best = { Double.MAX_VALUE };
        permute(g, order, 1, best);
        return best[0];
    }

    static void permute(Graph g, List<Integer> order, int k, double[] best) {
        if (k == order.size()) {
            best[0] = Math.min(best[0], routeLength(g, order));
            return;
        }
        for (int i = k; i < order.size(); i++) {
            java.util.Collections.swap(order, k, i);
            permute(g, order, k + 1, best);
            java.util.Collections.swap(order, k, i);
        }
    }

    static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name);
        }
    }

    static void checkClose(String name, double expected, double actual) {
        boolean ok = Math.abs(expected - actual) < 1e-9;
        check(name + (ok ? "" : "  (expected " + expected + ", got " + actual + ")"), ok);
    }
}
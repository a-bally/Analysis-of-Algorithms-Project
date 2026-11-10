package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 2-opt heuristic: starts from a route and keeps reversing a section of it
// whenever that makes the round trip shorter, until no reversal helps.
// The route keeps stop 0 first, like the other algorithms.
public class TwoOpt {

    // Improve a given starting route (for example, the greedy route)
    public static List<Integer> twoOpt(Graph g, List<Integer> startRoute) {
        List<Integer> route = new ArrayList<>(startRoute);
        int n = route.size();
        if (n < 4) {
            return route; // nothing to improve with 3 or fewer stops
        }

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    int a = route.get(i - 1);
                    int b = route.get(i);
                    int c = route.get(j);
                    int d = route.get((j + 1) % n);

                    // Change in total distance if we reverse route[i..j]
                    double delta = g.getDistance(a, c) + g.getDistance(b, d)
                                 - g.getDistance(a, b) - g.getDistance(c, d);

                    if (delta < -1e-9) {
                        Collections.reverse(route.subList(i, j + 1));
                        improved = true;
                    }
                }
            }
        }
        return route;
    }

    // Convenience version: starts from the stops in order 0, 1, 2, ...
    public static List<Integer> twoOpt(Graph g) {
        List<Integer> start = new ArrayList<>();
        for (int i = 0; i < g.size(); i++) {
            start.add(i);
        }
        return twoOpt(g, start);
    }
}

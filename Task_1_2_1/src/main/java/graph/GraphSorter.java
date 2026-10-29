package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides topological sorting algorithms for directed graphs.
 */
public class GraphSorter {
    private Map<Integer, Integer> visited;
    private Graph graph;
    private List<Integer> stack;

    /**
     * Sorts the vertices using Kahn's algorithm.
     *
     * @param graph the graph to sort
     * @return a topological ordering, or {@code null} if the graph contains a cycle
     */
    public static List<Integer> kahnSort(Graph graph) {
        Map<Integer, Integer> degree = graph.getVertices().stream().collect(
                Collectors.toMap(key -> key, value -> 0));

        for (Integer key : graph.getVertices()) {
            List<Integer> neighbours = graph.getNeighbours(key);
            for (Integer neighbour : neighbours) {
                int count = degree.get(neighbour) + 1;
                degree.put(neighbour, count);
            }
        }

        Queue<Integer> queue = new ArrayDeque<>(
                degree.keySet().stream()
                        .filter(key -> degree.get(key) == 0)
                        .toList());
        List<Integer> result = new ArrayList<>();
        Set<Integer> inResult = new HashSet<>();

        while (queue.size() > 0) {
            int key = queue.poll();
            result.add(key);
            inResult.add(key);

            for (Integer neighbour : graph.getNeighbours(key)) {
                int newDegree = degree.get(neighbour) - 1;
                degree.put(neighbour, newDegree);
                if (newDegree == 0) {
                    queue.offer(neighbour);
                }
            }
        }

        if (inResult.size() < degree.size()) {
            return null;
        }
        return result;
    }

    /**
     * Sorts the vertices using depth-first search.
     *
     * @param graph the graph to sort
     * @return a topological ordering, or {@code null} if the graph contains a cycle
     */
    public List<Integer> dfsSort(Graph graph) {
        this.graph = graph;
        stack = new ArrayList<>();
        visited = graph.getVertices().stream().collect(
                Collectors.toMap(key -> key, value -> 0));

        for (Integer key : graph.getVertices()) {
            if (visited.get(key) == 0) {
                try {
                    dfs(key);
                } catch (IllegalStateException exception) {
                    return null;
                }
            }
        }

        Collections.reverse(stack);
        return stack;
    }

    /**
     * Performs a depth-first search starting from the specified vertex.
     *
     * @param key the key of the vertex to visit
     * @throws IllegalStateException if a cycle is detected
     */
    public void dfs(int key) {
        visited.put(key, 1);

        for (Integer neighbour : graph.getNeighbours(key)) {
            if (visited.get(neighbour) == 0) {
                dfs(neighbour);
            } else if (visited.get(neighbour) == 1) {
                throw new IllegalStateException();
            }
        }

        stack.add(key);
        visited.put(key, 2);
    }
}
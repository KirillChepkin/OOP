package graph.adjacency.lists;

import graph.BaseGraph;
import graph.Graph;
import graph.Vertex;

import java.util.*;

/**
 * Graph implementation using adjacency lists.
 */
public class AdjacencyListsGraph extends BaseGraph implements Graph {
    private final Map<Integer, List<Integer>> vertexMap = new HashMap<>();

    private long nextCode = 0;

    private final PriorityQueue<Integer> freeCodes = new PriorityQueue<>();

    @Override
    public void addVertex(int key) {
        if (keyToCode.containsKey(key)) {
            throw new IllegalArgumentException("Key " + key + " is already in the graph.");
        }

        int code;
        if (!freeCodes.isEmpty()) {
            code = freeCodes.poll();
        } else {
            if (nextCode > Integer.MAX_VALUE) {
                throw new IllegalStateException("No more vertex codes available.");
            }
            code = (int) nextCode++;
        }

        keyToCode.put(key, code);
        codeToKey.put(code, key);
        vertexMap.put(code, new ArrayList<>());
    }

    @Override
    public void deleteVertex(int key) {
        if (!vertexExists(key)) {
            throw new NoSuchElementException(
                    "Cannot delete requested vertex with the key " + key
                            + " from the graph. It's not in it.");
        }
        int code = keyToCode.get(key);
        keyToCode.remove(key);
        codeToKey.remove(code);
        vertexMap.remove(code);
        for (List<Integer> list : vertexMap.values()) {
            list.remove(Integer.valueOf(code));
        }
        freeCodes.add(code);
    }

    @Override
    public void addEdge(int start, int end) {
        if (!vertexExists(start) || !vertexExists(end)) {
            throw new NoSuchElementException(
                    "Cannot add the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexMap.get(keyToCode.get(start)).add(keyToCode.get(end));
    }

    @Override
    public void deleteEdge(int start, int end) {
        if (!vertexExists(start) || !vertexExists(end)) {
            throw new NoSuchElementException(
                    "Cannot delete the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexMap.get(keyToCode.get(start)).remove(keyToCode.get(end));
    }

    @Override
    public boolean edgeExists(int start, int end) {
        return keyToCode.containsKey(start)
                && keyToCode.containsKey(end)
                && vertexMap.get(keyToCode.get(start)).contains(keyToCode.get(end));
    }

    @Override
    public List<Integer> getNeighbours(int key) {
        if (!vertexExists(key)) {
            throw new NoSuchElementException(
                    "Cannot find Neighbours for the vertex with index "
                    + key + " because is not in the graph.");
        }

        int code = keyToCode.get(key);
        List<Integer> list = new ArrayList<>();
        for (int vertexCode : vertexMap.get(code)) {
            list.add(codeToKey.get(vertexCode));
        }
        return list;
    }
}
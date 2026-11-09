package graph.adjacency.lists;

import graph.BaseGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Represents a directed graph using adjacency lists of internal vertex codes.
 *
 * <p>Public vertex keys are mapped to internal codes by {@link BaseGraph}.
 * Deleted vertex codes are reused when new vertices are added.</p>
 */
public class AdjacencyListsGraph extends BaseGraph {

    private final Map<Integer, List<Integer>> vertexMap = new HashMap<>();
    private final PriorityQueue<Integer> freeCodes = new PriorityQueue<>();
    private long nextCode = 0;

    /**
     * Adds a vertex with the specified public key.
     *
     * @param key the public key of the vertex
     * @return the internal code assigned to the vertex
     * @throws IllegalArgumentException if a vertex with this key already exists
     * @throws IllegalStateException if no more internal vertex codes are available
     */
    @Override
    public int addVertex(int key) {
        if (vertexExists(key)) {
            throw new IllegalArgumentException("Duplicate vertex key: " + key);
        }

        int code;

        if (!freeCodes.isEmpty()) {
            code = freeCodes.remove();
        } else {
            if (nextCode > Integer.MAX_VALUE) {
                throw new IllegalStateException("No more vertex codes available");
            }

            code = (int) nextCode++;
        }

        registerVertex(key, code);
        vertexMap.put(code, new ArrayList<>());

        return code;
    }

    /**
     * Removes a vertex and all edges connected to it.
     *
     * @param key the public key of the vertex to remove
     * @throws java.util.NoSuchElementException if the vertex does not exist
     */
    @Override
    public void deleteVertex(int key) {
        int code = requireCode(key);

        vertexMap.remove(code);

        for (List<Integer> neighbours : vertexMap.values()) {
            // Remove by value, not by list index.
            neighbours.remove(Integer.valueOf(code));
        }

        unregisterVertex(key);
        freeCodes.add(code);
    }

    /**
     * Adds a directed edge between two vertices.
     *
     * <p>If the edge already exists, the graph remains unchanged.</p>
     *
     * @param start the public key of the starting vertex
     * @param end the public key of the ending vertex
     * @throws java.util.NoSuchElementException if either vertex does not exist
     */
    @Override
    public void addEdge(int start, int end) {
        int startCode = requireCode(start);
        int endCode = requireCode(end);
        List<Integer> neighbours = vertexMap.get(startCode);

        if (!neighbours.contains(endCode)) {
            neighbours.add(endCode);
        }
    }

    /**
     * Removes a directed edge between two vertices.
     *
     * <p>If the edge does not exist, the graph remains unchanged.</p>
     *
     * @param start the public key of the starting vertex
     * @param end the public key of the ending vertex
     * @throws java.util.NoSuchElementException if either vertex does not exist
     */
    @Override
    public void deleteEdge(int start, int end) {
        int startCode = requireCode(start);
        int endCode = requireCode(end);

        vertexMap.get(startCode).remove(Integer.valueOf(endCode));
    }

    /**
     * Checks whether a directed edge exists between two vertices.
     *
     * @param start the public key of the starting vertex
     * @param end the public key of the ending vertex
     * @return {@code true} if both vertices exist and the edge exists;
     *         otherwise, {@code false}
     */
    @Override
    public boolean edgeExists(int start, int end) {
        Integer startCode = keyToCode.get(start);
        Integer endCode = keyToCode.get(end);

        return startCode != null && endCode != null
                && vertexMap.get(startCode).contains(endCode);
    }

    /**
     * Returns the outgoing neighbors of a vertex.
     *
     * @param key the public key of the vertex
     * @return a list of public keys of outgoing neighbors
     * @throws java.util.NoSuchElementException if the vertex does not exist
     */
    @Override
    public List<Integer> getNeighbours(int key) {
        int code = requireCode(key);
        List<Integer> neighbours = new ArrayList<>();

        for (int adjacentCode : vertexMap.get(code)) {
            neighbours.add(codeToKey.get(adjacentCode));
        }

        return neighbours;
    }
}
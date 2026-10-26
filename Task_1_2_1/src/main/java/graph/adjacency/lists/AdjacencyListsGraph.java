package graph.adjacency.lists;

import graph.BaseGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** Directed graph stored as lists of internal neighbouring vertex codes. */
public class AdjacencyListsGraph extends BaseGraph {
    private final Map<Integer, List<Integer>> vertexMap = new HashMap<>();
    private final PriorityQueue<Integer> freeCodes = new PriorityQueue<>();
    private long nextCode = 0;

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

    @Override
    public void deleteVertex(int key) {
        int code = requireCode(key);
        vertexMap.remove(code);
        for (List<Integer> neighbours : vertexMap.values()) {
            neighbours.remove(Integer.valueOf(code)); // remove by VALUE, not by list index
        }
        unregisterVertex(key);
        freeCodes.add(code);
    }

    @Override
    public void addEdge(int start, int end) {
        int startCode = requireCode(start);
        int endCode = requireCode(end);
        List<Integer> neighbours = vertexMap.get(startCode);
        if (!neighbours.contains(endCode)) {
            neighbours.add(endCode);
        }
    }

    @Override
    public void deleteEdge(int start, int end) {
        int startCode = requireCode(start);
        int endCode = requireCode(end);
        vertexMap.get(startCode).remove(Integer.valueOf(endCode));
    }

    @Override
    public boolean edgeExists(int start, int end) {
        Integer startCode = keyToCode.get(start);
        Integer endCode = keyToCode.get(end);
        return startCode != null && endCode != null
                && vertexMap.get(startCode).contains(endCode);
    }

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

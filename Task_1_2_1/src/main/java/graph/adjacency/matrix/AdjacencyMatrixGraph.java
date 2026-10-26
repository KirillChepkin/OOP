package graph.adjacency.matrix;

import graph.BaseGraph;
import graph.Graph;
import graph.Vertex;
import java.util.*;

/**
 * Graph implementation powered by adjacency matrix.
 */
public class AdjacencyMatrixGraph extends BaseGraph implements Graph {
    private final List<List<Boolean>> matrix = new ArrayList<>();

    public List<List<Boolean>> getMatrix() {
        return matrix;
    }

    @Override
    public void addVertex(int key) {
        int code = matrix.size() - 1;
        keyToCode.put(key, code);
        codeToKey.put(code, key);
        matrix.add(new ArrayList<>(Collections.nCopies(matrix.size(), false)));
        for (List<Boolean> list : matrix) {
            list.add(false);
        }
    }

    @Override
    public void deleteVertex(int code) {

        if (code < 0 || vertices.size() <= code) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete requested vertex with the index" + code
                            + ". It's not in the graph.");
        }
        vertices.remove(code);
        for (int i = code; i < vertices.size(); i++) {
            vertices.get(i).setCode(i);
        }
        matrix.remove(code);
        for (List<Boolean> list : matrix) {
            list.remove(code);
        }
    }

    @Override
    public Vertex<T> getVertex(int code) {
        if (code < 0 || vertices.size() <= code) {
            throw new IndexOutOfBoundsException(
                    "Cannot return requested vertex with the index" + code
                    + ". It's not in the graph.");
        }
        return vertices.get(code);
    }

    @Override
    public void addEdge(int start, int end) {
        if (start < 0 || end < 0 || vertices.size() <= start || vertices.size() <= end) {
            throw new IndexOutOfBoundsException(
                    "Cannot add the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        matrix.get(start).set(end, true);
    }

    @Override
    public void deleteEdge(int start, int end) {
        if (start < 0 || end < 0 || vertices.size() <= start || vertices.size() <= end) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        matrix.get(start).set(end, false);
    }

    @Override
    public boolean edgeExists(int start, int end) {
        return keyToCode.containsKey(start) &&  matrix
    }

    @Override
    public List<Vertex<T>> getNeighbours(int code) {
        if (code < 0 || vertices.size() <= code) {
            throw new IndexOutOfBoundsException(
                    "Cannot find neighbours of requested vertex with the index" + code
                            + ". It's not in the graph.");
        }
        List<Vertex<T>> list = new ArrayList<>();
        for (int i = 0; i < matrix.get(code).size(); i++) {
            if (matrix.get(code).get(i)) {
                list.add(vertices.get(i));
            }
        }
        return list;
    }
}

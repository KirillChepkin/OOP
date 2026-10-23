package graph.adjacency.matrix;

import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final List<List<Boolean>> matrix = new ArrayList<>();

    public List<List<Boolean>> getMatrix() {
        return matrix;
    }

    private final List<Vertex<T>> vertices = new ArrayList<>();

    public List<Vertex<T>> getVertices() {
        return vertices;
    }

    public int addVertex(T vertex) {
        matrix.add(new ArrayList<>(Collections.nCopies(matrix.size(), false)));
        for (List<Boolean> list : matrix) {
            list.add(false);
        }
        return matrix.size() - 1;
    }

    @Override
    public void deleteVertex(int code) {

    }

    public Vertex<T> getVertex(int code) {
        if (vertices.size() <= code) {
            throw new IndexOutOfBoundsException(
                    "Cannot return requested vertex with the index" + code
                    + ". It's not in the graph.");
        }
        return vertices.get(code);
    }

    public void addEdge(int start, int end) {
        if (vertices.size() <= start && vertices.size() <= end) {
            throw new IndexOutOfBoundsException(
                    "Cannot add the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        matrix.get(start).set(end, true);
    }

    public List<Vertex<T>> getNeighbours(int code) {
        if (vertices.size() <= code) {
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

    public void readGraph() {

    }
}

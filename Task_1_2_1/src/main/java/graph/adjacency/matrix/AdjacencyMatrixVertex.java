package graph.adjacency.matrix;

import graph.Vertex;

/**
 * Vertex.
 *
 * @param <T> value type.
 */
public class AdjacencyMatrixVertex<T> extends Vertex<T> {
    public AdjacencyMatrixVertex(int code, T content) {
        super(code, content);
    }
}

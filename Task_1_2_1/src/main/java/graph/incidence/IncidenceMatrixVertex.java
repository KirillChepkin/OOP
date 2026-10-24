package graph.incidence;

import graph.Vertex;

/**
 * Class represents vertex of the IncidenceMatrixGraph.
 *
 * @param <T> generic type of the value stored by vertex.
 */
public class IncidenceMatrixVertex<T> extends Vertex<T> {
    public IncidenceMatrixVertex(int code, T content) {
        super(code, content);
    }
}
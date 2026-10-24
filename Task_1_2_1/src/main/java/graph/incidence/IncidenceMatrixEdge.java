package graph.incidence;

/**
 * This class represents an edge of a graph.
 *
 * @param <T> edge has references to both its vertices of generic type.
 */
public class IncidenceMatrixEdge<T> {
    private int code;

    private final IncidenceMatrixVertex<T> startVertex;
    private final IncidenceMatrixVertex<T> endVertex;

    /**
     * Constructor for edge representation.
     *
     * @param code code that was assigned to edge by the graph that stores it.
     *
     * @param startVertex start vertex of the edge.
     *
     * @param endVertex end vertex of the edge.
     */
    public IncidenceMatrixEdge(
            int code,
            IncidenceMatrixVertex<T> startVertex,
            IncidenceMatrixVertex<T> endVertex) {
        this.code = code;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public int getCode() {
        return code;
    }

    void setCode(int code) {
        this.code = code;
    }

    public IncidenceMatrixVertex<T> getStartVertex() {
        return startVertex;
    }

    public IncidenceMatrixVertex<T> getEndVertex() {
        return endVertex;
    }
}
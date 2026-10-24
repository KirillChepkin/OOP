package graph.incidence;

public class IncidenceMatrixEdge<T> {
    private int code;

    private final IncidenceMatrixVertex<T> startVertex;
    private final IncidenceMatrixVertex<T> endVertex;

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
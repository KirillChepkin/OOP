package graph.incidence;

/** A directed edge identified by its column and its endpoint vertex keys. */
public final class IncidenceMatrixEdge {
    private int code;
    private final int startKey;
    private final int endKey;

    public IncidenceMatrixEdge(int code, int startKey, int endKey) {
        this.code = code;
        this.startKey = startKey;
        this.endKey = endKey;
    }

    public int getCode() {
        return code;
    }

    void setCode(int code) {
        this.code = code;
    }

    public int getStartKey() {
        return startKey;
    }

    public int getEndKey() {
        return endKey;
    }
}

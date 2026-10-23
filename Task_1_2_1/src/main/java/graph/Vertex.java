package graph;

import java.util.HashSet;

public abstract class Vertex<T> {
    private final T content;
    private final int code;

    public T getContent() {
        return content;
    }

    public Vertex(int code, T content) {
        this.content = content;
        this.code = code;
    }
}

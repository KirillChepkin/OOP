package graph;

import java.util.HashSet;

public abstract class Vertex<T> {
    private final T content;

    public T getContent() {
        return content;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    public Vertex(int code, T content) {
        this.content = content;
        this.code = code;
    }
}

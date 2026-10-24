package graph;

import java.util.HashSet;

public abstract class Vertex<T> {
    private final T content;

    public T getContent() {
        return content;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Vertex(int code, T content) {
        this.content = content;
        this.code = code;
    }
}

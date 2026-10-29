package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Provides common vertex management functionality for graph implementations.
 */
public abstract class BaseGraph implements Graph {

    protected final Map<Integer, Integer> keyToCode = new HashMap<>();
    protected final Map<Integer, Integer> codeToKey = new HashMap<>();

    /**
     * Returns the public keys of all vertices in the graph.
     *
     * @return a list of vertex keys
     */
    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(keyToCode.keySet());
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    @Override
    public int getVertexCount() {
        return keyToCode.size();
    }

    /**
     * Checks whether a vertex with the specified key exists.
     *
     * @param key the public key of the vertex
     * @return {@code true} if the vertex exists; otherwise, {@code false}
     */
    @Override
    public boolean vertexExists(int key) {
        return keyToCode.containsKey(key);
    }

    /**
     * Returns the internal code of an existing vertex.
     *
     * @param key the public key of the vertex
     * @return the internal vertex code
     * @throws NoSuchElementException if the vertex does not exist
     */
    protected final int requireCode(int key) {
        Integer code = keyToCode.get(key);

        if (code == null) {
            throw new NoSuchElementException(
                    "Vertex key " + key + " does not exist");
        }

        return code;
    }

    /**
     * Registers a vertex and associates its public key with an internal code.
     *
     * @param key the public key of the vertex
     * @param code the internal code of the vertex
     * @throws IllegalArgumentException if the key already exists
     */
    protected final void registerVertex(int key, int code) {
        if (vertexExists(key)) {
            throw new IllegalArgumentException("Duplicate vertex key: " + key);
        }

        keyToCode.put(key, code);
        codeToKey.put(code, key);
    }

    /**
     * Unregisters a vertex without compacting the remaining internal codes.
     * Intended for sparse-code representations such as adjacency lists.
     *
     * @param key the public key of the vertex to remove
     */
    protected final void unregisterVertex(int key) {
        int code = requireCode(key);

        keyToCode.remove(key);
        codeToKey.remove(code);
    }

    /**
     * Unregisters a vertex and shifts higher internal codes down by one.
     * Intended for dense-code representations such as adjacency matrices.
     *
     * @param key the public key of the vertex to remove
     */
    protected final void unregisterAndCompact(int key) {
        int removedCode = requireCode(key);
        int oldCount = keyToCode.size();

        keyToCode.remove(key);
        codeToKey.remove(removedCode);

        for (int oldCode = removedCode + 1; oldCode < oldCount; oldCode++) {
            int movedKey = codeToKey.remove(oldCode);

            codeToKey.put(oldCode - 1, movedKey);
            keyToCode.put(movedKey, oldCode - 1);
        }
    }
}
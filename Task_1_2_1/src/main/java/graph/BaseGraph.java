package graph;

import java.util.*;

public abstract class BaseGraph implements Graph {
    protected final Map<Integer, Integer> keyToCode = new HashMap<>();
    protected final Map<Integer, Integer> codeToKey = new HashMap<>();

    public List<Integer> getVertices() {
        return new ArrayList<>(keyToCode.keySet());
    }

    public int getVertexCount() {
        return keyToCode.size();
    }

    public boolean vertexExists(int key) {
        return keyToCode.containsKey(key);
    }

    protected final int requireCode(int key) {
        Integer code = keyToCode.get(key);
        if (code == null) {
            throw new NoSuchElementException("Vertex key " + key + " does not exist");
        }
        return code;
    }

    protected final void registerVertex(int key, int code) {
        if (vertexExists(key)) {
            throw new IllegalArgumentException("Duplicate vertex key: " + key);
        }
        keyToCode.put(key, code);
        codeToKey.put(code, key);
    }

    /** For sparse-code representations such as adjacency lists. */
    protected final void unregisterVertex(int key) {
        int code = requireCode(key);
        keyToCode.remove(key);
        codeToKey.remove(code);
    }

    /** For dense-code representations: remove key, then shift higher codes down. */
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

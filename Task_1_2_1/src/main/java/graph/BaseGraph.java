package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseGraph {
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
}

package graph;

import graph.incidence.IncidenceMatrixGraph;
import graph.adjacency.lists.AdjacencyListsGraph;
import graph.adjacency.matrix.AdjacencyMatrixGraph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Deterministic mixed operations cross-check all three implementations. */
class RandomizedConsistencyTest {
    @Test
    void randomizedOperationsAgreeWithReferenceModel() {
        List<Graph> graphs = List.of(
                new AdjacencyListsGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph());
        Set<Integer> keys = new HashSet<>();
        Set<Long> edges = new HashSet<>();
        Random random = new Random(20261026L);

        for (int step = 0; step < 400; step++) {
            int start = random.nextInt(25) - 12;
            int end = random.nextInt(25) - 12;
            int operation = random.nextInt(4);
            if (operation == 0) {
                if (keys.add(start)) {
                    for (Graph graph : graphs) {
                        graph.addVertex(start);
                    }
                } else {
                    for (Graph graph : graphs) {
                        assertThrows(IllegalArgumentException.class,
                                () -> graph.addVertex(start));
                    }
                }
            } else if (operation == 1) {
                if (keys.remove(start)) {
                    edges.removeIf(edge -> (int) (edge >> 32) == start
                            || (int) (long) edge == start);
                    for (Graph graph : graphs) {
                        graph.deleteVertex(start);
                    }
                } else {
                    for (Graph graph : graphs) {
                        assertThrows(NoSuchElementException.class,
                                () -> graph.deleteVertex(start));
                    }
                }
            } else if (operation == 2) {
                if (keys.contains(start) && keys.contains(end)) {
                    edges.add(edgeId(start, end));
                    for (Graph graph : graphs) {
                        graph.addEdge(start, end);
                    }
                } else {
                    for (Graph graph : graphs) {
                        assertThrows(NoSuchElementException.class,
                                () -> graph.addEdge(start, end));
                    }
                }
            } else {
                if (keys.contains(start) && keys.contains(end)) {
                    edges.remove(edgeId(start, end));
                    for (Graph graph : graphs) {
                        graph.deleteEdge(start, end);
                    }
                } else {
                    for (Graph graph : graphs) {
                        assertThrows(NoSuchElementException.class,
                                () -> graph.deleteEdge(start, end));
                    }
                }
            }

            List<Integer> expectedKeys = new ArrayList<>(keys);
            expectedKeys.sort(Integer::compareTo);
            for (Graph graph : graphs) {
                assertEquals(new HashSet<>(expectedKeys), new HashSet<>(graph.getVertices()));
                assertEquals(keys.size(), graph.getVertexCount());
                for (int key : keys) {
                    Set<Integer> expectedNeighbours = new HashSet<>();
                    for (int other : keys) {
                        boolean expected = edges.contains(edgeId(key, other));
                        if (expected) {
                            expectedNeighbours.add(other);
                        }
                        assertEquals(expected, graph.edgeExists(key, other));
                    }
                    assertEquals(expectedNeighbours, new HashSet<>(graph.getNeighbours(key)));
                    assertEquals(expectedNeighbours.size(), graph.getNeighbours(key).size());
                }
                assertFalse(graph.edgeExists(123456, 654321));
            }
        }
    }

    private static long edgeId(int start, int end) {
        return ((long) start << 32) | (end & 0xffffffffL);
    }
}

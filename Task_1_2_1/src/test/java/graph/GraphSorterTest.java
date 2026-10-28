package graph;

import graph.adjacency.lists.AdjacencyListsGraph;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphSorterTest {

    @Test
    void emptyGraphReturnsEmptyOrder() {
        Graph graph = new AdjacencyListsGraph();

        assertEquals(List.of(), GraphSorter.kahnSort(graph));
        assertEquals(List.of(), new GraphSorter().dfsSort(graph));
    }

    @Test
    void singleVertexReturnsValidOrder() {
        Graph graph = new AdjacencyListsGraph();
        graph.addVertex(42);

        assertValidTopologicalOrder(graph, GraphSorter.kahnSort(graph));
        assertValidTopologicalOrder(graph, new GraphSorter().dfsSort(graph));
    }

    @Test
    void chainGraphReturnsValidOrder() {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addVertex(40);

        graph.addEdge(10, 20);
        graph.addEdge(20, 30);
        graph.addEdge(30, 40);

        assertValidTopologicalOrder(graph, GraphSorter.kahnSort(graph));
        assertValidTopologicalOrder(graph, new GraphSorter().dfsSort(graph));
    }

    @Test
    void branchingGraphReturnsValidOrder() {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(-10);
        graph.addVertex(0);
        graph.addVertex(7);
        graph.addVertex(20);
        graph.addVertex(100);

        graph.addEdge(-10, 0);
        graph.addEdge(-10, 7);
        graph.addEdge(0, 20);
        graph.addEdge(7, 20);

        // Vertex 100 is isolated.

        assertValidTopologicalOrder(graph, GraphSorter.kahnSort(graph));
        assertValidTopologicalOrder(graph, new GraphSorter().dfsSort(graph));
    }

    @Test
    void graphWithMultipleComponentsReturnsValidOrder() {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);

        // Vertices 1, 2, 3 form one component.
        // Vertices 4, 5 form another.
        // Vertex 6 is isolated.

        assertValidTopologicalOrder(graph, GraphSorter.kahnSort(graph));
        assertValidTopologicalOrder(graph, new GraphSorter().dfsSort(graph));
    }

    @Test
    void graphWithCycleReturnsNull() {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        assertNull(GraphSorter.kahnSort(graph));
        assertNull(new GraphSorter().dfsSort(graph));
    }

    @Test
    void graphWithSelfLoopReturnsNull() {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 1);
        graph.addEdge(1, 2);

        assertNull(GraphSorter.kahnSort(graph));
        assertNull(new GraphSorter().dfsSort(graph));
    }

    @Test
    void dfsSorterCanBeReusedForDifferentGraphs() {
        GraphSorter sorter = new GraphSorter();

        Graph firstGraph = new AdjacencyListsGraph();
        firstGraph.addVertex(1);
        firstGraph.addVertex(2);
        firstGraph.addEdge(1, 2);

        assertValidTopologicalOrder(firstGraph, sorter.dfsSort(firstGraph));

        Graph secondGraph = new AdjacencyListsGraph();
        secondGraph.addVertex(10);
        secondGraph.addVertex(20);
        secondGraph.addVertex(30);
        secondGraph.addEdge(30, 20);
        secondGraph.addEdge(20, 10);

        assertValidTopologicalOrder(secondGraph, sorter.dfsSort(secondGraph));
    }

    private void assertValidTopologicalOrder(
            Graph graph, List<Integer> order) {

        assertNotNull(order, "A DAG must produce a topological ordering.");

        List<Integer> vertices = graph.getVertices();

        assertEquals(
                vertices.size(),
                order.size(),
                "The ordering must contain every vertex exactly once."
        );

        assertEquals(
                new HashSet<>(vertices),
                new HashSet<>(order),
                "The ordering must contain exactly the graph's vertices."
        );

        Map<Integer, Integer> positions = new HashMap<>();

        for (int i = 0; i < order.size(); i++) {
            positions.put(order.get(i), i);
        }

        for (Integer vertex : vertices) {
            for (Integer neighbour : graph.getNeighbours(vertex)) {
                assertTrue(
                        positions.get(vertex) < positions.get(neighbour),
                        "Vertex " + vertex
                                + " must appear before its neighbour "
                                + neighbour + "."
                );
            }
        }
    }
}
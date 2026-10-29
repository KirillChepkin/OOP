package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.adjacency.lists.AdjacencyListsGraph;
import org.junit.jupiter.api.Test;

/**
 * Tests reading and writing directed graphs using {@link GraphIO}.
 */
class GraphIOTest {

    @Test
    void printGraphWritesSortedVerticesAndEdges() throws IOException {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(3);
        graph.addVertex(-1);
        graph.addVertex(2);
        graph.addVertex(10);

        graph.addEdge(3, 2);
        graph.addEdge(-1, 3);
        graph.addEdge(2, 3);

        String expected = """
                4
                -1
                2
                3
                10
                3
                -1,3
                2,3
                3,2
                """;

        assertEquals(expected, writeGraph(graph));
    }

    @Test
    void readGraphRestoresVerticesAndEdges() throws IOException {
        String input = """
                4
                -2147483648
                7
                2147483647
                0
                3
                -2147483648,7
                7,2147483647
                2147483647,2147483647
                """;

        Graph graph = new AdjacencyListsGraph();

        GraphIO.readGraph(graph, new BufferedReader(new StringReader(input)));

        assertEquals(
                Set.of(Integer.MIN_VALUE, 7, Integer.MAX_VALUE, 0),
                new HashSet<>(graph.getVertices()));

        assertEquals(
                Set.of(
                        new GraphIO.Edge(Integer.MIN_VALUE, 7),
                        new GraphIO.Edge(7, Integer.MAX_VALUE),
                        new GraphIO.Edge(Integer.MAX_VALUE, Integer.MAX_VALUE)),
                getEdges(graph));
    }

    @Test
    void readGraphReplacesExistingGraph() throws IOException {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(100);
        graph.addVertex(200);
        graph.addEdge(100, 200);

        String input = """
                3
                1
                2
                3
                2
                1,2
                2,3
                """;

        GraphIO.readGraph(graph, new BufferedReader(new StringReader(input)));

        assertEquals(Set.of(1, 2, 3), new HashSet<>(graph.getVertices()));
        assertEquals(
                Set.of(
                        new GraphIO.Edge(1, 2),
                        new GraphIO.Edge(2, 3)),
                getEdges(graph));

        assertFalse(graph.vertexExists(100));
        assertFalse(graph.vertexExists(200));
    }

    @Test
    void emptyGraphCanBePrintedAndRead() throws IOException {
        Graph graph = new AdjacencyListsGraph();

        assertEquals("0\n0\n", writeGraph(graph));

        Graph restored = new AdjacencyListsGraph();
        restored.addVertex(42);

        GraphIO.readGraph(
                restored,
                new BufferedReader(new StringReader("0\n0\n")));

        assertEquals(List.of(), restored.getVertices());
        assertEquals(0, restored.getVertexCount());
    }

    @Test
    void roundTripPreservesGraph() throws IOException {
        Graph original = new AdjacencyListsGraph();

        original.addVertex(50);
        original.addVertex(-10);
        original.addVertex(0);
        original.addVertex(999);

        original.addEdge(50, -10);
        original.addEdge(-10, 0);
        original.addEdge(0, 50);
        original.addEdge(50, 50);

        String serialized = writeGraph(original);
        Graph restored = new AdjacencyListsGraph();

        GraphIO.readGraph(
                restored,
                new BufferedReader(new StringReader(serialized)));

        assertEquals(
                new HashSet<>(original.getVertices()),
                new HashSet<>(restored.getVertices()));
        assertEquals(getEdges(original), getEdges(restored));
    }

    @Test
    void readGraphDoesNotConsumeInputAfterDeclaredGraph() throws IOException {
        String input = """
                2
                1
                2
                1
                1,2
                NEXT
                """;

        BufferedReader reader = new BufferedReader(new StringReader(input));
        Graph graph = new AdjacencyListsGraph();

        GraphIO.readGraph(graph, reader);

        assertEquals(Set.of(1, 2), new HashSet<>(graph.getVertices()));
        assertEquals("NEXT", reader.readLine());
    }

    @Test
    void readGraphRejectsInvalidInput() {
        List<String> invalidInputs = List.of(
                "",
                "-1\n",
                "abc\n",
                "1\n",
                "2\n1\n1\n",
                "0\n-1\n",
                "1\n1\n1\n",
                "1\n1\n1\n1-1\n",
                "1\n1\n1\n,1\n",
                "1\n1\n1\n1,\n",
                "1\n1\n1\n1,1,1\n",
                "1\n1\n1\n2,1\n",
                "2\n1\n2\n2\n1,2\n1,2\n"
        );

        for (String input : invalidInputs) {
            assertThrows(
                    IOException.class,
                    () -> GraphIO.readGraph(
                            new AdjacencyListsGraph(),
                            new BufferedReader(new StringReader(input))),
                    "Expected IOException for input: " + input);
        }
    }

    @Test
    void invalidInputDoesNotModifyExistingGraph() {
        Graph graph = new AdjacencyListsGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addEdge(10, 20);

        Set<Integer> originalVertices = new HashSet<>(graph.getVertices());
        Set<GraphIO.Edge> originalEdges = getEdges(graph);

        String invalidInput = """
                2
                1
                1
                0
                """;

        assertThrows(
                IOException.class,
                () -> GraphIO.readGraph(
                        graph,
                        new BufferedReader(new StringReader(invalidInput))));

        assertEquals(originalVertices, new HashSet<>(graph.getVertices()));
        assertEquals(originalEdges, getEdges(graph));
    }

    private String writeGraph(Graph graph) throws IOException {
        StringWriter stringWriter = new StringWriter();

        try (BufferedWriter writer = new BufferedWriter(stringWriter)) {
            GraphIO.printGraph(graph, writer);
        }

        return stringWriter.toString();
    }

    private Set<GraphIO.Edge> getEdges(Graph graph) {
        Set<GraphIO.Edge> edges = new HashSet<>();

        for (int start : graph.getVertices()) {
            for (int end : graph.getNeighbours(start)) {
                edges.add(new GraphIO.Edge(start, end));
            }
        }

        return edges;
    }
}
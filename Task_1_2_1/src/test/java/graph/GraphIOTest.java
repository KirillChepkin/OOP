//package graph;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import graph.incidence.IncidenceMatrixGraph;
//import graph.adjacency.lists.AdjacencyListsGraph;
//import graph.adjacency.matrix.AdjacencyMatrixGraph;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Set;
//import java.util.function.Supplier;
//import java.util.stream.Stream;
//import org.junit.jupiter.api.io.TempDir;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.api.Test;
//
//class GraphIOTest {
//    @TempDir
//    Path tempDir;
//
//    static Stream<Arguments> implementations() {
//        return Stream.of(
//                Arguments.of("lists", (Supplier<Graph>) AdjacencyListsGraph::new),
//                Arguments.of("adjacency matrix", (Supplier<Graph>) AdjacencyMatrixGraph::new),
//                Arguments.of("incidence matrix", (Supplier<Graph>) IncidenceMatrixGraph::new));
//    }
//
//    private Graph populated(Supplier<Graph> factory) {
//        Graph graph = factory.get();
//        graph.addVertex(100);
//        graph.addVertex(-55);
//        graph.addVertex(Integer.MIN_VALUE);
//        graph.addVertex(Integer.MAX_VALUE);
//        graph.addEdge(100, -55);
//        graph.addEdge(-55, Integer.MIN_VALUE);
//        graph.addEdge(Integer.MIN_VALUE, Integer.MIN_VALUE);
//        return graph;
//    }
//
//    @ParameterizedTest(name = "{0}: canonical text format")
//    @MethodSource("implementations")
//    void writesCanonicalFormat(String name, Supplier<Graph> factory) throws IOException {
//        Graph graph = populated(factory);
//        graph.addEdge(100, -55); // Duplicate has no effect.
//        Path file = tempDir.resolve("canonical.txt");
//        graph.printGraph(file);
//        assertEquals("4\n-2147483648\n-55\n100\n2147483647\n"
//                        + "3\n-2147483648,-2147483648\n-55,-2147483648\n100,-55\n",
//                Files.readString(file));
//    }
//
//    @ParameterizedTest(name = "{0}: load replaces entire graph")
//    @MethodSource("implementations")
//    void readingReplacesVerticesAndEdges(String name, Supplier<Graph> factory)
//            throws IOException {
//        Graph graph = populated(factory);
//        Path path = tempDir.resolve("replacement.txt");
//        Files.writeString(path, "3\n10\n-20\n30\n2\n10,-20\n-20,-20\n");
//        graph.readGraph(path);
//        assertEquals(Set.of(10, -20, 30), Set.copyOf(graph.getVertices()));
//        assertEquals(3, graph.getVertexCount());
//        assertTrue(graph.edgeExists(10, -20));
//        assertTrue(graph.edgeExists(-20, -20));
//        assertFalse(graph.vertexExists(100));
//        assertTrue(graph.getNeighbours(30).isEmpty());
//    }
//
//    @ParameterizedTest(name = "{0}: write/read/write round-trip")
//    @MethodSource("implementations")
//    void roundTrip(String name, Supplier<Graph> factory) throws IOException {
//        Graph graph = populated(factory);
//        Path file = tempDir.resolve("first.txt");
//        Path restoredFile = tempDir.resolve("second.txt");
//        graph.printGraph(file);
//        Graph loaded = factory.get();
//        loaded.readGraph(file);
//        loaded.printGraph(restoredFile);
//        assertEquals(Files.readString(file), Files.readString(restoredFile));
//    }
//
//    @ParameterizedTest(name = "{0}: isolated vertices survive")
//    @MethodSource("implementations")
//    void isolatedVerticesAndEmptyEdges(String name, Supplier<Graph> factory)
//            throws IOException {
//        Graph graph = factory.get();
//        graph.addVertex(-100);
//        graph.addVertex(2);
//        Path path = tempDir.resolve("isolated.txt");
//        graph.printGraph(path);
//        assertEquals("2\n-100\n2\n0\n", Files.readString(path));
//        Graph loaded = factory.get();
//        loaded.readGraph(path);
//        assertEquals(Set.of(-100, 2), Set.copyOf(loaded.getVertices()));
//        assertTrue(loaded.getNeighbours(-100).isEmpty());
//    }
//
//    @ParameterizedTest(name = "{0}: empty graph")
//    @MethodSource("implementations")
//    void emptyGraph(String name, Supplier<Graph> factory) throws IOException {
//        Graph graph = factory.get();
//        Path path = tempDir.resolve("empty.txt");
//        graph.printGraph(path);
//        assertEquals("0\n0\n", Files.readString(path));
//        graph.addVertex(999);
//        graph.readGraph(path);
//        assertEquals(0, graph.getVertexCount());
//    }
//
//    @ParameterizedTest(name = "{0}: invalid file does not modify graph")
//    @MethodSource("implementations")
//    void rejectsMalformedInputWithoutMutation(String name, Supplier<Graph> factory)
//            throws IOException {
//        Graph graph = populated(factory);
//        Path snapshot = tempDir.resolve("snapshot.txt");
//        graph.printGraph(snapshot);
//        String original = Files.readString(snapshot);
//        String[] invalid = {
//            "", "one\n", "-1\n", "1\n", "1\n2\n", "0\n-1\n",
//            "2\n1\n1\n0\n", "1\n2147483648\n0\n",
//            "1\n1\n1\n1,2\n", "1\n1\n2\n1,1\n1,1\n",
//            "1\n1\n1\n1,\n", "1\n1\n1\n1,1,1\n",
//            "0\n0\nextra\n", "0\n0\n\n", "1\n1\n1\n1,-3\n"
//        };
//        for (int i = 0; i < invalid.length; i++) {
//            Path broken = tempDir.resolve("bad" + i + ".txt");
//            Files.writeString(broken, invalid[i], StandardCharsets.UTF_8);
//            assertThrows(IOException.class, () -> graph.readGraph(broken), invalid[i]);
//            graph.printGraph(snapshot);
//            assertEquals(original, Files.readString(snapshot), invalid[i]);
//        }
//    }
//
//    @ParameterizedTest(name = "{0}: filenames as String")
//    @MethodSource("implementations")
//    void acceptsStringFilename(String name, Supplier<Graph> factory) throws IOException {
//        Graph graph = populated(factory);
//        Path path = tempDir.resolve("string.txt");
//        graph.printGraph(path.toString());
//        Graph loaded = factory.get();
//        loaded.readGraph(path.toString());
//        assertTrue(loaded.edgeExists(100, -55));
//    }
//
//    @Test
//    void filesCanBeExchangedBetweenImplementations() throws IOException {
//        Graph from = new AdjacencyListsGraph();
//        from.addVertex(-10);
//        from.addVertex(12);
//        from.addVertex(99); // Isolated.
//        from.addEdge(12, -10);
//        Path path = tempDir.resolve("exchange.txt");
//        from.printGraph(path);
//
//        for (Graph to : new Graph[]{new AdjacencyMatrixGraph(), new IncidenceMatrixGraph()}) {
//            to.readGraph(path);
//            assertEquals(Set.of(-10, 12, 99), Set.copyOf(to.getVertices()));
//            assertTrue(to.edgeExists(12, -10));
//            Path saved = tempDir.resolve(to.getClass().getSimpleName() + ".txt");
//            to.printGraph(saved);
//            assertEquals(Files.readString(path), Files.readString(saved));
//        }
//    }
//}

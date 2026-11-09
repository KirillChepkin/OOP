package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Provides file input and output for directed graphs with integer vertex keys.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public final class GraphIO {
    private GraphIO() {
    }

    /**
     * Represents a directed edge using public vertex keys.
     */
    public static final class Edge implements Comparable<Edge> {
        private final int start;
        private final int end;

        /**
         * Creates an edge from one vertex to another.
         *
         * @param start the key of the starting vertex
         * @param end the key of the ending vertex
         */
        public Edge(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * Returns the key of the starting vertex.
         *
         * @return the starting vertex key
         */
        public int getStart() {
            return start;
        }

        /**
         * Returns the key of the ending vertex.
         *
         * @return the ending vertex key
         */
        public int getEnd() {
            return end;
        }

        /**
         * Compares edges by their starting keys and then their ending keys.
         *
         * @param other the edge to compare with
         * @return a negative integer, zero, or a positive integer
         */
        @Override
        public int compareTo(Edge other) {
            int comparison = Integer.compare(start, other.start);
            return comparison != 0 ? comparison : Integer.compare(end, other.end);
        }

        /**
         * Checks whether another object represents the same directed edge.
         *
         * @param object the object to compare with
         * @return {@code true} if both endpoints are equal; otherwise, {@code false}
         */
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Edge)) {
                return false;
            }
            Edge other = (Edge) object;
            return start == other.start && end == other.end;
        }

        /**
         * Returns a hash code based on the endpoint keys.
         *
         * @return the hash code of this edge
         */
        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

    private static final class GraphData {
        private final List<Integer> vertices;
        private final List<Edge> edges;

        private GraphData(List<Integer> vertices, List<Edge> edges) {
            this.vertices = vertices;
            this.edges = edges;
        }
    }

    /**
     * Replaces the contents of a graph with data read from a reader.
     *
     * <p>The reader must contain the vertex count, vertex keys, edge count,
     * and directed edges in the established graph file format.</p>
     *
     * @param graph the graph to populate
     * @param reader the source of graph data
     * @throws IOException if the input is invalid or cannot be read
     */
    public static void readGraph(Graph graph, BufferedReader reader) throws IOException {
        Objects.requireNonNull(graph, "graph");
        GraphData data = parse(reader);

        for (int key : new ArrayList<>(graph.getVertices())) {
            graph.deleteVertex(key);
        }
        for (int key : data.vertices) {
            graph.addVertex(key);
        }
        for (Edge edge : data.edges) {
            graph.addEdge(edge.getStart(), edge.getEnd());
        }
    }

    /**
     * Writes a graph to a writer in the established graph file format.
     *
     * <p>Vertices and edges are written in sorted order.</p>
     *
     * @param graph the graph to write
     * @param writer the destination for graph data
     * @throws IOException if the graph contains inconsistent data or writing fails
     */
    public static void printGraph(Graph graph, BufferedWriter writer) throws IOException {
        Objects.requireNonNull(graph, "graph");
        List<Integer> vertices = new ArrayList<>(graph.getVertices());
        Collections.sort(vertices);

        if (vertices.size() != graph.getVertexCount()
                || new HashSet<>(vertices).size() != vertices.size()) {
            throw new IOException("Inconsistent graph vertex collection");
        }

        Set<Integer> vertexSet = new HashSet<>(vertices);
        List<Edge> edges = new ArrayList<>();
        Set<Edge> seenEdges = new HashSet<>();

        for (int start : vertices) {
            for (int end : graph.getNeighbours(start)) {
                Edge edge = new Edge(start, end);
                if (!vertexSet.contains(end) || !seenEdges.add(edge)) {
                    throw new IOException(
                            "Graph has an invalid or duplicate edge: " + start + "," + end);
                }
                edges.add(edge);
            }
        }

        Collections.sort(edges);

        writer.write(Integer.toString(vertices.size()));
        writer.write('\n');

        for (int key : vertices) {
            writer.write(Integer.toString(key));
            writer.write('\n');
        }

        writer.write(Integer.toString(edges.size()));
        writer.write('\n');

        for (Edge edge : edges) {
            writer.write(Integer.toString(edge.getStart()));
            writer.write(',');
            writer.write(Integer.toString(edge.getEnd()));
            writer.write('\n');
        }
    }

    private static GraphData parse(BufferedReader reader) throws IOException {
        int vertexCount = parseNonnegativeCount(reader.readLine(), "vertex count", 1);
        List<Integer> vertices = new ArrayList<>();
        Set<Integer> vertexKeys = new HashSet<>();

        for (int i = 0; i < vertexCount; i++) {
            int lineNumber = i + 2;
            int key = parseInteger(reader.readLine(), "vertex key", lineNumber);

            if (!vertexKeys.add(key)) {
                throw new IOException(
                        "Duplicate vertex key " + key + " on line " + lineNumber);
            }
            vertices.add(key);
        }

        int edgeCountLine = vertexCount + 2;
        int edgeCount = parseNonnegativeCount(
                reader.readLine(), "edge count", edgeCountLine);
        List<Edge> edges = new ArrayList<>();
        Set<Edge> seen = new HashSet<>();

        for (int i = 0; i < edgeCount; i++) {
            int lineNumber = edgeCountLine + 1 + i;
            String line = reader.readLine();

            if (line == null) {
                throw new IOException("Missing edge on line " + lineNumber);
            }

            int comma = line.indexOf(',');
            if (comma <= 0 || comma != line.lastIndexOf(',')
                    || comma == line.length() - 1) {
                throw new IOException("Invalid edge on line " + lineNumber + ": " + line);
            }

            int start = parseInteger(
                    line.substring(0, comma), "edge start", lineNumber);
            int end = parseInteger(
                    line.substring(comma + 1), "edge end", lineNumber);

            if (!vertexKeys.contains(start) || !vertexKeys.contains(end)) {
                throw new IOException("Unknown edge endpoint on line " + lineNumber);
            }

            Edge edge = new Edge(start, end);
            if (!seen.add(edge)) {
                throw new IOException("Duplicate edge on line " + lineNumber);
            }
            edges.add(edge);
        }

        return new GraphData(vertices, edges);
    }

    private static int parseNonnegativeCount(String text, String what, int line)
            throws IOException {
        int count = parseInteger(text, what, line);

        if (count < 0) {
            throw new IOException("Negative " + what + " on line " + line);
        }
        return count;
    }

    private static int parseInteger(String text, String what, int line)
            throws IOException {
        if (text == null) {
            throw new IOException("Missing " + what + " on line " + line);
        }

        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IOException(
                    "Invalid " + what + " on line " + line + ": " + text,
                    exception);
        }
    }
}
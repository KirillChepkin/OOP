package main;

import graph.Graph;
import graph.GraphIO;
import graph.GraphSorter;
import graph.adjacency.lists.AdjacencyListsGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph graph = new AdjacencyListsGraph();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            GraphIO.readGraph(graph, reader);
        }
        catch (IOException exception){
            System.out.println("Error when reading input: " + exception.getMessage());
        }
        for (int start : graph.getVertices()) {
            for (int end : graph.getVertices()) {
                if (graph.edgeExists(start, end)) {
                    System.out.println("(" + start + ", " + end + ")");
                }
            }
        }
        System.out.println(GraphSorter.kahnSort(graph));
    }
}
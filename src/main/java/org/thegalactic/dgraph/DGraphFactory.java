/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thegalactic.dgraph;

/**
 * DGraphFactory.
 */
public final class DGraphFactory {

    /*
     * ----------- STATIC GENERATION METHODS -------------
     */
    /**
     * Generates a random directed graph of size nodes.
     *
     * @param size      the number of nodes of the generated graph
     * @param threshold the threshold to generate an edge
     *
     * @return a random graph
     */
    public static DGraph random(int size, double threshold) {
        DGraph graph = new DGraph();
        // addition of nodes
        for (int i = 1; i <= size; i++) {
            Node node = new Node(i);
            graph.addNode(node);
        }
        // addition of edges
        for (Node source : graph.getNodes()) {
            for (Node target : graph.getNodes()) {
                if (Math.random() < threshold) {
                    graph.addEdge(source, target);
                }
            }
        }
        return graph;
    }

    /**
     * Generates a random directed graph of size nodes.
     *
     * @param size the number of nodes of the generated graph
     *
     * @return a random graph
     */
    public static DGraph random(int size) {
        return random(size, 0.5);
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private DGraphFactory() {
        super();
    }

}

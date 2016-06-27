package org.thegalactic.dgraph;

/*
 * DGraphFactory.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * DGraphFactory.
 */
public class DGraphFactory {

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
    public static DGraph random(final int size, final double threshold) {
        final DGraph graph = new DGraph();
        // addition of nodes
        for (int i = 1; i <= size; i++) {
            Node node = new Node(i);
            graph.addNode(node);
        }
        // addition of edges
        for (final Node source : graph.getNodes()) {
            for (final Node target : graph.getNodes()) {
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
    public static DGraph random(final int size) {
        return random(size, 0.5);
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private DGraphFactory() {
        super();
    }
}

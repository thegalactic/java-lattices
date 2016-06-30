package org.thegalactic.dgraph;

/*
 * DAGraphFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * DAGraphFactory.
 */
public final class DAGraphFactory {

    /**
     * The singleton instance.
     */
    private static final DAGraphFactory INSTANCE = new DAGraphFactory();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static DAGraphFactory getInstance() {
        return INSTANCE;
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private DAGraphFactory() {
        super();
    }

    /**
     * Generates the directed asyclic graph (DAG) of divisors for integers
     * included betwwen 2 and the specified value.
     *
     * In this DAG, nodes corresponds to the integers, and there is an edge
     * between two integers if and only if the second one is divisible by the
     * first one.
     *
     * @param number the maximal integer
     *
     * @return the acyclic graph
     */
    public DAGraph<Integer, ?> divisors(final int number) {
        final DAGraph<Integer, ?> graph = new DAGraph<Integer, Object>();
        // addition of nodes
        for (int i = 2; i <= number; i++) {
            graph.addNode(new Node<Integer>(i));
        }
        // addition of edges
        for (final Node<Integer> source : graph.getNodes()) {
            for (final Node<Integer> target : graph.getNodes()) {
                final int v1 = source.getContent();
                final int v2 = target.getContent();
                if (v1 < v2 && v2 % v1 == 0) {
                    graph.addEdge(source, target);
                }
            }
        }
        return graph;
    }

    /**
     * Generates a random directed and acyclic graph (DAG) of size nodes.
     *
     * @param size      the number of nodes of the generated graph
     * @param threshold the threshold to generate an edge
     *
     * @return a random acyclic graph
     */
    public DAGraph<Integer, ?> random(final int size, final double threshold) {
        final DAGraph<Integer, ?> graph = new DAGraph<Integer, Object>();
        // addition of Nodes
        for (int i = 1; i <= size; i++) {
            graph.addNode(new Node<Integer>(i));
        }
        // addition of edges
        for (final Node<Integer> source : graph.getNodes()) {
            for (final Node<Integer> target : graph.getNodes()) {
                // Test to avoid cycles
                if (source.compareTo(target) > 0) {
                    if (Math.random() < threshold) {
                        graph.addEdge(source, target);
                    }
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
     * @return a random acyclic graph
     */
    public DAGraph<Integer, ?> random(final int size) {
        return this.random(size, 0.5);
    }
}

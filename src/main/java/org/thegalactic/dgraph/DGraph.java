package org.thegalactic.dgraph;

/*
 * DGraph.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.IOException;
import java.util.SortedSet;

/**
 * DGraph.
 *
 * @param <N> Node content type
 * @param <E> Edge content type
 *
 * @todo Do use N insteand of Node and E instead of Edge by defining DGraph<N extends Comparable<N>, E>
 */
public interface DGraph<N, E> {

    /**
     * Returns the set of nodes of this component.
     *
     * @return the set of nodes
     */
    SortedSet<Node<N>> getNodes();

    /**
     * Returns the set of edges of this component.
     *
     * @return the set of edges
     */
    SortedSet<Edge<N, E>> getEdges();

    /**
     * Returns the set of edges predecessors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of edges
     */
    SortedSet<Edge<N, E>> getPredecessorEdges(final Node<N> node);

    /**
     * Returns the set of edges successors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of edges
     */
    SortedSet<Edge<N, E>> getSuccessorEdges(final Node<N> node);

    /**
     * Returns the set of nodes predecessors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of nodes
     */
    SortedSet<Node<N>> getPredecessorNodes(final Node<N> node);

    /**
     * Returns the set of nodes successors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of nodes
     */
    SortedSet<Node<N>> getSuccessorNodes(final Node<N> node);

    /**
     * Returns the sinks of this component.
     *
     * @return the sinks
     */
    SortedSet<Node<N>> getSinks();

    /**
     * Returns the wells of this component.
     *
     * @return the wells
     */
    SortedSet<Node<N>> getWells();

    /**
     * Returns the number of edges of this component.
     *
     * @return the number of edges
     */
    int sizeEdges();

    /**
     * Returns the number of nodes of this component.
     *
     * @return the number of nodes
     */
    int sizeNodes();

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param filename the name of the file
     *
     * @throws IOException When an IOException occurs
     */
    void save(final String filename) throws IOException;

    /**
     * Returns a String representation of this component.
     *
     * @return the string representation
     */
    @Override
    String toString();
}

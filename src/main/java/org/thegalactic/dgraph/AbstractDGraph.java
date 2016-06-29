package org.thegalactic.dgraph;

/*
 * AbstractDGraph.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.IOException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.thegalactic.dgraph.io.DGraphIOFactory;
import org.thegalactic.io.Filer;

/**
 * AbstractDGraph.
 *
 * @param <N> Node content type
 * @param <E> Edge content type
 */
public abstract class AbstractDGraph<N, E> implements DGraph<N, E> {

    /**
     * Basic constructor.
     */
    protected AbstractDGraph() {
        super();
    }

    /**
     * Returns a String representation of this component.
     *
     * @return the string representation
     */
    @Override
    public final String toString() {
        final StringBuilder nodes = new StringBuilder();
        nodes.append(this.sizeNodes()).append(" Nodes: {");
        final StringBuilder edges = new StringBuilder();
        edges.append(this.sizeEdges()).append(" Edges: {");
        for (final Node<N> node : this.getNodes()) {
            nodes.append(node.toString()).append(',');
        }
        for (final Edge<N, E> edge : this.getEdges()) {
            edges.append(edge.toString()).append(',');
        }
        final String newLine = System.getProperty("line.separator");
        nodes.append('}').append(newLine).append(edges).append('}').append(newLine);
        return nodes.toString();
    }


    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param filename the name of the file
     *
     * @throws IOException When an IOException occurs
     */
    @Override
    public void save(final String filename) throws IOException {
        Filer.getInstance().save(this, DGraphIOFactory.getInstance(), filename);
    }


    /*
     * --------------- GRAPH HANDLING METHODS ------------
     */
    /**
     * Returns the sinks of this component.
     *
     * @return the sinks
     */
    @Override
    public final SortedSet<Node<N>> getSinks() {
        return new Sinks(this);
    }

    /**
     * Returns the wells of this component.
     *
     * @return the wells
     */
    @Override
    public final SortedSet<Node<N>> getWells() {
        return new Wells(this);
    }

    /**
     * Check if this component is acyclic.
     *
     * @return true if the component is acyclic
     */
    public final boolean isAcyclic() {
        return this.topologicalSort().size() == this.sizeNodes();
    }

    /**
     * Returns a topological sort of the node of this component.
     *
     * This topological sort is a sort on all the nodes according to their
     * successors. If the graph is not acyclic, some nodes don't belong to the
     * sort. This treatment is performed in O(n+m), where n corresponds to the
     * number of nodes, and m corresponds to the number of edges.
     *
     * @return the nodes
     */
    public final List<Node<N>> topologicalSort() {
        final TreeSet<Node<N>> sinks = new TreeSet<Node<N>>(this.getSinks());
        // initialise a map with the number of predecessors (value) for each node (key);
        final TreeMap<Node<N>, Integer> size = new TreeMap<Node<N>, Integer>();
        for (final Node<N> node : this.getNodes()) {
            size.put(node, this.getPredecessorNodes(node).size());
        }
        final List<Node<N>> sort = new ArrayList<Node<N>>();
        while (!sinks.isEmpty()) {
            final Node<N> node = sinks.pollFirst();
            sort.add(node);
            // updating of the set min by considering the successors of node
            for (final Node<N> successor : this.getSuccessorNodes(node)) {
                final int newSize = size.get(successor) - 1;
                size.put(successor, newSize);
                if (newSize == 0) {
                    sinks.add(successor);
                }
            }
        }
        return sort;
    }

    /**
     * AbstractEnds.
     */
    private abstract class AbstractEnds extends AbstractSet<Node<N>> implements SortedSet<Node<N>> {

        /**
         * The underlying graph.
         */
        private final AbstractDGraph graph;

        /**
         * Constructs a sorted set of the edges source a graph.
         *
         * @param graph A DGraph
         */
        protected AbstractEnds(final AbstractDGraph graph) {
            super();
            this.graph = graph;
        }

        /**
         * Get the underlying graph.
         *
         * @return the graph
         */
        protected final AbstractDGraph getGraph() {
            return this.graph;
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the first edge
         */
        public final Node<N> first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the last edge
         */
        public final Node<N> last() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param node the to node
         *
         * @return The head set
         *
         * @throws UnsupportedOperationException
         */
        public final SortedSet<Node<N>> headSet(final Node<N> node) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param node the source node
         *
         * @return The tail set
         *
         * @throws UnsupportedOperationException
         */
        public final SortedSet<Node<N>> tailSet(final Node<N> node) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param fromNode the source node
         * @param toNode   the to node
         *
         * @return The sub set
         *
         * @throws UnsupportedOperationException
         */
        public final SortedSet<Node<N>> subSet(final Node<N> fromNode, final Node<N> toNode) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return null
         */
        public final Comparator<? super Node<N>> comparator() {
            return null;
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return the size of the collection
         */
        public final int size() {
            int size = 0;
            final Iterator iterator = this.iterator();
            while (iterator.hasNext()) {
                size++;
                iterator.next();
            }
            return size;
        }

    }

    /**
     * AbstractEndsIterator.
     */
    private abstract class AbstractEndsIterator implements Iterator<Node<N>> {

        /**
         * The nodes iterator.
         */
        private final Iterator<Node<N>> nodesIterator;

        /**
         * The sinks object.
         */
        private final AbstractEnds ends;

        /**
         * The next sink.
         */
        private Node<N> next;

        /**
         * The hasNext flag.
         */
        private boolean hasNext;

        /**
         * Constructs the iterator source a set of sinks.
         *
         * @param ends The ends.
         */
        protected AbstractEndsIterator(final AbstractEnds ends) {
            super();
            this.ends = ends;
            this.nodesIterator = ends.getGraph().getNodes().iterator();
            this.prepareNext();
        }

        /**
         * Get the ends.
         *
         * @return the ends
         */
        protected final AbstractEnds getEnds() {
            return this.ends;
        }

        /**
         * Get the next node to be analyzed.
         *
         * @return the next node
         */
        protected final Node<N> getNext() {
            return this.next;
        }

        /**
         * Prepare the next sink and the hasNext flag.
         */
        private void prepareNext() {
            this.hasNext = false;
            while (!this.hasNext && this.nodesIterator.hasNext()) {
                this.next = this.nodesIterator.next();
                this.hasNext = this.computeHasNext();
            }
        }

        /**
         * The remove operation is not supported.
         *
         * @throws UnsupportedOperationException
         */
        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * The next method returns the next sink.
         *
         * @return The next sink
         */
        public final Node<N> next() {
            final Node<N> sink = this.next;
            this.prepareNext();
            return sink;
        }

        /**
         * The hasNext method return true if the iterator has a next edge.
         *
         * @return true if the iterator has a next edge
         */
        public final boolean hasNext() {
            return this.hasNext;
        }

        /**
         * Compute the hasNext flag.
         *
         * @return the hasNext flag
         */
        protected abstract boolean computeHasNext();

    }

    /**
     * This class implements a sorted set of the sinks.
     */
    private class Sinks extends AbstractEnds {

        /**
         * Constructor.
         *
         * @param graph the underlying graph
         */
        protected Sinks(final AbstractDGraph graph) {
            super(graph);
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return a new sinks iterator
         */
        public final Iterator iterator() {
            return new SinksIterator(this);
        }

        /**
         * This class implements an iterator over the edges of a graph.
         */
        private class SinksIterator extends AbstractEndsIterator {

            /**
             * Basic constructor.
             *
             * @param sinks the sinks
             */
            protected SinksIterator(final Sinks sinks) {
                super(sinks);
            }

            /**
             * Compute the hasNext flag value.
             *
             * @return the hasNext flag value
             */
            protected final boolean computeHasNext() {
                return this.getEnds().getGraph().getPredecessorEdges(this.getNext()).isEmpty();
            }
        }
    }

    /**
     * This class implements a sorted set of the wells.
     */
    private class Wells extends AbstractEnds {

        /**
         * Constructor.
         *
         * @param graph the underlying graph
         */
        protected Wells(final AbstractDGraph graph) {
            super(graph);
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return a new Wells iterator
         */
        public final Iterator iterator() {
            return new WellsIterator(this);
        }

        /**
         * This class implements an iterator over the edges of a graph.
         */
        private class WellsIterator extends AbstractEndsIterator {

            /**
             * Constructs the iterator source a set of wells.
             *
             * @param wells The wells.
             */
            protected WellsIterator(final Wells wells) {
                super(wells);
            }

            /**
             * Compute the hasNext flag value.
             *
             * @return the hasNext flag value
             */
            protected final boolean computeHasNext() {
                return this.getEnds().getGraph().getSuccessorEdges(this.getNext()).isEmpty();
            }
        }
    }
}

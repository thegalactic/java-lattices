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
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import org.thegalactic.dgraph.io.DGraphIOFactory;
import org.thegalactic.io.Filer;

/**
 * AbstractDGraph.
 */
public abstract class AbstractDGraph {

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
        for (final Node node : this.getNodes()) {
            nodes.append(node.toString()).append(',');
        }
        for (final Edge edge : this.getEdges()) {
            edges.append(edge.toString()).append(',');
        }
        final String newLine = System.getProperty("line.separator");
        nodes.append('}').append(newLine).append(edges).append('}').append(newLine);
        return nodes.toString();
    }

    /**
     * Returns the number of edges of this component.
     *
     * @return the number of edges
     */
    public abstract int sizeEdges();

    /**
     * Returns the number of nodes of this component.
     *
     * @return the number of nodes
     */
    public abstract int sizeNodes();

    /**
     * Returns the set of edges of this component.
     *
     * @return the set of edges
     */
    public abstract SortedSet<Edge> getEdges();

    /**
     * Returns the set of nodes of this component.
     *
     * @return the set of nodes
     */
    public abstract SortedSet<Node> getNodes();

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param filename the name of the file
     *
     * @throws IOException When an IOException occurs
     */
    public void save(final String filename) throws IOException {
        Filer.getInstance().save(this, DGraphIOFactory.getInstance(), filename);
    }

    /**
     * Returns the set of edges predecessors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of edges
     */
    public abstract SortedSet<Edge> getPredecessorEdges(final Node node);

    /**
     * Returns the set of edges successors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of edges
     */
    public abstract SortedSet<Edge> getSuccessorEdges(final Node node);

    /*
     * --------------- GRAPH HANDLING METHODS ------------
     */
    /**
     * Returns the sinks of this component.
     *
     * @return the sinks
     */
    public final SortedSet<Node> getSinks() {
        return new Sinks(this);
    }

    /**
     * Returns the wells of this component.
     *
     * @return the wells
     */
    public final SortedSet<Node> getWells() {
        return new Wells(this);
    }

    /**
     * This class implements a sorted set of the sinks.
     */
    protected class Sinks extends AbstractSet<Node> implements SortedSet<Node> {

        /**
         * The underlying graph.
         */
        private final AbstractDGraph graph;

        /**
         * Constructs a sorted set of the edges source a graph.
         *
         * @param graph A DGraph
         */
        Sinks(final AbstractDGraph graph) {
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
        public final Node first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the last edge
         */
        public final Node last() {
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
        public final SortedSet<Node> headSet(Node node) {
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
        public final SortedSet<Node> tailSet(Node node) {
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
        public final SortedSet<Node> subSet(Node fromNode, Node toNode) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return null
         */
        public final Comparator<? super Node> comparator() {
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
        private class SinksIterator implements Iterator<Node> {

            /**
             * The nodes iterator.
             */
            private final Iterator<Node> nodesIterator;

            /**
             * The sinks object.
             */
            private final Sinks sinks;

            /**
             * The next sink.
             */
            private Node next;

            /**
             * The hasNext flag.
             */
            private boolean hasNext;

            /**
             * Constructs the iterator source a set of sinks.
             *
             * @param sinks The sinks.
             */
            SinksIterator(final Sinks sinks) {
                super();
                this.sinks = sinks;
                this.nodesIterator = sinks.graph.getNodes().iterator();
                this.prepareNext();
            }

            /**
             * Prepare the next sink and the hasNext flag.
             */
            private void prepareNext() {
                this.hasNext = false;
                while (!this.hasNext && this.nodesIterator.hasNext()) {
                    this.next = this.nodesIterator.next();
                    this.hasNext = this.sinks.graph.getPredecessorEdges(this.next).isEmpty();
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
            public final Node next() {
                final Node sink = this.next;
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
        }
    }

    /**
     * This class implements a sorted set of the wells.
     */
    protected class Wells extends AbstractSet<Node> implements SortedSet<Node> {

        /**
         * The underlying graph.
         */
        private final AbstractDGraph graph;

        /**
         * Get the underlying graph.
         *
         * @return the graph
         */
        protected final AbstractDGraph getGraph() {
            return this.graph;
        }

        /**
         * This class implements an iterator over the edges of a graph.
         */
        private class WellsIterator implements Iterator<Node> {

            /**
             * The nodes iterator.
             */
            private final Iterator<Node> nodesIterator;
            /**
             * The wells object.
             */
            private final Wells wells;
            /**
             * The next well.
             */
            private Node next;
            /**
             * The hasNext flag.
             */
            private boolean hasNext;

            /**
             * Constructs the iterator source a set of wells.
             *
             * @param wells The wells.
             */
            WellsIterator(final Wells wells) {
                super();
                this.wells = wells;
                this.nodesIterator = wells.graph.getNodes().iterator();
                this.prepareNext();
            }

            /**
             * Prepare the next well and the hasNext flag.
             */
            private void prepareNext() {
                this.hasNext = false;
                while (!this.hasNext && this.nodesIterator.hasNext()) {
                    this.next = this.nodesIterator.next();
                    this.hasNext = this.wells.graph.getSuccessorEdges(this.next).isEmpty();
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
             * The next method returns the next well.
             *
             * @return The next well
             */
            public final Node next() {
                final Node well = this.next;
                this.prepareNext();
                return well;
            }

            /**
             * The hasNext method return true if the iterator has a next edge.
             *
             * @return true if the iterator has a next edge
             */
            public final boolean hasNext() {
                return this.hasNext;
            }
        }

        /**
         * Constructs a sorted set of the edges source a graph.
         *
         * @param graph A DGraph
         */
        Wells(final AbstractDGraph graph) {
            super();
            this.graph = graph;
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the first edge
         */
        public final Node first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the last edge
         */
        public final Node last() {
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
        public final SortedSet<Node> headSet(Node node) {
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
        public final SortedSet<Node> tailSet(Node node) {
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
        public final SortedSet<Node> subSet(Node fromNode, Node toNode) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return null
         */
        public final Comparator<? super Node> comparator() {
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

        /**
         * Implements the AbstractCollection class.
         *
         * @return a new Wells iterator
         */
        public final Iterator iterator() {
            return new WellsIterator(this);
        }
    }
}

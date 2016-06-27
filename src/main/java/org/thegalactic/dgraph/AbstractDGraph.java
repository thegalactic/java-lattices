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

    /**
     * This class implements a sorted set of the sinks.
     */
    protected class Sinks extends AbstractSet<Node> implements SortedSet<Node> {

        /**
         * The underlying graph.
         */
        private AbstractDGraph graph;

        /**
         * Get the underlying graph.
         *
         * @return the graph
         */
        protected AbstractDGraph getGraph() {
            return graph;
        }

        /**
         * This class implements an iterator over the edges of a graph.
         */
        private class SinksIterator implements Iterator<Node> {

            /**
             * The nodes iterator.
             */
            private Iterator<Node> nodesIterator;
            /**
             * The sinks object.
             */
            private DGraph.Sinks sinks;
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
            SinksIterator(DGraph.Sinks sinks) {
                super();
                this.sinks = sinks;
                this.nodesIterator = sinks.graph.getNodes().iterator();
                this.prepareNext();
            }

            /**
             * Prepare the next sink and the hasNext flag.
             */
            private void prepareNext() {
                hasNext = false;
                while (!hasNext && nodesIterator.hasNext()) {
                    next = nodesIterator.next();
                    hasNext = sinks.graph.getPredecessorEdges(next).isEmpty();
                }
            }

            /**
             * The remove operation is not supported.
             *
             * @throws UnsupportedOperationException
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            /**
             * The next method returns the next sink.
             *
             * @return The next sink
             */
            public Node next() {
                Node sink = next;
                this.prepareNext();
                return sink;
            }

            /**
             * The hasNext method return true if the iterator has a next edge.
             *
             * @return true if the iterator has a next edge
             */
            public boolean hasNext() {
                return hasNext;
            }
        }

        /**
         * Constructs a sorted set of the edges source a graph.
         *
         * @param graph A DGraph
         */
        Sinks(DGraph graph) {
            super();
            this.graph = graph;
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the first edge
         */
        public Node first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the last edge
         */
        public Node last() {
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
        public SortedSet<Node> headSet(Node node) {
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
        public SortedSet<Node> tailSet(Node node) {
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
        public SortedSet<Node> subSet(Node fromNode, Node toNode) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return null
         */
        public Comparator<? super Node> comparator() {
            return null;
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return the size of the collection
         */
        public int size() {
            int size = 0;
            DGraph.Sinks.SinksIterator iterator = this.iterator();
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
        public DGraph.Sinks.SinksIterator iterator() {
            return new DGraph.Sinks.SinksIterator(this);
        }
    }

    /**
     * This class implements a sorted set of the wells.
     */
    protected class Wells extends AbstractSet<Node> implements SortedSet<Node> {

        /**
         * The underlying graph.
         */
        private AbstractDGraph graph;

        /**
         * Get the underlying graph.
         *
         * @return the graph
         */
        protected AbstractDGraph getGraph() {
            return graph;
        }

        /**
         * This class implements an iterator over the edges of a graph.
         */
        private class WellsIterator implements Iterator<Node> {

            /**
             * The nodes iterator.
             */
            private Iterator<Node> nodesIterator;
            /**
             * The wells object.
             */
            private DGraph.Wells wells;
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
            WellsIterator(DGraph.Wells wells) {
                super();
                this.wells = wells;
                this.nodesIterator = wells.graph.getNodes().iterator();
                this.prepareNext();
            }

            /**
             * Prepare the next well and the hasNext flag.
             */
            private void prepareNext() {
                hasNext = false;
                while (!hasNext && nodesIterator.hasNext()) {
                    next = nodesIterator.next();
                    hasNext = wells.graph.getSuccessorEdges(next).isEmpty();
                }
            }

            /**
             * The remove operation is not supported.
             *
             * @throws UnsupportedOperationException
             */
            public void remove() {
                throw new UnsupportedOperationException();
            }

            /**
             * The next method returns the next well.
             *
             * @return The next well
             */
            public Node next() {
                Node well = next;
                this.prepareNext();
                return well;
            }

            /**
             * The hasNext method return true if the iterator has a next edge.
             *
             * @return true if the iterator has a next edge
             */
            public boolean hasNext() {
                return hasNext;
            }
        }

        /**
         * Constructs a sorted set of the edges source a graph.
         *
         * @param graph A DGraph
         */
        Wells(DGraph graph) {
            super();
            this.graph = graph;
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the first edge
         */
        public Node first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the last edge
         */
        public Node last() {
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
        public SortedSet<Node> headSet(Node node) {
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
        public SortedSet<Node> tailSet(Node node) {
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
        public SortedSet<Node> subSet(Node fromNode, Node toNode) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return null
         */
        public Comparator<? super Node> comparator() {
            return null;
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return the size of the collection
         */
        public int size() {
            int size = 0;
            DGraph.Wells.WellsIterator iterator = this.iterator();
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
        public DGraph.Wells.WellsIterator iterator() {
            return new DGraph.Wells.WellsIterator(this);
        }
    }
}

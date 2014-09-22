package fr.kbertet.dgraph;

/*
 * DGraph.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.dgraph.io.DGraphWriterFactory;
import fr.kbertet.dgraph.io.DGraphWriterDot;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.AbstractSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Comparator;

/**
 * This class gives a standard representation for a directed graph
 * by sets of successors and predecessors.
 *
 * A directed graph is composed of
 *
 * - a treeset of node, defined by class {@link Node};
 * - a treemap of successors that associates to each node a treeset of successors, defined by class {@link Edge};
 * - a treemap of predecessors that associates to each node a treeset of predecessors, defined by class {@link Edge}.
 *
 * This class provides methods implementing classical operation on a directed graph:
 *
 * - sinks
 * - wells
 * - subgraph
 * - reflexive closure and reduction
 * - transitive closure
 * - strongly connected components
 * - ...
 *
 * This class also provides a static method randomly generating a directed graph.
 *
 * ![DGraph](DGraph.png)
 *
 * @uml DGraph.png
 * !include src/fr/kbertet/dgraph/DGraph.iuml
 * !include src/fr/kbertet/dgraph/Edge.iuml
 * !include src/fr/kbertet/dgraph/Node.iuml
 *
 * hide members
 * show DGraph members
 * class DGraph #LightCyan
 * title DGraph UML graph
 */
public class DGraph implements Cloneable {
    /*
     * Register dot writer
     */
    static {
        if (DGraphWriterFactory.get("dot") == null) {
            DGraphWriterDot.register();
        }
    }

    /* ------------- FIELDS ------------------ */

    /**
     * A set of elements.
     */
    private TreeSet<Node> nodes;

    /**
     * A map to associate a set of successors to each node.
     */
    private TreeMap<Node, TreeSet<Edge>> successors;

    /**
     * A map to associate a set of predecessors to each node.
     */
    private TreeMap<Node, TreeSet<Edge>> predecessors;

    /**
     * This class implements a sorted set of the edges.
     */
    private class Edges extends AbstractSet<Edge> implements SortedSet<Edge> {
        /**
         * The underlying graph.
         */
        private DGraph graph;

        /**
         * Get the underlying graph.
         *
         * @return  the graph
         */
        protected DGraph getGraph() {
            return graph;
        }

        /**
         * This class implements an iterator over the edges of a graph.
         */
        private class EdgesIterator implements Iterator<Edge> {
            /**
             * The nodes iterator.
             */
            private Iterator<Node> nodesIterator;

            /**
             * The edges iterator for the current node.
             */
            private Iterator<Edge> edgesIterator;

            /**
             * The edges object.
             */
            private Edges edges;

            /**
             * The hasNext flag.
             */
            private boolean hasNext;

            /**
             * Constructs the iterator from a set of edges.
             *
             * @param   edges  The edges.
             */
            public EdgesIterator(Edges edges) {
                this.edges = edges;
                this.nodesIterator = edges.graph.nodes.iterator();
                this.prepareNext();
            }

            /**
             * Prepare the next edge and the hasNext flag.
             */
            private void prepareNext() {
                hasNext = false;
                while (!hasNext && nodesIterator.hasNext()) {
                    edgesIterator = edges.getGraph().successors.get(nodesIterator.next()).iterator();
                    hasNext = edgesIterator.hasNext();
                }
            }

            /**
             * The remove operation is not supported.
             *
             * @throws  UnsupportedOperationException
             */
            public void remove() {
                throw new UnsupportedOperationException();
            }

            /**
             * The next method returns the next edge.
             *
             * @return  The next edge
             */
            public Edge next() {
                Edge edge = this.edgesIterator.next();
                if (!this.edgesIterator.hasNext()) {
                    this.prepareNext();
                }
                return edge;
            }

            /**
             * The hasNext method return true if the iterator has a next edge.
             *
             * @return  true if the iterator has a next edge
             */
            public boolean hasNext() {
                return hasNext;
            }
        }

        /**
         * Constructs an sorted set of the edges from a graph.
         *
         * @param   graph  A DGraph
         */
        public Edges(DGraph graph) {
            this.graph = graph;
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return  the first edge
         */
        public Edge first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return  the last edge
         */
        public Edge last() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param   edge  the to edge
         *
         * @return  The head set
         *
         * @throws  UnsupportedOperationException
         */
        public SortedSet<Edge> headSet(Edge edge) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param   edge  the from edge
         *
         * @return  The tail set
         *
         * @throws  UnsupportedOperationException
         */
        public SortedSet<Edge> tailSet(Edge edge) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param   fromEdge  the from edge
         * @param   toEdge    the to edge
         *
         * @return  The sub set
         *
         * @throws  UnsupportedOperationException
         */
        public SortedSet<Edge> subSet(Edge fromEdge, Edge toEdge) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return  null
         */
        public Comparator<? super Edge> comparator() {
            return null;
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return  the size of the collection
         */
        public int size() {
            return graph.sizeEdges();
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return  a new edges iterator
         */
        public EdgesIterator iterator() {
            return new EdgesIterator(this);
        }
    }

    /* ------------- CONSTRUCTORS ------------------ */

    /**
     * Constructs a new directed graph with an empty set of node.
     */
    public DGraph() {
        this.nodes = new TreeSet<Node>();
        this.successors = new TreeMap<Node, TreeSet<Edge>>();
        this.predecessors = new TreeMap<Node, TreeSet<Edge>>();
    }

    /**
     * Constructs a new directed graph from the specified set of nodes.
     *
     * Successors and predecessors of each nodes are initialised by an empty set.
     *
     * @param   set  the set of nodes
     */
    public DGraph(final Set<Node> set) {
        this.nodes = new TreeSet<Node>(set);
        this.successors = new TreeMap<Node, TreeSet<Edge>>();
        for (Node node : this.nodes) {
            this.successors.put(node, new TreeSet<Edge>());
        }
        this.predecessors = new TreeMap<Node, TreeSet<Edge>>();
        for (Node node : this.nodes) {
            this.predecessors.put(node, new TreeSet<Edge>());
        }
    }

    /**
     * Constructs this component as a copy of the specified directed graph.
     *
     * @param   graph  the directed graph to be copied
     */
    public DGraph(final DGraph graph) {
        this.nodes = new TreeSet<Node>(graph.nodes);
        this.successors = new TreeMap<Node, TreeSet<Edge>>();
        this.predecessors = new TreeMap<Node, TreeSet<Edge>>();
        for (Node node : graph.nodes) {
            this.successors.put(node, new TreeSet<Edge>(graph.successors.get(node)));
            this.predecessors.put(node, new TreeSet<Edge>(graph.predecessors.get(node)));
        }
    }

    /* ----------- STATIC GENERATION METHODS ------------- */

    /**
     * Generates a random directed graph of size nodes.
     *
     * @param   size       the number of nodes of the generated graph
     * @param   threshold  the threshold to generate an edge
     *
     * @return  a random graph
     */
    public static DGraph random(int size, double threshold) {
        DGraph graph = new DGraph();
        // addition of nodes
        for (int i = 1; i <= size; i++) {
            Node node = new Node(new Integer(i));
            graph.addNode(node);
        }
        // addition of edges
        for (Node from : graph.nodes) {
            for (Node to : graph.nodes) {
                if (Math.random() < threshold) {
                    graph.addEdge(from, to);
                }
            }
        }
        return graph;
    }

    /**
     * Generates a random directed graph of size nodes.
     *
     * @param   size  the number of nodes of the generated graph
     *
     * @return  a random graph
     */
    public static DGraph random(int size) {
        return random(size, 0.5);
    }

    /* --------------- ACCESSOR AND OVERLAPPING METHODS ------------ */

    /**
     * Returns a clone of this component composed of a clone of each node and each edge.
     *
     * @return  the clone of this
     */
    public DGraph clone() {
        return new DGraph(this);
    }

    /**
     * Returns the set of nodes of this component.
     *
     * @return  the set of nodes
     */
    public SortedSet<Node> getNodes() {
        return Collections.unmodifiableSortedSet((SortedSet<Node>) this.nodes);
    }

    /**
     * Set the set of nodes of this component.
     *
     * @param   nodes  The nodes
     *
     * @return  this for chaining
     */
    protected DGraph setNodes(final TreeSet<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    /**
     * Returns the successors of this component.
     *
     * @return  the map
     */
    protected TreeMap<Node, TreeSet<Edge>> getSuccessors() {
        return this.successors;
    }

    /**
     * Set the successors of this component.
     *
     * @param   successors  The successors
     *
     * @return  this for chaining
     */
    protected DGraph setSuccessors(final TreeMap<Node, TreeSet<Edge>> successors) {
        this.successors = successors;
        return this;
    }

    /**
     * Returns the predecessors of this component.
     *
     * @return  the map
     */
    protected TreeMap<Node, TreeSet<Edge>> getPredecessors() {
        return this.predecessors;
    }

    /**
     * Set the predecessors of this component.
     *
     * @param   predecessors  The predecessors
     *
     * @return  this for chaining
     */
    protected DGraph setPredecessors(final TreeMap<Node, TreeSet<Edge>> predecessors) {
        this.predecessors = predecessors;
        return this;
    }

    /**
     * Returns the set of edges of this component.
     *
     * @return  the set of edges
     */
    public SortedSet<Edge> getEdges() {
        return new Edges(this);
    }

    /**
     * Returns the set of edges successors of the specified node.
     *
     * @param   node  the node to search for
     *
     * @return  the set of edges
     */
    public SortedSet<Edge> getSuccessorEdges(final Node node) {
        return Collections.unmodifiableSortedSet((SortedSet<Edge>) this.successors.get(node));
    }

    /**
     * Returns the set of edges predecessors of the specified node.
     *
     * @param   node  the node to search for
     *
     * @return  the set of edges
     */
    public SortedSet<Edge> getPredecessorEdges(final Node node) {
        return Collections.unmodifiableSortedSet((SortedSet<Edge>) this.predecessors.get(node));
    }

    /**
     * Returns the set of nodes successors of the specified node.
     *
     * @param   node  the node to search for
     *
     * @return  the set of nodes
     *
     * @todo  use iterator pattern (some changes in ArrowRelation.java and Lattice.java)
     */
    public SortedSet<Node> getSuccessorNodes(final Node node) {
        TreeSet<Node> successors = new TreeSet<Node>();
        for (Edge edge : this.successors.get(node)) {
            successors.add(edge.getTo());
        }
        return successors;
    }

    /**
     * Returns the set of nodes predecessors of the specified node.
     *
     * @param   node  the node to search for
     *
     * @return  the set of nodes
     *
     * @todo  use iterator pattern (some changes in ArrowRelation.java and Lattice.java)
     */
    public TreeSet<Node> getPredecessorNodes(final Node node) {
        TreeSet<Node> predecessors = new TreeSet<Node>();
        for (Edge edge : this.predecessors.get(node)) {
            predecessors.add(edge.getFrom());
        }
        return predecessors;
    }

    /**
     * Returns, if it exists, the edge between node from and node to.
     *
     * @param   from  The origin node
     * @param   to    The destination node
     *
     * @return  the found edge or null
     *
     * @todo  see getNode
     */
    public Edge getEdge(final Node from, final Node to) {
        TreeSet<Edge> node = successors.get(from);
        if (node == null) {
            return null;
        }
        if (this.containsEdge(from, to)) {
            for (Edge edge : this.successors.get(from)) {
                if (edge.getTo().equals(to)) {
                    return edge;
                }
            }
        }
        return null;
    }

    /**
     * Returns the node that is equal to the specified one.
     *
     * @param   search  The node to search for
     *
     * @return  the found node or null
     *
     * @todo  maybe use
     *    try {
              return this.nodes.subSet(search, true, search, true).first();
          } catch (NoSuchElementException e) {
            return null;
          }
     *
     */
     public Node getNode(Node search) {
        for (Node node : this.nodes) {
            if (node.equals(search)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the node whose content is equal to the specified one.
     *
     * @param   content  The content to search for
     *
     * @return  the found node or null
     *
     * @todo  this method is not efficient. Do we remove it or add an index on Dgraph using content field?
     *        Verify where it is called for migrating it if necessary.
     */
    public Node getNodeByContent(final Object content) {
        for (Node node : this.nodes) {
            if (node.getContent().equals(content)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the node whose ident is equal to the specified one.
     *
     * @param  identifier  node identifier
     *
     * @return  the found node or null
     */
    public Node getNodeByIdentifier(int identifier) {
        for (Node node : this.nodes) {
            if (node.getIdentifier() == identifier) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the number of nodes of this component.
     *
     * @return  the number of nodes
     */
    public int sizeNodes() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges of this component.
     *
     * @return  the number of edges
     */
    public int sizeEdges() {
        int size = 0;
        for (Node node : nodes) {
            size += successors.get(node).size();
        }
        return size;
    }

    /**
     * Returns a String representation of this component.
     *
     * @return  the string representation
     */
    public String toString() {
        StringBuffer nodes  = new StringBuffer();
        nodes.append(this.sizeNodes()).append(" Nodes: {");
        StringBuffer edges = new StringBuffer();
        edges.append(this.sizeEdges()).append(" Edges: {");
        for (Node from : this.nodes) {
            nodes.append(from.toString() + ",");
        }
        for (Edge ed : this.getEdges()) {
            edges.append(ed.toString() + ",");
        }
        nodes.append("}\n").append(edges).append("}\n");
        return nodes.toString();
    }

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param   filename  the name of the file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void save(final String filename) throws IOException {
        String extension = "";
        int index = filename.lastIndexOf('.');
        if (index > 0) {
            extension = filename.substring(index + 1);
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(filename));
        DGraphWriterFactory.get(extension).write(this, file);
        file.close();
    }

    /* --------------- NODES AND EDGES MODIFICATION METHODS ------------ */

    /**
     * Checks if the specified node belong to this component.
     *
     * @param   node  the node to insert
     *
     * @return  true if the node has been successfully inserted
     */
    public boolean containsNode(final Node node) {
        return this.nodes.contains(node);
    }

    /**
     * Adds the specified node to the set of node of this component.
     *
     * @param   node  the node to insert
     *
     * @return  true if the node has been successfully inserted
     */
    public boolean addNode(final Node node) {
        if (!this.containsNode(node)) {
            this.nodes.add(node);
            this.successors.put(node, new TreeSet<Edge>());
            this.predecessors.put(node, new TreeSet<Edge>());
            return true;
        }
        return false;
    }

    /**
     * Removes the specified node from this component.
     *
     * @param   node  the node to remove
     *
     * @return  true if the node was successfully removed
     */
    public boolean removeNode(final Node node) {
        if (this.containsNode(node)) {
            // Remove the edges (node,to) with key node in successors, and key to in predecessors
            for (Edge successor : this.successors.get(node)) {
                if (successor.getTo().compareTo(node) != 0) {
                     for (Edge predecessor : this.predecessors.get(successor.getTo())) {
                        if (predecessor.getFrom().compareTo(node) == 0) {
                            this.predecessors.get(successor.getTo()).remove(predecessor);
                        }
                    }
                }
                this.successors.remove(node);
            }
            // Remove the edges (from,node) with key node in predecessors, and key from in successors
            for (Edge predecessor : this.predecessors.get(node)) {
                if (predecessor.getFrom().compareTo(node) != 0) {
                     for (Edge successor : this.successors.get(predecessor.getFrom())) {
                        if (successor.getTo().compareTo(node) == 0) {
                            this.successors.get(predecessor.getFrom()).remove(successor);
                        }
                    }
                }
                this.predecessors.remove(node);
            }
            // Remove node
            this.nodes.remove(node);
            return true;
        }
        return false;
    }

    /**
     * Removes the specified set of nodes from this component.
     *
     * @param   nodes  set of nodes
     *
     * @return  true if all nodes were removed
     */
    public boolean removeNodes(final Set<Node> nodes) {
        boolean all = true;
        for (Node node : nodes) {
            if (!this.removeNode(node)) {
                all = false;
            }
        }
        return all;
    }

    /**
     * Checks if there exists an edge between the two specified nodes.
     *
     * @param   from  the node origine of the edge
     * @param   to    the node destination of the edge
     *
     * @return  true if the edge is contained by this component
     */
    public boolean containsEdge(final Node from, final Node to) {
        return this.containsNode(from)
            && this.containsNode(to)
            && this.getSuccessorNodes(from).contains(to)
            && this.getPredecessorNodes(to).contains(from);
    }

    /**
     * Checks if the specified edge belong to this component.
     *
     * @param   edge  the edge to be checked
     *
     * @return  true if the edge is contained by this component
     */
    public boolean containsEdge(final Edge edge) {
        return this.containsEdge(edge.getFrom(), edge.getTo());
    }

    /**
     * Adds an edge between the specified nodes to this component:
     * `to` is added as a successor of `from`.
     *
     * If the case where specified nodes don't belongs to the node set,
     * then the edge will not be added.
     *
     * @param   from     the node origin of the edge
     * @param   to       the node destination of the edge
     * @param   content  the edge content
     *
     * @return  true if the edge was successfully added
     */
    public boolean addEdge(final Node from, final Node to, final Object content) {
        if (this.containsNode(from) && this.containsNode(to)) {
            Edge edge = new Edge(from, to, content);
            this.successors.get(from).add(edge);
            this.predecessors.get(to).add(edge);
            return true;
        }
        return false;
    }

    /**
     * Adds an edge between the specified nodes to this component:
     * `to` is added as a successor of `from`.
     *
     * If the case where specified nodes don't belongs to the node set,
     * then the edge will not be added.
     *
     * @param   from  the node origin of the edge
     * @param   to    the node destination of the edge
     *
     * @return  true if the edge was successfully added
     */
    public boolean addEdge(final Node from, final Node to) {
        return this.addEdge(from, to, null);
    }

    /**
     * Adds the specified edge to this component in the successors of edge.getFrom() and in the
     * predecessors of edge.getTo().
     *
     * If the case where nodes to and from of this edges don't belongs to the node set,
     * then the edge will not be added.
     *
     * @param   edge  the edge to be added
     *
     * @return  true if the edge was added
     */
    public boolean addEdge(final Edge edge) {
        if (this.containsNode(edge.getFrom()) && this.containsNode(edge.getTo())) {
            this.successors.get(edge.getFrom()).add(edge);
            this.predecessors.get(edge.getTo()).add(edge);
            return true;
        }
        return false;
    }

    /**
     * Removes from this component the edge between the specified node.
     *
     * `to` is removed from the successors of `from`
     * and `to` is removed from the predecessors of `from`.
     *
     * @param   from  the node origine of the edge
     * @param   to    the node destination of the edge
     *
     * @return  true if the edge was removed
     */
    public boolean removeEdge(final Node from, final Node to) {
        if (this.containsEdge(from, to)) {
            Edge edge = new Edge(from, to);
            this.successors.get(from).remove(edge);
            this.predecessors.get(to).remove(edge);
            return true;
        }
        return false;
    }

    /**
     * Removes from this component the specified edge from the successors of edge.getFrom()
     * and from the predecessors of edge.getTo().
     *
     * @param   edge  the edge to be removed.
     *
     * @return  true if the edge was removed
     */
    public boolean removeEdge(final Edge edge) {
        if (this.containsEdge(edge)) {
            this.successors.get(edge.getFrom()).remove(edge);
            this.predecessors.get(edge.getTo()).remove(edge);
            return true;
        }
        return false;
    }

    /* --------------- ACYCLIC CHECKING METHODS ------------ */

    /**
     * Check if this component is acyclic.
     *
     * @return  true if the component is acyclic
     */
    public boolean isAcyclic() {
        ArrayList<Node> nodes = this.topologicalSort();
        return (nodes.size() == this.sizeNodes());
    }

    /**
     * Returns a topological sort of the node of this component.
     *
     * This topological sort is a sort on all the nodes according to their successors.
     * If the graph is not acyclic, some nodes don't belong to the sort.
     * This treatment is performed in O(n+m), where n corresponds to the number of nodes,
     * and m corresponds to the number of edges.
     *
     * @return  the nodes
     */
    public ArrayList<Node> topologicalSort() {
        TreeSet<Node> sinks = this.getSinks();
        // initialise a map with the number of predecessors (value) for each node (key);
        TreeMap<Node, Integer> size = new TreeMap<Node, Integer>();
        for (Node node : this.nodes) {
            size.put(node, new Integer(this.getPredecessorNodes(node).size()));
        }
        ArrayList<Node> sort = new ArrayList<Node>();
        while (!sinks.isEmpty()) {
            Node node = sinks.pollFirst();
            sort.add(node);
            // updating of the set min by considering the successors of node
            for (Node successor : this.getSuccessorNodes(node)) {
                int newSize = size.get(successor).intValue() - 1;
                size.put(successor, new Integer(newSize));
                if (newSize == 0) {
                    sinks.add(successor);
                }
            }
        }
        return sort;
    }

    /* --------------- GRAPH HANDLING METHODS ------------ */

   /**
    * Returns the sinks of this component.
    *
    * @return  the sinks
    *
    * @todo  use iterator pattern
    */
    public TreeSet<Node> getSinks() {
        TreeSet<Node> sinks = new TreeSet<Node>();
        for (Node node : this.nodes) {
            if (this.predecessors.get(node).isEmpty()) {
                sinks.add(node);
            }
        }
        return sinks;
    }

   /**
    * Returns the wells of this component.
    *
    * @return  the wells
    *
    * @todo  use iterator pattern
    */
    public TreeSet<Node> getWells() {
        TreeSet<Node> wells = new TreeSet<Node>();
        for (Node node : this.nodes) {
            if (this.successors.get(node).isEmpty()) {
                wells.add(node);
            }
        }
        return wells;
    }

    /**
     * Returns the subgraph of this component induced by the specified set of nodes.
     *
     * The subgraph only contains nodes of the specified set that also are in this component.
     *
     * @param   nodes  The set of nodes
     *
     * @return  The subgraph
     *
     * @todo  implement a SubGraph class?
     */
    public DGraph getSubgraphByNodes(final Set<Node> nodes) {
        DGraph graph = new DGraph();
        // addition of nodes in the subgraph
        for (Node node : nodes) {
            if (this.containsNode(node)) {
                graph.addNode(node);
            }
        }
        // addition of edges in the subgraph
        for (Edge edge : this.getEdges()) {
            if (graph.containsNode(edge.getTo())) {
                graph.addEdge(edge);
            }
        }
        return graph;
    }

    /**
     * Returns the subgraph of this component induced by the specified set of edges.
     *
     * The subgraph contains all nodes of this components, and
     * only edges of the specified set that also are in this component.
     *
     * @param   edges  The set of edges
     *
     * @return  The subgraph
     *
     * @todo  implement a SubGraph class?
     */
    public DGraph getSubgraphByEdges(final Set<Edge> edges) {
        DGraph graph = new DGraph();
        // addition of all nodes in the subgraph
        for (Node node : this.nodes) {
            graph.addNode(node);
        }
        // addition of specified edges in the subgraph
        for (Edge edge : edges) {
            if (this.containsEdge(edge)) {
                graph.addEdge(edge);
            }
        }
        return graph;
    }

    /**
     * Replaces this component by its complementary graph.
     *
     * There is an edge between to nodes in the complementary graph when
     * there is no edges between the nodes in this component.
     */
    public void complementary() {
        for (Node from : this.nodes) {
            TreeSet<Node> newSuccessors = new TreeSet<Node>(this.nodes);
            newSuccessors.removeAll(this.getSuccessorNodes(from));
            TreeSet<Node> oldSuccessors = new TreeSet<Node>(this.getSuccessorNodes(from));
            for (Node to : oldSuccessors) {
                this.removeEdge(from, to);
            }
            for (Node to : newSuccessors) {
                this.addEdge(from, to);
            }
        }
    }

    /* --------------- GRAPH TREATMENT METHODS ------------ */

    /**
     * Computes the reflexive reduction of this component.
     *
     * @return the number of removed edges
     */
    public int reflexiveReduction() {
        int size = 0;
        for (Node node : this.nodes) {
            if (this.containsEdge(node, node)) {
                size++;
                this.removeEdge(node, node);
            }
        }
        return size;
    }

    /**
     * Computes the reflexive closure of this component.
     *
     * @return  the number of added edges
     */
    public int reflexiveClosure() {
        int size = 0;
        for (Node node : this.nodes) {
            if (!this.containsEdge(node, node)) {
                size++;
                this.addEdge(node, node);
            }
        }
        return size;
    }

    /**
     * Computes the transitive closure of this component.
     *
     * This treatment is performed in O(nm+m_c), where n corresponds to the number of nodes,
     * m to the number of edges, and m_c to the number of edges in the closure.
     * This treatment improves the Roy-Warshall algorithm that directly implements
     * the definition in O(logm n^3).
     *
     * This treatment is overlapped in class {@link DAGraph}
     * with a more efficient algorithm dedicated to directed acyclic graph.
     *
     * @return  the number of added edges
     */
    public int transitiveClosure() {
        int size = 0;
        // mark each node to false
        TreeMap<Node, Boolean> mark = new TreeMap<Node, Boolean>();
        for (Node node : this.nodes) {
            mark.put(node, new Boolean(false));
        }
        // treatment of nodes
        for (Node x : this.nodes) {
            ArrayList<Node> list = new ArrayList<Node>();
            list.add(x);
            while (!list.isEmpty()) {
                Node y = list.remove(0);
                for (Node z : this.getSuccessorNodes(y)) {
                    // treatment of y when not marked
                    if (!mark.get(z).booleanValue()) {
                        mark.put(z, new Boolean(true));
                        this.addEdge(x, z);
                        size++;
                        list.add(z);
                    }
                }
            }
            for (Node y : this.getSuccessorNodes(x)) {
                mark.put(y, new Boolean(false));
            }
        }
        return size;
    }

     /**
      * Returns a two cells array containing the first visited sort on the nodes,
      * and the last visited sort on the nodes, by a depth first traverse issued from
      * the specified node.
      *
      * @param   source   The source node
      * @param   visited  The visited nodes
      * @param   sort     The sort parameter
      *
      * @return  The array
      *
      * @todo  Do we change that to iterators?
      *        Change to private and add a method that return an iterator
      */
     private ArrayList<Node>[] depthFirstSearch(Node source, TreeSet<Node> visited, ArrayList<Node> sort) {
        ArrayList<Node> first = new ArrayList<Node>();
        ArrayList<Node> last = new ArrayList<Node>();
        first.add(source);
        visited.add(source);
        ArrayList<Node>[] arrayVisited = new ArrayList[2];
        if (sort != null) {
            // search according to a sort
            for (Node node : sort) {
                if (this.getSuccessorNodes(source).contains(node) && !visited.contains(node)) {
                    arrayVisited = this.depthFirstSearch(node, visited, sort);
                    visited.addAll(arrayVisited[0]);
                    first.addAll(arrayVisited[0]);
                    last.addAll(arrayVisited[1]);
                }
            }
        } else {
            // classical search
            for (Node node : this.getSuccessorNodes(source)) {
                if (!visited.contains(node)) {
                    arrayVisited = this.depthFirstSearch(node, visited, sort);
                    visited.addAll(arrayVisited[0]);
                    first.addAll(arrayVisited[0]);
                    last.addAll(arrayVisited[1]);
                }
            }
        }
        last.add(source);
        arrayVisited[0] = first;
        arrayVisited[1] = last;
        return arrayVisited;
    }

     /**
      * Returns a two cells array containing the first visited sort on the nodes,
      * and the last visited sort on the nodes, by a depth first search.
      *
      * @return  The array
      */
    private ArrayList<Node>[] depthFirstSearch() {
        TreeSet<Node> visited = new TreeSet<Node>();
        ArrayList<Node>[] arrayVisited = new ArrayList[2];
        ArrayList<Node> first = new ArrayList<Node>();
        ArrayList<Node> last = new ArrayList<Node>();
        for (Node node : this.nodes) {
            if (!visited.contains(node)) {
                arrayVisited = this.depthFirstSearch(node, visited, null);
                visited.addAll(arrayVisited[0]);
                first.addAll(arrayVisited[0]);
                last.addAll(arrayVisited[1]);
            }
        }
        arrayVisited[0] = first;
        arrayVisited[1] = last;
        return arrayVisited;
    }

    /**
     * Transposes this component by replacing for each node
     * its successor set by its predecessor set, and its predecessor set
     * by its successor set.
     */
    public void transpose() {
        DGraph tmp = new DGraph(this);
        for (Edge edge : tmp.getEdges()) {
             this.removeEdge(edge);
             this.addEdge(new Edge(edge.getTo(), edge.getFrom(), edge.getContent()));
        }
    }

    /**
     * Returns the directed acyclic graph where each node corresponds to a
     * strongly connected component (SCC) of this component stored in a TreeSet of nodes.
     *
     * When two nodes in two different SCC are in relation, the same is for the SCC
     * they belongs to.
     *
     * @return  The directed acyclic graph
     */
    public DAGraph getStronglyConnectedComponent() {
        DAGraph cc = new DAGraph();
        // first depth first search
        DGraph tmp = new DGraph(this);
        tmp.transitiveClosure();
        ArrayList<Node> last = tmp.depthFirstSearch()[1];
        // transposition of the graph
        DGraph transposedGraph = new DGraph(this);
        transposedGraph.transpose();
        // sort nodes according to the reverse last sort
        ArrayList<Node> sort = new ArrayList<Node>();
        Object [] array = last.toArray();
        for (int i = array.length - 1; i >= 0; i--) {
            sort.add((Node) array[i]);
        }
        // second depth first search according to sort
        TreeSet<Node> visited = new TreeSet<Node>();
        for (Node n : sort) {
            if (!visited.contains(n)) {
                last = transposedGraph.depthFirstSearch(n, visited, sort)[1];
                visited.addAll(last);
                TreeSet<Node> sCC = new TreeSet<Node>(last);
                // a strongly connected component is generated
                cc.addNode(new Node(sCC));    // addition of
            }
        }
        // edges between strongly connected component
        for (Node ccFrom : cc.getNodes()) {
            for (Node ccTo : cc.getNodes()) {
                for (Node from : (TreeSet<Node>) ccFrom.getContent()) {
                    for (Node to : (TreeSet<Node>) ccTo.getContent()) {
                        if (tmp.getSuccessorNodes(from).contains(to)) {
                            cc.addEdge(ccFrom, ccTo);
                        }
                    }
                }
            }
        }
        cc.reflexiveReduction();
        return cc;
    }
}


package org.thegalactic.dgraph;

/*
 * ConcreteDGraph.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class gives a standard representation for a directed graph by sets of
 * successors and predecessors.
 *
 * A directed graph is composed of
 *
 * - a treeset of node, defined by class {@link Node}; - a treemap of successors
 * that associates to each node a treeset of successors, defined by class
 * {@link Edge}; - a treemap of predecessors that associates to each node a
 * treeset of predecessors, defined by class {@link Edge}.

 This class provides methods implementing classical operation on a directed
 graph:

 - sinks - wells - subgraph - reflexive closure and reduction - transitive
 closure - strongly connected components - ...

 This class also provides a static method randomly generating a directed
 graph.

 ![ConcreteDGraph](ConcreteDGraph.png)
 *
 * @uml DGraph.png
 * !include resources/org/thegalactic/dgraph/DGraph.iuml
 * !include resources/org/thegalactic/dgraph/Edge.iuml
 * !include resources/org/thegalactic/dgraph/Node.iuml
 *
 * hide members
 * show DGraph members
 * class DGraph #LightCyan
 * title DGraph UML graph
 */
public class ConcreteDGraph extends AbstractDGraph implements Cloneable {

    /*
     * ------------- FIELDS ------------------
     */
    /**
     * A set of elements.
     */
    private SortedSet<Node> nodes;

    /**
     * A map to associate a set of successors to each node.
     */
    private SortedMap<Node, SortedSet<Edge>> successors;

    /**
     * A map to associate a set of predecessors to each node.
     */
    private SortedMap<Node, SortedSet<Edge>> predecessors;


    /*
     * ------------- CONSTRUCTORS ------------------
     */
    /**
     * Constructs a new directed graph with an empty set of node.
     */
    public ConcreteDGraph() {
        this.nodes = new TreeSet<Node>();
        this.successors = new TreeMap<Node, SortedSet<Edge>>();
        this.predecessors = new TreeMap<Node, SortedSet<Edge>>();
    }

    /**
     * Constructs a new directed graph source the specified set of nodes.
     *
     * Successors and predecessors of each nodes are initialised by an empty
     * set.
     *
     * @param set the set of nodes
     */
    public ConcreteDGraph(final Set<Node> set) {
        this.nodes = new TreeSet<Node>(set);
        this.successors = new TreeMap<Node, SortedSet<Edge>>();
        for (Node node : this.nodes) {
            this.successors.put(node, new TreeSet<Edge>());
        }
        this.predecessors = new TreeMap<Node, SortedSet<Edge>>();
        for (Node node : this.nodes) {
            this.predecessors.put(node, new TreeSet<Edge>());
        }
    }

    /**
     * Constructs this component as a copy of the specified directed graph.
     *
     * @param graph the directed graph to be copied
     */
    public ConcreteDGraph(final ConcreteDGraph graph) {
        this.nodes = new TreeSet<Node>(graph.nodes);
        this.successors = new TreeMap<Node, SortedSet<Edge>>();
        this.predecessors = new TreeMap<Node, SortedSet<Edge>>();
        for (Node node : graph.nodes) {
            this.successors.put(node, new TreeSet<Edge>(graph.successors.get(node)));
            this.predecessors.put(node, new TreeSet<Edge>(graph.predecessors.get(node)));
        }
    }

    /*
     * --------------- ACCESSOR AND OVERLAPPING METHODS ------------
     */
    /**
     * Returns a clone of this component composed of a clone of each node and
     * each edge.
     *
     * @return the clone of this
     *
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public ConcreteDGraph clone() throws CloneNotSupportedException {
        return new ConcreteDGraph(this);
    }

    /**
     * Returns the set of nodes of this component.
     *
     * @return the set of nodes
     */
    public final SortedSet<Node> getNodes() {
        return Collections.unmodifiableSortedSet(this.nodes);
    }

    /**
     * Returns the set of edges of this component.
     *
     * @return the set of edges
     */
    public final SortedSet<Edge> getEdges() {
        return new Edges(this);
    }

    /**
     * Returns the set of edges successors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of edges
     */
    public final SortedSet<Edge> getSuccessorEdges(final Node node) {
        return Collections.unmodifiableSortedSet(this.successors.get(node));
    }

    /**
     * Returns the set of edges predecessors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of edges
     */
    public final SortedSet<Edge> getPredecessorEdges(final Node node) {
        return Collections.unmodifiableSortedSet(this.predecessors.get(node));
    }

    /**
     * Returns the set of nodes successors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of nodes
     *
     * @todo use iterator pattern (some changes in ArrowRelation.java and
     * Lattice.java)
     */
    public final SortedSet<Node> getSuccessorNodes(final Node node) {
        final SortedSet<Node> successorsFromNode = new TreeSet<Node>();
        for (final Edge edge : this.successors.get(node)) {
            successorsFromNode.add(edge.getTarget());
        }
        return Collections.unmodifiableSortedSet(successorsFromNode);
    }

    /**
     * Returns the set of nodes predecessors of the specified node.
     *
     * @param node the node to search for
     *
     * @return the set of nodes
     *
     * @todo use iterator pattern (some changes in ArrowRelation.java and
     * Lattice.java)
     */
    public final SortedSet<Node> getPredecessorNodes(final Node node) {
        final SortedSet<Node> predecessorsFromNode = new TreeSet<Node>();
        for (final Edge edge : this.predecessors.get(node)) {
            predecessorsFromNode.add(edge.getSource());
        }
        return Collections.unmodifiableSortedSet(predecessorsFromNode);
    }

    /**
     * Returns, if it exists, the edge between node source and node to.
     *
     * @param source The origin node
     * @param target The destination node
     *
     * @return the found edge or null
     *
     * @todo see getNode
     */
    public final Edge getEdge(final Node source, final Node target) {
        SortedSet<Edge> node = successors.get(source);
        if (node == null) {
            return null;
        }
        if (this.containsEdge(source, target)) {
            for (final Edge edge : this.successors.get(source)) {
                if (edge.getTarget().equals(target)) {
                    return edge;
                }
            }
        }
        return null;
    }

    /**
     * Returns the node that is equal to the specified one.
     *
     * @param search The node to search for
     *
     * @return the found node or null
     *
     * @todo maybe use try { return this.nodes.subSet(search, true, search,
     * true).first(); } catch (NoSuchElementException e) { return null; }
     *
     */
    public final Node getNode(final Node search) {
        for (final Node node : this.nodes) {
            if (node.equals(search)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the node whose content is equal to the specified one.
     *
     * @param content The content to search for
     *
     * @return the found node or null
     *
     * @todo this method is not efficient. Do we remove it or add an index on
     * Dgraph using content field? Verify where it is called for migrating it if
     * necessary.
     */
    public final Node getNodeByContent(final Object content) {
        for (final Node node : this.nodes) {
            if (node.getContent().equals(content)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the node whose ident is equal to the specified one.
     *
     * @param identifier node identifier
     *
     * @return the found node or null
     */
    public final Node getNodeByIdentifier(int identifier) {
        for (final Node node : this.nodes) {
            if (node.getIdentifier() == identifier) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the number of nodes of this component.
     *
     * @return the number of nodes
     */
    public final int sizeNodes() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges of this component.
     *
     * @return the number of edges
     */
    public final int sizeEdges() {
        int size = 0;
        for (final Node node : this.nodes) {
            size += this.successors.get(node).size();
        }
        return size;
    }


    /*
     * --------------- NODES AND EDGES MODIFICATION METHODS ------------
     */
    /**
     * Checks if the specified node belong to this component.
     *
     * @param node the node to insert
     *
     * @return true if the node has been successfully inserted
     */
    public boolean containsNode(final Node node) {
        return this.nodes.contains(node);
    }

    /**
     * Adds the specified node to the set of node of this component.
     *
     * @param node the node to insert
     *
     * @return true if the node has been successfully inserted
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
     * @param node the node to remove
     *
     * @return true if the node was successfully removed
     */
    public boolean removeNode(final Node node) {
        if (this.containsNode(node)) {
            // Remove the edges (node,target) with key node in successors, and key target in predecessors
            for (final Edge successor : this.successors.get(node)) {
                if (successor.getTarget().compareTo(node) != 0) {
                    for (final Edge predecessor : this.predecessors.get(successor.getTarget())) {
                        if (predecessor.getSource().compareTo(node) == 0) {
                            this.predecessors.get(successor.getTarget()).remove(predecessor);
                        }
                    }
                }
                this.successors.remove(node);
            }
            // Remove the edges (source,node) with key node in predecessors, and key source in successors
            for (final Edge predecessor : this.predecessors.get(node)) {
                if (predecessor.getSource().compareTo(node) != 0) {
                    for (final Edge successor : this.successors.get(predecessor.getSource())) {
                        if (successor.getTarget().compareTo(node) == 0) {
                            this.successors.get(predecessor.getSource()).remove(successor);
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
     * @param nodes set of nodes
     *
     * @return true if all nodes were removed
     */
    public boolean removeNodes(final Set<Node> nodes) {
        boolean all = true;
        for (final Node node : nodes) {
            if (!this.removeNode(node)) {
                all = false;
            }
        }
        return all;
    }

    /**
     * Checks if there exists an edge between the two specified nodes.
     *
     * @param source the node origine of the edge
     * @param target the node destination of the edge
     *
     * @return true if the edge is contained by this component
     */
    public boolean containsEdge(final Node source, final Node target) {
        return this.containsNode(source)
                && this.containsNode(target)
                && this.getSuccessorNodes(source).contains(target)
                && this.getPredecessorNodes(target).contains(source);
    }

    /**
     * Checks if the specified edge belong to this component.
     *
     * @param edge the edge to be checked
     *
     * @return true if the edge is contained by this component
     */
    public boolean containsEdge(final Edge edge) {
        return this.containsEdge(edge.getSource(), edge.getTarget());
    }

    /**
     * Adds an edge between the specified nodes to this component: `to` is added
     * as a successor of `source`.
     *
     * If the case where specified nodes don't belongs to the node set, then the
     * edge will not be added.
     *
     * @param source  the node origin of the edge
     * @param target  the node destination of the edge
     * @param content the edge content
     *
     * @return true if the edge was successfully added
     */
    public boolean addEdge(final Node source, final Node target, final Object content) {
        if (this.containsNode(source) && this.containsNode(target)) {
            final Edge edge = new Edge(source, target, content);
            this.successors.get(source).add(edge);
            this.predecessors.get(target).add(edge);
            return true;
        }
        return false;
    }

    /**
     * Adds an edge between the specified nodes to this component: `to` is added
     * as a successor of `source`.
     *
     * If the case where specified nodes don't belongs to the node set, then the
     * edge will not be added.
     *
     * @param source the node origin of the edge
     * @param target the node destination of the edge
     *
     * @return true if the edge was successfully added
     */
    public boolean addEdge(final Node source, final Node target) {
        return this.addEdge(source, target, null);
    }

    /**
     * Adds the specified edge to this component in the successors of
     * edge.getSource() and in the predecessors of edge.getTarget().
     *
     * If the case where nodes to and source of this edges don't belongs to the
     * node set, then the edge will not be added.
     *
     * @param edge the edge to be added
     *
     * @return true if the edge was added
     */
    public boolean addEdge(final Edge edge) {
        if (this.containsNode(edge.getSource()) && this.containsNode(edge.getTarget())) {
            this.successors.get(edge.getSource()).add(edge);
            this.predecessors.get(edge.getTarget()).add(edge);
            return true;
        }
        return false;
    }

    /**
     * Removes source this component the edge between the specified node.
     *
     * `to` is removed source the successors of `source` and `to` is removed
     * source
     * the predecessors of `source`.
     *
     * @param source the node origine of the edge
     * @param target the node destination of the edge
     *
     * @return true if the edge was removed
     */
    public boolean removeEdge(final Node source, final Node target) {
        if (this.containsEdge(source, target)) {
            Edge edge = new Edge(source, target);
            this.successors.get(source).remove(edge);
            this.predecessors.get(target).remove(edge);
            return true;
        }
        return false;
    }

    /**
     * Removes source this component the specified edge source the successors of
     * edge.getSource() and source the predecessors of edge.getTarget().
     *
     * @param edge the edge to be removed.
     *
     * @return true if the edge was removed
     */
    public boolean removeEdge(final Edge edge) {
        if (this.containsEdge(edge)) {
            this.successors.get(edge.getSource()).remove(edge);
            this.predecessors.get(edge.getTarget()).remove(edge);
            return true;
        }
        return false;
    }

    /*
     * --------------- ACYCLIC CHECKING METHODS ------------
     */
    /**
     * Check if this component is acyclic.
     *
     * @return true if the component is acyclic
     */
    public boolean isAcyclic() {
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
    public List<Node> topologicalSort() {
        TreeSet<Node> sinks = new TreeSet<Node>(this.getSinks());
        // initialise a map with the number of predecessors (value) for each node (key);
        TreeMap<Node, Integer> size = new TreeMap<Node, Integer>();
        for (Node node : this.nodes) {
            size.put(node, Integer.valueOf(this.getPredecessorNodes(node).size()));
        }
        List<Node> sort = new ArrayList<Node>();
        while (!sinks.isEmpty()) {
            Node node = sinks.pollFirst();
            sort.add(node);
            // updating of the set min by considering the successors of node
            for (Node successor : this.getSuccessorNodes(node)) {
                int newSize = size.get(successor).intValue() - 1;
                size.put(successor, Integer.valueOf(newSize));
                if (newSize == 0) {
                    sinks.add(successor);
                }
            }
        }
        return sort;
    }


    /**
     * Returns the subgraph of this component induced by the specified set of
     * nodes.
     *
     * The subgraph only contains nodes of the specified set that also are in
     * this component.
     *
     * @param nodes The set of nodes
     *
     * @return The subgraph
     *
     * @todo implement a SubGraph class?
     */
    public ConcreteDGraph getSubgraphByNodes(final Set<Node> nodes) {
        ConcreteDGraph graph = new ConcreteDGraph();
        // addition of nodes in the subgraph
        for (Node node : nodes) {
            if (this.containsNode(node)) {
                graph.addNode(node);
            }
        }
        // addition of edges in the subgraph
        for (Edge edge : this.getEdges()) {
            if (graph.containsNode(edge.getTarget())) {
                graph.addEdge(edge);
            }
        }
        return graph;
    }

    /**
     * Returns the subgraph of this component induced by the specified set of
     * edges.
     *
     * The subgraph contains all nodes of this components, and only edges of the
     * specified set that also are in this component.
     *
     * @param edges The set of edges
     *
     * @return The subgraph
     *
     * @todo implement a SubGraph class?
     */
    public ConcreteDGraph getSubgraphByEdges(final Set<Edge> edges) {
        ConcreteDGraph graph = new ConcreteDGraph();
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
     * There is an edge between to nodes in the complementary graph when there
     * is no edges between the nodes in this component.
     */
    public void complementary() {
        for (Node source : this.nodes) {
            TreeSet<Node> newSuccessors = new TreeSet<Node>(this.nodes);
            newSuccessors.removeAll(this.getSuccessorNodes(source));
            TreeSet<Node> oldSuccessors = new TreeSet<Node>(this.getSuccessorNodes(source));
            for (Node target : oldSuccessors) {
                this.removeEdge(source, target);
            }
            for (Node target : newSuccessors) {
                this.addEdge(source, target);
            }
        }
    }

    /*
     * --------------- GRAPH TREATMENT METHODS ------------
     */
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
     * @return the number of added edges
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
     * This treatment is performed in O(nm+m_c), where n corresponds to the
     * number of nodes, m to the number of edges, and m_c to the number of edges
     * in the closure. This treatment improves the Roy-Warshall algorithm that
     * directly implements the definition in O(logm n^3).
     *
     * This treatment is overlapped in class {@link DAGraph} with a more
     * efficient algorithm dedicated to directed acyclic graph.
     *
     * @return the number of added edges
     */
    public int transitiveClosure() {
        int size = 0;
        // mark each node to false
        TreeMap<Node, Boolean> mark = new TreeMap<Node, Boolean>();
        for (Node node : this.nodes) {
            mark.put(node, Boolean.FALSE);
        }
        // treatment of nodes
        for (Node x : this.nodes) {
            ArrayList<Node> list = new ArrayList<Node>();
            list.add(x);
            while (!list.isEmpty()) {
                Node y = list.remove(0);
                for (Node z : this.getSuccessorNodes(y)) {
                    // treatment of y when not marked
                    if (!mark.get(z)) {
                        mark.put(z, Boolean.TRUE);
                        this.addEdge(x, z);
                        size++;
                        list.add(z);
                    }
                }
            }
            for (Node y : this.getSuccessorNodes(x)) {
                mark.put(y, Boolean.FALSE);
            }
        }
        return size;
    }

    /**
     * Transposes this component by replacing for each node its successor set by
     * its predecessor set, and its predecessor set by its successor set.
     */
    public void transpose() {
        ConcreteDGraph tmp = new ConcreteDGraph(this);
        for (Edge edge : tmp.getEdges()) {
            this.removeEdge(edge);
            this.addEdge(new Edge(edge.getTarget(), edge.getSource(), edge.getContent()));
        }
    }

    /**
     * Returns the directed acyclic graph where each node corresponds to a
     * strongly connected component (SCC) of this component stored in a TreeSet
     * of nodes.
     *
     * When two nodes in two different SCC are in relation, the same is for the
     * SCC they belongs to.
     *
     * @return The directed acyclic graph
     */
    public DAGraph getStronglyConnectedComponent() {
        DAGraph cc = new DAGraph();
        // first depth first search
        ConcreteDGraph tmp = new ConcreteDGraph(this);
        tmp.transitiveClosure();
        ArrayList<Node> last = tmp.depthFirstSearch()[1];
        // transposition of the graph
        ConcreteDGraph transposedGraph = new ConcreteDGraph(this);
        transposedGraph.transpose();
        // sort nodes according to the reverse last sort
        ArrayList<Node> sort = new ArrayList<Node>();
        Object[] array = last.toArray();
        for (int i = array.length - 1; i >= 0; i--) {
            sort.add((Node) array[i]);
        }
        // second depth first search according to sort
        TreeSet<Node> visited = new TreeSet<Node>();
        for (Node node : sort) {
            if (!visited.contains(node)) {
                last = transposedGraph.depthFirstSearch(node, visited, sort)[1];
                visited.addAll(last);
                TreeSet<Node> sCC = new TreeSet<Node>(last);
                // a strongly connected component is generated
                cc.addNode(new Node(sCC));    // addition of
            }
        }
        // edges between strongly connected component
        for (Node ccSource : cc.getNodes()) {
            for (Node ccTarget : cc.getNodes()) {
                for (Node source : (TreeSet<Node>) ccSource.getContent()) {
                    for (Node target : (TreeSet<Node>) ccTarget.getContent()) {
                        if (tmp.getSuccessorNodes(source).contains(target)) {
                            cc.addEdge(ccSource, ccTarget);
                        }
                    }
                }
            }
        }
        cc.reflexiveReduction();
        return cc;
    }

    /**
     * Set the set of nodes of this component.
     *
     * @param nodes The nodes
     *
     * @return this for chaining
     */
    protected ConcreteDGraph setNodes(final TreeSet<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    /**
     * Returns the successors of this component.
     *
     * @return the map
     */
    protected SortedMap<Node, SortedSet<Edge>> getSuccessors() {
        return this.successors;
    }

    /**
     * Set the successors of this component.
     *
     * @param successors The successors
     *
     * @return this for chaining
     */
    protected ConcreteDGraph setSuccessors(final TreeMap<Node, SortedSet<Edge>> successors) {
        this.successors = successors;
        return this;
    }

    /**
     * Returns the predecessors of this component.
     *
     * @return the map
     */
    protected SortedMap<Node, SortedSet<Edge>> getPredecessors() {
        return this.predecessors;
    }

    /**
     * Set the predecessors of this component.
     *
     * @param predecessors The predecessors
     *
     * @return this for chaining
     */
    protected ConcreteDGraph setPredecessors(final TreeMap<Node, SortedSet<Edge>> predecessors) {
        this.predecessors = predecessors;
        return this;
    }

    /**
     * Returns a two cells array containing the first visited sort on the nodes,
     * and the last visited sort on the nodes, by a depth first traverse issued
     * source the specified node.
     *
     * @param source  The source node
     * @param visited The visited nodes
     * @param sort    The sort parameter
     *
     * @return The array
     *
     * @todo Do we change that to iterators? Change to private and add a method
     * that return an iterator
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
     * @return The array
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
     * This class implements a sorted set of the edges.
     */
    private class Edges extends AbstractSet<Edge> implements SortedSet<Edge> {

        /**
         * The underlying graph.
         */
        private ConcreteDGraph graph;

        /**
         * Get the underlying graph.
         *
         * @return the graph
         */
        protected ConcreteDGraph getGraph() {
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
             * Constructs the iterator source a set of edges.
             *
             * @param edges The edges.
             */
            EdgesIterator(Edges edges) {
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
             * @throws UnsupportedOperationException
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            /**
             * The next method returns the next edge.
             *
             * @return The next edge
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
             * @return true if the iterator has a next edge
             */
            public boolean hasNext() {
                return hasNext;
            }
        }

        /**
         * Constructs a sorted set of the edges source a graph.
         *
         * @param graph A ConcreteDGraph
         */
        Edges(ConcreteDGraph graph) {
            this.graph = graph;
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the first edge
         */
        public Edge first() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return the last edge
         */
        public Edge last() {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param edge the to edge
         *
         * @return The head set
         *
         * @throws UnsupportedOperationException
         */
        public SortedSet<Edge> headSet(Edge edge) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param edge the source edge
         *
         * @return The tail set
         *
         * @throws UnsupportedOperationException
         */
        public SortedSet<Edge> tailSet(Edge edge) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @param fromEdge the source edge
         * @param toEdge   the to edge
         *
         * @return The sub set
         *
         * @throws UnsupportedOperationException
         */
        public SortedSet<Edge> subSet(Edge fromEdge, Edge toEdge) {
            throw new UnsupportedOperationException();
        }

        /**
         * Implements the SortedSet interface.
         *
         * @return null
         */
        public Comparator<? super Edge> comparator() {
            return null;
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return the size of the collection
         */
        public int size() {
            return graph.sizeEdges();
        }

        /**
         * Implements the AbstractCollection class.
         *
         * @return a new edges iterator
         */
        public EdgesIterator iterator() {
            return new EdgesIterator(this);
        }
    }
}

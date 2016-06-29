package org.thegalactic.dgraph;

/*
 * DAGraph.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class extends the representation of a directed graph given by class
 * {@link ConcreteDGraph} for directed acyclic graph (DAG).
 *
 * The main property of a directed acyclic graph is to be a partially ordered
 * set (poset) when transitively closed, and a Hasse diagram when transitively
 * reduced.
 *
 * This property is not ensured for components of this class because it would
 * require a checking treatment over the graph whenever a new edge or node is
 * added. However, this property can be explicitely ckecked using method
 * {@link #isAcyclic}.
 *
 * This class provides methods implementing classical operation on a directed
 * acyclic graph: minorants and majorants, filter and ideal, transitive
 * reduction, ideal lattice, ...
 *
 * This class also provides a static method randomly generating a directed
 * acyclic graph, and a static method generating the graph of divisors.
 *
 * ![DAGraph](DAGraph.png)
 *
 * @param <N> Node content type
 * @param <E> Edge content type
 *
 * @todo Do we forbid to add an edge that breaks acyclic property by verifying
 * that the destination node has no successors? May be a DAGraph could contain a
 * DGraph and export only interesting method by proxy
 *
 * @uml DAGraph.png
 * !include resources/org/thegalactic/dgraph/DAGraph.iuml
 * !include resources/org/thegalactic/dgraph/DGraph.iuml
 * !include resources/org/thegalactic/dgraph/Edge.iuml
 * !include resources/org/thegalactic/dgraph/Node.iuml
 *
 * hide members
 * show DAGraph members
 * class DAGraph #LightCyan
 * title DAGraph UML graph
 */
public class DAGraph<N, E> extends ConcreteDGraph<N, E> {

    /*
     * ----------- STATIC GENERATION METHODS -------------
     */
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
    public static DAGraph<Integer, ?> divisors(int number) {
        DAGraph<Integer, ?> graph = new DAGraph<Integer, Object>();
        // addition of nodes
        for (int i = 2; i <= number; i++) {
            graph.addNode(new Node<Integer>(Integer.valueOf(i)));
        }
        // addition of edges
        for (Node<Integer> source : graph.getNodes()) {
            for (Node<Integer> target : graph.getNodes()) {
                int v1 = ((Integer) source.getContent()).intValue();
                int v2 = ((Integer) target.getContent()).intValue();
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
    public static DAGraph<Integer, ?> random(int size, double threshold) {
        DAGraph<Integer, ?> graph = new DAGraph<Integer, Object>();
        // addition of Nodes
        for (int i = 1; i <= size; i++) {
            graph.addNode(new Node<Integer>(Integer.valueOf(i)));
        }
        // addition of edges
        for (Node<Integer> source : graph.getNodes()) {
            for (Node<Integer> target : graph.getNodes()) {
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
    public static DAGraph<Integer, ?> random(int size) {
        return random(size, 0.5);
    }

    /**
     * Constructs a new DAG with an empty set of node.
     */
    public DAGraph() {
        super();
    }

    /**
     * Constructs this component with the specified set of nodes, and empty
     * treemap of successors and predecessors.
     *
     * @param set the set of nodes
     */
    public DAGraph(final SortedSet<Node<N>> set) {
        super(set);
    }

    /**
     * Constructs this component as a copy of the specified directed graph.
     *
     * Acyclic property is checked for the specified DAG. When not verified,
     * this component is construct with the same set of nodes but with no edges.
     *
     * @param graph the ConcreteDGraph to be copied
     */
    public DAGraph(final ConcreteDGraph<N, E> graph) {
        super(graph);
        if (this.isAcyclic()) {
            this.reflexiveReduction();
        } else {
            TreeMap<Node<N>, TreeSet<Edge<N, E>>> successors = new TreeMap<Node<N>, TreeSet<Edge<N, E>>>();
            TreeMap<Node<N>, TreeSet<Edge<N, E>>> predecessors = new TreeMap<Node<N>, TreeSet<Edge<N, E>>>();
            for (Node<N> node : this.getNodes()) {
                successors.put(node, new TreeSet<Edge<N, E>>());
                predecessors.put(node, new TreeSet<Edge<N, E>>());
            }
            this.setSuccessors(successors);
            this.setPredecessors(predecessors);
        }
    }

    /*
     * --------------- DAG HANDLING METHODS ------------
     */
    /**
     * Returns the minimal element of this component.
     *
     * @return the minimal element
     */
    public SortedSet<Node<N>> min() {
        return this.getSinks();
    }

    /**
     * Returns the maximal element of this component.
     *
     * @return the maximal element
     */
    public SortedSet<Node<N>> max() {
        return this.getWells();
    }

    /**
     * Returns the set of majorants of the specified node.
     *
     * Majorants of a node are its successors in the transitive closure
     *
     * @param node the specified node
     *
     * @return the set of majorants
     */
    public SortedSet<Node<N>> majorants(final Node<N> node) {
        DAGraph graph = new DAGraph(this);
        graph.transitiveClosure();
        return graph.getSuccessorNodes(node);
    }

    /**
     * Returns the set of minorants of the specified node.
     *
     * Minorants of a node are its predecessors in the transitive closure
     *
     * @param node the specified node
     *
     * @return the set of minorants
     */
    public SortedSet<Node<N>> minorants(final Node<N> node) {
        DAGraph graph = new DAGraph(this);
        graph.transitiveClosure();
        return graph.getPredecessorNodes(node);
    }

    /**
     * Returns the subgraph induced by the specified node and its successors in
     * the transitive closure.
     *
     * @param node the specified node
     *
     * @return the subgraph
     */
    public DAGraph<N, E> filter(final Node<N> node) {
        TreeSet<Node<N>> set = new TreeSet<Node<N>>(this.majorants(node));
        set.add(node);
        return this.getSubgraphByNodes(set);
    }

    /**
     * Returns the subgraph induced by the specified node and its predecessors
     * in the transitive closure.
     *
     * @param node the specified node
     *
     * @return the subgraph
     */
    public DAGraph<N, E> ideal(final Node<N> node) {
        TreeSet<Node<N>> set = new TreeSet<Node<N>>(this.minorants(node));
        set.add(node);
        return this.getSubgraphByNodes(set);
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
     */
    @Override
    public DAGraph<N, E> getSubgraphByNodes(final Set<Node<N>> nodes) {
        ConcreteDGraph tmp = new ConcreteDGraph(this);
        tmp.transitiveClosure();
        ConcreteDGraph sub = tmp.getSubgraphByNodes(nodes);
        DAGraph sub2 = new DAGraph(sub);
        sub2.transitiveReduction();
        return sub2;
    }

    /*
     * --------------- DAG TREATMENT METHODS ------------
     */
    /**
     * Computes the transitive reduction of this component.
     *
     * The transitive reduction is not uniquely defined only when the acyclic
     * property is verified. In this case, it corresponds to the Hasse diagram
     * of the DAG.
     *
     * This method is an implementation of the Goralcikova-Koubeck algorithm
     * that can also compute the transitive closure. This tratment is performed
     * in O(n+nm_r+nm_c), where n corresponds to the number of nodes, m_r to the
     * numer of edges in the transitive closure, and m_r the number of edges in
     * the transitive reduction.
     *
     * @return the number of added edges
     */
    public int transitiveReduction() {

        // copy this component in a new DAG graph
        DAGraph<N, E> graph = new DAGraph<N, E>(this);
        graph.reflexiveReduction();
        // initalize this component with no edges
        this.setSuccessors(new TreeMap<Node<N>, TreeSet<Edge<N, E>>>());
        for (Node<N> node : this.getNodes()) {
            this.getSuccessors().put(node, new TreeSet<Edge<N, E>>());
        }
        this.setPredecessors(new TreeMap<Node<N>, TreeSet<Edge<N, E>>>());
        for (Node<N> node : this.getNodes()) {
            this.getPredecessors().put(node, new TreeSet<Edge<N, E>>());
        }
        int number = 0;
        // mark each node to false
        TreeMap<Node<N>, Boolean> mark = new TreeMap<Node<N>, Boolean>();
        for (Node<N> node : graph.getNodes()) {
            mark.put(node, Boolean.FALSE);
        }
        // treatment of nodes according to a topological sort
        List<Node<N>> sort = graph.topologicalSort();
        for (Node<N> x : sort) {
            TreeSet<Node<N>> set = new TreeSet<Node<N>>(graph.getSuccessorNodes(x));
            while (!set.isEmpty()) {
                // compute the smallest successor y of x according to the topological sort
                int i = 0;
                while (!set.contains(sort.get(i))) {
                    i++;
                }
                Node<N> y = sort.get(i);
                // when y is not not marked, x->y is a reduced edge
                if (y != null && !mark.get(y)) {
                    this.addEdge(x, y);
                    graph.addEdge(x, y);
                }
                for (Node<N> z : graph.getSuccessorNodes(y)) {
                    // treatment of z when not marked
                    if (!mark.get(z)) {
                        mark.put(z, Boolean.TRUE);
                        graph.addEdge(x, z);
                        number++;
                        set.add(z);
                    }
                }
                set.remove(y);
            }
            for (Node<N> y : graph.getSuccessorNodes(x)) {
                mark.put(y, Boolean.FALSE);
            }
        }
        return number;
    }

    /**
     * Computes the transitive closure of this component.
     *
     * This method overlaps the computation of the transitive closure for
     * directed graph in class {@link ConcreteDGraph} with an implementation of the
     * Goralcikova-Koubeck algorithm dedicated to acyclic directed graph. This
     * algorithm can also compute the transitive reduction of a directed acyclic
     * graph.
     *
     * This treatment is performed in O(n+nm_r+nm_c), where n corresponds to the
     * number of nodes, m_r to the numer of edges in the transitive closure, and
     * m_r the number of edges in the transitive reduction.
     *
     * @return the number of added edges
     */
    public int transitiveClosure() {
        int number = 0;
        // mark each node to false
        TreeMap<Node<N>, Boolean> mark = new TreeMap<Node<N>, Boolean>();
        for (Node<N> node : this.getNodes()) {
            mark.put(node, Boolean.FALSE);
        }
        // treatment of nodes according to a topological sort
        List<Node<N>> sort = this.topologicalSort();
        for (Node<N> x : sort) {
            TreeSet<Node<N>> set = new TreeSet<Node<N>>(this.getSuccessorNodes(x));
            while (!set.isEmpty()) {
                // compute the smallest successor y of x according to the topological sort
                int i = 0;
                do {
                    i++;
                } while (!set.contains(sort.get(i)));
                Node<N> y = sort.get(i);
                for (Node<N> z : this.getSuccessorNodes(y)) {
                    // treatment of z when not marked
                    if (!mark.get(z)) {
                        mark.put(z, Boolean.TRUE);
                        this.addEdge(x, z);
                        number++;
                        set.add(z);
                    }
                }
                set.remove(y);
            }
            for (Node<N> y : this.getSuccessorNodes(x)) {
                mark.put(y, Boolean.FALSE);
            }
        }
        return number;
    }
}

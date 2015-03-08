package org.thegalactic.dgraph;

/*
 * DAGraphTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.TreeSet;
import java.util.SortedSet;

/**
 * Test the dgraph.DAGraph class.
 */
public class DAGraphTest {

    /**
     * Test the empty constructor.
     */
    @Test
    public void constructorEmpty() {
        DAGraph dag = new DAGraph();
        assertTrue(dag.getNodes().isEmpty());
    }

    /**
     * Test the constructor from a set of nodes.
     */
    @Test
    public void testConstructorFromSet() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        set.add(node1);
        set.add(node2);
        DAGraph dag = new DAGraph(set);
        assertEquals(dag.sizeNodes(), 2);
        assertTrue(dag.getNodes().contains(node1));
        assertTrue(dag.getNodes().contains(node2));
        assertTrue(dag.getSuccessorEdges(node1).isEmpty());
        assertTrue(dag.getSuccessorEdges(node2).isEmpty());
        assertTrue(dag.getPredecessorEdges(node1).isEmpty());
        assertTrue(dag.getPredecessorEdges(node2).isEmpty());
    }

    /**
     * Test the constructor from a graph.
     */
    @Test
    public void testConstructorFromGraph() {
        // Test for acyclic graph
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        set.add(node1);
        set.add(node2);
        DGraph graph = new DGraph(set);
        graph.addEdge(node1, node2);
        DAGraph dag = new DAGraph(graph);
        assertEquals(dag.sizeNodes(), 2);
        assertTrue(dag.getNodes().contains(node1));
        assertTrue(dag.getNodes().contains(node2));
        assertEquals(dag.getSuccessorEdges(node1).size(), 1);
        assertTrue(dag.getSuccessorEdges(node2).isEmpty());
        assertTrue(dag.getPredecessorEdges(node1).isEmpty());
        assertEquals(dag.getPredecessorEdges(node2).size(), 1);
        assertTrue(dag.containsEdge(node1, node2));

        // Test for cyclic graph
        graph.addEdge(node2, node1);
        dag = new DAGraph(graph);
        assertEquals(dag.sizeNodes(), 2);
        assertTrue(dag.getSuccessorEdges(node1).isEmpty());
        assertTrue(dag.getSuccessorEdges(node2).isEmpty());
        assertTrue(dag.getPredecessorEdges(node1).isEmpty());
        assertTrue(dag.getPredecessorEdges(node2).isEmpty());
    }

    /**
     * Test the min method.
     */
    @Test
    public void testMin() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        set.add(node1);
        set.add(node2);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        assertEquals(dag.min(), dag.getSinks());
    }

    /**
     * Test the max method.
     */
    @Test
    public void testMax() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        set.add(node1);
        set.add(node2);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        assertEquals(dag.max(), dag.getWells());
    }

    /**
     * Test the majorants method.
     */
    @Test
    public void testMajorants() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        set.add(node1);
        set.add(node2);
        set.add(node3);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        dag.addEdge(node2, node3);
        SortedSet<Node> majorants = dag.majorants(node1);
        assertEquals(majorants.size(), 2);
        assertTrue(majorants.contains(node2));
        assertTrue(majorants.contains(node3));
    }

    /**
     * Test the minorants method.
     */
    @Test
    public void testMinorants() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        set.add(node1);
        set.add(node2);
        set.add(node3);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        dag.addEdge(node2, node3);
        SortedSet<Node> minorants = dag.minorants(node3);
        assertEquals(minorants.size(), 2);
        assertTrue(minorants.contains(node1));
        assertTrue(minorants.contains(node2));
    }

    /**
     * Test the filter method.
     */
    @Test
    public void testFilter() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        set.add(node1);
        set.add(node2);
        set.add(node3);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        dag.addEdge(node2, node3);
        DAGraph filter = dag.filter(node2);
        assertEquals(filter.sizeNodes(), 2);
        assertEquals(filter.sizeEdges(), 1);
        assertTrue(filter.containsEdge(node2, node3));
    }

    /**
     * Test the ideal method.
     */
    @Test
    public void testIdeal() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        set.add(node1);
        set.add(node2);
        set.add(node3);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        dag.addEdge(node2, node3);
        DAGraph ideal = dag.ideal(node2);
        assertEquals(ideal.sizeNodes(), 2);
        assertEquals(ideal.sizeEdges(), 1);
        assertTrue(ideal.containsEdge(node1, node2));
    }

    /**
     * Test the random static method.
     */
    @Test
    public void testRandom() {
        DAGraph dag = DAGraph.random(10, 0);
        assertEquals(dag.sizeNodes(), 10);
        assertEquals(dag.sizeEdges(), 0);

        dag = DAGraph.random(10, 1);
        assertEquals(dag.sizeNodes(), 10);
        assertEquals(dag.sizeEdges(), 45);
    }

    /**
     * Test the divisors static method.
     */
    @Test
    public void testDivisors() {
        DAGraph dag = DAGraph.divisors(20);
        assertEquals(dag.sizeNodes(), 19);
        assertEquals(dag.sizeEdges(), 27);
    }

    /**
     * Test the transitiveReduction method.
     */
    @Test
    public void testTransitiveReduction() {
        DAGraph dag = DAGraph.divisors(20);
        assertEquals(dag.transitiveReduction(), 9);
        assertEquals(dag.sizeEdges(), 18);
    }

    /**
     * Test the transitiveReduction method.
     */
    @Test
    public void testTransitiveClosure() {
        DAGraph dag = DAGraph.divisors(20);
        DAGraph copy = new DAGraph(dag);
        copy.transitiveReduction();
        copy.transitiveClosure();
        assertEquals(copy.toString(), dag.toString());
    }
}

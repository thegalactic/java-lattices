package org.thegalactic.dgraph;

/*
 * DGraphTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.TreeSet;
import java.util.SortedSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * Test the dgraph.DGraph class.
 */
public class DGraphTest {
    /**
     * Test the empty constructor.
     */
    @Test
    public void constructorEmpty() {
        DGraph graph = new DGraph();
        assertTrue(graph.getNodes().isEmpty());
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
        DGraph graph = new DGraph(set);
        assertEquals(graph.sizeNodes(), 2);
        assertTrue(graph.getNodes().contains(node1));
        assertTrue(graph.getNodes().contains(node2));
        assertTrue(graph.getSuccessorEdges(node1).isEmpty());
        assertTrue(graph.getSuccessorEdges(node2).isEmpty());
        assertTrue(graph.getPredecessorEdges(node1).isEmpty());
        assertTrue(graph.getPredecessorEdges(node2).isEmpty());
    }

    /**
     * Test the constructor by copy.
     */
    @Test
    public void testConstructorCopy() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node();
        Node node2 = new Node();
        set.add(node1);
        set.add(node2);
        DGraph graph = new DGraph(set);
        graph.addEdge(node1, node2);
        DGraph copy = new DGraph(graph);
        assertEquals(copy.sizeNodes(), 2);
        assertTrue(copy.getNodes().contains(node1));
        assertTrue(copy.getNodes().contains(node2));
        assertEquals(copy.getSuccessorEdges(node1).size(), 1);
        assertTrue(copy.getSuccessorEdges(node2).isEmpty());
        assertTrue(copy.getPredecessorEdges(node1).isEmpty());
        assertEquals(copy.getPredecessorEdges(node2).size(), 1);
        assertTrue(copy.containsEdge(node1, node2));
    }

    /**
     * Test the random static method.
     */
    @Test
    public void testRandom() {
        DGraph graph = DGraph.random(10, 0);
        assertEquals(graph.sizeNodes(), 10);
        assertEquals(graph.sizeEdges(), 0);

        graph = DGraph.random(10, 1);
        assertEquals(graph.sizeNodes(), 10);
        assertEquals(graph.sizeEdges(), 100);
    }

    /**
     * Test the clone method.
     */
    @Test
    public void testClone() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        set.add(node1);
        set.add(node2);
        DGraph graph = new DGraph(set);
        graph.addEdge(node1, node2);
        DGraph copy = graph.clone();
        assertEquals(copy.sizeNodes(), 2);
        node1 = copy.getNodeByContent("1");
        node2 = copy.getNodeByContent("2");
        assertTrue(node1 != null);
        assertTrue(node2 != null);
        assertTrue(copy.containsEdge(node1, node2));
    }

    /**
     * Test the getNodes method.
     */
    @Test
    public void testGetNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node1);
        graph.addNode(node2);
        assertEquals(graph.sizeNodes(), 2);
        assertTrue(graph.getNodes().contains(node1));
        assertTrue(graph.getNodes().contains(node2));
    }

    /**
     * Test the getEdges method.
     */
    @Test
    public void testGetEdges() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        Edge[] edges = new Edge[4];
        edges[0] = new Edge(node1, node1);
        edges[1] = new Edge(node1, node2);
        edges[2] = new Edge(node1, node3);
        edges[3] = new Edge(node2, node3);
        for (int i = 0; i < 4; i++) {
            graph.addEdge(edges[i]);
        }
        assertEquals(graph.getEdges().size(), 4);
        int i = 0;
        for (Edge edge : graph.getEdges()) {
            assertEquals(edge, edges[i++]);
        }
    }

    /**
     * Test the getSuccessorEdges method.
     */
    @Test
    public void testGetSuccessorEdges() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        assertEquals(graph.getSuccessorEdges(node1).size(), 1);
        assertEquals(graph.getSuccessorEdges(node1).first().compareTo(new Edge(node1, node2)), 0);
        assertEquals(graph.getSuccessorEdges(node2).size(), 0);
    }

    /**
     * Test the getPredecessorEdges method.
     */
    @Test
    public void testGetPredecessorEdges() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        assertEquals(graph.getPredecessorEdges(node1).size(), 0);
        assertEquals(graph.getPredecessorEdges(node2).size(), 1);
        assertEquals(graph.getPredecessorEdges(node2).first().compareTo(new Edge(node1, node2)), 0);
    }

    /**
     * Test the getSuccessorNodes method.
     */
    @Test
    public void testGetSuccessorNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        assertEquals(graph.getSuccessorNodes(node1).size(), 1);
        assertEquals(graph.getSuccessorNodes(node1).first(), node2);
        assertEquals(graph.getSuccessorNodes(node2).size(), 0);
    }

    /**
     * Test the getPredecessorNodes method.
     */
    @Test
    public void testGetPredecessorNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        assertEquals(graph.getPredecessorNodes(node1).size(), 0);
        assertEquals(graph.getPredecessorNodes(node2).size(), 1);
        assertEquals(graph.getPredecessorNodes(node2).first(), node1);
    }

    /**
     * Test the getNodeByContent method.
     */
    @Test
    public void testGetNodeByContent() {
        Node node = new Node("1");
        DGraph graph = new DGraph();
        graph.addNode(node);
        assertEquals(graph.getNodeByContent("1"), node);
        assertEquals(graph.getNodeByContent("notfound"), null);
    }

    /**
     * Test the getNodeByIdentifier method.
     */
    @Test
    public void testGetNodeByIdentifier() {
        Node node = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node);
        assertEquals(graph.getNodeByIdentifier(node.getIdentifier()), node);
        assertEquals(graph.getNodeByIdentifier(0), null);
    }

    /**
     * Test the sizeNodes method.
     */
    @Test
    public void testSizeNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        assertEquals(graph.sizeNodes(), 0);
        graph.addNode(node1);
        graph.addNode(node2);
        assertEquals(graph.sizeNodes(), 2);
    }

    /**
     * Test the sizeEdges method.
     */
    @Test
    public void testSizeEdges() {
        Node node1 = new Node();
        Node node2 = new Node();
        DGraph graph = new DGraph();
        assertEquals(graph.sizeEdges(), 0);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        assertEquals(graph.sizeEdges(), 1);
    }

    /**
     * Test the save method.
     */
    @Test
    public void testSave() {
        try {
            File file = File.createTempFile("junit", ".dot");
            String filename = file.getPath();
            file.delete();
            DGraph graph = new DGraph();
            Node node1 = new Node("1");
            Node node2 = new Node("2");
            Node node3 = new Node("3");
            graph.addNode(node1);
            graph.addNode(node2);
            graph.addNode(node3);
            graph.addEdge(node1, node2, "R1");
            graph.addEdge(node2, node3, "R2");
            graph.save(filename);
            String content = "";
            file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }
            assertEquals(content, "digraph G {Graph [rankdir=BT]"
                + node1.getIdentifier() + " [label=\"1\"]"
                + node2.getIdentifier() + " [label=\"2\"]"
                + node3.getIdentifier() + " [label=\"3\"]"
                + node1.getIdentifier() + "->" + node2.getIdentifier() + " [label=\"R1\"]"
                + node2.getIdentifier() + "->" + node3.getIdentifier() + " [label=\"R2\"]"
                + "}"
            );
            file.delete();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        Node node1 = new Node("Hello");
        Node node2 = new Node("World");
        DGraph graph = new DGraph();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2, "happy");
        assertEquals(graph.toString(), "2 Nodes: {Hello,World,}\n1 Edges: {Hello->World(happy),}\n");
    }

    /**
     * Test the containsNode method.
     */
    @Test
    public void testContainsNode() {
        Node node = new Node();
        Node dummy = new Node();
        DGraph graph = new DGraph();
        graph.addNode(node);
        assertTrue(graph.containsNode(node));
        assertFalse(graph.containsNode(dummy));
    }

    /**
     * Test the addNode method.
     */
    @Test
    public void testAddNode() {
        Node node = new Node();
        DGraph graph = new DGraph();
        assertTrue(graph.addNode(node));
        assertTrue(graph.containsNode(node));
        assertTrue(graph.getSuccessorEdges(node).isEmpty());
        assertTrue(graph.getPredecessorEdges(node).isEmpty());
        assertFalse(graph.addNode(node));
    }

    /**
     * Test the removeNode method.
     */
    @Test
    public void testRemoveNode() {
        Node from = new Node();
        Node to = new Node();
        DGraph graph = new DGraph();
        graph.addNode(from);
        graph.addNode(to);
        graph.addEdge(from, to);
        assertTrue(graph.removeNode(from));
        assertFalse(graph.removeNode(from));
        assertFalse(graph.containsNode(from));
        assertTrue(graph.containsNode(to));
        assertFalse(graph.containsEdge(from, to));
        assertTrue(graph.getSuccessorEdges(to).isEmpty());
        assertTrue(graph.getPredecessorEdges(to).isEmpty());
    }

    /**
     * Test the removeNodes method.
     */
    @Test
    public void testRemoveNodes() {
        Node from = new Node();
        Node to = new Node();
        DGraph graph = new DGraph();
        graph.addNode(from);
        graph.addNode(to);
        graph.addEdge(from, to);
        TreeSet<Node> set = new TreeSet<Node>();
        set.add(from);
        assertTrue(graph.removeNodes(set));
        assertFalse(graph.removeNodes(set));
        assertFalse(graph.containsNode(from));
        assertTrue(graph.containsNode(to));
        assertFalse(graph.containsEdge(from, to));
        assertTrue(graph.getSuccessorEdges(to).isEmpty());
        assertTrue(graph.getPredecessorEdges(to).isEmpty());
    }

    /**
     * Test the containsEdge, addEdge, removeEdge method using 2 nodes.
     */
    @Test
    public void testEdgeNodes() {
        Node from = new Node();
        Node to = new Node();
        DGraph graph = new DGraph();
        graph.addNode(from);
        graph.addNode(to);
        assertFalse(graph.containsEdge(from, to));
        graph.addEdge(from, to);
        assertTrue(graph.containsEdge(from, to));
        graph.removeEdge(from, to);
        assertFalse(graph.containsEdge(from, to));
    }

    /**
     * Test the containsEdge, addEdge, removeEdge method using one edge.
     */
    @Test
    public void testEdge() {
        Node from = new Node();
        Node to = new Node();
        Edge edge = new Edge(from, to);
        DGraph graph = new DGraph();
        graph.addNode(from);
        graph.addNode(to);
        assertFalse(graph.containsEdge(edge));
        graph.addEdge(edge);
        assertTrue(graph.containsEdge(edge));
        graph.removeEdge(edge);
        assertFalse(graph.containsEdge(edge));
    }

    /**
     * Test the isAcyclic method.
     */
    @Test
    public void testIsAcyclic() {
        DGraph graph = new DGraph();
        assertTrue(graph.isAcyclic());
        Node from = new Node();
        Node to = new Node();
        graph.addNode(from);
        graph.addNode(to);
        graph.addEdge(from, to);
        assertTrue(graph.isAcyclic());
        graph.addEdge(to, from);
        assertFalse(graph.isAcyclic());
    }

    /**
     * Test the topologicalSort method.
     */
    @Test
    public void testTopologicalSort() {
        DGraph graph = new DGraph();
        assertTrue(graph.topologicalSort().isEmpty());
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addEdge(node1, node4);
        graph.addEdge(node1, node5);
        graph.addEdge(node2, node4);
        graph.addEdge(node3, node5);
        graph.addEdge(node3, node8);
        graph.addEdge(node4, node6);
        graph.addEdge(node4, node7);
        graph.addEdge(node4, node8);
        graph.addEdge(node5, node7);
        ArrayList<Node> sort = graph.topologicalSort();
        assertTrue(sort.indexOf(node1) < sort.indexOf(node4));
        assertTrue(sort.indexOf(node1) < sort.indexOf(node5));
        assertTrue(sort.indexOf(node1) < sort.indexOf(node6));
        assertTrue(sort.indexOf(node1) < sort.indexOf(node7));
        assertTrue(sort.indexOf(node1) < sort.indexOf(node8));
        assertTrue(sort.indexOf(node2) < sort.indexOf(node4));
        assertTrue(sort.indexOf(node2) < sort.indexOf(node5));
        assertTrue(sort.indexOf(node2) < sort.indexOf(node6));
        assertTrue(sort.indexOf(node2) < sort.indexOf(node7));
        assertTrue(sort.indexOf(node2) < sort.indexOf(node8));
        assertTrue(sort.indexOf(node3) < sort.indexOf(node4));
        assertTrue(sort.indexOf(node3) < sort.indexOf(node5));
        assertTrue(sort.indexOf(node3) < sort.indexOf(node6));
        assertTrue(sort.indexOf(node3) < sort.indexOf(node7));
        assertTrue(sort.indexOf(node3) < sort.indexOf(node8));
        assertTrue(sort.indexOf(node4) < sort.indexOf(node6));
        assertTrue(sort.indexOf(node4) < sort.indexOf(node7));
        assertTrue(sort.indexOf(node4) < sort.indexOf(node8));
        assertTrue(sort.indexOf(node5) < sort.indexOf(node6));
        assertTrue(sort.indexOf(node5) < sort.indexOf(node7));
        assertTrue(sort.indexOf(node5) < sort.indexOf(node8));

        graph.addEdge(node8, node2);
        sort = graph.topologicalSort();
        assertEquals(sort.size(), 3);
        assertTrue(sort.indexOf(node1) < sort.indexOf(node5));
        assertTrue(sort.indexOf(node3) < sort.indexOf(node5));
    }

    /**
     * Test the getSinks method.
     */
    @Test
    public void testGetSinks() {
        Node from = new Node();
        Node to = new Node();
        Edge edge = new Edge(from, to);
        DGraph graph = new DGraph();
        graph.addNode(from);
        graph.addNode(to);
        graph.addEdge(edge);
        SortedSet<Node> sinks = graph.getSinks();
        assertEquals(sinks.size(), 1);
        for (Node sink : sinks) {
            assertEquals(sink, from);
        }
    }

    /**
     * Test the getWells method.
     */
    @Test
    public void testGetWells() {
        Node from = new Node();
        Node to = new Node();
        Edge edge = new Edge(from, to);
        DGraph graph = new DGraph();
        graph.addNode(from);
        graph.addNode(to);
        graph.addEdge(edge);
        SortedSet<Node> wells = graph.getWells();
        assertEquals(wells.size(), 1);
        for (Node well : wells) {
            assertEquals(well, to);
        }
    }

    /**
     * Test the getSubgraphByNodes method.
     */
    @Test
    public void testGetSubgraphByNodes() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addEdge(node1, node4);
        graph.addEdge(node1, node5);
        graph.addEdge(node2, node4);
        graph.addEdge(node3, node5);
        graph.addEdge(node3, node8);
        graph.addEdge(node4, node6);
        graph.addEdge(node4, node7);
        graph.addEdge(node4, node8);
        graph.addEdge(node5, node7);

        TreeSet<Node> set = new TreeSet<Node>();
        set.add(node1);
        set.add(node4);
        set.add(node6);
        set.add(node7);
        DGraph subgraph = graph.getSubgraphByNodes(set);
        assertEquals(subgraph.sizeNodes(), 4);
        assertTrue(subgraph.getNodes().contains(node1));
        assertTrue(subgraph.getNodes().contains(node4));
        assertTrue(subgraph.getNodes().contains(node6));
        assertTrue(subgraph.getNodes().contains(node7));
        assertEquals(subgraph.sizeEdges(), 3);
        assertTrue(subgraph.getEdges().contains(new Edge(node1, node4)));
        assertTrue(subgraph.getEdges().contains(new Edge(node4, node6)));
        assertTrue(subgraph.getEdges().contains(new Edge(node4, node7)));
    }

    /**
     * Test the getSubgraphByEdges method.
     */
    @Test
    public void testGetSubgraphByEdges() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        Edge edge1 = new Edge(node1, node4);
        Edge edge2 = new Edge(node1, node5);
        Edge edge3 = new Edge(node2, node4);
        Edge edge4 = new Edge(node3, node5);
        Edge edge5 = new Edge(node3, node8);
        Edge edge6 = new Edge(node4, node6);
        Edge edge7 = new Edge(node4, node6);
        Edge edge8 = new Edge(node4, node8);
        Edge edge9 = new Edge(node5, node7);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);
        graph.addEdge(edge6);
        graph.addEdge(edge7);
        graph.addEdge(edge8);
        graph.addEdge(edge9);

        TreeSet<Edge> set = new TreeSet<Edge>();
        set.add(edge1);
        set.add(edge2);
        set.add(edge3);
        DGraph subgraph = graph.getSubgraphByEdges(set);
        assertEquals(subgraph.sizeNodes(), graph.sizeNodes());
        assertEquals(subgraph.sizeEdges(), 3);
        assertTrue(subgraph.getEdges().contains(edge1));
        assertTrue(subgraph.getEdges().contains(edge2));
        assertTrue(subgraph.getEdges().contains(edge3));
    }

    /**
     * Test the complementary method.
     */
    @Test
    public void testComplementary() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node2, node3);

        graph.complementary();
        assertEquals(graph.sizeEdges(), 6);
        assertTrue(graph.getEdges().contains(new Edge(node1, node1)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node1)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node2)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node1)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node2)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node3)));
    }

    /**
     * Test the reflexiveReduction method.
     */
    @Test
    public void testReflexiveReduction() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node1);
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node2, node3);

        assertEquals(graph.reflexiveReduction(), 1);
        assertEquals(graph.sizeEdges(), 3);
        assertTrue(graph.getEdges().contains(new Edge(node1, node2)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node3)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node3)));
    }

    /**
     * Test the reflexiveClosure method.
     */
    @Test
    public void testReflexiveClosure() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node1);
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node2, node3);

        assertEquals(graph.reflexiveClosure(), 2);
        assertEquals(graph.sizeEdges(), 6);
        assertTrue(graph.getEdges().contains(new Edge(node1, node1)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node2)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node3)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node3)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node2)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node3)));
    }

    /**
     * Test the transitiveClosure method.
     */
    @Test
    public void testTransitiveClosure() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addEdge(node1, node4);
        graph.addEdge(node1, node5);
        graph.addEdge(node2, node4);
        graph.addEdge(node3, node5);
        graph.addEdge(node3, node8);
        graph.addEdge(node4, node6);
        graph.addEdge(node4, node7);
        graph.addEdge(node4, node8);
        graph.addEdge(node5, node7);
        graph.transitiveClosure();
        assertEquals(graph.sizeEdges(), 16);

        assertTrue(graph.getEdges().contains(new Edge(node1, node4)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node5)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node4)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node5)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node8)));
        assertTrue(graph.getEdges().contains(new Edge(node4, node6)));
        assertTrue(graph.getEdges().contains(new Edge(node4, node7)));
        assertTrue(graph.getEdges().contains(new Edge(node4, node8)));
        assertTrue(graph.getEdges().contains(new Edge(node5, node7)));

        assertTrue(graph.getEdges().contains(new Edge(node1, node6)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node7)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node8)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node6)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node7)));
        assertTrue(graph.getEdges().contains(new Edge(node2, node8)));
        assertTrue(graph.getEdges().contains(new Edge(node3, node5)));
    }

    /**
     * Test the depthFirstSearch method.
     */
/*    @Test
    public void testDepthFirstSearch() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addEdge(node1, node4);
        graph.addEdge(node1, node5);
        graph.addEdge(node2, node4);
        graph.addEdge(node3, node5);
        graph.addEdge(node3, node8);
        graph.addEdge(node4, node6);
        graph.addEdge(node4, node7);
        graph.addEdge(node4, node8);
        graph.addEdge(node5, node7);
        ArrayList<Node>[] result = graph.depthFirstSearch();
        assertEquals(result[0].size(), 8);
        assertEquals(result[1].size(), 8);
        assertEquals(result[0].get(0), node1);
        assertEquals(result[0].get(1), node4);
        assertEquals(result[0].get(2), node6);
        assertEquals(result[0].get(3), node7);
        assertEquals(result[0].get(4), node8);
        assertEquals(result[0].get(5), node5);
        assertEquals(result[0].get(6), node2);
        assertEquals(result[0].get(7), node3);
        assertEquals(result[1].get(0), node6);
        assertEquals(result[1].get(1), node7);
        assertEquals(result[1].get(2), node8);
        assertEquals(result[1].get(3), node4);
        assertEquals(result[1].get(4), node5);
        assertEquals(result[1].get(5), node1);
        assertEquals(result[1].get(6), node2);
        assertEquals(result[1].get(7), node3);
    }
*/
    /**
     * Test the transpose method.
     */
    @Test
    public void testTranspose() {
        DGraph graph = new DGraph();
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2, "R1");
        graph.addEdge(node2, node3, "R2");
        graph.transpose();
        assertEquals(graph.sizeEdges(), 2);
        assertTrue(graph.containsEdge(node2, node1));
        assertTrue(graph.containsEdge(node3, node2));
    }

    /**
     * Test the getStronglyConnectedComponent method.
     */
    @Test
    public void testGetStronglyConnectedComponent() {
        DGraph graph = new DGraph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addEdge(node1, node2);
        graph.addEdge(node2, node3);
        graph.addEdge(node2, node4);
        graph.addEdge(node2, node5);
        graph.addEdge(node3, node4);
        graph.addEdge(node3, node7);
        graph.addEdge(node4, node3);
        graph.addEdge(node4, node8);
        graph.addEdge(node5, node1);
        graph.addEdge(node5, node6);
        graph.addEdge(node6, node7);
        graph.addEdge(node7, node6);
        graph.addEdge(node8, node4);
        graph.addEdge(node8, node7);
        DAGraph dag = graph.getStronglyConnectedComponent();

        assertEquals(dag.sizeNodes(), 3);
        assertEquals(dag.sizeEdges(), 3);

        int i = 0;
        Node set1 = null;
        Node set2 = null;
        Node set3 = null;
        for (Node node : dag.getNodes()) {
            switch (i) {
                case 0:
                    set1 = node;
                    break;
                case 1:
                    set2 = node;
                    break;
                case 2:
                    set3 = node;
                    break;
                default:
            }
            i++;
        }

        assertEquals(((TreeSet<Node>) set1.getContent()).size(), 3);
        assertTrue(((TreeSet<Node>) set1.getContent()).contains(node1));
        assertTrue(((TreeSet<Node>) set1.getContent()).contains(node2));
        assertTrue(((TreeSet<Node>) set1.getContent()).contains(node5));

        assertEquals(((TreeSet<Node>) set2.getContent()).size(), 3);
        assertTrue(((TreeSet<Node>) set2.getContent()).contains(node3));
        assertTrue(((TreeSet<Node>) set2.getContent()).contains(node4));
        assertTrue(((TreeSet<Node>) set2.getContent()).contains(node8));

        assertEquals(((TreeSet<Node>) set3.getContent()).size(), 2);
        assertTrue(((TreeSet<Node>) set3.getContent()).contains(node6));
        assertTrue(((TreeSet<Node>) set3.getContent()).contains(node7));

        assertTrue(dag.containsEdge(set1, set2));
        assertTrue(dag.containsEdge(set1, set3));
        assertTrue(dag.containsEdge(set2, set3));
    }
}


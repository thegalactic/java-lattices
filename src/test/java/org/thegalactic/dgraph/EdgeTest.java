package org.thegalactic.dgraph;

/*
 * EdgeTest.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test the dgraph.Edge class.
 */
public class EdgeTest {

    /**
     * Test the full constructor.
     */
    @Test
    public void testConstructorFull() {
        Node<String> source = new Node<String>();
        Node<String> target = new Node<String>();
        String content = "happy new year";
        Edge<String, String> edge = new Edge<String, String>(source, target, content);
        assertEquals(content, edge.getContent());
        assertEquals(source, edge.getSource());
        assertEquals(target, edge.getTarget());
    }

    /**
     * Test the restricted constructor.
     */
    @Test
    public void testConstructorRestricted() {
        Node<String> source = new Node<String>();
        Node<String> target = new Node<String>();
        Edge<String, String> edge = new Edge<String, String>(source, target);
        assertEquals(null, edge.getContent());
        assertEquals(source, edge.getSource());
        assertEquals(target, edge.getTarget());
    }

    /**
     * Test of setContent method, of class Edge.
     */
    @Test
    public void testSetContent() {
        Node<String> source = new Node<String>();
        Node<String> target = new Node<String>();
        Edge<String, String> edge = new Edge<String, String>(source, target);
        String content = "happy new year";
        assertEquals(edge, edge.setContent(content));
        assertEquals(content, edge.getContent());
    }

    /**
     * Test the hasContent method, of class Edge.
     */
    @Test
    public void testHasContent() {
        Node<String> source = new Node<String>();
        Node<String> target = new Node<String>();
        Edge<String, String> edge = new Edge<String, String>(source, target);
        assertFalse(edge.hasContent());
        edge.setContent("happy new year");
        assertTrue(edge.hasContent());
    }

    /**
     * Test the compareTo method, of class Edge.
     */
    @Test
    public void testCompareTo() {
        Node<String> source1 = new Node<String>();
        Node<String> target1 = new Node<String>();
        Node<String> source2 = new Node<String>();
        Node<String> target2 = new Node<String>();
        Edge<String, String> edge1 = new Edge<String, String>(source1, target1);
        Edge<String, String> edge2 = new Edge<String, String>(source2, target2);
        Edge<String, String> edge3 = new Edge<String, String>(source1, target2);
        assertEquals(0, edge1.compareTo(edge1));
        assertTrue(edge1.compareTo(edge2) < 0);
        assertTrue(edge2.compareTo(edge1) > 0);
        assertTrue(edge1.compareTo(edge3) < 0);
        assertTrue(edge3.compareTo(edge1) > 0);
    }

    /**
     * Test the toString method, of class Edge.
     */
    @Test
    public void testToString() {
        Node<String> source = new Node<String>("Hello");
        Node<String> target = new Node<String>("World");
        Edge<String, String> edge = new Edge<String, String>(source, target, "happy new year");
        assertEquals("[Hello]-(happy new year)->[World]", edge.toString());
    }

    /**
     * Test of getTarget method, of class Edge.
     */
    @Test
    public void testGetSource() {
        Node<String> source = new Node<String>("Hello");
        Node<String> target = new Node<String>("World");
        Edge<String, String> edge = new Edge<String, String>(source, target, "happy new year");
        assertEquals(source, edge.getSource());
    }

    /**
     * Test of getTarget method, of class Edge.
     */
    @Test
    public void testGetTarget() {
        Node<String> source = new Node<String>("Hello");
        Node<String> target = new Node<String>("World");
        Edge<String, String> edge = new Edge<String, String>(source, target, "happy new year");
        assertEquals(target, edge.getTarget());
    }

    /**
     * Test of getContent method, of class Edge.
     */
    @Test
    public void testGetContent() {
        Node<String> source = new Node<String>("Hello");
        Node<String> target = new Node<String>("World");
        String content = "happy new year";
        Edge<String, String> edge = new Edge<String, String>(source, target, content);
        assertEquals(content, edge.getContent());
    }

    /**
     * Test of hashCode method, of class Edge.
     */
    @Test
    public void testHashCode() {
        Node<String> source = new Node<String>("Hello");
        Node<String> target = new Node<String>("World");
        Edge<String, String> edge = new Edge<String, String>(source, target, "happy new year");
        assertEquals(1013 * source.hashCode() ^ 1009 * target.hashCode(), edge.hashCode());
    }
}

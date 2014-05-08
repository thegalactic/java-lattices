package dgraph;

/*
 * EdgeTest.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test the dgraph.Edge class.
 */
public class EdgeTest {
    /**
     * Test the full constructor.
     */
    @Test
    public void testConstructorFull() {
        Node from = new Node();
        Node to = new Node();
        Object content = new Object();
        Edge edge = new Edge(from, to, content);
        assertEquals(edge.getContent(), content);
        assertEquals(edge.getFrom(), from);
        assertEquals(edge.getTo(), to);
    }

    /**
     * Test the restricted constructor.
     */
    @Test
    public void testConstructorRestricted() {
        Node from = new Node();
        Node to = new Node();
        Edge edge = new Edge(from, to);
        assertEquals(edge.getContent(), null);
        assertEquals(edge.getFrom(), from);
        assertEquals(edge.getTo(), to);
    }

    /**
     * Test the setContent() method.
     */
    @Test
    public void testSetContent() {
        Node from = new Node();
        Node to = new Node();
        Edge edge = new Edge(from, to);
        Object content = new Object();
        assertEquals(edge.setContent(content), edge);
        assertEquals(edge.getContent(), content);
    }

    /**
     * Test the hasContent() method.
     */
    @Test
    public void testHasContent() {
        Node from = new Node();
        Node to = new Node();
        Edge edge = new Edge(from, to);
        assertFalse(edge.hasContent());
        edge.setContent(new Object());
        assertTrue(edge.hasContent());
    }

    /**
     * Test the compareTo() method.
     */
    @Test
    public void testCompareTo() {
        Node from1 = new Node();
        Node to1 = new Node();
        Node from2 = new Node();
        Node to2 = new Node();
        Edge edge1 = new Edge(from1, to1);
        Edge edge2 = new Edge(from2, to2);
        Edge edge3 = new Edge(from1, to2);
        assertTrue(edge1.compareTo(new Object()) < 0);
        assertTrue(edge1.compareTo(edge1) == 0);
        assertTrue(edge1.compareTo(edge2) < 0);
        assertTrue(edge2.compareTo(edge1) > 0);
        assertTrue(edge1.compareTo(edge3) < 0);
        assertTrue(edge3.compareTo(edge1) > 0);
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        Node from = new Node("Hello");
        Node to = new Node("World");
        Edge edge = new Edge(from, to, "happy new year");
        assertEquals(edge.toString(), "Hello->World(happy new year)");
    }
}


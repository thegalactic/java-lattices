package fr.kbertet.dgraph;

/*
 * NodeTest.java
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
 * Test the dgraph.Node class.
 */
public class NodeTest {
    /**
     * Test the full constructor.
     */
    @Test
    public void testConstructorFull() {
        Object content = new Object();
        Node node1 = new Node(content);
        assertEquals(node1.getContent(), content);

        Node node2 = new Node();
        assertEquals(node1.getIdentifier() + 1, node2.getIdentifier());
    }

    /**
     * Test the empty constructor.
     */
    @Test
    public void testConstructorEmpty() {
        Node node = new Node();
        assertEquals(node.getContent(), null);
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        String content = new String("Hello World");
        Node node = new Node(content);
        assertEquals(node.toString(), "Hello World");
    }

    /**
     * Test the copy() method.
     */
    @Test
    public void testCopy() {
        Object content = new Object();
        Node node = new Node(content);
        Node copy = node.copy();
        assertEquals(copy.getContent(), content);
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Node node = new Node();
        assertTrue(node.equals(node));
        assertFalse(node.equals(new Integer(1)));
    }

    /**
     * Test the hashCode() method.
     */
    @Test
    public void testHashCode() {
        Node node = new Node();
        assertEquals(node.getIdentifier(), node.hashCode());
    }

    /**
     * Test the compareTo() method.
     */
    @Test
    public void testCompareTo() {
        Node node1 = new Node();
        Node node2 = new Node();
        assertEquals(node1.compareTo(node1), 0);
        assertTrue(node1.compareTo(node2) < 0);
        assertTrue(node2.compareTo(node1) > 0);
    }
}


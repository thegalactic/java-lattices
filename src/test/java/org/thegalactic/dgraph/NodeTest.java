package org.thegalactic.dgraph;

/*
 * NodeTest.java
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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the dgraph.Node class.
 */
public class NodeTest {

    /**
     * Test the full constructor.
     */
    @Test
    public void testConstructorFull() {
        String content = "Hello";
        Node<String> node1 = new Node<String>(content);
        assertEquals(content, node1.getContent());

        Node<String> node2 = new Node<String>();
        assertEquals(node1.getIdentifier() + 1, node2.getIdentifier());
    }

    /**
     * Test the empty constructor.
     */
    @Test
    public void testConstructorEmpty() {
        Node<String> node = new Node<String>();
        assertEquals(null, node.getContent());
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        String content = "Hello World";
        Node<String> node = new Node<String>(content);
        StringBuilder builder = new StringBuilder();
        assertEquals(content, node.toString());
    }

    /**
     * Test the clone() method.
     */
    @Test
    public void testClone() {
        String content = "Hello World";
        Node node = new Node<String>(content);
        try {
            Node<String> copy = node.clone();
            assertEquals(content, copy.getContent());
            assertNotEquals(node.getIdentifier(), copy.getIdentifier());
        } catch (CloneNotSupportedException e) {
        }
    }

    /**
     * Test the setContent() method.
     */
    @Test
    public void testSetContent() {
        String content = "Hello World";
        Node<String> node = new Node<String>();
        assertEquals(node, node.setContent(content));
        assertEquals(content, node.getContent());
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Node<String> node = new Node<String>();
        assertFalse(node.equals(1));
    }

    /**
     * Test the hashCode() method.
     */
    @Test
    public void testHashCode() {
        Node<String> node = new Node<String>();
        assertEquals(node.getIdentifier(), node.hashCode());
    }

    /**
     * Test the compareTo() method.
     */
    @Test
    public void testCompareTo() {
        Node<String> node1 = new Node<String>();
        Node<String> node2 = new Node<String>();
        assertEquals(0, node1.compareTo(node1));
        assertTrue(node1.compareTo(node2) < 0);
        assertTrue(node2.compareTo(node1) > 0);
    }
}

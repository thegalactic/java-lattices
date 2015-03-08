package org.thegalactic.context;

/*
 * TemporaryContextTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import org.thegalactic.dgraph.Node;
import org.thegalactic.lattice.ConceptLattice;
import org.thegalactic.lattice.Lattice;
import org.thegalactic.lattice.LatticeFactory;
import org.thegalactic.util.Couple;

/**
 * Test TemporaryContext class.
 */
public class TemporaryContextTest {

    /**
     * Test the empty constructor of TemporaryContext.
     */
    @Test
    public void testEmptyContext() {
        TemporaryContext context = new TemporaryContext();
        assertEquals(context.getAttributes(), new TreeSet<Comparable>());
        assertEquals(context.getObservations(), new TreeSet<Comparable>());
    }

    /**
     * Test the copy constructor of TemporaryContext.
     */
    @Test
    public void testCopyContext() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        TemporaryContext copy = new TemporaryContext(context);
        assertEquals(context.getAttributes(), copy.getAttributes());
        assertEquals(context.getObservations(), copy.getObservations());
        assertEquals(context.getIntent("1"), copy.getIntent("1"));
        assertEquals(context.getExtent("c"), copy.getExtent("c"));
    }

    /**
     * Test the constructor from file .txt of TemporaryContext.
     */
    @Test
    public void testFileContextText() {
        try {
            File file = File.createTempFile("junit", ".txt");
            String filename = file.getPath();
            TemporaryContext context = new TemporaryContext();
            context.addToAttributes("a");
            context.addToAttributes("b");
            context.addToAttributes("c");
            context.addToObservations("1");
            context.addToObservations("2");
            context.addToObservations("3");
            context.addExtentIntent("1", "a");
            context.addExtentIntent("1", "b");
            context.addExtentIntent("2", "a");
            context.addExtentIntent("3", "b");
            context.addExtentIntent("3", "c");
            context.save(filename);
            TemporaryContext copy = new TemporaryContext(filename);
            assertEquals(context.getAttributes(), copy.getAttributes());
            assertEquals(context.getObservations(), copy.getObservations());
            assertEquals(context.getIntent("1"), copy.getIntent("1"));
            assertEquals(context.getExtent("c"), copy.getExtent("c"));
            new File(filename).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test random method.
     */
    @Test
    public void testrandom() {
        TemporaryContext ctx = TemporaryContext.random(10, 53, 20);
        assertEquals(ctx.getObservations().size(), 10);
        assertEquals(ctx.getAttributes().size(), 1060);
    }

    /**
     * Test getSubContext method.
     */
    @Test
    public void testGetSubContext() {
        TemporaryContext ctx = new TemporaryContext();
        ctx.addToAttributes(1);
        ctx.addToAttributes(2);
        ctx.addToObservations("a");
        ctx.addToObservations("b");
        ctx.addExtentIntent("a", 1);
        TemporaryContext sub = ctx.getSubContext(ctx.getObservations(), ctx.getAttributes());
        assertTrue(sub.containsAllObservations(ctx.getObservations()));
        assertTrue(sub.containsAllAttributes(ctx.getAttributes()));
        assertTrue(sub.containAsExtent(1, "a"));
        assertTrue(sub.containAsIntent("a", 1));
    }

    /**
     * Test of containsAttribute.
     */
    @Test
    public void testContainsAttribute() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        assertTrue(context.containsAttribute("a"));
        assertFalse(context.containsObservation("b"));
    }

    /**
     * Test of containsAllAttributes.
     */
    @Test
    public void testContainsAllAttributes() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        TreeSet<Comparable> attributes = new TreeSet();
        attributes.add("a");
        attributes.add("b");
        TreeSet<Comparable> attributesFalse = new TreeSet();
        attributesFalse.add("a");
        attributesFalse.add("c");
        assertTrue(context.containsAllAttributes(attributes));
        assertFalse(context.containsAllAttributes(attributesFalse));
    }

    /**
     * Test of containsObservation.
     */
    @Test
    public void testContainsObservation() {
        TemporaryContext context = new TemporaryContext();
        context.addToObservations("1");
        assertTrue(context.containsObservation("1"));
        assertFalse(context.containsObservation("2"));
    }

    /**
     * Test of containsAllObservations.
     */
    @Test
    public void testContainsAllObservations() {
        TemporaryContext context = new TemporaryContext();
        context.addToObservations("1");
        context.addToObservations("2");
        TreeSet<Comparable> observations = new TreeSet();
        observations.add("1");
        observations.add("2");
        TreeSet<Comparable> observationsFalse = new TreeSet();
        observationsFalse.add("1");
        observationsFalse.add("3");
        assertTrue(context.containsAllObservations(observations));
        assertFalse(context.containsAllObservations(observationsFalse));
    }

    /**
     * Test of the insertion of an attribute.
     */
    @Test
    public void testAddAttribute() {
        TemporaryContext context = new TemporaryContext();
        assertTrue(context.addToAttributes("a"));
        assertFalse(context.addToAttributes("a"));
    }

    /**
     * Test of the insertion of some attributes.
     */
    @Test
    public void testAddAttributes() {
        TemporaryContext context = new TemporaryContext();
        TreeSet<Comparable> attributes = new TreeSet();
        attributes.add("a");
        attributes.add("b");
        TreeSet<Comparable> attributesFalse = new TreeSet();
        attributesFalse.add("c");
        attributesFalse.add("a");
        assertTrue(context.addAllToAttributes(attributes));
        assertFalse(context.addAllToAttributes(attributesFalse));
    }

    /**
     * Test of the insertion of an observation.
     */
    @Test
    public void testAddObservation() {
        TemporaryContext context = new TemporaryContext();
        assertTrue(context.addToObservations("1"));
        assertFalse(context.addToObservations("1"));
    }

    /**
     * Test of the insertion of some observations.
     */
    @Test
    public void testAddObservations() {
        TemporaryContext context = new TemporaryContext();
        TreeSet<Comparable> observations = new TreeSet();
        observations.add("1");
        observations.add("2");
        TreeSet<Comparable> observationsFalse = new TreeSet();
        observationsFalse.add("3");
        observationsFalse.add("1");
        assertTrue(context.addAllToObservations(observations));
        assertFalse(context.addAllToObservations(observationsFalse));
    }

    /**
     * Test of the removal of an attribute.
     */
    @Test
    public void testRemoveAttribute() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        assertTrue(context.removeFromAttributes("a"));
        assertFalse(context.getIntent("1").contains("a"));
        assertFalse(context.getIntent("2").contains("a"));
        assertFalse(context.removeFromAttributes("d"));
    }

    /**
     * Test of the removal of an observation.
     */
    @Test
    public void testRemoveObservation() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        assertTrue(context.removeFromObservations("1"));
        assertFalse(context.getExtent("a").contains("1"));
        assertFalse(context.getExtent("b").contains("1"));
        assertFalse(context.removeFromAttributes("4"));
    }

    /**
     * Test of getExtentNb.
     */
    @Test
    public void testExtentNb() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        TreeSet<Comparable> attributes = new TreeSet();
        attributes.add("a");
        assertTrue(context.getExtentNb(attributes) == 2);
        attributes.add("b");
        assertTrue(context.getExtentNb(attributes) == 1);
        attributes.add("c");
        assertTrue(context.getExtentNb(attributes) == 0);
        attributes.remove("a");
        assertTrue(context.getExtentNb(attributes) == 1);
        attributes.remove("c");
        assertTrue(context.getExtentNb(attributes) == 2);
        attributes.remove("b");
        assertTrue(context.getExtentNb(attributes) == 3);
    }

    /**
     * Test of getIntentNb.
     */
    @Test
    public void testIntentNb() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        TreeSet<Comparable> observations = new TreeSet();
        observations.add("1");
        assertTrue(context.getIntentNb(observations) == 2);
        observations.add("2");
        assertTrue(context.getIntentNb(observations) == 1);
        observations.add("3");
        assertTrue(context.getIntentNb(observations) == 0);
        observations.remove("2");
        assertTrue(context.getIntentNb(observations) == 1);
        observations.remove("1");
        assertTrue(context.getIntentNb(observations) == 2);
    }

    /**
     * Test of context reversion.
     */
    @Test
    public void testGetReverseContext() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        TemporaryContext iContext = context.getReverseContext();
        assertFalse(context.getAttributes().equals(context.getObservations()));
        assertTrue(context.getAttributes().equals(iContext.getObservations()));
        assertTrue(iContext.getAttributes().equals(context.getObservations()));
    }

    /**
     * Test of arrowClosure methods.
     */
    @Test
    public void testArrowClosure() {
        TemporaryContext ctx = new TemporaryContext();
        ctx.addToAttributes('a');
        ctx.addToAttributes('b');
        ctx.addToAttributes('c');
        ctx.addToObservations(1);
        ctx.addToObservations(2);
        ctx.addToObservations(3);
        ctx.addExtentIntent(1, 'a');
        ctx.addExtentIntent(2, 'a');
        ctx.addExtentIntent(2, 'c');
        ctx.addExtentIntent(3, 'b');
        TreeSet<Comparable> obs = new TreeSet<Comparable>();
        obs.add(1);
        assertTrue(ctx.arrowClosureObject(obs).getAttributes().size() == 3);
        assertTrue(ctx.arrowClosureObject(obs).getObservations().size() == 3);
        TreeSet<Comparable> attr = new TreeSet<Comparable>();
        attr.add('c');
        assertTrue(ctx.arrowClosureAttribute(attr).getAttributes().size() == 3);
        assertTrue(ctx.arrowClosureAttribute(attr).getObservations().size() == 3);
    }

    /**
     * Test subDirectDecomposition method.
     */
    @Test
    public void testSubDirectDecomposition() {
        TemporaryContext ctx = TemporaryContext.random(20, 3, 4);
        ctx.reduction();
        ConceptLattice cl = ctx.conceptLattice(true);
        Lattice l = ctx.subDirectDecomposition();
        int count = 0;
        for (Node n : l.getNodes()) {
            Couple c = (Couple) n.getContent();
            if (c.getRight().toString() == "true") {
                count++;
            }
        }
        assertEquals(count, cl.getNodes().size());
    }

    /**
     * Test getArrowClosedSubContext method.
     */
    @Test
    public void testGetArrowClosedSubContext() {
        Lattice l = new Lattice();
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        l.addNode(n1);
        l.addNode(n2);
        l.addNode(n3);
        l.addNode(n4);
        l.addEdge(n1, n2);
        l.addEdge(n1, n3);
        l.addEdge(n2, n4);
        l.addEdge(n3, n4);
        TemporaryContext ctx = l.getTable();
        ctx.reduction();
        TemporaryContext arrowCtx = ctx.getArrowClosedSubContext();
        assertTrue(arrowCtx.getExtent(n3).contains(n2));
        assertTrue(arrowCtx.getExtent(n2).contains(n3));
    }

    /**
     * Test for getDivisionContext and getDivisionConvex methods.
     */
    @Test
    public void testLatticeDivision() {
        Lattice l = LatticeFactory.booleanAlgebra(2);
        TemporaryContext ctx = l.getTable();
        ctx.reduction();
        ArrayList<TemporaryContext> subContexts = ctx.getDivisionContext();
        TreeSet<Node> convex = ctx.getDivisionConvex(subContexts.get(0));
        assertEquals(subContexts.get(0).conceptLattice(true).getNodes().size() + convex.size(), l.getNodes().size());
    }
}

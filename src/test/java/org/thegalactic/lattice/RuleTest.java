package org.thegalactic.lattice;

/*
 * RuleTest.java
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

import java.util.Collection;
import java.util.TreeSet;

import org.thegalactic.dgraph.Node;
import org.thegalactic.util.ComparableSet;

/**
 *
 * @author jeff
 */
public class RuleTest {

    /**
     * Test the empty constructor.
     */
    @Test
    public void testEmptyConstructor() {
        Rule r = new Rule();
        assertTrue(r.getPremise().isEmpty());
    }

    /**
     * Test the constructor.
     */
    @Test
    public void testConstructor() {
        ComparableSet p = new ComparableSet();
        ComparableSet c = new ComparableSet();
        Rule r = new Rule(p, c);
        assertEquals(r.getPremise(), p);
        assertEquals(r.getConclusion(), c);
    }

    /**
     * Test the getPremise method.
     */
    @Test
    public void testgetPremise() {
        ComparableSet p = new ComparableSet();
        ComparableSet c = new ComparableSet();
        Rule r = new Rule(p, c);
        assertEquals(r.getPremise(), p);
        assertEquals(r.getConclusion(), c);
    }

    /**
     * Test the getConclusion method.
     */
    @Test
    public void testgetConclusion() {
        ComparableSet p = new ComparableSet();
        ComparableSet c = new ComparableSet();
        Rule r = new Rule(p, c);
        assertEquals(r.getPremise(), p);
        assertEquals(r.getConclusion(), c);
    }

    /**
     * Test the addToPremise method.
     */
    @Test
    public void testaddToPremise() {
        Rule r = new Rule();
        ComparableSet s = new ComparableSet();
        r.addToPremise(s);
        assertTrue(r.getPremise().contains(s));
    }

    /**
     * Test the removeFromPremise method.
     */
    @Test
    public void testremoveFromPremise() {
        Rule r = new Rule();
        ComparableSet s = new ComparableSet();
        r.addToPremise(s);
        assertTrue(r.getPremise().contains(s));
        r.removeFromPremise(s);
        assertFalse(r.getPremise().contains(s));
    }

    /**
     * Test the addToConclusion method.
     */
    @Test
    public void testaddToConclusion() {
        Rule r = new Rule();
        ComparableSet s = new ComparableSet();
        r.addToConclusion(s);
        assertTrue(r.getConclusion().contains(s));
    }

    /**
     * Test the removeFromConclusion method.
     */
    @Test
    public void testremoveFromConclusion() {
        Rule r = new Rule();
        ComparableSet s = new ComparableSet();
        r.addToConclusion(s);
        assertTrue(r.getConclusion().contains(s));
        r.removeFromConclusion(s);
        assertFalse(r.getConclusion().contains(s));
    }

    /**
     * Test the addAllToPremise method.
     */
    @Test
    public void testaddAllToPremise() {
        Rule r = new Rule();
        TreeSet<Node> s = new TreeSet<Node>();
        Node a = new Node('a');
        Node b = new Node('b');
        s.add(a);
        s.add(b);
        r.addAllToPremise((Collection) s);
        assertTrue(r.getPremise().contains(a));
        assertTrue(r.getPremise().contains(b));
    }

    /**
     * Test the removeAllFromPremise method.
     */
    @Test
    public void testremoveAllFromPremise() {
        Rule r = new Rule();
        TreeSet<ComparableSet> s = new TreeSet<ComparableSet>();
        r.addAllToPremise((Collection) s);
        r.removeAllFromPremise((Collection) s);
        assertTrue(r.getPremise().isEmpty());
    }

    /**
     * Test the addAllToConclusion method.
     */
    @Test
    public void testaddAllToConclusion() {
        Rule r = new Rule();
        TreeSet<Node> s = new TreeSet<Node>();
        Node a = new Node('a');
        Node b = new Node('b');
        s.add(a);
        s.add(b);
        r.addAllToConclusion((Collection) s);
        assertTrue(r.getConclusion().contains(a));
        assertTrue(r.getConclusion().contains(b));
    }

    /**
     * Test the removeAllFromConclusion method.
     */
    @Test
    public void testremoveAllFromConclusion() {
        Rule r = new Rule();
        TreeSet<ComparableSet> s = new TreeSet<ComparableSet>();
        r.addAllToConclusion((Collection) s);
        r.removeAllFromConclusion((Collection) s);
        assertTrue(r.getConclusion().isEmpty());
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testtoString() {
        Rule r = new Rule();
        Node a = new Node('a');
        Node b = new Node('b');
        r.addToPremise(a);
        r.addToConclusion(b);
        assertEquals(r.toString(), "a  -> b ");
    }

    /**
     * Test the hashCode method.
     */
    @Test
    public void testhashCode() {
        Rule r = new Rule();
        assertEquals(r.hashCode(), (Object) r.hashCode());
    }

    /**
     * Test the Equals method.
     */
    @Test
    public void testEquals() {
        Rule r = new Rule();
        assertTrue(r.equals(r));
        assertFalse(r.equals(new Object()));
    }

    /**
     * Test the compareTo method.
     */
    @Test
    public void testcompareTo() {
        Rule r = new Rule();
        assertEquals(r.compareTo(r), 0);
    }
}

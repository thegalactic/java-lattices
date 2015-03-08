package org.thegalactic.lattice;

/*
 * ClosureSystemTest.java
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

import org.thegalactic.util.ComparableSet;
import org.thegalactic.context.TemporaryContext;

/**
 *
 * @author jeff
 */
public class ClosureSystemTest {

    /**
     * Test for the allClosures method for TemporaryContext.
     */
    @Test
    public void testallClosuresCTX() {
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
        assertEquals(context.allClosures().size(), 6);
    }

    /**
     * Test for the allClosures method for ImplicationSystem.
     */
    @Test
    public void testallClosuresIS() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        elements.add('c');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        r.addToConclusion('c');
        is.addRule(r);
        assertEquals(is.allClosures().size(), 5);
    }

    /**
     * Test for the closedSetLattice method from a context.
     *
     * TODO There's something wrong with that test : diagramLattice is always empty ... See ConceptLattice tests
     */
    @Test
    public void testclosedSetLatticeCTX() {
        TemporaryContext context = TemporaryContext.random(10, 3, 5);
        assertEquals(context.closedSetLattice(true).getNodes().size(), ConceptLattice.diagramLattice(context).getNodes().size());
        assertEquals(context.closedSetLattice(false).getNodes().size(), ConceptLattice.completeLattice(context).getNodes().size());
        assertEquals(context.closedSetLattice(true).getEdges().size(), ConceptLattice.diagramLattice(context).getEdges().size());
        assertEquals(context.closedSetLattice(false).getEdges().size(), ConceptLattice.completeLattice(context).getEdges().size());
    }

    /**
     * Test for the closedSetLattice method from an implication system.
     *
     * TODO There's something wrong with that test : diagramLattice is always almost empty ... See ConceptLattice tests
     */
    @Test
    public void testclosedSetLatticeIS() {
        ImplicationalSystem is = ImplicationalSystem.random(7, 4);
        assertEquals(is.closedSetLattice(true).getNodes().size(), ConceptLattice.diagramLattice(is).getNodes().size());
        assertEquals(is.closedSetLattice(false).getNodes().size(), ConceptLattice.completeLattice(is).getNodes().size());
        assertEquals(is.closedSetLattice(true).getEdges().size(), ConceptLattice.diagramLattice(is).getEdges().size());
        assertEquals(is.closedSetLattice(false).getEdges().size(), ConceptLattice.completeLattice(is).getEdges().size());
    }

    /**
     * Test for the getReductibleElements method for ImplicationalSystem.
     */
    @Test
    public void testgetReducibleElementsIS() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add(0);
        elements.add(1);
        elements.add(2);
        is.addAllElements(elements);
        Rule r1 = new Rule();
        r1.addToPremise(0);
        r1.addToConclusion(1);
        is.addRule(r1);
        Rule r2 = new Rule();
        r2.addToPremise(1);
        r2.addToConclusion(0);
        is.addRule(r2);
        assertTrue(is.getReducibleElements().get(1).contains(0));
        assertTrue(is.getReducibleElements().get(2).isEmpty());
    }

    /**
     * Test for the getReductibleElements method for TemporaryContext.
     */
    @Test
    public void testgetReducibleElementsCTX() {
        TemporaryContext context = new TemporaryContext();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        assertEquals(context.getReducibleElements().size(), 1);
    }

    /**
     * Test for the nextClosure method for TemporaryContext.
     */
    @Test
    public void testnextClosureCTX() {
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
        assertEquals(context.nextClosure(new Concept(context.closure(new ComparableSet()), false)).toString(), "[b]");
    }

    /**
     * Test for the nextClosure method for ImplicationalSystem.
     */
    @Test
    public void testnextClosureIS() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        elements.add('c');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        r.addToConclusion('c');
        is.addRule(r);
        assertEquals(is.nextClosure(new Concept(is.closure(new ComparableSet()), false)).toString(), "[c]");
    }

    /**
     * Test for the precedenceGraph method for ImplicationalSystem.
     */
    @Test
    public void testprecedenceGraphIS() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add(0);
        elements.add(1);
        is.addAllElements(elements);
        Rule r1 = new Rule();
        r1.addToPremise(0);
        r1.addToConclusion(1);
        is.addRule(r1);
        assertEquals(is.precedenceGraph().getEdges().size(), 1);
    }

    /**
     * Test for the precedenceGraph method for TemporaryContext.
     */
    @Test
    public void testprecedenceGraphCTX() {
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
        assertEquals(context.precedenceGraph().getNodes().size(), 3);
        assertEquals(context.precedenceGraph().getEdges().size(), 1);
    }
}

package org.thegalactic.lattice;

/*
 * ConceptTest.java
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.TreeSet;

import org.thegalactic.util.ComparableSet;
import org.thegalactic.dgraph.Node;
import org.thegalactic.context.Context;

/**
 *
 * @author jeff
 */
public class ConceptTest {
    /**
     * Test the constructor from SetA and SetB.
     */
    @Test
    public void testSetASetBConcept() {
        TreeSet<Comparable> setA = new TreeSet<Comparable>();
        TreeSet<Comparable> setB = new TreeSet<Comparable>();
        Concept c = new Concept(setA, setB);
        assertFalse(c.getSetA() == null);
        assertFalse(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA and SetB=False.
     */
    @Test
    public void testSetABFConcept() {
        TreeSet<Comparable> setA = new TreeSet<Comparable>();
        Concept c = new Concept(setA, false);
        assertFalse(c.getSetA() == null);
        assertTrue(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA and SetB=True.
     */
    @Test
    public void testSetABTConcept() {
        TreeSet<Comparable> setA = new TreeSet<Comparable>();
        Concept c = new Concept(setA, true);
        assertFalse(c.getSetA() == null);
        assertFalse(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA=false and SetB.
     */
    @Test
    public void testAFSetBConcept() {
        TreeSet<Comparable> setB = new TreeSet<Comparable>();
        Concept c = new Concept(false, setB);
        assertTrue(c.getSetA() == null);
        assertFalse(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA=true and SetB.
     */
    @Test
    public void testATSetBConcept() {
        TreeSet<Comparable> setB = new TreeSet<Comparable>();
        Concept c = new Concept(true, setB);
        assertFalse(c.getSetA() == null);
        assertFalse(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA=true and SetB=true.
     */
    @Test
    public void testATBTConcept() {
        Concept c = new Concept(true, true);
        assertFalse(c.getSetA() == null);
        assertFalse(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA=true and SetB=false.
     */
    @Test
    public void testATBFConcept() {
        Concept c = new Concept(true, false);
        assertFalse(c.getSetA() == null);
        assertTrue(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA=false and SetB=true.
     */
    @Test
    public void testAFBTConcept() {
        Concept c = new Concept(false, true);
        assertTrue(c.getSetA() == null);
        assertFalse(c.getSetB() == null);
    }
    /**
     * Test the constructor from SetA=false and SetB=false.
     */
    @Test
    public void testAFBFConcept() {
        Concept c = new Concept(false, false);
        assertTrue(c.getSetA() == null);
        assertTrue(c.getSetB() == null);
    }
    /**
     * Test the constructor from a copy.
     */
    @Test
    public void testCopyConcept() {
        Concept tt = new Concept(true, true);
        Concept copytt = new Concept(tt);
        assertEquals(tt, copytt);
        Concept ft = new Concept(false, true);
        Concept copyft = new Concept(ft);
        assertTrue(copyft.getSetA() == null);
        assertFalse(copyft.getSetB() == null);
        Concept tf = new Concept(true, false);
        Concept copytf = new Concept(tf);
        assertFalse(copytf.getSetA() == null);
        assertTrue(copytf.getSetB() == null);
        Concept ff = new Concept(false, false);
        Concept copyff = new Concept(ff);
        assertTrue(copyff.getSetA() == null);
        assertTrue(copyff.getSetB() == null);
    }
    /**
     * Test the clone method.
     */
    @Test
    public void testClone() {
        Concept tt = new Concept(true, true);
        Concept copytt = tt.clone();
        assertEquals(tt, copytt);
        Concept tf = new Concept(true, false);
        Concept copytf = tf.clone();
        assertEquals(tf, copytf);
        Concept ft = new Concept(false, true);
        Concept copyft = ft.clone();
        assertEquals(ft, copyft);
        Concept ff = new Concept(false, false);
        Concept copyff = ff.clone();
        assertFalse(copyff.hasSetA());
        assertFalse(copyff.hasSetB());
    }
    /**
     * Test the hasSetB method.
     */
    @Test
    public void testhasSetB() {
        Concept c = new Concept(true, true);
        assertTrue(c.hasSetB());
    }
    /**
     * Test the hasSetA method.
     */
    @Test
    public void testhasSetA() {
        Concept c = new Concept(true, true);
        assertTrue(c.hasSetA());
    }
    /**
     * Test the containsInA method.
     */
    @Test
    public void testcontainsInA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        c.addToA(n);
        assertTrue(c.containsInA(n));
    }
    /**
     * Test the containsInA method.
     * false case
     */
    @Test
    public void testcontainsInAF() {
        Concept c = new Concept(false, true);
        Node n = new Node('n');
        c.addToA(n);
        assertFalse(c.containsInA(n));
    }
    /**
     * Test the containsInB method.
     */
    @Test
    public void testcontainsInB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        c.addToB(n);
        assertTrue(c.containsInB(n));
    }
    /**
     * Test the containsInB method.
     * false case
     */
    @Test
    public void testcontainsInBF() {
        Concept c = new Concept(true, false);
        Node n = new Node('n');
        c.addToB(n);
        assertFalse(c.containsInB(n));
    }
    /**
     * Test the containsAllInA method.
     */
    @Test
    public void testcontainsAllInA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.putSetA(tn);
        assertTrue(c.containsAllInA(tn));
    }
    /**
     * Test the containsAllInB method.
     */
    @Test
    public void testcontainsAllInB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.putSetB(tn);
        assertTrue(c.containsAllInB(tn));
    }
    /**
     * Test the containsAllInA method.
     * false case
     */
    @Test
    public void testcontainsAllInAF() {
        Concept c = new Concept(false, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.addAllToA(tn);
        assertFalse(c.containsAllInA(tn));
    }
    /**
     * Test the containsAllInB method.
     * false case
     */
    @Test
    public void testcontainsAllInBF() {
        Concept c = new Concept(true, false);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.addAllToB(tn);
        assertFalse(c.containsAllInB(tn));
    }
    /**
     * Test the putSetB method.
     */
    @Test
    public void testputSetB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.putSetB(tn);
        assertTrue(c.containsAllInB(tn));
        Concept cf = new Concept(true, false);
        cf.putSetB(tn);
        assertTrue(cf.containsAllInB(tn));
    }
    /**
     * Test the putSetA method.
     */
    @Test
    public void testputSetA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.putSetA(tn);
        assertTrue(c.containsAllInA(tn));
        Concept cf = new Concept(false, true);
        cf.putSetA(tn);
        assertTrue(cf.containsAllInA(tn));
    }
    /**
     * Test the addToA method.
     */
    @Test
    public void testaddToA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        c.addToA(n);
        assertTrue(c.containsInA(n));
    }
    /**
     * Test the addToB method.
     */
    @Test
    public void testaddToB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        c.addToB(n);
        assertTrue(c.containsInB(n));
    }
    /**
     * Test the removeFromA method.
     */
    @Test
    public void testremoveFromA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        c.addToA(n);
        c.removeFromA(n);
        assertFalse(c.containsInA(n));
        Concept cf = new Concept(false, true);
        cf.addToA(n);
        cf.removeFromA(n);
        assertFalse(cf.containsInA(n));
    }
    /**
     * Test the removeFromB method.
     */
    @Test
    public void testremoveFromB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        c.addToB(n);
        c.removeFromB(n);
        assertFalse(c.containsInB(n));
        Concept cf = new Concept(true, false);
        cf.addToB(n);
        cf.removeFromB(n);
        assertFalse(cf.containsInB(n));
    }
    /**
     * Test the addAllToA method.
     */
    @Test
    public void testaddAllToA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.addAllToA(tn);
        assertTrue(c.containsAllInA(tn));
    }
    /**
     * Test the addAllToB method.
     */
    @Test
    public void testaddAllToB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.addAllToB(tn);
        assertTrue(c.containsAllInB(tn));
    }
    /**
     * Test the removeAllFromA method.
     */
    @Test
    public void testremoveAllFromA() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.addAllToA(tn);
        c.removeAllFromA(tn);
        assertFalse(c.containsAllInA(tn));
        Concept cf = new Concept(false, true);
        cf.addAllToA(tn);
        cf.removeAllFromA(tn);
        assertFalse(cf.containsAllInA(tn));
    }
    /**
     * Test the removeAllFromB method.
     */
    @Test
    public void testremoveAllFromB() {
        Concept c = new Concept(true, true);
        Node n = new Node('n');
        Node m = new Node('m');
        ComparableSet tn = new ComparableSet();
        tn.add(m);
        tn.add(n);
        c.addAllToB(tn);
        c.removeAllFromB(tn);
        assertFalse(c.containsAllInB(tn));
        Concept cf = new Concept(true, false);
        cf.addAllToB(tn);
        cf.removeAllFromB(tn);
        assertFalse(cf.containsAllInB(tn));
    }
    /**
     * Test the hashCode method.
     */
    @Test
    public void testhashCode() {
        Concept c = new Concept(true, true);
        assertEquals(c.hashCode(), (Object) c.hashCode());
    }
    /**
     * Test the Equals method.
     */
    @Test
    public void testEquals() {
        Concept c = new Concept(true, true);
        assertTrue(c.equals(c));
    }
    /**
     * Test the immediateSuccessorsLOA method.
     */
    @Test
    public void testimmediateSuccessorsLOA() {
        // The following context is present in the paper "An efficient algorithm ..."
        Context ctx = new Context();
        ctx.addToAttributes('b');
        ctx.addToAttributes('h');
        ctx.addToAttributes('m');
        ctx.addToAttributes('t');
        ctx.addToAttributes('w');
        ctx.addToObservations('1');
        ctx.addToObservations('2');
        ctx.addToObservations('3');
        ctx.addToObservations('4');
        ctx.addToObservations('5');
        ctx.addExtentIntent('1', 'b');
        ctx.addExtentIntent('2', 'b');
        ctx.addExtentIntent('3', 'b');
        ctx.addExtentIntent('4', 'b');
        ctx.addExtentIntent('1', 'h');
        ctx.addExtentIntent('4', 'h');
        ctx.addExtentIntent('3', 'm');
        ctx.addExtentIntent('5', 'm');
        ctx.addExtentIntent('4', 't');
        ctx.addExtentIntent('5', 't');
        ctx.addExtentIntent('2', 'w');
        ctx.addExtentIntent('5', 'w');
        ctx.setBitSets();
        TreeSet<Comparable> setA = new TreeSet<Comparable>();
        TreeSet<Comparable> setB = new TreeSet<Comparable>();
        setA.add('b');
        //setA.add('h');
        //setA.add('m');
        //setA.add('t');
        //setA.add('w');
        //setB.add('1');
        //setB.add('2');
        //setB.add('3');
        //setB.add('4');
        //setB.add('5');
        setA.addAll(ctx.closure(setA));
        setB.addAll(ctx.getExtent(setA));
        Concept c = new Concept(setA, setB);
        ArrayList<TreeSet<Comparable>> succ = c.immediateSuccessorsLOA(ctx);
        assertTrue(succ.size() == 3);
    }
    /**
     * Test the immediateSuccessors method.
     */
    @Test
    public void testimmediateSucessors() {
        Node a = new Node('a');
        Node b = new Node('b');
        Node c = new Node('c');
        ComparableSet tA = new ComparableSet();
        tA.add(a);
        ComparableSet tB = new ComparableSet();
        tB.add(b);
        ComparableSet tC = new ComparableSet();
        tC.add(c);
        Concept cA = new Concept(tA, false);
        new Concept(tB, false);
        new Concept(tC, false);
        Rule r1 = new Rule(tA, tB);
        Rule r2 = new Rule(tB, tC);
        Rule r3 = new Rule(tC, tA);
        TreeSet<Rule> rs = new TreeSet<Rule>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        ImplicationalSystem is = new ImplicationalSystem(rs);
        ArrayList<TreeSet<Comparable>> succ = cA.immediateSuccessors(is);
        assertTrue(succ.get(0).contains(a));
        assertTrue(succ.get(0).contains(b));
        assertTrue(succ.get(0).contains(c));
    }
    /**
     * Test the toString method.
     */
    @Test
    public void testtoString() {
        Concept c = new Concept(true, true);
        assertEquals(c.toString(), "[]-[]");
    }
    /**
     * Test the toDot method.
     */
    @Test
    public void testtoDot() {
        Concept c = new Concept(true, true);
        //assertEquals(c.toDot(), "1 [label=\" []\\n[]\"]");
    }
}

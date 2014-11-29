package fr.kbertet.lattice;

/*
 * ConceptLatticeTest.java
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
import java.util.Vector;
import java.util.Scanner;
import java.io.File;

import fr.kbertet.util.ComparableSet;
import fr.kbertet.dgraph.DAGraph;
import fr.kbertet.dgraph.Node;
import fr.kbertet.context.Context;

/**
 * Test of class ConceptLattice.
 */
public class ConceptLatticeTest {
    /**
     * Test of addNode method, of class ConceptLattice.
     */
    @Test
    public void testConstructorFromLattice() {
        Lattice l = new Lattice();
        Concept a = new Concept(true, true); l.addNode(a);
        Concept b = new Concept(true, true); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice cl = new ConceptLattice(l);
        assertTrue(cl.isLattice());
        assertTrue(cl.containsConcepts());
        assertEquals(2, cl.getNodes().size());
        assertEquals(1, cl.getEdges().size());
    }

    /**
     * Test of addNode method, of class ConceptLattice.
     */
    @Test
    public void testAddNode() {
        Concept n = new Concept(true, true);
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.addNode(n));
    }

    /**
     * Test of addEdge method, of class ConceptLattice.
     */
    @Test
    public void testAddEdge() {
        Concept from = new Concept(true, true);
        Concept to = new Concept(true, true);
        ConceptLattice instance = new ConceptLattice();
        instance.addNode(from);
        instance.addNode(to);
        assertTrue(instance.addEdge(from, to));
    }

    /**
     * Test of containsConcepts method, of class ConceptLattice.
     */
    @Test
    public void testContainsConcepts() {
        Concept concept1 = new Concept(true, true);
        Concept concept2 = new Concept(true, true);
        TreeSet<Concept> set = new TreeSet<Concept>();
        set.add(concept1);
        set.add(concept2);
        ConceptLattice cl = new ConceptLattice(set);
        cl.addEdge(concept1, concept2);
        assertTrue(cl.containsConcepts());
    }

    /**
     * Test of isConceptLattice method, of class ConceptLattice.
     */
    @Test
    public void testIsConceptLattice() {
        TreeSet<Comparable> com = new TreeSet<Comparable>();
        com.add((Comparable) "a");
        Concept concept1 = new Concept(true, com);
        Concept concept2 = new Concept(true, true);
        TreeSet<Concept> set = new TreeSet<Concept>();
        set.add(concept1);
        set.add(concept2);
        ConceptLattice cl = new ConceptLattice(set);
        cl.addEdge(concept1, concept2);
        assertTrue(cl.isConceptLattice());
    }

    /**
     * Test of containsAllSetA method, of class ConceptLattice.
     */
    @Test
    public void testContainsAllSetA() {
        Lattice l = new Lattice();
        Concept a = new Concept(true, true); l.addNode(a);
        Concept b = new Concept(true, true); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice instance = new ConceptLattice(l);
        boolean expResult = true;
        for (Node n : instance.getNodes()) {
            expResult &= ((Concept) n).hasSetA();
        }
        boolean result = instance.containsAllSetA();
        assertEquals(expResult, result);
    }

    /**
     * Test of containsAllSetB method, of class ConceptLattice.
     */
    @Test
    public void testContainsAllSetB() {
        Lattice l = new Lattice();
        Concept a = new Concept(true, true); l.addNode(a);
        Concept b = new Concept(true, true); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice instance = new ConceptLattice(l);
        boolean expResult = true;
        for (Node n : instance.getNodes()) {
            expResult &= ((Concept) n).hasSetB();
        }
        boolean result = instance.containsAllSetB();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class ConceptLattice.
     */
    @Test
    public void testClone() {
        Lattice l = new Lattice();
        Concept a = new Concept(true, true); l.addNode(a);
        Concept b = new Concept(true, true); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice cl = new ConceptLattice(l);
        ConceptLattice result = cl.clone();
        assertEquals(a, result.getNode(a));
        assertEquals(b, result.getNode(b));
        assertEquals(1, result.getEdges().size());
    }

    /**
     * Test of getConcept method.
     */
    @Test
    public void testGetConcept() {
        Lattice l = new Lattice();
        Concept a = new Concept(true, true); l.addNode(a);
        ConceptLattice cl = new ConceptLattice(l);
        assertFalse(cl.getConcept(new ComparableSet(), new ComparableSet()) == null);
    }
    /**
     * Test of removeAllSetA method, of class ConceptLattice.
     */
    @Test
    public void testRemoveAllSetA() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.removeAllSetA());
    }

    /**
     * Test of removeAllSetB method, of class ConceptLattice.
     */
    @Test
    public void testRemoveAllSetB() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.removeAllSetB());
    }

    /**
     * Test of initialiseSetAForJoin method, of class ConceptLattice.
     */
    @Test
    public void testInitialiseSetAForJoin() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.initialiseSetAForJoin());
    }

    /**
     * Test of initialiseSetBForMeet method, of class ConceptLattice.
     */
    @Test
    public void testInitialiseSetBForMeet() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.initialiseSetBForMeet());
    }

    /**
     * Test of makeInclusionReduction method, of class ConceptLattice.
     */
    @Test
    public void testMakeInclusionReduction() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.makeInclusionReduction());
    }

    /**
     * Test of makeIrreduciblesReduction method, of class ConceptLattice.
     */
    @Test
    public void testMakeIrreduciblesReduction() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.makeIrreduciblesReduction());
    }

    /**
     * Test of makeEdgeValuation method, of class ConceptLattice.
     */
    @Test
    public void testMakeEdgeValuation() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.makeEdgeValuation());
    }

    /**
     * Test of getJoinReduction method, of class ConceptLattice.
     */
    @Test
    public void testGetJoinReduction() {
        TreeSet<Comparable> com1 = new TreeSet<Comparable>();
        TreeSet<Comparable> com2 = new TreeSet<Comparable>();
        TreeSet<Comparable> com3 = new TreeSet<Comparable>();
        TreeSet<Comparable> com4 = new TreeSet<Comparable>();
        com1.add((Comparable) "a");
        com2.add((Comparable) "b");
        com3.add((Comparable) "c");
        com4.add((Comparable) "d");
        Lattice l = new Lattice();
        Concept a = new Concept(com1, com4); l.addNode(a);
        Concept b = new Concept(com3, com2); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice instance = new ConceptLattice(l);
        Lattice lat = instance.getJoinReduction();
        assertTrue(lat.isLattice());
        assertEquals(2, lat.getNodes().size());
        assertEquals(1, lat.getEdges().size());
    }

    /**
     * Test of getMeetReduction method, of class ConceptLattice.
     */
    @Test
    public void testGetMeetReduction() {
        TreeSet<Comparable> com1 = new TreeSet<Comparable>();
        TreeSet<Comparable> com2 = new TreeSet<Comparable>();
        TreeSet<Comparable> com3 = new TreeSet<Comparable>();
        TreeSet<Comparable> com4 = new TreeSet<Comparable>();
        com1.add((Comparable) "a");
        com2.add((Comparable) "b");
        com3.add((Comparable) "c");
        com4.add((Comparable) "d");
        Lattice l = new Lattice();
        Concept a = new Concept(com1, com4); l.addNode(a);
        Concept b = new Concept(com3, com2); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice instance = new ConceptLattice(l);
        Lattice lat = instance.getMeetReduction();
        assertTrue(lat.isLattice());
        assertEquals(2, lat.getNodes().size());
        assertEquals(1, lat.getEdges().size());
    }

    /**
     * Test of getIrreduciblesReduction method, of class ConceptLattice.
     */
    @Test
    public void testGetIrreduciblesReduction() {
        TreeSet<Comparable> com1 = new TreeSet<Comparable>();
        TreeSet<Comparable> com2 = new TreeSet<Comparable>();
        TreeSet<Comparable> com3 = new TreeSet<Comparable>();
        TreeSet<Comparable> com4 = new TreeSet<Comparable>();
        com1.add((Comparable) "a");
        com2.add((Comparable) "b");
        com3.add((Comparable) "c");
        com4.add((Comparable) "d");
        Lattice l = new Lattice();
        Concept a = new Concept(com1, com4); l.addNode(a);
        Concept b = new Concept(com3, com2); l.addNode(b);
        l.addEdge(a, b);
        ConceptLattice instance = new ConceptLattice(l);
        Lattice lat = instance.getIrreduciblesReduction();
        assertTrue(lat.isLattice());
        assertEquals(2, lat.getNodes().size());
        assertEquals(1, lat.getEdges().size());
        for (Node n : lat.getNodes()) {
            assertTrue(n.getContent() != null);
        }
    }

    /**
     * Test of idealLattice method, of class ConceptLattice.
     */
    @Test
    public void testIdealLattice() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node("e");
        Node node2 = new Node("f");
        set.add(node1);
        set.add(node2);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        ConceptLattice result = ConceptLattice.idealLattice(dag);
        assertEquals(3, result.getNodes().size());
        assertTrue(result.getEdges().isEmpty());
    }

    /**
     * Test of completeLattice method, of class ConceptLattice.
     */
    @Test
    public void testCompleteLattice() {
        TreeSet<Comparable> comparablesAtts = new TreeSet<Comparable>();
        TreeSet<Comparable> comparablesObjs = new TreeSet<Comparable>();
        comparablesAtts.add((Comparable) "a");
        comparablesAtts.add((Comparable) "b");
        comparablesObjs.add((Comparable) "1");
        comparablesObjs.add((Comparable) "2");
        comparablesObjs.add((Comparable) "3");
        Context cs = new Context();
        cs.addAllToAttributes(comparablesAtts);
        cs.addAllToObservations(comparablesObjs);
        cs.addExtentIntent("1", "a");
        cs.addExtentIntent("2", "a");
        cs.addExtentIntent("3", "b");
        ConceptLattice result = ConceptLattice.completeLattice(cs);
        assertEquals(4, result.getNodes().size());
        assertEquals(9, result.getEdges().size());
    }

    /**
     * Test of diagramLattice method, of class ConceptLattice.
     */
    @Test
    public void testDiagramLattice() {
        TreeSet<Comparable> comparablesAtts = new TreeSet<Comparable>();
        TreeSet<Comparable> comparablesObjs = new TreeSet<Comparable>();
        comparablesAtts.add((Comparable) "a");
        comparablesAtts.add((Comparable) "b");
        comparablesAtts.add((Comparable) "c");
        comparablesObjs.add((Comparable) "1");
        comparablesObjs.add((Comparable) "2");
        comparablesObjs.add((Comparable) "3");
        Context cs = new Context();
        cs.addAllToAttributes(comparablesAtts);
        cs.addAllToObservations(comparablesObjs);
        cs.addExtentIntent("1", "a");
        cs.addExtentIntent("2", "a");
        cs.addExtentIntent("3", "b");
        cs.addExtentIntent("3", "c");
        ConceptLattice result = ConceptLattice.diagramLattice(cs);
        assertEquals(null, result.getNodes().first().getContent());
    }

    /**
     * Test of immediateSuccessors method, of class ConceptLattice.
     */
    @Test
    public void testImmediateSuccessors() {
        TreeSet<Comparable> comparablesAtts = new TreeSet<Comparable>();
        TreeSet<Comparable> comparablesObjs = new TreeSet<Comparable>();
        comparablesAtts.add((Comparable) "a");
        comparablesAtts.add((Comparable) "b");
        comparablesAtts.add((Comparable) "c");
        comparablesAtts.add((Comparable) "d");
        comparablesObjs.add((Comparable) "1");
        comparablesObjs.add((Comparable) "2");
        comparablesObjs.add((Comparable) "3");
        Context cs = new Context();
        cs.addAllToAttributes(comparablesAtts);
        cs.addAllToObservations(comparablesObjs);
        cs.addExtentIntent("1", "a");
        cs.addExtentIntent("1", "c");
        cs.addExtentIntent("2", "a");
        cs.addExtentIntent("2", "b");
        cs.addExtentIntent("2", "c");
        cs.addExtentIntent("2", "d");
        cs.addExtentIntent("3", "a");
        cs.addExtentIntent("3", "b");
        TreeSet<Comparable> a = new TreeSet<Comparable>();
        a.add((Comparable) "a");
        a.add((Comparable) "b");
        Concept c = new Concept(a, true);
        DAGraph da = new DAGraph(cs.precedenceGraph());
        Lattice l = new Lattice(da);
        ConceptLattice instance = new ConceptLattice(l);
        Vector<TreeSet<Comparable>> result = instance.immediateSuccessors(c, cs);
        TreeSet<Comparable> b = new TreeSet<Comparable>();
        b.add("a");
        b.add("b");
        b.add("c");
        b.add("d");
        Vector<TreeSet<Comparable>> expResult = new Vector<TreeSet<Comparable>>();
        expResult.add(b);
        assertEquals(expResult, result);
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
            ConceptLattice l = new ConceptLattice();
            Concept a = new Concept(true, true); l.addNode(a);
            Concept b = new Concept(true, true); l.addNode(b);
            l.addEdge(a, b);
            l.save(filename);
            String content = "";
            file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }
            assertEquals(content, "digraph G {Graph [rankdir=BT]"
                + a.getIdentifier() + " [label=\" []\\n[]\"]"
                + b.getIdentifier() + " [label=\" []\\n[]\"]"
                + a.getIdentifier() + "->" + b.getIdentifier()
                + "}"
            );
            file.delete();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Test iceberg method.
     */
    @Test
    public void testIceberg() {
        TreeSet<Comparable> comparablesAtts = new TreeSet<Comparable>();
        TreeSet<Comparable> comparablesObjs = new TreeSet<Comparable>();
        comparablesAtts.add((Comparable) "a");
        comparablesAtts.add((Comparable) "b");
        comparablesAtts.add((Comparable) "c");
        comparablesAtts.add((Comparable) "d");
        comparablesObjs.add((Comparable) "1");
        comparablesObjs.add((Comparable) "2");
        comparablesObjs.add((Comparable) "3");
        Context cs = new Context();
        cs.addAllToAttributes(comparablesAtts);
        cs.addAllToObservations(comparablesObjs);
        cs.addExtentIntent("1", "a");
        cs.addExtentIntent("1", "c");
        cs.addExtentIntent("2", "a");
        cs.addExtentIntent("2", "b");
        cs.addExtentIntent("2", "c");
        cs.addExtentIntent("2", "d");
        cs.addExtentIntent("3", "a");
        cs.addExtentIntent("3", "b");
        ConceptLattice l = cs.conceptLattice(true);
        assertEquals(l.getNodes().size(), l.iceberg((float) 0.0).getNodes().size());
    }
}

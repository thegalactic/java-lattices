/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lattice;

import dgraph.DAGraph;
import dgraph.Node;
import java.util.TreeSet;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author ndao01
 */
public class ConceptLatticeTest {    
    public ConceptLatticeTest() {
    }    
    @BeforeClass
    public static void setUpClass() {
    }    
    @AfterClass
    public static void tearDownClass() {
    }    
    @Before
    public void setUp() {
    }    
    @After
    public void tearDown() {
    }
    /**
     * Test of addNode method, of class ConceptLattice.
     */
    @Test
    public void testConstructorFromLattice() {
        Lattice lat = new Lattice();
        ConceptLattice CL = new ConceptLattice(lat);
        if (!CL.isConceptLattice()) {
            assertTrue(CL.getNodes().isEmpty());
            assertTrue(CL.getEdges().isEmpty());
        }
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
        for (Node n : instance.getNodes()){
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
        for (Node n : instance.getNodes()){
            expResult &= ((Concept) n).hasSetB();
        }
        boolean result = instance.containsAllSetB();
        assertEquals(expResult, result);
    }
    /**
     * Test of copy method, of class ConceptLattice.
     */
    @Test
    public void testCopy() {
        Lattice l = new Lattice();
        Concept a = new Concept(true, true); l.addNode(a);
        Concept b = new Concept(true, true); l.addNode(b);
        ConceptLattice instance = new ConceptLattice(l);
        ConceptLattice result = new ConceptLattice(instance.copy());
        assertEquals(a, result.getNode(a));
        assertEquals(b, result.getNode(b));
        assertTrue(result.getEdges().isEmpty());
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
     * Test of initializeSetAForJoin method, of class ConceptLattice.
     */
    @Test
    public void testInitializeSetAForJoin() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.initializeSetAForJoin());
    }
    /**
     * Test of initializeSetBForMeet method, of class ConceptLattice.
     */
    @Test
    public void testInitializeSetBForMeet() {
        ConceptLattice instance = new ConceptLattice();
        assertTrue(instance.initializeSetBForMeet());
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
        for (Node n : lat.getNodes()){
            assertTrue(n.getContent() != null);
        }
    }
    /**
     * Test of idealsLattice method, of class ConceptLattice.
     */
    @Test
    public void testIdealsLattice() {
        TreeSet<Node> set = new TreeSet<Node>();
        Node node1 = new Node("e");
        Node node2 = new Node("f");
        set.add(node1);
        set.add(node2);
        DAGraph dag = new DAGraph(set);
        dag.addEdge(node1, node2);
        ConceptLattice result = ConceptLattice.idealsLattice(dag);
        
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
//    /**
//     * Test of recursiveDiagramLattice method, of class ConceptLattice.
//     */
//    @Test
//    public void testRecursiveDiagramLattice() {
//        System.out.println("recursiveDiagramLattice");
//        
//        TreeSet<Comparable> comparablesAtts = new TreeSet<Comparable>();
//        TreeSet<Comparable> comparablesObjs = new TreeSet<Comparable>();
//        comparablesAtts.add((Comparable) "a");
//        comparablesAtts.add((Comparable) "b");
//        comparablesAtts.add((Comparable) "c");
//        comparablesAtts.add((Comparable) "d");       
//        
//        comparablesObjs.add((Comparable) "1");
//        comparablesObjs.add((Comparable) "2");
//        comparablesObjs.add((Comparable) "3");
//        
//        Context cs = new Context();
//        cs.addAllToAttributes(comparablesAtts);
//        cs.addAllToObservations(comparablesObjs);
//        cs.addExtentIntent("1", "a");
//        cs.addExtentIntent("1", "c");
//        cs.addExtentIntent("2", "a");
//        cs.addExtentIntent("2", "b");
//        cs.addExtentIntent("2", "c");
//        cs.addExtentIntent("2", "d");
//        cs.addExtentIntent("3", "a");
//        cs.addExtentIntent("3", "b");
//        
//        TreeSet<Comparable> A = new TreeSet<Comparable>();
//        A.add((Comparable) "a");
//        A.add((Comparable) "b");
//        Concept c = new Concept(true, true);
//        
//        DAGraph da = new DAGraph(cs.precedenceGraph());
//        Lattice l = new Lattice(da);
//        ConceptLattice instance = new ConceptLattice(l);
//        instance.recursiveDiagramLattice(c, cs);
//        
////        Concept n = null;
////        ClosureSystem init = null;
////        ConceptLattice instance = new ConceptLattice();
////        instance.recursiveDiagramLattice(n, init);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
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
        TreeSet<Comparable> A = new TreeSet<Comparable>();
        A.add((Comparable) "a");
        A.add((Comparable) "b");
        Concept c = new Concept(A, true);        
        DAGraph da = new DAGraph(cs.precedenceGraph());
        Lattice l = new Lattice(da);
        ConceptLattice instance = new ConceptLattice(l);
        Vector<TreeSet<Comparable>> result = instance.immediateSuccessors(c, cs);        
        TreeSet<Comparable> B = new TreeSet<Comparable>();
        B.add("a");
        B.add("b");
        B.add("c");
        Vector<TreeSet<Comparable>> expResult = new Vector<TreeSet<Comparable>>();
        expResult.add(B);        
        assertEquals(expResult, result);
    }    
}

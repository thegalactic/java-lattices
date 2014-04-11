package lattice;

import dgraph.DAGraph;
import dgraph.Node;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author jeff
 */
public class LatticeFactoryTest {
    /**
     * Test constructor.
     */
    @Test
    public void testLatticeFactory() {
        LatticeFactory lf = new LatticeFactory();
        assertEquals(lf.getClass().toString(), "class lattice.LatticeFactory");
    }
    /**
     * Test for the random method.
     */
    @Test
    public void testrandom() {
        Lattice l = LatticeFactory.random(10);
        assertTrue(l.isLattice());
        assertEquals(l.getNodes().size(), 10);
    }
    /**
     * Test for the booleanAlgebra method.
     */
    @Test
    public void testbooleanAlgebra() {
        Random r = new Random();
        int n = r.nextInt(10);
        Lattice l = LatticeFactory.booleanAlgebra(n);
        assertEquals(l.getNodes().size(), (int) Math.pow(2, n));
        assertEquals(l.getEdges().size(), n * ((int) Math.pow(2, n - 1)));
    }
    /**
     * Test for the permutationLattice method.
     */
    @Test
    public void testpermutationLattice() {
        Lattice l = LatticeFactory.permutationLattice(6);
        assertEquals(l.getNodes().size(), 6 * 5 * 4 * 3 * 2);
    }
    /**
     * Test product method.
     */
    @Test
    public void testproduct() {
        Lattice l = LatticeFactory.booleanAlgebra(2);
        Lattice r = LatticeFactory.booleanAlgebra(2);
        Lattice lr = LatticeFactory.product(l, r);
        assertTrue(lr.getNodes().size() == 16);
    }
    /**
     * Test doublingConvex method.
     */
    @Test
    public void testdoublingConvex() {
        Lattice l = new Lattice();
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        l.addNode(n1);
        l.addNode(n2);
        l.addNode(n4);
        l.addNode(n5);
        l.addEdge(n1, n2);
        l.addEdge(n1, n4);
        l.addEdge(n2, n5);
        l.addEdge(n4, n5);
        DAGraph c = new DAGraph();
        c.addNode(n1);
        c.addNode(n2);
        c.addNode(n4);
        c.addEdge(n1, n2);
        c.addEdge(n1, n4);
        Lattice dbl = LatticeFactory.doublingConvex(l, c);
        assertEquals(dbl.getNodes().size(), 7);
        assertEquals(dbl.getEdges().size(), 9);
    }
}

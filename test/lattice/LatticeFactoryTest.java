package lattice;

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
}

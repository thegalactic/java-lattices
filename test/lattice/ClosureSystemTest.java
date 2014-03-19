package lattice;

import java.util.TreeSet;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author jeff
 */
public class ClosureSystemTest {
    /**
     * Test for the allClosures method.
     *
     * @todo implementing test
     */
    @Test
    public void testallClosures() {
        assertTrue(true);
    }
    /**
     * Test for the closedSetLattice method.
     *
     * @todo implementing test
     */
    @Test
    public void testclosedSetLattice() {
        assertTrue(true);
    }
    /**
     * Test for the getReductibleElements method.
     */
    @Test
    public void testgetReducibleElements() {
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
     * Test for the nextClosure method.
     *
     * @todo implementing test
     */
    @Test
    public void testnextClosure() {
        assertTrue(true);
    }
    /**
     * Test for the precedenceGraph method.
     */
    @Test
    public void testprecedenceGraph() {
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
}

package lattice;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import dgraph.Node;
import dgraph.DGraph;

/**
 *
 * @author Jean-François
 */
public class LatticeTest {
    /**
     * Test the ArrowRelation methode.
     */
    @Test
    public void testArrowRelation() {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        Node e = new Node("e"); l.addNode(e);
        Node f = new Node("f"); l.addNode(f);
        Node g = new Node("g"); l.addNode(g);
        Node h = new Node("h"); l.addNode(h);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(b, d);
        l.addEdge(c, e);
        l.addEdge(d, f);
        l.addEdge(e, g);
        l.addEdge(f, g);
        l.addEdge(g, h);
        DGraph ar = l.ArrowRelation();
        assertEquals((String) ar.getEdge(g, b).getContent(), "Cross");
        assertEquals((String) ar.getEdge(f, c).getContent(), "UpDown");
        assertEquals((String) ar.getEdge(f, e).getContent(), "Up");
        assertEquals((String) ar.getEdge(d, c).getContent(), "Down");
        assertEquals((String) ar.getEdge(a, h).getContent(), "Circ");
    }
}

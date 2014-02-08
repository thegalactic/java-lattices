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
     * Test the ArrowRelation method.
     */
    @Test
    public void testArrowRelation() {
        
        Lattice L= new Lattice();
        
        Node a= new Node("a");L.addNode(a);
        Node b= new Node("b");L.addNode(b);
        Node c= new Node("c");L.addNode(c);
        Node d= new Node("d");L.addNode(d);
        Node e= new Node("e");L.addNode(e);
        Node f= new Node("f");L.addNode(f);
        Node g= new Node("g");L.addNode(g);
        Node h= new Node("h");L.addNode(h);
        L.addEdge(a, b);
        L.addEdge(b, c);
        L.addEdge(b, d);
        L.addEdge(c, e);
        L.addEdge(d, f);
        L.addEdge(e, g);
        L.addEdge(f, g);
        L.addEdge(g, h);
        
        DGraph AR = L.ArrowRelation();
        
        assertEquals((String)AR.getEdge(g, b).getContent(),"Cross");    
        assertEquals((String)AR.getEdge(f, c).getContent(),"UpDown");
        assertEquals((String)AR.getEdge(f, e).getContent(),"Up");
        assertEquals((String)AR.getEdge(d, c).getContent(),"Down");
        assertEquals((String)AR.getEdge(a, h).getContent(),"Circ");
             
    }
}

    


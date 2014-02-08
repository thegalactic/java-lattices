package lattice;

import dgraph.Node;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Jean-François
 */
public class ArrowRelationTest {
    /**
     * Test the toLaTex method.
     */
    @Test
    public void testtoLaTeX() {
        try{
        Lattice L= new Lattice();
        
        Node a= new Node("a");L.addNode(a);
        Node b= new Node("b");L.addNode(b);
        Node c= new Node("c");L.addNode(c);
        Node d= new Node("d");L.addNode(d);
        Node e= new Node("e");L.addNode(e);
        
        L.addEdge(a, b);
        L.addEdge(b, c);
        L.addEdge(c, e);
        L.addEdge(a, d);
        L.addEdge(d, e);
        
        ArrowRelation AR = new ArrowRelation(L);    
        
        File file = File.createTempFile("junit", ".dot");
        
        String filename = file.getName();
        file.delete();
            
        AR.writeLaTex(filename);
            
        String content = "";
        file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
        content += scanner.nextLine();
        }
            
        assertEquals(content, "\\begin{tabular}{|c|*{3}{c|}}"
                +"\\hline"
                +" & b & c & d\\\\ "
                +"\\hline"
                +"b & $\\times$ & $\\circ$ & $\\downarrow$\\\\ "
                +"\\hline"
                +"c & $\\times$ & $\\times$ & $\\updownarrow$\\\\ "
                +"\\hline"
                +"d & $\\updownarrow$ & $\\uparrow$ & $\\times$\\\\ "
                +"\\hline" 
                +"\\end{tabular}"
            );
            file.delete();
    }catch(IOException e){System.out.println("IOException : "+e.getMessage());}
}
    
}

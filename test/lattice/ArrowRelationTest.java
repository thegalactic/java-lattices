package lattice;

/*
 * ArrowRelationTest.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

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
    public void testSave() {
        try {
        Lattice l = new Lattice();
        Node a = new Node("a"); l.addNode(a);
        Node b = new Node("b"); l.addNode(b);
        Node c = new Node("c"); l.addNode(c);
        Node d = new Node("d"); l.addNode(d);
        Node e = new Node("e"); l.addNode(e);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(c, e);
        l.addEdge(a, d);
        l.addEdge(d, e);
        ArrowRelation ar = new ArrowRelation(l);
        File file = File.createTempFile("junit", ".tex");
        String filename = file.getName();
        file.delete();
        ar.save(filename);
        String content = "";
        file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            content += scanner.nextLine();
        }
        assertEquals(content, "\\begin{tabular}{|c|*{3}{c|}}"
                + "\\hline"
                + " & b & c & d\\\\ "
                + "\\hline"
                + "b & $\\times$ & $\\circ$ & $\\downarrow$\\\\ "
                + "\\hline"
                + "c & $\\times$ & $\\times$ & $\\updownarrow$\\\\ "
                + "\\hline"
                + "d & $\\updownarrow$ & $\\uparrow$ & $\\times$\\\\ "
                + "\\hline"
                + "\\end{tabular}"
        );
        file.delete();
    } catch (IOException e) { System.out.println("IOException : " + e.getMessage()); }
}
}

package org.thegalactic.lattice;

/*
 * ArrowRelationTest.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify
 * it under the terms of the CeCILL-B license.
 */
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;
import org.thegalactic.context.Context;

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
            Node a = new Node("a");
            l.addNode(a);
            Node b = new Node("b");
            l.addNode(b);
            Node c = new Node("c");
            l.addNode(c);
            Node d = new Node("d");
            l.addNode(d);
            Node e = new Node("e");
            l.addNode(e);
            l.addEdge(a, b);
            l.addEdge(b, c);
            l.addEdge(c, e);
            l.addEdge(a, d);
            l.addEdge(d, e);
            ArrowRelation ar = new ArrowRelation(l);
            File file = File.createTempFile("junit", ".tex");
            String filename = file.getPath();
            ar.save(filename);
            String content = "";
            file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }
            assertEquals(content, "\\begin{tabular}{|c|*{3}{c|}}"
                    + "\\hline"
                    + " & b & c & d\\\\"
                    + "\\hline"
                    + "b & $\\times$ & $\\times$ & $\\updownarrow$\\\\"
                    + "\\hline"
                    + "c & $\\updownarrow$ & $\\times$ & $\\uparrow$\\\\"
                    + "\\hline"
                    + "d & $\\downarrow$ & $\\updownarrow$ & $\\times$\\\\"
                    + "\\hline"
                    + "\\end{tabular}"
            );
            file.delete();
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
        }
    }

    /**
     * Test the getDoubleUpArrowTable method.
     */
    @Test
    public void testGetDoubleUpArrowTable() {
        Lattice l = new Lattice();
        Node a = new Node("a");
        l.addNode(a);
        Node b = new Node("b");
        l.addNode(b);
        Node c = new Node("c");
        l.addNode(c);
        Node d = new Node("d");
        l.addNode(d);
        Node e = new Node("e");
        l.addNode(e);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(c, e);
        l.addEdge(a, d);
        l.addEdge(d, e);
        ArrowRelation ar = new ArrowRelation(l);
        Context ctx = ar.getDoubleUpArrowTable();
        assertTrue(ctx.getIntent(c).contains(d));
    }

    /**
     * Test the getDownArrowTable method.
     */
    @Test
    public void testGetDoubleDownArrowTable() {
        Lattice l = new Lattice();
        Node a = new Node("a");
        l.addNode(a);
        Node b = new Node("b");
        l.addNode(b);
        Node c = new Node("c");
        l.addNode(c);
        Node d = new Node("d");
        l.addNode(d);
        Node e = new Node("e");
        l.addNode(e);
        l.addEdge(a, b);
        l.addEdge(b, c);
        l.addEdge(c, e);
        l.addEdge(a, d);
        l.addEdge(d, e);
        ArrowRelation ar = new ArrowRelation(l);
        Context ctx = ar.getDoubleDownArrowTable();
        assertTrue(ctx.getIntent(d).contains(b));
    }

    /**
     * Test getDoubleArrowTable method.
     */
    @Test
    public void testgetDoubleArrowTable() {
        Lattice l = new Lattice();
        Node b = new Node('b');
        l.addNode(b);
        Node c = new Node('c');
        l.addNode(c);
        Node d = new Node('d');
        l.addNode(d);
        Node e = new Node('e');
        l.addNode(e);
        Node f = new Node('f');
        l.addNode(f);
        Node g = new Node('g');
        l.addNode(g);
        Node t = new Node('t');
        l.addNode(t);
        Edge bc = new Edge(b, c);
        l.addEdge(bc);
        Edge bd = new Edge(b, d);
        l.addEdge(bd);
        Edge be = new Edge(b, e);
        l.addEdge(be);
        Edge cf = new Edge(c, f);
        l.addEdge(cf);
        Edge df = new Edge(d, f);
        l.addEdge(df);
        Edge dg = new Edge(d, g);
        l.addEdge(dg);
        Edge eg = new Edge(e, g);
        l.addEdge(eg);
        Edge ft = new Edge(f, t);
        l.addEdge(ft);
        Edge gt = new Edge(g, t);
        l.addEdge(gt);
        ArrowRelation ar = new ArrowRelation(l);
        Context ctx = ar.getDoubleArrowTable();
        assertTrue(ctx.getExtent(c).contains(d));
        assertTrue(ctx.getExtent(e).contains(d));
        assertTrue(ctx.getExtent(f).contains(e));
        assertTrue(ctx.getExtent(g).contains(c));
    }

    /**
     * Test getDoubleArrowTable method.
     */
    @Test
    public void testgetDoubleCircArrowTable() {
        Lattice l = new Lattice();
        Node a = new Node('a');
        l.addNode(a);
        Node b = new Node('b');
        l.addNode(b);
        Node c = new Node('c');
        l.addNode(c);
        Node d = new Node('d');
        l.addNode(d);
        Node e = new Node('e');
        l.addNode(e);
        Node f = new Node('f');
        l.addNode(f);
        Node g = new Node('g');
        l.addNode(g);
        Node h = new Node('h');
        l.addNode(h);
        Edge ab = new Edge(a, b);
        l.addEdge(ab);
        Edge bc = new Edge(b, c);
        l.addEdge(bc);
        Edge cd = new Edge(c, d);
        l.addEdge(cd);
        Edge de = new Edge(d, e);
        l.addEdge(de);
        Edge af = new Edge(a, f);
        l.addEdge(af);
        Edge fg = new Edge(f, g);
        l.addEdge(fg);
        Edge gh = new Edge(g, h);
        l.addEdge(gh);
        Edge he = new Edge(h, e);
        l.addEdge(he);
        ArrowRelation ar = new ArrowRelation(l);
        Context ctx = ar.getDoubleCircArrowTable();
        assertTrue(ctx.getExtent(f).contains(c));
        assertTrue(ctx.getExtent(f).contains(d));
    }
}

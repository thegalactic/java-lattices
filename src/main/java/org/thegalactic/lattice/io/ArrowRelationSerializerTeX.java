package org.thegalactic.lattice.io;

/*
 * ArrowRelationSerializerTeX.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;
import org.thegalactic.io.Writer;
import org.thegalactic.lattice.ArrowRelation;

/**
 * This class defines the way for writing an arrow relatin as a tex file.
 *
 * ![ArrowRelationSerializerTeX](ArrowRelationSerializerTeX.png)
 *
 * @uml ArrowRelationSerializerTeX.png
 * !include resources/org/thegalactic/lattice/io/ArrowRelationSerializerTeX.iuml
 * !include resources/org/thegalactic/lattice/io/ArrowRelationWriter.iuml
 *
 * hide members
 * show ArrowRelationSerializerTeX members
 * class ArrowRelationSerializerTeX #LightCyan
 * title ArrowRelationSerializerTeX UML graph
 */
public final class ArrowRelationSerializerTeX implements Writer<ArrowRelation> {

    /**
     * The singleton instance.
     */
    private static final ArrowRelationSerializerTeX INSTANCE = new ArrowRelationSerializerTeX();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ArrowRelationSerializerTeX getInstance() {
        return INSTANCE;
    }

    /**
     * Register this class for writing .tex files.
     */
    public static void register() {
        ArrowRelationIOFactory.getInstance().registerWriter(ArrowRelationSerializerTeX.getInstance(), "tex");
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ArrowRelationSerializerTeX() {
    }

    /**
     * Produces the LaTex source code for the array of arrows of this component.
     * The LaTeX source produced isn't autonomous. It must be included in a
     * document.
     *
     * @param arrow an arrow relation to write
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(ArrowRelation arrow, BufferedWriter file) throws IOException {
        SortedSet<Edge> edges = arrow.getEdges();
        TreeSet<Node> m = new TreeSet<Node>();
        TreeSet<Node> j = new TreeSet<Node>();
        for (Edge e : edges) {
            m.add(e.getFrom());
            j.add(e.getTo());
        }
        String newLine = System.getProperty("line.separator");
        String str = "\\begin{tabular}{|c|*{" + Integer.toString(j.size()) + "}{c|}}" + newLine;
        str += "\\hline" + newLine;
        for (Node nj : j) {
            str += " & " + nj.getContent();
        }
        str += "\\\\ " + newLine;
        str += "\\hline" + newLine;
        for (Node nm : m) {
            str += nm.getContent();
            for (Node nj : j) {
                Edge e = arrow.getEdge(nm, nj);
                if (arrow.isUp(e)) {
                    str += " & $\\uparrow$";
                } else if (arrow.isDown(e)) {
                    str += " & $\\downarrow$";
                } else if (arrow.isUpDown(e)) {
                    str += " & $\\updownarrow$";
                } else if (arrow.isCross(e)) {
                    str += " & $\\times$";
                } else {
                    str += " & $\\circ$";
                } /* Previous code, in a Java7 only way, was :
                switch ((String)e.getContent()) {
                    case "Up":str += " & $\\uparrow$";break;
                    case "Down":str += " & $\\downarrow$";break;
                    case "UpDown":str += " & $\\updownarrow$";break;
                    case "Cross":str += " & $\\times$";break;
                    case "Circ":str += " & $\\circ$";break;
                    default :break;
                 */
            }
            str += "\\\\ " + newLine;
            str += "\\hline" + newLine;
        }
        str += "\\end{tabular}" + newLine;
        file.write(str);
    }
}

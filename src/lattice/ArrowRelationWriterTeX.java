package lattice;

/*
 * ArrowRelationWriterTeX.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.SortedSet;
import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.IOException;
import dgraph.Edge;
import dgraph.Node;

/**
 * This class defines the way for writing an arrow relatin as a tex file.
 *
 * ![ArrowRelationWriterTex](ArrowRelationWriterTex.png)
 *
 * @uml ArrowRelationWriterTeX.png
 * !include src/lattice/ArrowRelationWriterTeX.iuml
 * !include src/lattice/ArrowRelationWriter.iuml
 *
 * hide members
 * show ArrowRelationWriterTeX members
 * class ArrowRelationWriterTeX #LightCyan
 * title ArrowRelationWriterTeX UML graph
 */
public final class ArrowRelationWriterTeX implements ArrowRelationWriter {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ArrowRelationWriterTeX() {
    }

    /**
     * The singleton instance.
     */
    private static ArrowRelationWriterTeX instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ArrowRelationWriterTeX getInstance() {
        if (instance == null) {
            instance = new ArrowRelationWriterTeX();
        }
        return instance;
    }

    /**
     * Register this class for writing .txt files.
     */
    public static void register() {
        ArrowRelationWriterFactory.register(ArrowRelationWriterTeX.getInstance(), "tex");
    }

    /**
     * Produces the LaTex source code for the array of arrows of this component.
     * The LaTeX source produced isn't autonomous. It must be included in a document.
     *
     * @param   arrow  an arrow relation to write
     * @param   file   a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void write(ArrowRelation arrow, BufferedWriter file) throws IOException {
        SortedSet<Edge> edges = arrow.getEdges();
        TreeSet<Node> m = new TreeSet<Node>();
        TreeSet<Node> j = new TreeSet<Node>();
        for (Edge e : edges) {
            m.add(e.getFrom());
            j.add(e.getTo());
        }
        String str = "\\begin{tabular}{|c|*{" + Integer.toString(j.size()) + "}{c|}}\n";
        str += "\\hline\n";
        for (Node nj : j) {
            str += " & " + nj.getContent();
        }
        str += "\\\\ \n";
        str += "\\hline\n";
        for (Node nm : m) {
            str += nm.getContent();
            for (Node nj : j) {
                Edge e = arrow.getEdge(nm, nj);
                if ((String) e.getContent() == "Up") {
                    str += " & $\\uparrow$";
                } else {
                    if ((String) e.getContent() == "Down") {
                    str += " & $\\downarrow$";
                } else {
                    if ((String) e.getContent() == "UpDown") {
                    str += " & $\\updownarrow$";
                } else {
                    if ((String) e.getContent() == "Cross") {
                    str += " & $\\times$";
                } else {
                    str += " & $\\circ$";
                    }
                    }
                    }
                /* Previous code, in a Java7 only way, was :
                switch ((String)e.getContent()) {
                    case "Up":str += " & $\\uparrow$";break;
                    case "Down":str += " & $\\downarrow$";break;
                    case "UpDown":str += " & $\\updownarrow$";break;
                    case "Cross":str += " & $\\times$";break;
                    case "Circ":str += " & $\\circ$";break;
                    default :break;
                */
                }
            }
            str += "\\\\ \n";
            str += "\\hline\n";
        }
        str += "\\end{tabular}\n";
        file.write(str);
    }
}


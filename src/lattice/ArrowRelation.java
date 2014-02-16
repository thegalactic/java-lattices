package lattice;

/*
 * ArrowRelation.java
 *
 * Copyright: 2013 University of La Rochelle, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Jean-Francois
 * @version 2014
 */

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.SortedSet;
import java.util.TreeSet;
import dgraph.Edge;
import dgraph.Node;
import dgraph.DGraph;

/**
 * The ArrowRelation class encodes arrow relation between meet & join-irreductibles of a lattice.
 *
 * Let m and b be respectively meet and join irreductibles of a lattice.
 * Recall that m has a unique successor say m+ and j has a unique predecessor say j-, then :
 * j "Up Arrow" m (stored has "Up") iff j is not less or equal than m and j is less than m+
 * j "Down Arrow" m (stored has "Down") iff j is not less or equal than m and j- is less than m
 * j "Up Down Arrow" m (stored has "UpDown") iff j "Up" m and j "Down" m
 * j "Cross" m (stored has "Cross") iff j is less or equal than m
 * j "Circ" m (stored has "Circ") iff neither j "Up" m nor j "Down" m nor j "Cross" m
 */
public class ArrowRelation extends DGraph  {
    /**
     * Unique constructor of this component from a lattice.
     *
     * Nodes are join or meet irreductibles of the lattice.
     * Edges content encodes arrows as String "Up", "Down", "UpDown", "Cross", "Circ".
     *
     * @param l : Lattice from which this component is deduced.
     */
    public ArrowRelation(Lattice l) {
        super(l.arrowRelation());
    }
    /**
     * Produces the LaTex source code for the array of arrows of this component.
     * The LaTeX source produced isn't autonomous. It must be included in a document.
     *
     * @param filename : Name of the file to be writen.
     */
    public void writeLaTex(String filename) {
        try {
            File outfile = new File(filename);
            FileWriter out = new FileWriter(outfile);
            SortedSet<Edge> edges = this.getEdges();
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
                    Edge e = this.getEdge(nm, nj);
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
            out.write(str);
            out.close();
        }  catch (IOException e)  { System.out.println("IOException : " + e.getMessage()); }
    }
}

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
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
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
        for (Edge edge : edges) {
            m.add(edge.getSource());
            j.add(edge.getTarget());
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\\begin{tabular}{|c|*{").append(j.size()).append("}{c|}}");
        file.write(builder.toString());
        file.newLine();

        builder.setLength(0);
        builder.append("\\hline");
        file.write(builder.toString());
        file.newLine();

        builder.setLength(0);
        for (Node nj : j) {
            builder.append(" & ").append(nj.getContent());
        }
        builder.append("\\\\");
        file.write(builder.toString());
        file.newLine();

        builder.setLength(0);
        builder.append("\\hline");
        file.write(builder.toString());
        file.newLine();

        for (Node nm : m) {
            builder.setLength(0);
            builder.append(nm.getContent());
            for (Node nj : j) {
                Edge edge = arrow.getEdge(nm, nj);
                if (arrow.isUp(edge)) {
                    builder.append(" & $\\uparrow$");
                } else if (arrow.isDown(edge)) {
                    builder.append(" & $\\downarrow$");
                } else if (arrow.isUpDown(edge)) {
                    builder.append(" & $\\updownarrow$");
                } else if (arrow.isCross(edge)) {
                    builder.append(" & $\\times$");
                } else {
                    builder.append(" & $\\circ$");
                }
            }
            builder.append("\\\\");
            file.write(builder.toString());
            file.newLine();

            builder.setLength(0);
            builder.append("\\hline");
            file.write(builder.toString());
            file.newLine();
        }
        builder.setLength(0);
        builder.append("\\end{tabular}");
        file.write(builder.toString());
        file.newLine();
    }
}

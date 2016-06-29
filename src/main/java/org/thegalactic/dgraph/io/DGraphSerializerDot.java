package org.thegalactic.dgraph.io;

/*
 * DGraphSerializerDot.java
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

import org.thegalactic.dgraph.ConcreteDGraph;
import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;
import org.thegalactic.io.Writer;

/**
 * This class defines the way for writing a graph to a text file.
 *
 * ![DGraphSerializerDot](DGraphSerializerDot.png)
 *
 * @param <N> Node content type
 * @param <E> Edge content type
 *
 * @uml
 *
 * DGraphSerializerDot.png
 *
 * !include resources/org/thegalactic/dgraph/io/DGraphSerializerDot.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members show DGraphSerializerDot members class DGraphSerializerDot
 * #LightCyan title DGraphSerializerDot UML graph
 */
public final class DGraphSerializerDot<N, E> implements Writer<ConcreteDGraph<N, E>> {

    /**
     * The singleton instance.
     */
    private static final DGraphSerializerDot INSTANCE = new DGraphSerializerDot();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static DGraphSerializerDot getInstance() {
        return INSTANCE;
    }

    /**
     * Register this class for writing .dot files.
     */
    public static void register() {
        DGraphIOFactory.getInstance().registerWriter(DGraphSerializerDot.getInstance(), "dot");
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private DGraphSerializerDot() {
    }

    /**
     * Write a graph to a output stream.
     *
     * @param graph a graph to write
     * @param file  a file
     *
     * @throws IOException When an IOException occurs
     */
    @Override
    public void write(final ConcreteDGraph<N, E> graph, final BufferedWriter file) throws IOException {
        // Writing start of graph
        file.write("digraph G {");
        file.newLine();
        file.write("Graph [rankdir=BT]");
        file.newLine();

        // Writing nodes
        for (final Node<N> node : graph.getNodes()) {
            file.write(String.valueOf(node.getIdentifier()));
            this.writeLabel(node.toString(), file);
            file.newLine();
        }

        // Writing edges
        for (final Edge<N, E> edge : graph.getEdges()) {
            file.write(String.valueOf(edge.getSource().getIdentifier()));
            file.write("->");
            file.write(String.valueOf(edge.getTarget().getIdentifier()));
            if (edge.hasContent()) {
                this.writeLabel(edge.getContent().toString(), file);
            }
            file.newLine();
        }

        // Writing end of graph
        file.write("}");
    }

    /**
     * Write a label to the dot file.
     *
     * @param label a label
     * @param file  a file
     *
     * @throws IOException When an IOException occurs
     */
    private void writeLabel(final String label, final BufferedWriter file) throws IOException {
        file.write(" [label=\"");
        file.write(label.replace("\"", "\\\""));
        file.write("\"]");
    }
}

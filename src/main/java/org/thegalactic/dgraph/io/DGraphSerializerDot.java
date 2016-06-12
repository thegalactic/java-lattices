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
import java.util.StringTokenizer;

import org.thegalactic.dgraph.DGraph;
import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;
import org.thegalactic.io.Writer;

/**
 * This class defines the way for writing a graph to a text file.
 *
 * ![DGraphSerializerDot](DGraphSerializerDot.png)
 *
 * @uml
 *
 * DGraphSerializerDot.png
 *
 * !include resources/org/thegalactic/dgraph/io/DGraphSerializerDot.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show DGraphSerializerDot members
 * class DGraphSerializerDot #LightCyan
 * title DGraphSerializerDot UML graph
 */
public final class DGraphSerializerDot implements Writer<DGraph> {

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
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    @Override
    public void write(DGraph graph, BufferedWriter file) throws IOException {
        String newLine = System.getProperty("line.separator");
        file.write("digraph G {" + newLine);
        file.write("Graph [rankdir=BT]" + newLine);
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        for (Node node : graph.getNodes()) {
            String dot = node.getIdentifier() + " [label=\"";
            StringTokenizer tokenizer = new StringTokenizer(node.toString(), "\"");
            while (tokenizer.hasMoreTokens()) {
                dot += tokenizer.nextToken();
            }
            dot += "\"]";
            nodes.append(dot).append(newLine);
        }
        for (Edge edge : graph.getEdges()) {
            String dot = edge.getFrom().getIdentifier() + "->" + edge.getTo().getIdentifier();
            if (edge.hasContent()) {
                dot = dot + " [" + "label=\"";
                StringTokenizer tokenizer = new StringTokenizer(edge.getContent().toString(), "\"");
                while (tokenizer.hasMoreTokens()) {
                    dot += tokenizer.nextToken();
                }
                dot = dot + "\"]";
            }
            edges.append(dot).append(newLine);
        }
        file.write(nodes.toString());
        file.write(edges.toString());
        file.write("}");
    }
}

package org.thegalactic.dgraph.io;

/*
 * DGraphWriterDot.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.thegalactic.dgraph.DGraph;
import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;

/**
 * This class defines the way for writing a graph as a dot file.
 *
 * ![DGraphWriterDot](DGraphWriterDot.png)
 *
 * @uml DGraphWriterDot.png
 * !include resources/org/thegalactic/dgraph/io/DGraphWriterDot.iuml
 * !include resources/org/thegalactic/dgraph/io/DGraphWriter.iuml
 *
 * hide members
 * show DGraphWriterDot members
 * class DGraphWriterDot #LightCyan
 * title DGraphWriterDot UML graph
 */
public final class DGraphWriterDot implements DGraphWriter {

    /**
     * The singleton instance.
     */
    private static final DGraphWriterDot INSTANCE = new DGraphWriterDot();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static DGraphWriterDot getInstance() {
        return INSTANCE;
    }

    /**
     * Register this class for writing .dot files.
     *
     * @return old writer
     */
    public static DGraphWriter register() {
        return DGraphWriterFactory.register(DGraphWriterDot.getInstance(), "dot");
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private DGraphWriterDot() {
    }

    /**
     * Write a graph to a output stream.
     *
     * @param graph a graph to write
     * @param file  a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(DGraph graph, BufferedWriter file) throws IOException {
        file.write("digraph G {\n");
        file.write("Graph [rankdir=BT]\n");
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        for (Node node : graph.getNodes()) {
            String dot = node.getIdentifier() + " [label=\"";
            StringTokenizer tokenizer = new StringTokenizer(node.toString(), "\"");
            while (tokenizer.hasMoreTokens()) {
                dot += tokenizer.nextToken();
            }
            dot += "\"]";
            nodes.append(dot).append("\n");
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
            edges.append(dot).append("\n");
        }
        file.write(nodes.toString());
        file.write(edges.toString());
        file.write("}");
    }
}

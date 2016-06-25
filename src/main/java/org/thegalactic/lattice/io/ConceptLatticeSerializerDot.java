package org.thegalactic.lattice.io;

/*
 * ConceptLatticeSerializerDot.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.IOException;

import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;
import org.thegalactic.io.Writer;
import org.thegalactic.lattice.Concept;
import org.thegalactic.lattice.ConceptLattice;

/**
 * This class defines the way for writing a concept lattice as a dot file.
 *
 * ![ConceptLatticeSerializerDot](ConceptLatticeSerializerDot.png)
 *
 * @uml
 *
 * ConceptLatticeSerializerDot.png
 *
 * !include resources/org/thegalactic/lattice/io/ConceptLatticeSerializerDot.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show ConceptLatticeSerializerDot members
 * class ConceptLatticeSerializerDot #LightCyan
 * title ConceptLatticeSerializerDot UML graph
 */
public final class ConceptLatticeSerializerDot implements Writer<ConceptLattice> {

    /**
     * The singleton instance.
     */
    private static final ConceptLatticeSerializerDot INSTANCE = new ConceptLatticeSerializerDot();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ConceptLatticeSerializerDot getInstance() {
        return INSTANCE;
    }

    /**
     * Register this class for writing .dot files.
     */
    public static void register() {
        ConceptLatticeIOFactory.getInstance().registerWriter(ConceptLatticeSerializerDot.getInstance(), "dot");
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ConceptLatticeSerializerDot() {
    }

    /**
     * Write a graph to a output stream.
     *
     * @param lattice a concept lattice to write
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(ConceptLattice lattice, BufferedWriter file) throws IOException {
        String newLine = System.getProperty("line.separator");
        file.write("digraph G {" + newLine);
        file.write("Graph [rankdir=BT]" + newLine);
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        for (Node node : lattice.getNodes()) {
            Concept concept = (Concept) node;
            String dot = concept.getIdentifier() + " [label=\" ";
            String tmp = "";
            if (concept.hasSetA()) {
                tmp += concept.getSetA();
            }
            if (concept.hasSetA() && concept.hasSetB()) {
                tmp += "\\n";
            }
            if (concept.hasSetB()) {
                tmp += concept.getSetB();
            }
            StringTokenizer st = new StringTokenizer(tmp, "\"");
            while (st.hasMoreTokens()) {
                dot += st.nextToken();
            }
            dot += "\"]";
            nodes.append(dot).append(newLine);
        }
        for (Edge edge : lattice.getEdges()) {
            String dot = edge.getSource().getIdentifier() + "->" + edge.getTarget().getIdentifier();
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

package lattice;

/*
 * ConceptLatticeWriterDot.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import dgraph.Edge;
import dgraph.Node;

/**
 * This class defines the way for writing a concept lattice as a dot file.
 *
 * ![ConceptLatticeWriterDot](ConceptLatticeWriterDot.png)
 *
 * @uml ConceptLatticeWriterDot.png
 * !include src/lattice/ConceptLatticeWriterDot.iuml
 * !include src/lattice/ConceptLatticeWriter.iuml
 *
 * hide members
 * show ConceptLatticeWriterDot members
 * class ConceptLatticeWriterDot #LightCyan
 * title ConceptLatticeWriterDot UML graph
 */
public final class ConceptLatticeWriterDot implements ConceptLatticeWriter {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ConceptLatticeWriterDot() {
    }

    /**
     * The singleton instance.
     */
    private static ConceptLatticeWriterDot instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ConceptLatticeWriterDot getInstance() {
        if (instance == null) {
            instance = new ConceptLatticeWriterDot();
        }
        return instance;
    }

    /**
     * Register this class for writing .dot files.
     */
    public static void register() {
        ConceptLatticeWriterFactory.register(ConceptLatticeWriterDot.getInstance(), "dot");
    }

    /**
     * Write a graph to a output stream.
     *
     * @param   lattice  a concept lattice to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void write(ConceptLattice lattice, BufferedWriter file) throws IOException {
        file.write("digraph G {\n");
        file.write("Graph [rankdir=BT]\n");
        StringBuffer nodes  = new StringBuffer();
        StringBuffer edges = new StringBuffer();
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
            nodes.append(dot).append("\n");
        }
        for (Edge edge : lattice.getEdges()) {
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


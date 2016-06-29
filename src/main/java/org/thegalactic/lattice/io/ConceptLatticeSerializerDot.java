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
import java.io.BufferedWriter;
import java.io.IOException;

import org.thegalactic.dgraph.Edge;
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
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(ConceptLattice lattice, BufferedWriter file) throws IOException {
        file.write("digraph G {");
        file.newLine();
        file.write("Graph [rankdir=BT]");
        file.newLine();
        StringBuilder builder = new StringBuilder();
        for (Object node : lattice.getNodes()) {
            builder.setLength(0);
            Concept concept = (Concept) node;
            file.write(String.valueOf(concept.getIdentifier()));
            file.write(" [label=\"");
            if (concept.hasSetA()) {
                builder.append(concept.getSetA().toString().replace("\"", "\\\""));
            }
            if (concept.hasSetA() && concept.hasSetB()) {
                builder.append("\\n");
            }
            if (concept.hasSetB()) {
                builder.append(concept.getSetB().toString().replace("\"", "\\\""));
            }
            file.write(builder.toString());
            file.write("\"]");
            file.newLine();
        }
        for (Object edge : lattice.getEdges()) {
            file.write(String.valueOf(((Edge) edge).getSource().getIdentifier()));
            file.write("->");
            file.write(String.valueOf(((Edge) edge).getTarget().getIdentifier()));
            if (((Edge) edge).hasContent()) {
                file.write(" [label=\"");
                builder.append(((Edge) edge).getContent().toString().replace("\"", "\\\""));
                file.write("\"]");
            }
            file.newLine();
        }
        file.write("}");
    }
}

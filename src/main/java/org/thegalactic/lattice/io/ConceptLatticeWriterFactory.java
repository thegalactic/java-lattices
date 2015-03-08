package org.thegalactic.lattice.io;

/*
 * ConceptLatticeWriterFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.HashMap;

/**
 * This class register writers for the ConceptLattice class.
 *
 * ![ConceptLatticeWriterFactory](ConceptLatticeWriterFactory.png)
 *
 * @uml ConceptLatticeWriterFactory.png
 * !include resources/org/thegalactic/lattice/io/ConceptLatticeWriterFactory.iuml
 * !include resources/org/thegalactic/lattice/io/ConceptLatticeWriter.iuml
 *
 * hide members
 * show ConceptLatticeWriterFactory members
 * class ConceptLatticeWriterFactory #LightCyan
 * title ConceptLatticeWriterFactory UML graph
 */
public final class ConceptLatticeWriterFactory {

    /**
     * Map of extension/writer.
     */
    private static final HashMap<String, ConceptLatticeWriter> WRITERS = new HashMap<String, ConceptLatticeWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param writer    The writer to register
     * @param extension The extension linked to the writer
     *
     * @return The old writer or null
     */
    public static ConceptLatticeWriter register(ConceptLatticeWriter writer, String extension) {
        ConceptLatticeWriter old = WRITERS.get(extension);
        WRITERS.put(extension, writer);
        return old;
    }

    /**
     * Unregister an extension.
     *
     * @param extension The extension linked to a writer
     *
     * @return The old writer or null
     */
    public static ConceptLatticeWriter unregister(String extension) {
        ConceptLatticeWriter old = WRITERS.get(extension);
        WRITERS.remove(extension);
        return old;
    }

    /**
     * Get the writer linked to an extension.
     *
     * @param extension The extension linked to a writer
     *
     * @return The writer or null
     */
    public static ConceptLatticeWriter get(String extension) {
        return WRITERS.get(extension);
    }

    /**
     * This class is not designed to be instantiated.
     */
    private ConceptLatticeWriterFactory() {
    }
}

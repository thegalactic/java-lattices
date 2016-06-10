package org.thegalactic.lattice.io;

/*
 * ConceptLatticeWriterFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of CeCILL-B license.
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
     * This class is not designed to be instantiated.
     */
    private ConceptLatticeWriterFactory() {
    }

    /**
     * Map of extension/writer.
     */
    private static HashMap<String, ConceptLatticeWriter> writers = new HashMap<String, ConceptLatticeWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old writer or null
     */
    public static ConceptLatticeWriter register(ConceptLatticeWriter writer, String extension) {
        ConceptLatticeWriter old = writers.get(extension);
        writers.put(extension, writer);
        return old;
    }

    /**
     * Unregister an extension.
     *
     * @param   extension  The extension linked to a writer
     *
     * @return  The old writer or null
     */
    public static ConceptLatticeWriter unregister(String extension) {
        ConceptLatticeWriter old = writers.get(extension);
        writers.remove(extension);
        return old;
    }

    /**
     * Get the writer linked to an extension.
     *
     * @param   extension  The extension linked to a writer
     *
     * @return  The writer or null
     */
    public static ConceptLatticeWriter get(String extension) {
        return writers.get(extension);
    }
}


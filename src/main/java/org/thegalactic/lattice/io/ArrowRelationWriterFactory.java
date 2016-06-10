package org.thegalactic.lattice.io;

/*
 * ArrowRelationWriterFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.HashMap;

/**
 * This class register writers for the ArrowRelation class.
 *
 * ![ArrowRelationWriterFactory](ArrowRelationWriterFactory.png)
 *
 * @uml ArrowRelationWriterFactory.png
 * !include resources/org/thegalactic/lattice/io/ArrowRelationWriterFactory.iuml
 * !include resources/org/thegalactic/lattice/io/ArrowRelationWriter.iuml
 *
 * hide members
 * show ArrowRelationWriterFactory members
 * class ArrowRelationWriterFactory #LightCyan
 * title ArrowRelationWriterFactory UML graph
 */
public final class ArrowRelationWriterFactory {
    /**
     * This class is not designed to be instantiated.
     */
    private ArrowRelationWriterFactory() {
    }

    /**
     * Map of extension/writer.
     */
    private static HashMap<String, ArrowRelationWriter> writers = new HashMap<String, ArrowRelationWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old writer or null
     */
    public static ArrowRelationWriter register(ArrowRelationWriter writer, String extension) {
        ArrowRelationWriter old = writers.get(extension);
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
    public static ArrowRelationWriter unregister(String extension) {
        ArrowRelationWriter old = writers.get(extension);
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
    public static ArrowRelationWriter get(String extension) {
        return writers.get(extension);
    }
}


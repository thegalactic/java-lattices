package org.thegalactic.dgraph.io;

/*
 * DGraphWriterFactory.java
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
 * This class register writers for the DGraph class.
 *
 * ![DGraphWriterFactory](DGraphWriterFactory.png)
 *
 * @uml DGraphWriterFactory.png
 * !include resources/org/thegalactic/dgraph/io/DGraphWriterFactory.iuml
 * !include resources/org/thegalactic/dgraph/io/DGraphWriter.iuml
 *
 * hide members
 * show DGraphWriterFactory members
 * class DGraphWriterFactory #LightCyan
 * title DGraphWriterFactory UML graph
 */
public final class DGraphWriterFactory {
    /**
     * This class is not designed to be instantiated.
     */
    private DGraphWriterFactory() {
    }

    /**
     * Map of extension/writer.
     */
    private static HashMap<String, DGraphWriter> writers = new HashMap<String, DGraphWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old writer or null
     */
    public static DGraphWriter register(DGraphWriter writer, String extension) {
        DGraphWriter old = writers.get(extension);
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
    public static DGraphWriter unregister(String extension) {
        DGraphWriter old = writers.get(extension);
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
    public static DGraphWriter get(String extension) {
        return writers.get(extension);
    }
}


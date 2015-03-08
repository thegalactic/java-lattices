package org.thegalactic.lattice.io;

/*
 * ArrowRelationWriterFactory.java
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
 * This class register WRITERS for the ArrowRelation class.

 ![ArrowRelationWriterFactory](ArrowRelationWriterFactory.png)
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
     * Map of extension/writer.
     */
    private static final HashMap<String, ArrowRelationWriter> WRITERS = new HashMap<String, ArrowRelationWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param writer    The writer to register
     * @param extension The extension linked to the writer
     *
     * @return The old writer or null
     */
    public static ArrowRelationWriter register(ArrowRelationWriter writer, String extension) {
        ArrowRelationWriter old = WRITERS.get(extension);
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
    public static ArrowRelationWriter unregister(String extension) {
        ArrowRelationWriter old = WRITERS.get(extension);
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
    public static ArrowRelationWriter get(String extension) {
        return WRITERS.get(extension);
    }

    /**
     * This class is not designed to be instantiated.
     */
    private ArrowRelationWriterFactory() {
    }
}

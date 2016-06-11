package org.thegalactic.lattice.io;

/*
 * ImplicationalSystemWriterFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.HashMap;

/**
 * This class register writers for the ImplicationalSystem class.
 *
 * ![ImplicationalSystemWriterFactory](ImplicationalSystemWriterFactory.png)
 *
 * @uml ImplicationalSystemWriterFactory.png
 * !include resources/org/thegalactic/lattice/io/ImplicationalSystemWriterFactory.iuml
 * !include resources/org/thegalactic/lattice/io/ImplicationalSystemWriter.iuml
 *
 * hide members
 * show ImplicationalSystemWriterFactory members
 * class ImplicationalSystemWriterFactory #LightCyan
 * title ImplicationalSystemWriterFactory UML graph
 */
public final class ImplicationalSystemWriterFactory {

    /**
     * Map of extension/writer.
     */
    private static final HashMap<String, ImplicationalSystemWriter> WRITERS = new HashMap<String, ImplicationalSystemWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param writer The writer to register
     * @param extension The extension linked to the writer
     *
     * @return The old writer or null
     */
    public static ImplicationalSystemWriter register(ImplicationalSystemWriter writer, String extension) {
        ImplicationalSystemWriter old = WRITERS.get(extension);
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
    public static ImplicationalSystemWriter unregister(String extension) {
        ImplicationalSystemWriter old = WRITERS.get(extension);
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
    public static ImplicationalSystemWriter get(String extension) {
        return WRITERS.get(extension);
    }

    /**
     * This class is not designed to be instantiated.
     */
    private ImplicationalSystemWriterFactory() {
    }
}

package fr.kbertet.lattice.io;

/*
 * ImplicationalSystemWriterFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.HashMap;

/**
 * This class register writers for the ImplicationalSystem class.
 *
 * ![ImplicationalSystemWriterFactory](ImplicationalSystemWriterFactory.png)
 *
 * @uml ImplicationalSystemWriterFactory.png
 * !include resources/fr/kbertet/lattice/io/ImplicationalSystemWriterFactory.iuml
 * !include resources/fr/kbertet/lattice/io/ImplicationalSystemWriter.iuml
 *
 * hide members
 * show ImplicationalSystemWriterFactory members
 * class ImplicationalSystemWriterFactory #LightCyan
 * title ImplicationalSystemWriterFactory UML graph
 */
public final class ImplicationalSystemWriterFactory {
    /**
     * This class is not designed to be instantiated.
     */
    private ImplicationalSystemWriterFactory() {
    }

    /**
     * Map of extension/writer.
     */
    private static HashMap<String, ImplicationalSystemWriter> writers = new HashMap<String, ImplicationalSystemWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old writer or null
     */
    public static ImplicationalSystemWriter register(ImplicationalSystemWriter writer, String extension) {
        ImplicationalSystemWriter old = writers.get(extension);
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
    public static ImplicationalSystemWriter unregister(String extension) {
        ImplicationalSystemWriter old = writers.get(extension);
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
    public static ImplicationalSystemWriter get(String extension) {
        return writers.get(extension);
    }
}


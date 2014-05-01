package lattice;

/*
 * ContextWriterFactory.java
 *
 * Copyright: 2013 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2014
 */

import java.util.HashMap;

/**
 * This class register writers for the Context class.
 *
 * ![ContextWriterFactory](ContextWriterFactory.png)
 *
 * @uml ContextWriterFactory.png
 * !include src/lattice/ContextWriterFactory.iuml
 * !include src/lattice/ContextWriter.iuml
 *
 * hide members
 * show ContextWriterFactory members
 * class ContextWriterFactory #LightCyan
 * title ContextWriterFactory UML graph
 */
public final class ContextWriterFactory {
    /**
     * This class is not designed to be instantiated.
     */
    private ContextWriterFactory() {
    }

    /**
     * Map of extension/writer.
     */
    private static HashMap<String, ContextWriter> writers = new HashMap<String, ContextWriter>();

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old writer or null
     */
    public static ContextWriter register(ContextWriter writer, String extension) {
        ContextWriter old = writers.get(extension);
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
    public static ContextWriter unregister(String extension) {
        ContextWriter old = writers.get(extension);
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
    public static ContextWriter get(String extension) {
        return writers.get(extension);
    }
}


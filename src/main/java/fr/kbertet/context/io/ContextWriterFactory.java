package fr.kbertet.context.io;

/*
 * ContextWriterFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.HashMap;

import fr.kbertet.io.Writer;
import fr.kbertet.context.Context;

/**
 * This class register writers for the Context class.
 *
 * ![ContextWriterFactory](ContextWriterFactory.png)
 *
 * @uml ContextWriterFactory.png
 * !include resources/fr/kbertet/context/io/ContextWriterFactory.iuml
 * !include resources/fr/kbertet/io/Writer.iuml
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
    private static HashMap<String, Writer<Context>> writers = new HashMap<String, Writer<Context>>();

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old writer or null
     */
    public static Writer<Context> register(Writer<Context> writer, String extension) {
        Writer<Context> old = writers.get(extension);
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
    public static Writer<Context> unregister(String extension) {
        Writer<Context> old = writers.get(extension);
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
    public static Writer<Context> get(String extension) {
        return writers.get(extension);
    }
}


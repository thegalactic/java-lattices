package fr.kbertet.context.io;

/*
 * ContextReaderFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.HashMap;

import fr.kbertet.io.Reader;
import fr.kbertet.context.Context;

/**
 * This class register readers for the Context class.
 *
 * ![ContextReaderFactory](ContextReaderFactory.png)
 *
 * @uml ContextReaderFactory.png
 * !include resources/fr/kbertet/context/io/ContextReaderFactory.iuml
 * !include resources/fr/kbertet/io/Reader.iuml
 *
 * hide members
 * show ContextReaderFactory members
 * class ContextReaderFactory #LightCyan
 * title ContextReaderFactory UML graph
 */
public final class ContextReaderFactory {
    /**
     * This class is not designed to be instantiated.
     */
    private ContextReaderFactory() {
    }

    /**
     * Map of extension/reader.
     */
    private static HashMap<String, Reader<Context>> readers = new HashMap<String, Reader<Context>>();

    /**
     * Register a reader with an extension.
     *
     * @param   reader     The reader to register
     * @param   extension  The extension linked to the reader
     *
     * @return  The old reader or null
     */
    public static Reader<Context> register(Reader<Context> reader, String extension) {
        Reader<Context> old = readers.get(extension);
        readers.put(extension, reader);
        return old;
    }

    /**
     * Unregister an extension.
     *
     * @param   extension  The extension linked to a reader
     *
     * @return  The old reader or null
     */
    public static Reader<Context> unregister(String extension) {
        Reader<Context> old = readers.get(extension);
        readers.remove(extension);
        return old;
    }

    /**
     * Get the reader linked to an extension.
     *
     * @param   extension  The extension linked to a reader
     *
     * @return  The reader or null
     */
    public static Reader<Context> get(String extension) {
        return readers.get(extension);
    }
}


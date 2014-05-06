package lattice;

/*
 * ContextReaderFactory.java
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
 * This class register readers for the Context class.
 *
 * ![ContextReaderFactory](ContextReaderFactory.png)
 *
 * @uml ContextReaderFactory.png
 * !include src/lattice/ContextReaderFactory.iuml
 * !include src/lattice/ContextReader.iuml
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
    private static HashMap<String, ContextReader> readers = new HashMap<String, ContextReader>();

    /**
     * Register a reader with an extension.
     *
     * @param   reader     The reader to register
     * @param   extension  The extension linked to the reader
     *
     * @return  The old reader or null
     */
    public static ContextReader register(ContextReader reader, String extension) {
        ContextReader old = readers.get(extension);
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
    public static ContextReader unregister(String extension) {
        ContextReader old = readers.get(extension);
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
    public static ContextReader get(String extension) {
        return readers.get(extension);
    }
}


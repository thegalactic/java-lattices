package lattice;

/*
 * ImplicationalSystemReaderFactory.java
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
 * This class register readers for the ImplicationalSystem class.
 *
 * ![ImplicationalSystemReaderFactory](ImplicationalSystemReaderFactory.png)
 *
 * @uml ImplicationalSystemReaderFactory.png
 * !include src/lattice/ImplicationalSystemReaderFactory.iuml
 * !include src/lattice/ImplicationalSystemReader.iuml
 *
 * hide members
 * show ImplicationalSystemReaderFactory members
 * class ImplicationalSystemReaderFactory #LightCyan
 * title ImplicationalSystemReaderFactory UML graph
 */
public final class ImplicationalSystemReaderFactory {
    /**
     * This class is not designed to be instantiated.
     */
    private ImplicationalSystemReaderFactory() {
    }

    /**
     * Map of extension/reader.
     */
    private static HashMap<String, ImplicationalSystemReader> readers = new HashMap<String, ImplicationalSystemReader>();

    /**
     * Register a reader with an extension.
     *
     * @param   reader     The reader to register
     * @param   extension  The extension linked to the reader
     *
     * @return  The old reader or null
     */
    public static ImplicationalSystemReader register(ImplicationalSystemReader reader, String extension) {
        ImplicationalSystemReader old = readers.get(extension);
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
    public static ImplicationalSystemReader unregister(String extension) {
        ImplicationalSystemReader old = readers.get(extension);
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
    public static ImplicationalSystemReader get(String extension) {
        return readers.get(extension);
    }
}


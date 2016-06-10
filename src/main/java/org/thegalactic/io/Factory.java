package org.thegalactic.io;

/*
 * Factory.java
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
 * This class defines a standard way for getting reader and writer.
 *
 * @param  <E>  Element to be saved and parsed
 *
 * ![Factory](Factory.png)
 *
 * @uml Factory.png
 * !include resources/org/thegalactic/io/Factory.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show Factory members
 * class Factory #LightCyan
 * title Factory UML graph
 */
public abstract class Factory<E> {
    /**
     * Map of extension/reader.
     */
    private HashMap<String, Reader<E>> readers = new HashMap<String, Reader<E>>();

    /**
     * Map of extension/writer.
     */
    private HashMap<String, Writer<E>> writers = new HashMap<String, Writer<E>>();

    /**
     * Register a reader with an extension.
     *
     * @param   reader     The reader to register
     * @param   extension  The extension linked to the reader
     *
     * @return  The old reader or null
     */
    public Reader<E> registerReader(Reader<E> reader, String extension) {
        Reader<E> old = readers.get(extension);
        readers.put(extension, reader);
        return old;
    }

    /**
     * Register a writer with an extension.
     *
     * @param   writer     The writer to register
     * @param   extension  The extension linked to the writer
     *
     * @return  The old reader or null
     */
    public Writer<E> registerWriter(Writer<E> writer, String extension) {
        Writer<E> old = writers.get(extension);
        writers.put(extension, writer);
        return old;
    }

    /**
     * Unregister a reader extension.
     *
     * @param   extension  The extension linked to a reader
     *
     * @return  The old reader or null
     */
    public Reader<E> unregisterReader(String extension) {
        Reader<E> old = readers.get(extension);
        readers.remove(extension);
        return old;
    }

    /**
     * Unregister a writer extension.
     *
     * @param   extension  The extension linked to a writer
     *
     * @return  The old writer or null
     */
    public Writer<E> unregisterWriter(String extension) {
        Writer<E> old = writers.get(extension);
        writers.remove(extension);
        return old;
    }

    /**
     * Get the reader linked to an extension.
     *
     * @param   extension  The extension linked to a reader
     *
     * @return  The reader or null
     */
    public Reader<E> getReader(String extension) {
        return readers.get(extension);
    }

    /**
     * Get the writer linked to an extension.
     *
     * @param   extension  The extension linked to a writer
     *
     * @return  The writer or null
     */
    public Writer<E> getWriter(String extension) {
        return writers.get(extension);
    }
}

package org.thegalactic.io;

/*
 * AbstractIOFactory.java
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
import java.util.Map;

/**
 * This class defines a standard way for getting reader and writer.
 *
 * @param <E> Element to be saved and parsed
 *
 * ![AbstractIOFactory](AbstractIOFactory.png)
 *
 * @uml Factory.png
 * !include resources/org/thegalactic/io/IOFactory.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show IOFactory members
 * class IOFactory #LightCyan
 * title IOFactory UML graph
 */
public abstract class AbstractIOFactory<E> {

    /**
     * Map of extension/reader.
     */
    private final Map<String, Reader<E>> readers = new HashMap<String, Reader<E>>();

    /**
     * Map of extension/writer.
     */
    private final Map<String, Writer<E>> writers = new HashMap<String, Writer<E>>();

    /**
     * Basic constructor.
     */
    protected AbstractIOFactory() {
        super();
    }

    /**
     * Register a reader with an extension.
     *
     * @param reader    The reader to register
     * @param extension The extension linked to the reader
     *
     * @return The old reader or null
     */
    public final Reader<E> registerReader(final Reader<E> reader, final String extension) {
        final Reader<E> old = this.readers.get(extension);
        this.readers.put(extension, reader);
        return old;
    }

    /**
     * Register a writer with an extension.
     *
     * @param writer    The writer to register
     * @param extension The extension linked to the writer
     *
     * @return The old reader or null
     */
    public final Writer<E> registerWriter(final Writer<E> writer, final String extension) {
        final Writer<E> old = this.writers.get(extension);
        this.writers.put(extension, writer);
        return old;
    }

    /**
     * Unregister a reader extension.
     *
     * @param extension The extension linked to a reader
     *
     * @return The old reader or null
     */
    public final Reader<E> unregisterReader(final String extension) {
        final Reader<E> old = this.readers.get(extension);
        this.readers.remove(extension);
        return old;
    }

    /**
     * Unregister a writer extension.
     *
     * @param extension The extension linked to a writer
     *
     * @return The old writer or null
     */
    public final Writer<E> unregisterWriter(final String extension) {
        final Writer<E> old = this.writers.get(extension);
        this.writers.remove(extension);
        return old;
    }

    /**
     * Get the reader linked to an extension.
     *
     * @param extension The extension linked to a reader
     *
     * @return The reader or null
     */
    public final Reader<E> getReader(final String extension) {
        return this.readers.get(extension);
    }

    /**
     * Get the writer linked to an extension.
     *
     * @param extension The extension linked to a writer
     *
     * @return The writer or null
     */
    public final Writer<E> getWriter(final String extension) {
        return this.writers.get(extension);
    }
}

package fr.kbertet.context.io;

/*
 * Factory.java
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
import fr.kbertet.io.Writer;
import fr.kbertet.context.Context;

/**
 * This class register readers and writers for the Context class.
 *
 * ![Factory](Factory.png)
 *
 * @uml Factory.png
 * !include resources/fr/kbertet/context/io/Factory.iuml
 * !include resources/fr/kbertet/io/Reader.iuml
 * !include resources/fr/kbertet/io/Writer.iuml
 *
 * hide members
 * show Factory members
 * class Factory #LightCyan
 * title Factory UML graph
 */
public final class Factory {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private Factory() {
    }

    /**
     * The singleton instance.
     */
    private static Factory instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
            Text.register();
            Burmeister.register();
            FIMI.register();
            Csv.register();
        }
        return instance;
    }

    /**
     * Map of extension/reader.
     */
    private HashMap<String, Reader<Context>> readers = new HashMap<String, Reader<Context>>();

    /**
     * Map of extension/writer.
     */
    private HashMap<String, Writer<Context>> writers = new HashMap<String, Writer<Context>>();

    /**
     * Register a reader with an extension.
     *
     * @param   reader     The reader to register
     * @param   extension  The extension linked to the reader
     *
     * @return  The old reader or null
     */
    public Reader<Context> registerReader(Reader<Context> reader, String extension) {
        Reader<Context> old = readers.get(extension);
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
    public Writer<Context> registerWriter(Writer<Context> writer, String extension) {
        Writer<Context> old = writers.get(extension);
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
    public Reader<Context> unregisterReader(String extension) {
        Reader<Context> old = readers.get(extension);
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
    public Writer<Context> unregisterWriter(String extension) {
        Writer<Context> old = writers.get(extension);
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
    public Reader<Context> getReader(String extension) {
        return readers.get(extension);
    }

    /**
     * Get the writer linked to an extension.
     *
     * @param   extension  The extension linked to a writer
     *
     * @return  The writer or null
     */
    public Writer<Context> getWriter(String extension) {
        return writers.get(extension);
    }
}


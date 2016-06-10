package org.thegalactic.io;

/*
 * Serializer.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

/**
 * This class is used to provide a generic way for serializing objects using the extension file.
 *
 * @param  <E>  The element to be saved/parsed
 */
public final class Serializer<E> {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private Serializer() {
    }

    /**
     * The singleton instance.
     */
    private static Serializer instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static Serializer getInstance() {
        if (instance == null) {
            instance = new Serializer();
        }
        return instance;
    }

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param   e         the element to save
     * @param   factory   the reader/writer factory
     * @param   filename  the name of the file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void save(E e, Factory factory, final String filename) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(filename));
        factory.getWriter(Serializer.getExtension(filename)).write(e, file);
        file.close();
    }

    /**
     * Parse the description of this component from a file whose name is specified.
     *
     * @param   e         the element to parse
     * @param   factory   the reader/writer factory
     * @param   filename  the name of the file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void parse(E e, Factory factory, final String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));
        factory.getReader(Serializer.getExtension(filename)).read(e, file);
        file.close();
    }

    /**
     * Get the filename extension.
     *
     * @param  filename  Filename to get extension from
     *
     * @return  the filename extension
     */
    private static String getExtension(final String filename) {
        String extension = "";
        int index = filename.lastIndexOf('.');
        if (index > 0) {
            extension = filename.substring(index + 1);
        }
        return extension;
    }
}

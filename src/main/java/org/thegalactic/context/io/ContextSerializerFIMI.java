package org.thegalactic.context.io;

/*
 * ContextSerializerFIMI.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import org.thegalactic.context.Context;
import org.thegalactic.io.Reader;
import org.thegalactic.io.Writer;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![ContextSerializerFIMI](ContextSerializerFIMI.png)
 *
 * @uml ContextSerializerFIMI.png
 * !include resources/org/thegalactic/context/io/ContextSerializerFIMI.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show ContextSerializerFIMI members
 * class ContextSerializerFIMI #LightCyan
 * title ContextSerializerFIMI UML graph
 */
public final class ContextSerializerFIMI implements Reader<Context>, Writer<Context> {

    /**
     * String extension.
     */
    private static final String EXTENSION = "dat";

    /**
     * The singleton instance.
     */
    private static final ContextSerializerFIMI INSTANCE = new ContextSerializerFIMI();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ContextSerializerFIMI getInstance() {
        return INSTANCE;
    }

    /**
     * Register this class for reading and writing .dat files.
     */
    public static void register() {
        ContextIOFactory.getInstance().registerReader(ContextSerializerFIMI.getInstance(), EXTENSION);
        ContextIOFactory.getInstance().registerWriter(ContextSerializerFIMI.getInstance(), EXTENSION);
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextSerializerFIMI() {
    }

    /**
     * Read a context from a file.
     *
     * The FIMI dat format file is respected:
     *
     * The file format is structured as follows:
     *
     * Each line corresponds to an observation Each line is made of a list of
     * integers corresponding to attributes separated by a space
     *
     * ~~~
     * 1 3
     * 2 4 5
     * 1 2
     * 3 4 5
     * ~~~
     *
     * For reading convinience, observations are labelled with 'O' + LineNumber.
     *
     * Be careful when using a downloaded file: an empty line at the end of the
     * file gives an observation with no attributes
     *
     * @param context a context to read
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void read(final Context context, final BufferedReader file) throws IOException {
        // Initialize the line number
        int lineNumber = 0;

        // Loop on the file
        while (file.ready()) {
            // Increment the line number
            lineNumber++;

            // Get the next identifier
            final String identifier = "O" + lineNumber;
            context.addToObservations(identifier);

            // Get the current line
            final String str = file.readLine();

            // Tokenize the line
            for (final String token : str.split(" +")) {
                final Integer attribute = Integer.parseInt(token);
                if (!context.containsAttribute(attribute)) {
                    context.addToAttributes(attribute);
                }

                // Add the extent/intent for the current identifier and current attribute
                context.addExtentIntent(identifier, attribute);
            }
        }
        context.setBitSets();
    }

    /**
     * Write a context to a file.
     *
     * The FIMI dat format file is respected :
     *
     * The file format is structured as follows:
     *
     * Each line corresponds to an observation Each line is made of a list of
     * integers corresponding to attributes separated by a space
     *
     * ~~~
     * 1 3
     * 2 4 5
     * 1 2
     * 3 4 5
     * ~~~
     *
     * @param context a context to write
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    @Override
    public void write(final Context context, final BufferedWriter file) throws IOException {
        final HashMap<Comparable, Integer> map = new HashMap();
        Integer count = 0;
        for (final Comparable att : context.getAttributes()) {
            count++;
            map.put(att, count);
        }
        for (final Comparable obs : context.getObservations()) {
            for (Comparable att : context.getIntent(obs)) {
                file.write(map.get(att) + " ");
            }
            file.write(System.getProperty("line.separator"));
        }
    }
}

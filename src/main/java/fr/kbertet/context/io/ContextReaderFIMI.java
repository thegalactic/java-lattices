package fr.kbertet.context.io;

/*
 * ContextReaderFIMI.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

import fr.kbertet.io.Reader;
import fr.kbertet.context.Context;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![ContextReaderFIMI](ContextReaderFIMI.png)
 *
 * @uml ContextReaderFIMI.png
 * !include resources/fr/kbertet/context/io/ContextReaderFIMI.iuml
 * !include resources/fr/kbertet/io/Reader.iuml
 *
 * hide members
 * show ContextReaderFIMI members
 * class ContextReaderFIMI #LightCyan
 * title ContextReaderFIMI UML graph
 */
public final class ContextReaderFIMI implements Reader<Context> {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextReaderFIMI() {
    }

    /**
     * The singleton instance.
     */
    private static ContextReaderFIMI instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextReaderFIMI getInstance() {
        if (instance == null) {
            instance = new ContextReaderFIMI();
        }
        return instance;
    }

    /**
     * Register this class for reading .dat files.
     */
    public static void register() {
        ContextReaderFactory.register(ContextReaderFIMI.getInstance(), "dat");
    }

    /**
     * Read a context from a file.
     *
     * The FIMI dat format file is respected :
     *
     * The file format is structured as follows:
     *
     * Each line corresponds to an observation
     * Each line is made of a list of integers corresponding to attributes separated by a space
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
     * Be careful when using a downloaded file: an empty line at the end of the file gives an observation with no attributes
     *
     * @param   context  a context to read
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void read(Context context, BufferedReader file) throws IOException {
        // Initialize the line number
        int lineNumber = 0;

        // Loop on the file
        while (file.ready()) {
            // Increment the line number
            lineNumber++;

            // Get the next identifier
            String identifier = "O" + lineNumber;
            context.addToObservations(identifier);

            // Get the current line
            String str = file.readLine();

            // Tokenize the line
            StringTokenizer tok = new StringTokenizer(str);
            while (tok.hasMoreTokens()) {
                // Get the next attribute
                Integer attribute = Integer.parseInt(tok.nextToken());
                if (!context.containsAttribute(attribute)) {
                    context.addToAttributes(attribute);
                }

                // Add the extent/intent for the current identifier and current attribute
                context.addExtentIntent(identifier, attribute);
            }
        }
        context.setBitSets();
    }
}

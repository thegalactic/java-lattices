package org.thegalactic.context.io;

/*
 * FIMI.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */

import java.util.StringTokenizer;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.thegalactic.io.Reader;
import org.thegalactic.io.Writer;
import org.thegalactic.context.TemporaryContext;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![FIMI](FIMI.png)
 *
 * @uml FIMI.png
 * !include resources/org/thegalactic/context/io/FIMI.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show FIMI members
 * class FIMI #LightCyan
 * title FIMI UML graph
 */
public final class FIMI implements Reader<TemporaryContext>, Writer<TemporaryContext> {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private FIMI() {
    }

    /**
     * The singleton instance.
     */
    private static FIMI instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static FIMI getInstance() {
        if (instance == null) {
            instance = new FIMI();
        }
        return instance;
    }

    /**
     * Register this class for reading .dat files.
     */
    public static void register() {
        IOFactory.getInstance().registerReader(FIMI.getInstance(), "dat");
        IOFactory.getInstance().registerWriter(FIMI.getInstance(), "dat");
    }

    /**
     * Read a context from a file.
     *
     * The FIMI dat format file is respected:
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
    public void read(TemporaryContext context, BufferedReader file) throws IOException {
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

    /**
     * Write a context to a file.
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
     * @param   context  a context to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    @Override
    public void write(TemporaryContext context, BufferedWriter file) throws IOException {
        HashMap<Comparable, Integer> map = new HashMap();
        Integer count = 0;
        for (Comparable att : context.getAttributes()) {
            count++;
            map.put(att, count);
        }
        for (Comparable obs : context.getObservations()) {
            for (Comparable att : context.getIntent(obs)) {
                file.write(map.get(att) + " ");
            }
            file.write("\n");
        }
    }
}

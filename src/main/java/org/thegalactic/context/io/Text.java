package org.thegalactic.context.io;

/*
 * Text.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of CeCILL-B license.
 */

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.thegalactic.io.Reader;
import org.thegalactic.io.Writer;
import org.thegalactic.context.Context;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![Text](Text.png)
 *
 * @uml Text.png
 * !include resources/org/thegalactic/context/io/Text.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 * hide members
 * show Text members
 * class Text #LightCyan
 * title Text UML graph
 */
public final class Text implements Reader<Context>, Writer<Context> {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private Text() {
    }

    /**
     * The singleton instance.
     */
    private static Text instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static Text getInstance() {
        if (instance == null) {
            instance = new Text();
        }
        return instance;
    }

    /**
     * Register this class for reading .txt files.
     */
    public static void register() {
        Factory.getInstance().registerReader(Text.getInstance(), "txt");
        Factory.getInstance().registerWriter(Text.getInstance(), "txt");
    }

    /**
     * Read a context from a file.
     *
     * The following format is respected:
     *
     * The list of observations separated by a space on the first line ;
     * the list of attrbutes separated by a space on the second line ;
     * then, for each observations o, the list of its intent on a line, written like o a1 a2 ...
     *
     * ~~~
     * Observations: 1 2 3
     * Attributes: a b c d e
     * 1: a c
     * 2: a b
     * 3: b d e
     * 4: c e
     * ~~~
     *
     * @param   context  a context to read
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void read(Context context, BufferedReader file) throws IOException {
        /*
         * First line : All observations separated by a space.
         * A StringTokenizer is used to divide the line into different observations considering spaces as separator.
         */
        StringTokenizer tokenizer =  new StringTokenizer(file.readLine());

        // First token corresponds to the string "Observations:"
        if (!tokenizer.nextToken().equals("Observations:")) {
            throw new IOException("Invalid declaration of observations");
        }

        // Add all the observations
        while (tokenizer.hasMoreTokens()) {
            if (!context.addToObservations(new String(tokenizer.nextToken()))) {
                throw new IOException("Duplicated observation");
            }
        }

        /*
         * Second line : All attributes separated by a space
         * A StringTokenizer is used to divide the line into different token considering spaces as separator.
         */
        tokenizer =  new StringTokenizer(file.readLine());

        // first token corresponds to the string "Attributes:"
        if (!tokenizer.nextToken().equals("Attributes:")) {
            throw new IOException("Invalid declaration of attributes");
        }

        // Add all the attributes
        while (tokenizer.hasMoreTokens()) {
            if (!context.addToAttributes(new String(tokenizer.nextToken()))) {
                throw new IOException("Duplicated attribute");
            }
        }

        /*
         * Next lines : All intents of observations, one on each line:
         * observation: list of attributes
         * a StringTokenizer is used to divide each intent.
         */
        String line = file.readLine();
        while (line != null && !line.isEmpty()) {
            tokenizer = new StringTokenizer(line);

            // Get the observation identifier
            String identifier = tokenizer.nextToken();

            // Detect invalid format
            if (!identifier.substring(identifier.length() - 1).equals(":")) {
                throw new IOException("Observation name must be followed by a semi-colon");
            }
            identifier = identifier.substring(0, identifier.length() - 1);

            // Verify existence of this observation
            if (!context.containsObservation(identifier)) {
                throw new IOException("Unexisting observation");
            }

            // Add intents for this observation
            while (tokenizer.hasMoreTokens()) {
                String attribute = tokenizer.nextToken();

                // Detect invalid format
                if (!context.containsAttribute(attribute)) {
                    throw new IOException("Unexisting attribute");
                }

                // Add the extent/intent for the current identifier and current attribute
                context.addExtentIntent(identifier, attribute);
            }
            line = file.readLine();
        }
        context.setBitSets();
    }

    /**
     * Write a context to a file.
     *
     * The following format is respected:
     *
     * The list of observations separated by a space on the first line ;
     * the list of attrbutes separated by a space on the second line ;
     * then, for each observations o, the list of its intent on a line, written like o a1 a2 ...
     *
     * ~~~
     * Observations: 1 2 3
     * Attributes: a b c d e
     * 1: a c
     * 2: a b
     * 3: b d e
     * 4: c e
     * ~~~
     *
     * @param   context  a context to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void write(Context context, BufferedWriter file) throws IOException {
        file.write(context.toString());
    }
}


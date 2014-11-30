package fr.kbertet.context.io;

/*
 * ContextWriterText.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.IOException;

import fr.kbertet.context.Context;

/**
 * This class defines the way for writing a context as a text file.
 *
 * ![ContextWriterBurmeister](ContextWriterBurmeister.png)
 *
 * @uml ContextWriterBurmeister.png
 * !include resources/fr/kbertet/context/io/ContextWriterBurmeister.iuml
 * !include resources/fr/kbertet/context/io/ContextWriter.iuml
 *
 * hide members
 * show ContextWriterBurmeister members
 * class ContextWriterBurmeister #LightCyan
 * title ContextWriterBurmeister UML graph
 */
public final class ContextWriterBurmeister implements ContextWriter {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextWriterBurmeister() {
    }

    /**
     * The singleton instance.
     */
    private static ContextWriterBurmeister instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextWriterBurmeister getInstance() {
        if (instance == null) {
            instance = new ContextWriterBurmeister();
        }
        return instance;
    }

    /**
     * Register this class for writing .cxt files.
     */
    public static void register() {
        ContextWriterFactory.register(ContextWriterBurmeister.getInstance(), "cxt");
    }

    /**
     * Write a context to a file.
     *
     * The Burmeister cxt format file is respected :
     *
     * The file format is structured as follows:
     *
     * The first line consists of a single "B"
     * The second line contains the name of the context (note that this is ignored)
     * The third and fourth line consist of the object and attribute count, respectively.
     * The fifth line is empty.
     * After that, all objects and all attributes are listed, each on a separate line
     * finally, the context is given as a combination of . and X, each row on a separate line.
     *
     * ~~~
     * B
     * Example
     * 2
     * 2
     *
     * a
     * b
     * 1
     * 2
     * .X
     * XX
     * ~~~
     *
     * @param   context  a context to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    @Override
    public void write(Context context, BufferedWriter file) throws IOException {
        // Magic header
        file.write("B");
        file.newLine();

        // Empty name
        file.newLine();

        TreeSet<Comparable> attributes = context.getAttributes();
        TreeSet<Comparable> observations = context.getObservations();

        // Observation and attributes size
        file.write(observations.size() + "");
        file.newLine();
        file.write(attributes.size() + "");
        file.newLine();

        // Observations
        for (Comparable observation : observations) {
            file.write(observation.toString());
            file.newLine();
        }

        // Attributes
        for (Comparable attribute : attributes) {
            file.write(attribute.toString());
            file.newLine();
        }

        // Extent/Intent
        for (Comparable observation : observations) {
            String line = "";
            for (Comparable attribute : attributes) {
                if (context.getIntent(observation).contains(attribute)) {
                    line = line + "X";
                } else {
                    line = line + ".";
                }
            }
            file.write(line);
            file.newLine();
        }
    }
}

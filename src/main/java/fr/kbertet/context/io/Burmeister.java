package fr.kbertet.context.io;

/*
 * Burmeister.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import fr.kbertet.io.Reader;
import fr.kbertet.io.Writer;
import fr.kbertet.context.TemporaryContext;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![Burmeister](Burmeister.png)
 *
 * @uml Burmeister.png
 * !include resources/fr/kbertet/context/io/Burmeister.iuml
 * !include resources/fr/kbertet/io/Reader.iuml
 * !include resources/fr/kbertet/io/Writer.iuml
 *
 * hide members
 * show Burmeister members
 * class Burmeister #LightCyan
 * title Burmeister UML graph
 */
public final class Burmeister implements Reader<TemporaryContext>, Writer<TemporaryContext> {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private Burmeister() {
    }

    /**
     * The singleton instance.
     */
    private static Burmeister instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static Burmeister getInstance() {
        if (instance == null) {
            instance = new Burmeister();
        }
        return instance;
    }

    /**
     * Register this class for reading .cxt files.
     */
    public static void register() {
        IOFactory.getInstance().registerReader(Burmeister.getInstance(), "cxt");
        IOFactory.getInstance().registerWriter(Burmeister.getInstance(), "cxt");
    }

    /**
     * Read a context from a file.
     *
     * The Burmeister cxt format file is respected :
     *
     * The file format is structured as follows:
     *
     * The first line consists of a single "B"
     * The second line contains the name of the context (note that this is ignored)
     * The third and fourth line consist of the object and attribute count, respectively
     * after that, all objects and all attributes are listed, each on a separate line
     * finally, the context is given as a combination of . and X, each row on a separate line.
     *
     * ~~~
     * B
     * Example
     * 2
     * 2
     * a
     * b
     * 1
     * 2
     * .X
     * XX
     * ~~~
     *
     * @param   context  a context to read
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void read(TemporaryContext context, BufferedReader file) throws IOException {
        // str corresponds to the string "B". First line (Unused).
        String str = file.readLine();

        // Detect Burmeister magic header
        if (!str.equals("B")) {
            throw new IOException("Burmeister magic header not found");
        }

        // str corresponds to the string "Name". Second line (Unused).
        str = file.readLine();

        // number of observations. Third line.
        Integer nbObs = Integer.parseInt(file.readLine());

        // number of attributes. Fourth line.
        Integer nbAtt = Integer.parseInt(file.readLine());

        // Now reading observations

        // Observations names must be recorded for the reading context phase
        String[] obsNames = new String[nbObs];
        for (int i = 0; i < nbObs; i++) {
            str = file.readLine();
            context.addToObservations(str);
            obsNames[i] = str;
        }

        // Now reading attributes

        // Attributes names must be recorded for the reading context phase
        String[] attNames = new String[nbAtt];
        for (int i = 0; i < nbAtt; i++) {
            str = file.readLine();
            context.addToAttributes(str);
            attNames[i] = str;
        }

        // Now reading context
        for (int i = 0; i < nbObs; i++) {
            str = file.readLine();
            for (int j = 0; j < nbAtt; j++) {
                if (str.charAt(j) == 'X') {
                    context.addExtentIntent(obsNames[i], attNames[j]);
                }
            }
        }
        context.setBitSets();
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
    public void write(TemporaryContext context, BufferedWriter file) throws IOException {
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

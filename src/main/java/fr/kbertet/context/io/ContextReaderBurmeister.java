package fr.kbertet.context.io;

/*
 * ContextReaderBurmeister.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.BufferedReader;
import java.io.IOException;

import fr.kbertet.context.Context;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![ContextReaderBurmeister](ContextReaderBurmeister.png)
 *
 * @uml ContextReaderBurmeister.png
 * !include resources/fr/kbertet/context/io/ContextReaderBurmeister.iuml
 * !include resources/fr/kbertet/context/io/ContextReader.iuml
 *
 * hide members
 * show ContextReaderBurmeister members
 * class ContextReaderBurmeister #LightCyan
 * title ContextReaderBurmeister UML graph
 */
public final class ContextReaderBurmeister implements ContextReader {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextReaderBurmeister() {
    }

    /**
     * The singleton instance.
     */
    private static ContextReaderBurmeister instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextReaderBurmeister getInstance() {
        if (instance == null) {
            instance = new ContextReaderBurmeister();
        }
        return instance;
    }

    /**
     * Register this class for reading .ctx files.
     */
    public static void register() {
        ContextReaderFactory.register(ContextReaderBurmeister.getInstance(), "cxt");
    }

    /**
     * Read a context from a file.
     *
     * The Burmeister ctx format file is respected :
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
    public void read(Context context, BufferedReader file) throws IOException {
        String str = file.readLine(); // str corresponds to the string "B". First line (Unused).
        str = file.readLine(); // str corresponds to the string "Name". Second line (Unused).
        Integer nbObs = Integer.parseInt(file.readLine()); // number of observations. Third line.
        Integer nbAtt = Integer.parseInt(file.readLine()); // number of attributes. Fourth line.
        str = file.readLine(); // str corresponds to an empty line
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
}

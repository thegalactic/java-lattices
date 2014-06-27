package fr.kbertet.lattice.io;

/*
 * ContextReaderText.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.lattice.Context;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![ContextReaderText](ContextReaderText.png)
 *
 * @uml ContextReaderText.png
 * !include src/fr/kbertet/lattice/io/ContextReaderText.iuml
 * !include src/fr/kbertet/lattice/io/ContextReader.iuml
 *
 * hide members
 * show ContextReaderText members
 * class ContextReaderText #LightCyan
 * title ContextReaderText UML graph
 */
public final class ContextReaderText implements ContextReader {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextReaderText() {
    }

    /**
     * The singleton instance.
     */
    private static ContextReaderText instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextReaderText getInstance() {
        if (instance == null) {
            instance = new ContextReaderText();
        }
        return instance;
    }

    /**
     * Register this class for reading .txt files.
     */
    public static void register() {
        ContextReaderFactory.register(ContextReaderText.getInstance(), "txt");
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
     * 1 a c
     * 2 a b
     * 3 b d e
     * 4 c e
     * ~~~
     *
     * @param   context  a context to read
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void read(Context context, BufferedReader file) throws IOException {
        // first line : All observations separated by a space
        // a StringTokenizer is used to divide the line into different observations
        // considering spaces as separator.
        StringTokenizer st =  new StringTokenizer(file.readLine());
        st.nextToken(); // first token corresponds to the string "Observations:"
        while (st.hasMoreTokens()) {
            String n = new String(st.nextToken());
            context.addToObservations(n);
        }
        // second line : All attributes separated by a space
        // a StringTokenizer is used to divide the line into different token,
        // considering spaces as separator.
        st =  new StringTokenizer(file.readLine());
        st.nextToken(); // first token corresponds to the string "Attributes:"
        while (st.hasMoreTokens()) {
            String n = new String(st.nextToken());
            context.addToAttributes(n);
        }
        // next lines : All intents of observations, one on each line:
        // observation : list of attributes
        // a StringTokenizer is used to divide each intent.
        String line = file.readLine();
        while (line != null && !line.isEmpty()) {
            st = new StringTokenizer(line);
            String word = st.nextToken();
            Comparable o = null;
            // search of o in observations
            for (Comparable e : context.getObservations()) {
                if (e.equals(word)) {
                    o = e;
                }
            }
            if (o != null) {
                word = st.nextToken(); // this token corresponds to the sting ":"
                while (st.hasMoreTokens()) {
                    word = st.nextToken();
                    Comparable a = null;
                    // search of a in attributes
                    for (Comparable e : context.getAttributes()) {
                        if (e.equals(word)) {
                            a = e;
                        }
                    }
                    if (a != null) {
                        context.addExtentIntent(o, a);
                    }
                }
            }
            line = file.readLine();
        }
        context.setBitSets();
    }
}


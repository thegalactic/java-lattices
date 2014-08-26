package fr.kbertet.lattice.io;

/*
 * ContextReaderFIMI.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.lattice.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![ContextReaderFIMI](ContextReaderFIMI.png)
 *
 * @uml ContextReaderFIMI.png
 * !include src/fr/kbertet/lattice/io/ContextReaderFIMI.iuml
 * !include src/fr/kbertet/lattice/io/ContextReader.iuml
 *
 * hide members
 * show ContextReaderFIMI members
 * class ContextReaderFIMI #LightCyan
 * title ContextReaderFIMI UML graph
 */
public final class ContextReaderFIMI implements ContextReader {
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
     * Be careful when using a downloaded file : an empty line at the end of the file gives an observation with no attributes
     *
     * @param   context  a context to read
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void read(Context context, BufferedReader file) throws IOException {
        Integer lineNumber = 0;
        while (file.ready()) {
            lineNumber++;
            String obs = "O" + lineNumber.toString();
            context.addToObservations(obs);
            String str = file.readLine();
            StringTokenizer tok = new StringTokenizer(str);
            while (tok.hasMoreTokens()) {
                Integer attr = Integer.parseInt(tok.nextToken());
                if (!context.containsAttribute(attr)) {
                    context.addToAttributes(attr);
                }
                context.addExtentIntent(obs, attr);
            }
        }
        context.setBitSets();
    }
}

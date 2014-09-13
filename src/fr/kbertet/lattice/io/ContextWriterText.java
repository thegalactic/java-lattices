package fr.kbertet.lattice.io;

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

import fr.kbertet.lattice.Context;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class defines the way for writing a context as a text file.
 *
 * ![ContextWriterText](ContextWriterText.png)
 *
 * @uml ContextWriterText.png
 * !include src/fr/kbertet/lattice/io/ContextWriterText.iuml
 * !include src/fr/kbertet/lattice/io/ContextWriter.iuml
 *
 * hide members
 * show ContextWriterText members
 * class ContextWriterText #LightCyan
 * title ContextWriterText UML graph
 */
public final class ContextWriterText implements ContextWriter {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextWriterText() {
    }

    /**
     * The singleton instance.
     */
    private static ContextWriterText instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextWriterText getInstance() {
        if (instance == null) {
            instance = new ContextWriterText();
        }
        return instance;
    }

    /**
     * Register this class for writing .txt files.
     */
    public static void register() {
        ContextWriterFactory.register(ContextWriterText.getInstance(), "txt");
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
     * 1 a c
     * 2 a b
     * 3 b d e
     * 4 c e
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


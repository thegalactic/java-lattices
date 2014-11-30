package fr.kbertet.context.io;

/*
 * ContextWriterFIMI.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.IOException;

import fr.kbertet.io.Writer;
import fr.kbertet.context.Context;

/**
 * This class defines the way for writing a context as a FIMI dat file.
 *
 * ![ContextWriterFIMI](ContextWriterFIMI.png)
 *
 * @uml ContextWriterFIMI.png
 * !include resources/fr/kbertet/context/io/ContextWriterFIMI.iuml
 * !include resources/fr/kbertet/io/Writer.iuml
 *
 * hide members
 * show ContextWriterFIMI members
 * class ContextWriterFIMI #LightCyan
 * title ContextWriterFIMI UML graph
 */
public final class ContextWriterFIMI implements Writer<Context> {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextWriterFIMI() {
    }

    /**
     * The singleton instance.
     */
    private static ContextWriterFIMI instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextWriterFIMI getInstance() {
        if (instance == null) {
            instance = new ContextWriterFIMI();
        }
        return instance;
    }

    /**
     * Register this class for writing .cxt files.
     */
    public static void register() {
        ContextWriterFactory.register(ContextWriterFIMI.getInstance(), "dat");
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
    public void write(Context context, BufferedWriter file) throws IOException {
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

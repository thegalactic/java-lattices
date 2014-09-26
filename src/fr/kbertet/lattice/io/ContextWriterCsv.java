package fr.kbertet.lattice.io;

/*
 * ContextWriterCsv.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.lattice.Context;
import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * This class defines the way for writing a context as a text file.
 *
 * ![ContextWriterCsv](ContextWriterCsv.png)
 *
 * @uml ContextWriterCsv.png
 * !include src/fr/kbertet/lattice/io/ContextWriterCsv.iuml
 * !include src/fr/kbertet/lattice/io/ContextWriter.iuml
 *
 * hide members
 * show ContextWriterCsv members
 * class ContextWriterCsv #LightCyan
 * title ContextWriterCsv UML graph
 */
public final class ContextWriterCsv implements ContextWriter {
    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextWriterCsv() {
    }

    /**
     * The singleton instance.
     */
    private static ContextWriterCsv instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static ContextWriterCsv getInstance() {
        if (instance == null) {
            instance = new ContextWriterCsv();
        }
        return instance;
    }

    /**
     * Register this class for writing .csv files.
     */
    public static void register() {
        ContextWriterFactory.register(ContextWriterCsv.getInstance(), "csv");
    }

    /**
     * Write a context to a csv file.
     *
     * The following format is respected:
     *
     * The first line contains the attribute names, the other lines contains the observations identifier followed by boolean values
     *
     * ~~~
     * "",a,b,c,d,e
     * 1,1,0,1,0,0
     * 2,1,1,0,0,0
     * 3,0,1,0,1,1
     * 4,0,0,1,0,1
     * ~~~
     *
     * @param   context  a context to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void write(Context context, BufferedWriter file) throws IOException {
        CSVPrinter printer = new CSVPrinter(file, CSVFormat.RFC4180);
        TreeSet<Comparable> attr = context.getAttributes();
        TreeSet<Comparable> obs = context.getObservations();

        printer.print("");

        for (Comparable a : attr) {
            printer.print(a);
        }

        printer.println();

        for (Comparable o : obs) {
            printer.print(o);

            for (Comparable a : attr) {
                if (context.getIntent(o).contains(a)) {
                    printer.print(1);
                } else {
                    printer.print(0);
                }
            }

            printer.println();
        }

        printer.close();
    }
}


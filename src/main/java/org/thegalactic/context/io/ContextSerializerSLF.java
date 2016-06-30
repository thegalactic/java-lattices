package org.thegalactic.context.io;

/*
 * ContextSerializerSLF.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of CeCILL-B license.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.thegalactic.context.Context;
import org.thegalactic.io.Reader;
import org.thegalactic.io.Writer;

/**
 * This class defines the way for reading a context from a slf file.
 *
 */
public final class ContextSerializerSLF implements Reader<Context>, Writer<Context> {

    /**
     * String extension.
     */
    private static final String EXTENSION = "slf";

    /**
     * Header.
     */
    private static final String HEADER = "[Lattice]";

    /**
     * Misformed exception.
     */
    private static final String MISFORMED = "Misformated SLF file.";

    /**
     * The singleton INSTANCE.
     */
    private static final ContextSerializerSLF INSTANCE = new ContextSerializerSLF();

    /**
     * Return the singleton INSTANCE of this class.
     *
     * @return the singleton INSTANCE
     */
    public static ContextSerializerSLF getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Register this class for reading and writing .slf files.
     */
    public static void register() {
        ContextIOFactory.getInstance().registerReader(ContextSerializerSLF.getINSTANCE(), EXTENSION);
        ContextIOFactory.getInstance().registerWriter(ContextSerializerSLF.getINSTANCE(), EXTENSION);
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextSerializerSLF() {
    }

    /**
     * Read a context from a file.
     *
     * The following format is respected:
     *
     * The number of objects and observation on two separated lines The list of
     * objects on seprated lines The list of attributes on separated lines
     *
     * then, for each observations, the list of its intent on a line, written
     * like 0 1 0
     *
     * ~~~
     * [Lattice]
     * 2
     * 3
     * [Objects]
     * 1 2
     * [Attributes]
     * a b c
     * [relation]
     * 0 1 0
     * 1 1 0
     * ~~~
     *
     * @param context a context to read
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    public void read(final Context context, final BufferedReader file) throws IOException {

        if (!file.readLine().equals(HEADER)) {
            throw new IOException(MISFORMED);
        }

        final int nbObs = Integer.parseInt(file.readLine());
        final int nbAtt = Integer.parseInt(file.readLine());
        final List<Comparable> obs = new ArrayList(nbObs);
        final List<Comparable> att = new ArrayList(nbAtt);

        if (!file.readLine().equals("[Objects]")) {
            throw new IOException(MISFORMED);
        }

        final TreeMap<Comparable, TreeSet<Comparable>> intent = new TreeMap();
        final TreeMap<Comparable, TreeSet<Comparable>> extent = new TreeMap();

        String line = file.readLine();
        while (!"[Attributes]".equals(line)) {
            obs.add(line);
            intent.put(line, new TreeSet());
            line = file.readLine();
        }
        line = file.readLine();
        while (!"[relation]".equals(line)) {
            att.add(line);
            extent.put(line, new TreeSet());
            line = file.readLine();
        }

        context.addAllToAttributes(new TreeSet(att));
        context.addAllToObservations(new TreeSet(obs));

        for (int i = 0; i < nbObs; i++) {
            line = file.readLine();
            StringTokenizer st = new StringTokenizer(line);
            int cpt = 0;
            while (st.hasMoreTokens()) {
                String n = st.nextToken();
                if ("1".equals(n)) {
                    context.addExtentIntent(obs.get(i), att.get(cpt));
                }
                cpt++;
            }
            if (cpt != nbAtt) {
                throw new IOException(MISFORMED);
            }
        }
        context.setBitSets();
    }

    /**
     * Write a context to a file.
     *
     * The Standard Lattice Format SLF format file is respected :
     *
     * The following format is respected:
     *
     * The number of objects and observation on two separated lines The list of
     * objects on seprated lines The list of attributes on separated lines
     *
     * then, for each observations, the list of its intent on a line, written
     * like 0 1 0
     *
     * ~~~
     * [Lattice]
     * 2
     * 3
     * [Objects]
     * 1
     * 2
     * [Attributes]
     * a
     * b
     * c
     * [relation]
     * 0 1 0
     * 1 1 0
     * ~~~
     *
     * @param context a context to write
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(Context context, BufferedWriter file) throws IOException {
        List<Comparable> obs = new ArrayList(context.getObservations());
        List<Comparable> att = new ArrayList(context.getAttributes());
        file.write(HEADER);
        file.newLine();
        file.write(String.valueOf(context.getObservations().size()));
        file.newLine();
        file.write(String.valueOf(context.getAttributes().size()));
        file.newLine();
        file.write("[Objects]");
        file.newLine();
        for (Comparable o : obs) {
            file.write((String) o);
            file.newLine();
        }
        file.write("[Attributes]");
        file.newLine();
        for (Comparable a : att) {
            file.write((String) a);
            file.newLine();
        }
        file.write("[relation]");
        file.newLine();
        for (Comparable o : obs) {
            TreeSet<Comparable> intent = context.getIntent(o);
            for (Comparable a : att) {
                if (intent.contains(a)) {
                    file.write("1 ");
                } else {
                    file.write("0 ");
                }
            }
            file.newLine();
        }
    }
}

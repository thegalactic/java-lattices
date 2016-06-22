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
     * The singleton instance.
     */
    private static ContextSerializerSLF instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ContextSerializerSLF getInstance() {
        if (instance == null) {
            instance = new ContextSerializerSLF();
        }
        return instance;
    }

    /**
     * Register this class for reading  and writing .slf files.
     */
    public static void register() {
        ContextIOFactory.getInstance().registerReader(ContextSerializerSLF.getInstance(), "slf");
        ContextIOFactory.getInstance().registerWriter(ContextSerializerSLF.getInstance(), "slf");
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
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void read(Context context, BufferedReader file) throws IOException {

        ArrayList<Comparable> obs = new ArrayList();
        ArrayList<Comparable> att = new ArrayList();

        TreeMap<Comparable, TreeSet<Comparable>> intent = new TreeMap();
        TreeMap<Comparable, TreeSet<Comparable>> extent = new TreeMap();

        if (!file.readLine().equals("[Lattice]")) {
            throw new IOException("Misformated SLF file.");
        }
        int nbObs = Integer.parseInt(file.readLine());
        int nbAtt = Integer.parseInt(file.readLine());

        if (!file.readLine().equals("[Objects]")) {
            throw new IOException("Misformated SLF file.");
        }
        String line = file.readLine();
        while (!line.equals("[Attributes]")) {
            obs.add(line);
            intent.put(line, new TreeSet());
            line = file.readLine();
        }
        line = file.readLine();
        while (!line.equals("[relation]")) {
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
                if (n.equals("1")) {
                    context.addExtentIntent(obs.get(i), att.get(cpt));
                }
                cpt++;
            }
            if (cpt != nbAtt) {
                throw new IOException("Misformated SLF file.");
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
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(Context context, BufferedWriter file) throws IOException {
        ArrayList<Comparable> obs = new ArrayList(context.getObservations());
        ArrayList<Comparable> att = new ArrayList(context.getAttributes());
        file.write("[Lattice]");
        file.newLine();
        file.write("" + context.getObservations().size());
        file.newLine();
        file.write("" + context.getAttributes().size());
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

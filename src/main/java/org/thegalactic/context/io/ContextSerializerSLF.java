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
import java.util.SortedSet;
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
     * Objects.
     */
    private static final String OBJECTS = "[Objects]";

    /**
     * Attributes.
     */
    private static final String ATTRIBUTES = "[Attributes]";

    /**
     * relation.
     */
    private static final String RELATION = "[relation]";

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

        final int countObservations = Integer.parseInt(file.readLine());
        final int countAttributes = Integer.parseInt(file.readLine());

        if (!file.readLine().equals(OBJECTS)) {
            throw new IOException(MISFORMED);
        }

        final List<Comparable> observations = new ArrayList(countObservations);
        final List<Comparable> attributes = new ArrayList(countAttributes);
        final TreeMap<Comparable, TreeSet<Comparable>> intent = new TreeMap();
        final TreeMap<Comparable, TreeSet<Comparable>> extent = new TreeMap();

        String line = file.readLine();
        while (!ATTRIBUTES.equals(line)) {
            observations.add(line);
            intent.put(line, new TreeSet());
            line = file.readLine();
        }
        line = file.readLine();
        while (!RELATION.equals(line)) {
            attributes.add(line);
            extent.put(line, new TreeSet());
            line = file.readLine();
        }

        context.addAllToAttributes(new TreeSet(attributes));
        context.addAllToObservations(new TreeSet(observations));

        for (int i = 0; i < countObservations; i++) {
            line = file.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);
            int count = 0;
            while (tokenizer.hasMoreTokens()) {
                String n = tokenizer.nextToken();
                if ("1".equals(n)) {
                    context.addExtentIntent(observations.get(i), attributes.get(count));
                }
                count++;
            }
            if (count != countAttributes) {
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
    public void write(final Context context, final BufferedWriter file) throws IOException {
        file.write(HEADER);
        file.newLine();
        file.write(String.valueOf(context.getObservations().size()));
        file.newLine();
        file.write(String.valueOf(context.getAttributes().size()));
        file.newLine();
        file.write(OBJECTS);
        file.newLine();
        for (final Comparable observation : context.getObservations()) {
            file.write(observation.toString());
            file.newLine();
        }
        file.write(ATTRIBUTES);
        file.newLine();
        for (final Comparable attribute : context.getAttributes()) {
            file.write(attribute.toString());
            file.newLine();
        }
        file.write(RELATION);
        file.newLine();
        for (final Comparable observation : context.getObservations()) {
            final SortedSet<Comparable> intent = context.getIntent(observation);
            for (final Comparable attribute : context.getAttributes()) {
                if (intent.contains(attribute)) {
                    file.write("1 ");
                } else {
                    file.write("0 ");
                }
            }
            file.newLine();
        }
    }
}

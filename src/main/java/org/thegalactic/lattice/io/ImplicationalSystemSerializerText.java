package org.thegalactic.lattice.io;

/*
 * ImplicationalSystemSerializerText.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.thegalactic.io.Reader;
import org.thegalactic.io.Writer;
import org.thegalactic.lattice.ImplicationalSystem;
import org.thegalactic.lattice.Rule;

/**
 * This class defines the way for reading an implicational system from a text
 * file.
 *
 * ![ImplicationalSystemSerializerText](ImplicationalSystemSerializerText.png)
 *
 * @uml
 *
 * ImplicationalSystemSerializerText.png
 *
 * !include resources/org/thegalactic/lattice/io/ImplicationalSystemSerializerText.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show ImplicationalSystemSerializerText members
 * class ImplicationalSystemSerializerText #LightCyan
 * title ImplicationalSystemSerializerText UML graph
 */
public final class ImplicationalSystemSerializerText implements Reader<ImplicationalSystem>, Writer<ImplicationalSystem> {

    /**
     * The singleton instance.
     */
    private static ImplicationalSystemSerializerText instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ImplicationalSystemSerializerText getInstance() {
        if (instance == null) {
            instance = new ImplicationalSystemSerializerText();
        }
        return instance;
    }

    /**
     * Register this class for reading and writing .txt files.
     */
    public static void register() {
        ImplicationalSystemIOFactory.getInstance().registerReader(ImplicationalSystemSerializerText.getInstance(), "txt");
        ImplicationalSystemIOFactory.getInstance().registerWriter(ImplicationalSystemSerializerText.getInstance(), "txt");
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ImplicationalSystemSerializerText() {
    }

    /**
     * Saves this component in a file.
     *
     * The following format is used:
     *
     * An implicational system can be instancied from and save to a text file in
     * the following format: A list of elements separated by a space in the
     * first line ; then, each rule on a line, written like [premise] ->
     * [conclusion] where elements are separated by a space.
     *
     * ~~~
     * a b c d e
     * a b -> c d
     * c d -> e
     * ~~~
     *
     * @param system a system to read
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void read(ImplicationalSystem system, BufferedReader file) throws IOException {
        // first line : All elements of S separated by a space
        // a StringTokenizer is used to divide the line into different token,
        // considering spaces as separator.
        StringTokenizer st = new StringTokenizer(file.readLine());
        while (st.hasMoreTokens()) {
            String n = new String(st.nextToken());
            system.addElement(n);
        }
        // next lines : [elements of the premise separated by a space] -> [elements of the conclusion separated by a space]
        // a StringTokenizer is used to divide each rule.
        String line = file.readLine();
        while (!(line == null) && !line.isEmpty()) {
            st = new StringTokenizer(line);
            Rule r = new Rule();
            boolean prem = true;
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                if (word.equals("->")) {
                    prem = false;
                } else {
                    String x = null;
                    // search of x in S
                    for (Comparable e : system.getSet()) {
                        if (((String) e).equals(word)) {
                            x = (String) e;
                        }
                    }
                    if (x != null) {
                        if (prem) {
                            r.addToPremise(x);
                        } else {
                            r.addToConclusion(x);
                        }
                    }
                }
            }
            if (!r.getConclusion().isEmpty()) {
                system.addRule(r);
            }
            line = file.readLine();
        }
    }

    /**
     * Saves this component in a file.
     *
     * The following format is used:
     *
     * An implicational system can be instancied from and save to a text file in
     * the following format: A list of elements separated by a space in the
     * first line ; then, each rule on a line, written like [premise] ->
     * [conclusion] where elements are separated by a space.
     *
     * ~~~
     * a b c d e
     * a b -> c d
     * c d -> e
     * ~~~
     *
     * @param system a system to write
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(ImplicationalSystem system, BufferedWriter file) throws IOException {
        file.write(system.toString());
    }
}

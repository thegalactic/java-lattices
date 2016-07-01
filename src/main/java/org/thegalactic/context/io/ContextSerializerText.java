package org.thegalactic.context.io;

/*
 * ContextSerializerText.java
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thegalactic.context.Context;
import org.thegalactic.io.Reader;
import org.thegalactic.io.Writer;

/**
 * This class defines the way for reading a context from a text file.
 *
 * ![ContextSerializerText](ContextSerializerText.png)
 *
 * @uml TextSerializer.png
 * !include resources/org/thegalactic/context/io/TextSerializer.iuml
 * !include resources/org/thegalactic/io/Reader.iuml
 * !include resources/org/thegalactic/io/Writer.iuml
 * hide members
 * show TextSerializer members
 * class TextSerializer #LightCyan
 * title TextSerializer UML graph
 */
public final class ContextSerializerText implements Reader<Context>, Writer<Context> {

    /**
     * String extension.
     */
    private static final String EXTENSION = "txt";

    /**
     * The singleton instance.
     */
    private static final ContextSerializerText INSTANCE = new ContextSerializerText();

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ContextSerializerText getInstance() {
        return INSTANCE;
    }

    /**
     * Register this class for reading and writing .txt files.
     */
    public static void register() {
        ContextIOFactory.getInstance().registerReader(ContextSerializerText.getInstance(), EXTENSION);
        ContextIOFactory.getInstance().registerWriter(ContextSerializerText.getInstance(), EXTENSION);
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextSerializerText() {
        super();
    }

    /**
     * Read a context from a file.
     *
     * The following format is respected:
     *
     * The list of observations separated by a space on the first line ; the
     * list of attrbutes separated by a space on the second line ; then, for
     * each observations o, the list of its intent on a line, written like o a1
     * a2 ...
     *
     * ~~~
     * Observations: 1 2 3
     * Attributes: a b c d e
     * 1: a c
     * 2: a b
     * 3: b d e
     * 4: c e
     * ~~~
     *
     * @param context a context to read
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    public void read(final Context context, final BufferedReader file) throws IOException {
        this.readObservations(context, file);
        this.readAttributes(context, file);
        this.readExtentIntent(context, file);
        context.setBitSets();
    }

    /**
     * Read observations from a file.
     *
     * The following format is respected:
     *
     * ~~~
     * Observations: 1 2 3
     * ~~~
     *
     * @param context a context to read
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    private void readObservations(final Context context, final BufferedReader file) throws IOException {
        final List<String> list = this.analyzeString(file.readLine());
        if ("Observations".equals(list.get(0))) {
            for (int i = 1; i < list.size(); i++) {
                if (!context.addToObservations(list.get(i))) {
                    throw new IOException("Duplicated observation");
                }
            }
        } else {
            throw new IOException("Invalid declaration of observations");
        }
    }

    /**
     * Read attributes from a file.
     *
     * The following format is respected:
     *
     * ~~~
     * Attributes: a b c d e
     * ~~~
     *
     * @param context a context to read
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    private void readAttributes(final Context context, final BufferedReader file) throws IOException {
        final List<String> list = this.analyzeString(file.readLine());
        if ("Attributes".equals(list.get(0))) {
            for (int i = 1; i < list.size(); i++) {
                if (!context.addToAttributes(list.get(i))) {
                    throw new IOException("Duplicated attribute");
                }
            }
        } else {
            throw new IOException("Invalid declaration of attributes");
        }
    }

    /**
     * Read extension and intension from a file.
     *
     * The following format is respected:
     *
     * ~~~
     * 1: a c
     * 2: a b
     * 3: b d e
     * 4: c e
     * ~~~
     *
     * @param context a context to read
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    private void readExtentIntent(final Context context, final BufferedReader file) throws IOException {
        String line;
        List<String> list;
        line = file.readLine();
        while (line != null && !line.isEmpty()) {
            list = this.analyzeString(line);
            if (!context.containsObservation(list.get(0))) {
                throw new IOException("Unexisting observation");
            }
            for (int i = 1; i < list.size(); i++) {
                if (!context.containsAttribute(list.get(i))) {
                    throw new IOException("Unexisting attribute");
                }
                // Add the extent/intent for the current observation and current attribute
                context.addExtentIntent(list.get(0), list.get(i));
            }
            line = file.readLine();
        }
    }

    /**
     * Analyze a string.
     *
     * @param string line to analyze
     *
     * @return a list of string
     */
    private List<String> analyzeString(final String string) {
        final Pattern definition = Pattern.compile("(?:(?:\"(?<quoted>(?:[^\"]|\\\")+)\")|(?<simple>[^ :\"]+)):(?<elements>.*)");
        final Pattern elements = Pattern.compile("(?:\"(?<quoted>(?:[^\"]|\\\")+)\")|(?<simple>[^ :\"]+)");
        final List<String> list = new ArrayList<String>();
        Matcher matcher;

        matcher = definition.matcher(string);
        if (matcher.find()) {
            // Get the first name (before the colon)
            list.add(this.getElement(matcher));
            matcher = elements.matcher(matcher.group("elements"));
            while (matcher.find()) {
                list.add(this.getElement(matcher));
            }
        }
        return list;
    }

    /**
     * Get the element using the matcher.
     *
     * @param matcher the matcher
     *
     * @return the string
     */
    private String getElement(final Matcher matcher) {
        String element;
        if (matcher.group("quoted") == null) {
            element = matcher.group("simple");
        } else {
            element = matcher.group("quoted").replace("\\\"", "\"").replace("\\n", "\n").replace("\\t", "\t");
        }
        return element;
    }

    /**
     * Write a context to a file.
     *
     * The following format is respected:
     *
     * The list of observations separated by a space on the first line ; the
     * list of attrbutes separated by a space on the second line ; then, for
     * each observations o, the list of its intent on a line, written like o a1
     * a2 ...
     *
     * ~~~
     * Observations: 1 2 3
     * Attributes: a b c d e
     * 1: a c
     * 2: a b
     * 3: b d e
     * 4: c e
     * ~~~
     *
     * @param context a context to write
     * @param file    a file
     *
     * @throws IOException When an IOException occurs
     */
    public void write(final Context context, final BufferedWriter file) throws IOException {
        file.write(context.toString());
    }
}

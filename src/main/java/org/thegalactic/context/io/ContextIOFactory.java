package org.thegalactic.context.io;

/*
 * ContextIOFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.Context;

/**
 * This class register readers and writers for the Context class.
 *
 * ![ContextIOFactory](ContextIOFactory.png)
 *
 * @uml ContextIOFactory.png
 * !include resources/org/thegalactic/context/io/ContextIOFactory.iuml
 *
 * hide members
 * show ContextIOFactory members
 * class ContextIOFactory #LightCyan
 * title ContextIOFactory UML graph
 */
public final class ContextIOFactory extends org.thegalactic.io.IOFactory<Context> {

    /**
     * The singleton instance.
     */
    private static final ContextIOFactory INSTANCE = new ContextIOFactory();

    /**
     * Initialise the unique instance of this class.
     */
    static {
        ContextIOFactory.init();
    }

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ContextIOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Initialse the factory.
     */
    protected static void init() {
        TextSerializer.register();
        BurmeisterSerializer.register();
        FIMISerializer.register();
        CsvSerializer.register();
        SLFSerializer.register();
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ContextIOFactory() {
    }
}

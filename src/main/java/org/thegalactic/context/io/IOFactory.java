package org.thegalactic.context.io;

/*
 * IOFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.TemporaryContext;

/**
 * This class register readers and writers for the TemporaryContext class.
 *
 * ![IOFactory](IOFactory.png)
 *
 * @uml Factory.png
 * !include resources/org/thegalactic/context/io/Factory.iuml
 *
 * hide members
 * show Factory members
 * class Factory #LightCyan
 * title Factory UML graph
 */
public final class IOFactory extends org.thegalactic.io.IOFactory<TemporaryContext> {

    /**
     * The singleton instance.
     */
    private static final IOFactory INSTANCE = new IOFactory();

    /**
     * Initialise the unique instance of this class.
     */
    static {
        IOFactory.init();
    }

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static IOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private IOFactory() {
    }

    /**
     * Initialse the factory.
     */
    protected static void init() {
        Text.register();
        Burmeister.register();
        FIMI.register();
        Csv.register();
    }
}

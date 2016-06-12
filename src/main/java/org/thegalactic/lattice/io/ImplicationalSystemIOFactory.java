package org.thegalactic.lattice.io;

/*
 * ArrowRelationIOFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.lattice.ImplicationalSystem;

/**
 * This class register readers and writers for the Context class.
 *
 * ![ImplicationalSystemIOFactory](ImplicationalSystemIOFactory.png)
 *
 * @uml ImplicationalSystemIOFactory.png
 * !include resources/org/thegalactic/lattice/io/ImplicationalSystemIOFactory.iuml
 *
 * hide members
 * show ImplicationalSystemIOFactory members
 * class ImplicationalSystemIOFactory #LightCyan
 * title ImplicationalSystemIOFactory UML graph
 */
public final class ImplicationalSystemIOFactory extends org.thegalactic.io.IOFactory<ImplicationalSystem> {

    /**
     * The singleton instance.
     */
    private static final ImplicationalSystemIOFactory INSTANCE = new ImplicationalSystemIOFactory();

    /**
     * Initialise the unique instance of this class.
     */
    static {
        ImplicationalSystemIOFactory.init();
    }

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ImplicationalSystemIOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Initialse the factory.
     */
    protected static void init() {
        ImplicationalSystemSerializerText.register();
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ImplicationalSystemIOFactory() {
    }
}

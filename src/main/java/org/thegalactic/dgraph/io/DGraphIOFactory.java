package org.thegalactic.dgraph.io;

/*
 * DGraphIOFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.dgraph.ConcreteDGraph;

/**
 * This class register readers and writers for the ConcreteDGraph class.

 ![DGraphIOFactory](DGraphIOFactory.png)
 *
 * @uml DGraphIOFactory.png
 * !include resources/org/thegalactic/dgraph/io/DGraphIOFactory.iuml
 *
 * hide members
 * show DGraphIOFactory members
 * class DGraphIOFactory #LightCyan
 * title DGraphIOFactory UML graph
 */
public final class DGraphIOFactory extends org.thegalactic.io.IOFactory<ConcreteDGraph> {

    /**
     * The singleton instance.
     */
    private static final DGraphIOFactory INSTANCE = new DGraphIOFactory();

    /**
     * Initialise the unique instance of this class.
     */
    static {
        DGraphIOFactory.init();
    }

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static DGraphIOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Initialse the factory.
     */
    protected static void init() {
        DGraphSerializerDot.register();
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private DGraphIOFactory() {
        super();
    }
}

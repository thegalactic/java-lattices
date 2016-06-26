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
import org.thegalactic.lattice.ArrowRelation;

/**
 * This class register readers and writers for the Context class.
 *
 * ![ArrowRelationIOFactory](ArrowRelationIOFactory.png)
 *
 * @uml ArrowRelationIOFactory.png
 * !include resources/org/thegalactic/lattice/io/ArrowRelationIOFactory.iuml
 *
 * hide members
 * show ArrowRelationIOFactory members
 * class ArrowRelationIOFactory #LightCyan
 * title ArrowRelationIOFactory UML graph
 */
public final class ArrowRelationIOFactory extends org.thegalactic.io.IOFactory<ArrowRelation> {

    /**
     * The singleton instance.
     */
    private static final ArrowRelationIOFactory INSTANCE = new ArrowRelationIOFactory();

    /**
     * Initialise the unique instance of this class.
     */
    static {
        ArrowRelationIOFactory.init();
    }

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ArrowRelationIOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Initialse the factory.
     */
    protected static void init() {
        ArrowRelationSerializerTeX.register();
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ArrowRelationIOFactory() {
        super();
    }
}

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
import org.thegalactic.lattice.ConceptLattice;

/**
 * This class register readers and writers for the Context class.
 *
 * ![ConceptLatticeIOFactory](ConceptLatticeIOFactory.png)
 *
 * @uml ConceptLatticeIOFactory.png
 * !include resources/org/thegalactic/lattice/io/ConceptLatticeIOFactory.iuml
 *
 * hide members
 * show ConceptLatticeIOFactory members
 * class ConceptLatticeIOFactory #LightCyan
 * title ConceptLatticeIOFactory UML graph
 */
public final class ConceptLatticeIOFactory extends org.thegalactic.io.IOFactory<ConceptLattice> {

    /**
     * The singleton instance.
     */
    private static final ConceptLatticeIOFactory INSTANCE = new ConceptLatticeIOFactory();

    /**
     * Initialise the unique instance of this class.
     */
    static {
        ConceptLatticeIOFactory.init();
    }

    /**
     * Return the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ConceptLatticeIOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Initialse the factory.
     */
    protected static void init() {
        ConceptLatticeSerializerDot.register();
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private ConceptLatticeIOFactory() {
    }
}

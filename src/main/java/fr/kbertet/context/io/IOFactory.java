package fr.kbertet.context.io;

/*
 * IOFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.context.TemporaryContext;

/**
 * This class register readers and writers for the TemporaryContext class.

 ![IOFactory](IOFactory.png)
 *
 * @uml Factory.png
 * !include resources/fr/kbertet/context/io/Factory.iuml
 *
 * hide members
 * show Factory members
 * class Factory #LightCyan
 * title Factory UML graph
 */
public final class IOFactory extends fr.kbertet.io.IOFactory<TemporaryContext> {
   /**
     * This class is not designed to be publicly instantiated.
     */
    private IOFactory() {
    }

    /**
     * The singleton instance.
     */
    private static IOFactory instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static IOFactory getInstance() {
        if (instance == null) {
            instance = new IOFactory();
            instance.init();
        }
        return instance;
    }

    /**
     * Initialse the factory.
     */
    protected void init() {
        Text.register();
        Burmeister.register();
        FIMI.register();
        Csv.register();
    }
}

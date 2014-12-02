package fr.kbertet.context.io;

/*
 * Factory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.context.Context;

/**
 * This class register readers and writers for the Context class.
 *
 * ![Factory](Factory.png)
 *
 * @uml Factory.png
 * !include resources/fr/kbertet/context/io/Factory.iuml
 *
 * hide members
 * show Factory members
 * class Factory #LightCyan
 * title Factory UML graph
 */
public final class Factory extends fr.kbertet.io.Factory<Context> {
   /**
     * This class is not designed to be publicly instantiated.
     */
    private Factory() {
    }

    /**
     * The singleton instance.
     */
    private static Factory instance = null;

    /**
     * Return the singleton instance of this class.
     *
     * @return  the singleton instance
     */
    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
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

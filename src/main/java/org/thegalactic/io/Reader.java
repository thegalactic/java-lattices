package org.thegalactic.io;

/*
 * Reader.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of CeCILL-B license.
 */

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This interface defines a standard way for reading a context.
 *
 * @param  <E>  The class of elements to read.
 *
 * ![Reader](Reader.png)
 *
 * @uml Reader.png
 * !include resources/org/thegalactic/io/Reader.iuml
 *
 * hide members
 * show Reader members
 * class Reader #LightCyan
 * title Reader UML graph
 */
public interface Reader<E> {
    /**
     * Read a context to a file.
     *
     * @param   e     an element to read
     * @param   file  a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void read(E e, BufferedReader file) throws IOException;
}


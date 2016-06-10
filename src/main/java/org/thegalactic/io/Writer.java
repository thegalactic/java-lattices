package org.thegalactic.io;

/*
 * Writer.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This interface defines a standard way for writing a context.
 *
 * @param  <E>  The class of elements to write
 *
 * ![Writer](Writer.png)
 *
 * @uml Writer.png
 * !include resources/org/thegalactic/io/Writer.iuml
 *
 * hide members
 * show Writer members
 * class Writer #LightCyan
 * title Writer UML graph
 */
public interface Writer<E> {
    /**
     * Write a context to a file.
     *
     * @param   e     an element to write
     * @param   file  a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void write(E e, BufferedWriter file) throws IOException;
}


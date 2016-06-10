package org.thegalactic.dgraph.io;

/*
 * DGraphWriter.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of CeCILL-B license.
 */

import java.io.BufferedWriter;
import java.io.IOException;

import org.thegalactic.dgraph.DGraph;

/**
 * This interface defines a standard way for writing a graph.
 *
 * ![DGraphWriter](DGraphWriter.png)
 *
 * @uml DGraphWriter.png
 * !include resources/org/thegalactic/dgraph/io/DGraphWriter.iuml
 *
 * hide members
 * show DGraphWriter members
 * class DGraphWriter #LightCyan
 * title DGraphWriter UML graph
 */
public interface DGraphWriter {
    /**
     * Write a graph to a output stream.
     *
     * @param   graph  a graph to write
     * @param   file   a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void write(DGraph graph, BufferedWriter file) throws IOException;
}


package dgraph;

/*
 * DGraphWriter.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This interface defines a standard way for writing a graph.
 *
 * ![DGraphWriter](DGraphWriter.png)
 *
 * @uml DGraphWriter.png
 * !include src/dgraph/DGraphWriter.iuml
 *
 * hide members
 * show DGraphWriter members
 * class DGraphWriter #LightCyan
 * title DGraphWriter UML graph
 */
interface DGraphWriter {
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


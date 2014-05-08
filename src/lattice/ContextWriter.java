package lattice;

/*
 * ContextWriter.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This interface defines a standard way for writing a context.
 *
 * ![ContextWriter](ContextWriter.png)
 *
 * @uml ContextWriter.png
 * !include src/lattice/ContextWriter.iuml
 *
 * hide members
 * show ContextWriter members
 * class ContextWriter #LightCyan
 * title ContextWriter UML graph
 */
interface ContextWriter {
    /**
     * Write a context to a file.
     *
     * @param   context  a context to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void write(Context context, BufferedWriter file) throws IOException;
}


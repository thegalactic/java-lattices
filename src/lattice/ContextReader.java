package lattice;

/*
 * ContextReader.java
 *
 * Copyright: 2013 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2014
 */

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This interface defines a standard way for writing a context.
 *
 * ![ContextReader](ContextReader.png)
 *
 * @uml ContextReader.png
 * !include src/lattice/ContextReader.iuml
 *
 * hide members
 * show ContextReader members
 * class ContextReader #LightCyan
 * title ContextReader UML graph
 */
interface ContextReader {
    /**
     * Read a context to a file.
     *
     * @param   context  a context to read
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void read(Context context, BufferedReader file) throws IOException;
}


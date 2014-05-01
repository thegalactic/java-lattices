package lattice;

/*
 * ImplicationalSystemWriter.java
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

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This interface defines a standard way for writing an implicational system.
 *
 * ![ImplicationalSystemWriter](ImplicationalSystemWriter.png)
 *
 * @uml ImplicationalSystemWriter.png
 * !include src/lattice/ImplicationalSystemWriter.iuml
 *
 * hide members
 * show ImplicationalSystemWriter members
 * class ImplicationalSystemWriter #LightCyan
 * title ImplicationalSystemWriter UML graph
 */
interface ImplicationalSystemWriter {
    /**
     * Write an implicational system to a file.
     *
     * @param   system  an implicational system to write
     * @param   file    a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void write(ImplicationalSystem system, BufferedWriter file) throws IOException;
}


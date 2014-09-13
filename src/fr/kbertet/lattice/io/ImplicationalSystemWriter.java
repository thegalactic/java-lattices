package fr.kbertet.lattice.io;

/*
 * ImplicationalSystemWriter.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.lattice.ImplicationalSystem;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This interface defines a standard way for writing an implicational system.
 *
 * ![ImplicationalSystemWriter](ImplicationalSystemWriter.png)
 *
 * @uml ImplicationalSystemWriter.png
 * !include src/fr/kbertet/lattice/io/ImplicationalSystemWriter.iuml
 *
 * hide members
 * show ImplicationalSystemWriter members
 * class ImplicationalSystemWriter #LightCyan
 * title ImplicationalSystemWriter UML graph
 */
public interface ImplicationalSystemWriter {
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


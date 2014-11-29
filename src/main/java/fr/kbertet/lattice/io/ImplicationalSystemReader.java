package fr.kbertet.lattice.io;

/*
 * ImplicationalSystemReader.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.BufferedReader;
import java.io.IOException;

import fr.kbertet.lattice.ImplicationalSystem;

/**
 * This interface defines a standard way for reading an implicational system.
 *
 * ![ImplicationalSystemReader](ImplicationalSystemReader.png)
 *
 * @uml ImplicationalSystemReader.png
 * !include resources/fr/kbertet/lattice/io/ImplicationalSystemReader.iuml
 *
 * hide members
 * show ImplicationalSystemReader members
 * class ImplicationalSystemReader #LightCyan
 * title ImplicationalSystemReader UML graph
 */
public interface ImplicationalSystemReader {
    /**
     * Read an implicational system from a file.
     *
     * @param   system  an implicational system to read
     * @param   file    a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void read(ImplicationalSystem system, BufferedReader file) throws IOException;
}


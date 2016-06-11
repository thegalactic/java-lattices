package org.thegalactic.lattice.io;

/*
 * ImplicationalSystemWriter.java
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

import org.thegalactic.lattice.ImplicationalSystem;

/**
 * This interface defines a standard way for writing an implicational system.
 *
 * ![ImplicationalSystemWriter](ImplicationalSystemWriter.png)
 *
 * @uml ImplicationalSystemWriter.png
 * !include resources/org/thegalactic/lattice/io/ImplicationalSystemWriter.iuml
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
     * @param system an implicational system to write
     * @param file   a file
     *
     * @throws IOException When an IOException occurs
     */
    void write(ImplicationalSystem system, BufferedWriter file) throws IOException;
}

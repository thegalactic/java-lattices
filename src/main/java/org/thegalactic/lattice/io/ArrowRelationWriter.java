package org.thegalactic.lattice.io;

/*
 * ArrowRelationWriter.java
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

import org.thegalactic.lattice.ArrowRelation;

/**
 * This interface defines a standard way for writing an arrow relation.
 *
 * ![ArrowRelationWriter](ArrowRelationWriter.png)
 *
 * @uml ArrowRelationWriter.png
 * !include resources/org/thegalactic/lattice/io/ArrowRelationWriter.iuml
 *
 * hide members
 * show ArrowRelationWriter members
 * class ArrowRelationWriter #LightCyan
 * title ArrowRelationWriter UML graph
 */
public interface ArrowRelationWriter {

    /**
     * Write an arrow relation to a file.
     *
     * @param arrow an arrow relation to write
     * @param file a file
     *
     * @throws IOException When an IOException occurs
     */
    void write(ArrowRelation arrow, BufferedWriter file) throws IOException;
}

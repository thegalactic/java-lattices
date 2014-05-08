package lattice;

/*
 * ArrowRelation.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import dgraph.DGraph;

/**
 * The ArrowRelation class encodes arrow relation between meet & join-irreductibles of a lattice.
 *
 * Let m and b be respectively meet and join irreductibles of a lattice.
 * Recall that m has a unique successor say m+ and j has a unique predecessor say j-, then :
 *
 * - j "Up Arrow" m (stored as "Up") iff j is not less or equal than m and j is less than m+
 * - j "Down Arrow" m (stored as "Down") iff j is not less or equal than m and j- is less than m
 * - j "Up Down Arrow" m (stored as "UpDown") iff j "Up" m and j "Down" m
 * - j "Cross" m (stored as "Cross") iff j is less or equal than m
 * - j "Circ" m (stored as "Circ") iff neither j "Up" m nor j "Down" m nor j "Cross" m
 *
 * ![ArrowRelation](ArrowRelation.png)
 *
 * @uml ArrowRelation.png
 * !include src/dgraph/DGraph.iuml
 * !include src/dgraph/Edge.iuml
 * !include src/dgraph/Node.iuml
 * !include src/lattice/ArrowRelation.iuml
 *
 * hide members
 * show ArrowRelation members
 * class ArrowRelation #LightCyan
 * title ArrowRelation UML graph
 */
public class ArrowRelation extends DGraph  {
    /*
     * Register tex writer
     */
    static {
        if (ArrowRelationWriterFactory.get("tex") == null) {
            ArrowRelationWriterTeX.register();
        }
    }

    /**
     * Unique constructor of this component from a lattice.
     *
     * Nodes are join or meet irreductibles of the lattice.
     * Edges content encodes arrows as String "Up", "Down", "UpDown", "Cross", "Circ".
     *
     * @param   lattice  Lattice from which this component is deduced.
     */
    public ArrowRelation(Lattice lattice) {
        super(lattice.getArrowRelation());
    }

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param   filename  the name of the file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void save(final String filename) throws IOException {
        String extension = "";
        int index = filename.lastIndexOf('.');
        if (index > 0) {
            extension = filename.substring(index + 1);
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(filename));
        ArrowRelationWriterFactory.get(extension).write(this, file);
        file.close();
    }
}

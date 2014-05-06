package lattice;

/*
 * ConceptLatticeWriter.java
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
 * This interface defines a standard way for writing a concept lattice.
 *
 * ![ConceptLatticeWriter](ConceptLatticeWriter.png)
 *
 * @uml ConceptLatticeWriter.png
 * !include src/lattice/ConceptLatticeWriter.iuml
 *
 * hide members
 * show ConceptLatticeWriter members
 * class ConceptLatticeWriter #LightCyan
 * title ConceptLatticeWriter UML graph
 */
interface ConceptLatticeWriter {
    /**
     * Write a concept lattice to a file.
     *
     * @param   lattice  a concept lattice to write
     * @param   file     a file
     *
     * @throws  IOException  When an IOException occurs
     */
    void write(ConceptLattice lattice, BufferedWriter file) throws IOException;
}


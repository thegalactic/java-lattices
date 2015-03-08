package org.thegalactic.context.attribute;

/*
 * Constraint.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.Set;

/**
 * A constraint.
 */
public interface Constraint {

    /**
     * Get the successors of a constraint.
     *
     * @return the successors of this.
     */
    Set<Constraint> getSuccessors();

    /**
     * Get the attribute linked to this constraint.
     *
     * @return the attribute linked to this constraint
     */
    Attribute getAttribute();
}

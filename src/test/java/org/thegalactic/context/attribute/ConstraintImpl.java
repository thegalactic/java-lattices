package org.thegalactic.context.attribute;

/*
 * ConstraintImpl.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.Set;

/**
 * Basic implementation iof AbstractConstraint.
 */
public class ConstraintImpl extends AbstractConstraint {

    /**
     * Constructor.
     *
     * @param attribute The attribute the constraint belongs to
     */
    public ConstraintImpl(Attribute attribute) {
        super(attribute);
    }

    /**
     * Get the successors of a constraint.
     *
     * @return the successors of this.
     */
    public Set<Constraint> getSuccessors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

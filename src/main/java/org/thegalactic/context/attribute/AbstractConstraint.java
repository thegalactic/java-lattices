package org.thegalactic.context.attribute;

/*
 * AbstractConstraint.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * An abstract constraint.
 */
public abstract class AbstractConstraint implements Constraint {

    /**
     * Constructor.
     *
     * @param attribute The attribute linked to this constraint.
     */
    protected AbstractConstraint(Attribute attribute) {
        this.attribute = attribute;
    }

    /**
     * Get the attribute linked to this constraint.
     *
     * @return the attribute linked to this constraint
     */
    public Attribute getAttribute() {
        return this.attribute;
    }

    /**
     * Attribute linked to this constraint.
     */
    private final Attribute attribute;
}

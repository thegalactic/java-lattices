package org.thegalactic.context.attribute.binary;

/*
 * BinaryAttribute.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.Context;
import org.thegalactic.context.attribute.AbstractAttribute;
import org.thegalactic.context.attribute.Constraint;

/**
 * BinaryAttribute.
 */
public class BinaryAttribute extends AbstractAttribute {

    /**
     * Type for boolean attribute.
     */
    public static final String TYPE = "bool";

    /**
     * Constructor.
     *
     * @param context the context the attribute belongs to
     * @param name    the attribute name
     */
    BinaryAttribute(String name, Context context) {
        super(name, context);
    }

    /**
     * Get the type of an attribute.
     *
     * @return The type
     */
    public String getType() {
        return TYPE;
    }

    /**
     * Get the domain associated with an attribute.
     *
     * @return The domain
     */
    public Constraint getDomain() {
        return BinaryConstraint.getDomain(this);
    }
}

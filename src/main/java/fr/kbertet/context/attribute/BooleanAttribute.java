package fr.kbertet.context.attribute;

/*
 * BooleanAttribute.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */
import fr.kbertet.context.Context;

/**
 * BooleanAttribute.
 */
public class BooleanAttribute extends AbstractAttribute {
    /**
     * Constructor.
     *
     * @param context the context the attribute belongs to
     * @param name    the attribute name
     */
    BooleanAttribute(String name, Context context) {
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
        return BooleanConstraint.getDomain(this);
    }

    /**
     * Type for boolean attribute.
     */
    public static final String TYPE = "boolean";
}

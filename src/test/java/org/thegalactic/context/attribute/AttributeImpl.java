package org.thegalactic.context.attribute;

/*
 * AttributeImpl.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.Context;

/**
 * Basic implementation of AbstractAttribute.
 */
public class AttributeImpl extends AbstractAttribute {

    /**
     * The attribute type.
     */
    private final String type;

    /**
     * Constructor.
     *
     * @param context The context the attribute belongs to
     * @param name    The attribute name
     * @param type    The attribute type
     */
    public AttributeImpl(Context context, String name, String type) {
        super(name, context);
        this.type = type;
    }

    /**
     * Get the domain.
     *
     * @return the domain
     */
    public Constraint getDomain() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get the type.
     *
     * @return the type
     */
    public String getType() {
        return this.type;
    }
}

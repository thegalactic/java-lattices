package fr.kbertet.context.attribute;

/*
 * AttributeImpl.java
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
 * Basic implementation of AbstractAttribute.
 */
class AttributeImpl extends AbstractAttribute {

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

    /**
     * The attribute type.
     */
    private final String type;
}

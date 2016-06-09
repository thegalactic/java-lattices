package org.thegalactic.context.attribute.binary;

import org.thegalactic.context.attribute.AbstractAttributeBuilder;
import org.thegalactic.context.attribute.Attribute;

/*
 * BinaryAttributeBuilder.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * BinaryAttributeBuilder.
 */
public class BinaryAttributeBuilder extends AbstractAttributeBuilder {

    /**
     * Create a boolean attribute using the context and name.
     *
     * @return a boolean attribute
     */
    public Attribute create() {
        return new BinaryAttribute(this.getName(), this.getContext());
    }
}

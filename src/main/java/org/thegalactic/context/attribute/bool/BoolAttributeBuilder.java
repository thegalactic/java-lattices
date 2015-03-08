package org.thegalactic.context.attribute.bool;

import org.thegalactic.context.attribute.AbstractAttributeBuilder;
import org.thegalactic.context.attribute.Attribute;

/*
 * BoolAttributeBuilder.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * BoolAttributeBuilder.
 */
public class BoolAttributeBuilder extends AbstractAttributeBuilder {

    /**
     * Create a new boolean attribute using the context and name set.
     *
     * @return a new boolean attribute
     */
    public Attribute create() {
        return new BoolAttribute(this.getName(), this.getContext());
    }
}

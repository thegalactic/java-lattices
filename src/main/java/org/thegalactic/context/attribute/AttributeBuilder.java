package org.thegalactic.context.attribute;

/*
 * AttributeBuilder.java
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
 * Attribute Builder.
 */
public interface AttributeBuilder {

    /**
     * Create the attribute.
     *
     * @return the a new attribute
     */
    Attribute create();

    /**
     * Set the context.
     *
     * @param context the context to be set
     *
     * @return this object for chaining
     */
    AttributeBuilder setContext(Context context);

    /**
     * Set the name.
     *
     * @param name the attribute name
     *
     * @return this object for chaining
     */
    AttributeBuilder setName(String name);
}

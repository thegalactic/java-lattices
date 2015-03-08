package org.thegalactic.context.attribute;

/*
 * AttributeFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.HashMap;

import org.thegalactic.context.Context;
import org.thegalactic.context.attribute.bool.BoolAttributeBuilder;
import org.thegalactic.context.attribute.bool.BoolAttribute;

/**
 * The attribute factory.
 */
public final class AttributeFactory {

    /**
     * This class is not designed to be instantiated.
     */
    private AttributeFactory() {
        builders = new HashMap<String, AttributeBuilder>();
        builders.put(BoolAttribute.TYPE, (AttributeBuilder) new BoolAttributeBuilder());
    }

    /**
     * Get the unique instance of AttributeFactory.
     *
     * @return the unique instance of AttributeFactory
     */
    public static AttributeFactory getInstance() {
        if (instance == null) {
            instance = new AttributeFactory();
        }
        return instance;
    }

    /**
     * Create a new attribute.
     *
     * @param type    the attribute type
     * @param context the context the attribute belongs to
     * @param name    the attribute name
     *
     * @return a new attribute
     */
    public Attribute create(String type, String name, Context context) {
        return builders.get(type).setContext(context).setName(name).create();
    }

    /**
     * Register a new attribute builder.
     *
     * @param type    the attribute type
     * @param builder the attribute builder
     *
     * @return this for chaining
     */
    public AttributeFactory register(String type, AttributeBuilder builder) {
        builders.put(type, builder);
        return this;
    }

    /**
     * Remove an attribute builder.
     *
     * @param type the attribute type
     *
     * @return this for chaining
     */
    public AttributeFactory unregister(String type) {
        builders.remove(type);
        return this;
    }

    /**
     * Unique instance of this class.
     */
    private static AttributeFactory instance;

    /**
     * Attribute builders.
     */
    private final HashMap<String, AttributeBuilder> builders;
}

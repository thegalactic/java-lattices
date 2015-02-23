package fr.kbertet.context.attribute;

/*
 * AbstractAttributeBuilder.java
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
 * AbstractAttributeBuilder.
 */
public abstract class AbstractAttributeBuilder implements AttributeBuilder {

    /**
     * Set the context for the new boolean attribute.
     *
     * @param context the context the boolean attribute belongs to
     *
     * @return this object for chaining
     */
    public AttributeBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    /**
     * Get the context.
     *
     * @return the context
     */
    protected Context getContext() {
        return context;
    }

    /**
     * Get the name.
     *
     * @return the name
     */
    protected String getName() {
        return name;
    }

    /**
     * Set the name for the new boolean attribute.
     *
     * @param name the boolean attribute name
     *
     * @return this object for chaining
     */
    public AttributeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The context used for constructing boolean attribute.
     */
    private Context context;

    /**
     * The boolean attribute name.
     */
    private String name;
}

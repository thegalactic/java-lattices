package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalValue.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * Categorical Value.
 */
public final class CategoricalValue {

    /**
     * Data.
     */
    private Object data;

    /**
     * Attribute.
     */
    private final CategoricalAttribute attribute;

    /**
     * Create a new CategoricalValue.
     *
     * @param data Object
     * @param attribute CategoricalAttribute
     *
     * @return a new CategoricalValue
     */
    protected static CategoricalValue create(Object data, CategoricalAttribute attribute) {
        return new CategoricalValue(data, attribute);
    }

    /**
     * This class is nod designed to be publicly instantiated.
     *
     * @param object Object
     * @param attribute CategoricalAttribute
     */
    private CategoricalValue(Object object, CategoricalAttribute attribute) {
        setData(object);
        this.attribute = attribute;
    }

    /**
     * Get the underlying attribute.
     *
     * @return the underlying attribute
     */
    public CategoricalAttribute getAttribute() {
        return attribute;
    }

    /**
     * Get the underlying model.
     *
     * @return the underlying model
     */
    public CategoricalModel getModel() {
        return attribute.getModel();
    }

    /**
     * Get the underlying data.
     *
     * @return the underlying data
     */
    public Object getData() {
        return data;
    }

    /**
     * Set the underlying data.
     *
     * @param data the underlying data
     *
     * @return this for chaining
     */
    public CategoricalValue setData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * Get the index of this value relatively to its attribute.
     *
     * @return the index of this value
     */
    protected int index() {
        return attribute.indexOf(this);
    }

    /**
     * Returns a string representation of the data.
     *
     * @return a string representation of the data.
     */
    @Override
    public String toString() {
        String string = data.toString();
        if (string.contains(" ") || string.contains("\"")) {
            return "\"" + string.replace("\"", "\\\"") + "\"";
        } else {
            return string;
        }
    }
}

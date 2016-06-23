package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalAttribute.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.ArrayList;

/**
 * Categorical Attribute.
 *
 * @todo Set public visibility?
 */
public final class CategoricalAttribute {

    /**
     * Values.
     */
    private final ArrayList<CategoricalValue> values;

    /**
     * Model.
     */
    private final CategoricalModel model;

    /**
     * Create a CategoricalAttribute.
     *
     * @param model The underlying CategoricalModel
     *
     * @return new CategoricalAttribute
     */
    protected static CategoricalAttribute create(final CategoricalModel model) {
        return new CategoricalAttribute(model);
    }

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param model The underlying CategoricalModel
     */
    private CategoricalAttribute(final CategoricalModel model) {
        this.values = new ArrayList();
        this.model = model;
    }

    /**
     * Add a value to a CategoricalAttribute.
     *
     * @param data the underlying data
     *
     * @return the categorical value
     */
    public CategoricalValue addValue(final Object data) {
        if (model.isInstantiated()) {
            throw new IllegalStateException("The underlying model has already been instantiated");
        } else {
            final CategoricalValue value = CategoricalValue.create(data, this);
            values.add(value);
            return value;
        }
    }

    /**
     * Get the collection of CategoricalValue.
     *
     * @return an iterable collection of CategoricalValue
     */
    public Iterable<CategoricalValue> getValues() {
        return values;
    }

    /**
     * Get the number of values.
     *
     * @return the number of values
     */
    public int size() {
        return values.size();
    }

    /**
     * Get the start of this.
     *
     * @return the start of this
     */
    protected int start() {
        return model.start(this);
    }

    /**
     * Get the index of a CategoricalValue.
     *
     * @param value the CategoricalValue
     *
     * @return the index of the CategoricalValue
     */
    protected int indexOf(final CategoricalValue value) {
        return values.indexOf(value);
    }

    /**
     * Get the underlying model.
     *
     * @return the underlying model
     */
    protected CategoricalModel getModel() {
        return model;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return values.toString();
    }
}

package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalModel.java
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
 * Categorical Model.
 */
public final class CategoricalModel {

    /**
     * Instantiated.
     */
    private boolean instantiated;

    /**
     * Values.
     */
    private final ArrayList<CategoricalAttribute> attributes;

    /**
     * Factory method to construct a categorical model.
     *
     * @return a new CategoricalModel object
     */
    public static CategoricalModel create() {
        return new CategoricalModel();
    }

    /**
     * This class is not designed to be publicly instantiated.
     */
    private CategoricalModel() {
        instantiated = false;
        attributes = new ArrayList<CategoricalAttribute>();
    }

    /**
     * Add a new categorical attribute.
     *
     * @return a new added CategoricalAttribute
     */
    public CategoricalAttribute addAttribute() {
        if (instantiated) {
            throw new IllegalStateException("This model has already been instantiated");
        } else {
            CategoricalAttribute attribute = CategoricalAttribute.create(this);
            attributes.add(attribute);
            return attribute;
        }
    }

    /**
     * Get the collection of CategoricalAttribute.
     *
     * @return an iterable collection of CategoricalAttribute
     */
    public Iterable<CategoricalAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Set the model instantiated.
     *
     * @return this for chaining
     */
    protected CategoricalModel instantiate() {
        instantiated = true;
        return this;
    }

    /**
     * Get the instantiated flag.
     *
     * @return the instantiated flag
     */
    protected boolean getInstantiated() {
        return instantiated;
    }

    /**
     * Get the number of attributes.
     *
     * @return the number of attributes
     */
    public int size() {
        return attributes.size();
    }

    /**
     * Get the index of the CategoricalValue.
     *
     * @param value CategoricalValue
     *
     * @return the index
     */
    public int indexOf(CategoricalValue value) {
        int index = 0;
        CategoricalAttribute current = value.getAttribute();
        for (CategoricalAttribute attribute : attributes) {
            if (attribute == current) {
                return index + current.indexOf(value);
            } else {
                index += attribute.size();
            }
        }
        throw new IllegalArgumentException("The CategoricalValue is not in this model");
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return attributes.toString();
    }
}

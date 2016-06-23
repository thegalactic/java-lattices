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
import java.util.HashMap;

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
     * startIndex.
     */
    private final HashMap<CategoricalAttribute, Integer> startIndex;

    /**
     * Size.
     */
    private int size;

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
        startIndex = new HashMap<CategoricalAttribute, Integer>();
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
            final CategoricalAttribute attribute = CategoricalAttribute.create(this);
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
    CategoricalModel instantiate() {
        int start = 0;
        for (final CategoricalAttribute attribute : attributes) {
            startIndex.put(attribute, start);
            start += attribute.size();
        }
        size = start;
        instantiated = true;
        return this;
    }

    /**
     * Get the instantiated flag.
     *
     * @return the instantiated flag
     */
    boolean isInstantiated() {
        return instantiated;
    }

    /**
     * Get the number of attributes.
     *
     * @return the number of attributes
     */
    public int sizeAttributes() {
        return attributes.size();
    }

    /**
     * Get the number of attributes.
     *
     * @return the number of attributes
     */
    public int sizeValues() {
        int value;
        if (instantiated) {
            value = size;
        } else {
            value = 0;
            for (final CategoricalAttribute attribute : attributes) {
                value += attribute.size();
            }
        }
        return value;
    }

    /**
     * Get the index of the CategoricalValue.
     *
     * @param value CategoricalValue
     *
     * @return the index
     */
    public int indexOf(final CategoricalValue value) {
        if (this.equals(value.getModel())) {
            return value.index();
        } else {
            throw new IllegalArgumentException("The CategoricalValue is not in this model");
        }
    }

    /**
     * Get the startIndex index of this attribute in its model.
     *
     * @param attribute CategoricalAttribute
     *
     * @return the startIndex
     */
    public int startIndex(final CategoricalAttribute attribute) {
        if (this.equals(attribute.getModel())) {
            int start;
            if (instantiated) {
                start = startIndex.get(attribute);
            } else {
                start = 0;
                for (final CategoricalAttribute current : attributes) {
                    if (attribute.equals(current)) {
                        break;
                    } else {
                        start += current.size();
                    }
                }
            }
            return start;
        } else {
            throw new IllegalArgumentException("The CategoricalAttribute is not in this model");
        }
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

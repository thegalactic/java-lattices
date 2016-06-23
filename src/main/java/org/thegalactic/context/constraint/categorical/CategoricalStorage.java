package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalStorage.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.BitSet;

/**
 * Categorical Storage.
 */
public final class CategoricalStorage {

    /**
     * Model.
     */
    private final CategoricalModel model;

    /**
     * Values.
     */
    private final BitSet values;

    /**
     * Create a CategoricalStorage.
     *
     * @param model Model of this storage
     *
     * @return a new CategoricalStorage
     */
    public static CategoricalStorage create(final CategoricalModel model) {
        return new CategoricalStorage(model);
    }

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param model the underlying model
     */
    private CategoricalStorage(final CategoricalModel model) {
        this.model = model.instantiate();
        final int size = model.sizeValues();
        this.values = new BitSet(size);
        this.values.set(0, size);
    }

    /**
     * Get truth value.
     *
     * @param value Value to get the truth value
     *
     * @return truth value
     */
    public boolean get(final CategoricalValue value) {
        if (model.equals(value.getModel())) {
            return values.get(value.index());
        } else {
            throw new IllegalArgumentException("The CategoricalValue is not in the model of the CategoricalStorage");
        }
    }

    /**
     * Get truth value.
     *
     * @param value Value to set the truth value
     * @param truth truth value to be set
     *
     * @return this for chaining
     */
    public CategoricalStorage set(final CategoricalValue value, boolean truth) {
        if (model.equals(value.getModel())) {
            values.set(value.index(), truth);
            return this;
        } else {
            throw new IllegalArgumentException("The CategoricalValue is not in the model of the CategoricalStorage");
        }
    }

    /**
     * Reduce truth value.
     *
     * @param value CategoricalValue to reduce
     * @param truth truth value
     *
     * @return this for chaining.
     */
    public CategoricalStorage reduce(CategoricalValue value, final boolean truth) {
        if (model.equals(value.getModel())) {
            final int index = value.index();
            values.set(index, truth && values.get(index));
            return this;
        } else {
            throw new IllegalArgumentException("The CategoricalValue is not in the model of the CategoricalStorage");
        }
    }

    /**
     * Extends truth value.
     *
     * @param value CategoricalValue to extend
     * @param truth truth value
     *
     * @return this for chaining.
     */
    public CategoricalStorage extend(final CategoricalValue value, final boolean truth) {
        if (model.equals(value.getModel())) {
            final int index = value.index();
            values.set(index, truth || values.get(index));
            return this;
        } else {
            throw new IllegalArgumentException("The CategoricalValue is not in the model of the CategoricalStorage");
        }
    }

    /**
     * Intersection.
     *
     * @param storage CategoricalStorage
     *
     * @return this for chaining.
     *
     * @throws IllegalArgumentException
     */
    public CategoricalStorage intersection(final CategoricalStorage storage) {
        if (model.equals(storage.model)) {
            values.and(storage.values);
        } else {
            throw new IllegalArgumentException("CategoricalStorage objects must have the same model");
        }
        return this;
    }

    /**
     * Union.
     *
     * @param storage CategoricalStorage
     *
     * @return this for chaining.
     *
     * @throws IllegalArgumentException
     */
    public CategoricalStorage union(final CategoricalStorage storage) {
        if (model.equals(storage.model)) {
            values.or(storage.values);
        } else {
            throw new IllegalArgumentException("CategoricalStorage objects must have the same model");
        }
        return this;
    }

    /**
     * Returns a String representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append('[');

        boolean firstAttribute = true;
        for (CategoricalAttribute attribute : model.getAttributes()) {
            if (!firstAttribute) {
                builder.append(", ");
            }
            firstAttribute = false;

            builder.append('[');

            boolean firstValue = true;
            for (CategoricalValue value : attribute.getValues()) {
                if (get(value)) {
                    if (!firstValue) {
                        builder.append(", ");
                    }
                    firstValue = false;
                    builder.append(value.toString());
                }
            }

            builder.append(']');
        }

        builder.append(']');

        return builder.toString();
    }
}

package org.thegalactic.context.constraint;

/*
 * BooleanStorage.java
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
 * Binary Storage.
 */
public final class BooleanStorage {

    /**
     * Factory method to construct a binary storage.
     *
     * @param length number of bits
     *
     * @return a new BooleanStorage object
     */
    static BooleanStorage create(int length) {
        return new BooleanStorage(length);
    }

    /**
     * # values.
     */
    private final int length;

    /**
     * Truth values.
     */
    private final BitSet values;

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param length number of bits
     */
    private BooleanStorage(int length) {
        this.length = length;
        values = new BitSet(length);
    }

    /**
     * Get truth value.
     *
     * @param index truth value to be get
     *
     * @return truth value
     */
    public boolean get(int index) {
        return values.get(index);
    }

    /**
     * Set truth value.
     *
     * @param index truth value to be set
     * @param value new truth value
     *
     * @return this for chaining.
     */
    public BooleanStorage set(int index, boolean value) {
        values.set(index, value);
        return this;
    }

    /**
     * Reduce truth value.
     *
     * @param index truth value to be reduced
     * @param value truth value
     *
     * @return this for chaining.
     */
    public BooleanStorage reduce(int index, boolean value) {
        values.set(index, value || values.get(index));
        return this;
    }

    /**
     * Extends truth value.
     *
     * @param index truth value to be extended
     * @param value truth value
     *
     * @return this for chaining.
     */
    public BooleanStorage extend(int index, boolean value) {
        values.set(index, value && values.get(index));
        return this;
    }

    /**
     * Intersection.
     *
     * @param storage BooleanStorage
     *
     * @return this for chaining.
     *
     * @throws IllegalArgumentException
     */
    public BooleanStorage intersection(BooleanStorage storage) {
        if (storage.length == this.length) {
            values.and(storage.values);
        } else {
            throw new IllegalArgumentException("BooleanStorage objects must have the same length");
        }
        return this;
    }

    /**
     * Intersection.
     *
     * @param storage BooleanStorage
     *
     * @return this for chaining.
     *
     * @throws IllegalArgumentException
     */
    public BooleanStorage union(BooleanStorage storage) {
        if (storage.length == this.length) {
            values.or(storage.values);
        } else {
            throw new IllegalArgumentException("BooleanStorage objects must have the same length");
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
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        for (int index = 0; index < length; index++) {
            if (values.get(index)) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}

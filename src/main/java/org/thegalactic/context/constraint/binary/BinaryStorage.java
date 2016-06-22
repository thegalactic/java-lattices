package org.thegalactic.context.constraint.binary;

/*
 * BinaryStorage.java
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
public final class BinaryStorage {

    /**
     * # values.
     */
    private final int size;

    /**
     * Truth values.
     */
    private final BitSet values;

    /**
     * Factory method to construct a binary storage.
     *
     * @param size number of bits
     *
     * @return a new BinaryStorage object
     */
    public static BinaryStorage create(int size) {
        return new BinaryStorage(size);
    }

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param size number of bits
     */
    private BinaryStorage(int size) {
        this.size = size;
        values = new BitSet(size);
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
     * @param truth new truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage set(int index, boolean truth) {
        values.set(index, truth);
        return this;
    }

    /**
     * Reduce truth value.
     *
     * @param index truth value to be reduced
     * @param truth truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage reduce(int index, boolean truth) {
        values.set(index, truth || values.get(index));
        return this;
    }

    /**
     * Extends truth value.
     *
     * @param index truth value to be extended
     * @param truth truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage extend(int index, boolean truth) {
        values.set(index, truth && values.get(index));
        return this;
    }

    /**
     * Intersection.
     *
     * @param storage BinaryStorage
     *
     * @return this for chaining.
     *
     * @throws IllegalArgumentException
     */
    public BinaryStorage intersection(BinaryStorage storage) {
        if (storage.size == size) {
            values.and(storage.values);
        } else {
            throw new IllegalArgumentException("BooleanStorage objects must have the same size");
        }
        return this;
    }

    /**
     * Union.
     *
     * @param storage BinaryStorage
     *
     * @return this for chaining.
     *
     * @throws IllegalArgumentException
     */
    public BinaryStorage union(BinaryStorage storage) {
        if (storage.size == size) {
            values.or(storage.values);
        } else {
            throw new IllegalArgumentException("BooleanStorage objects must have the same size");
        }
        return this;
    }

    /**
     * Get the size.
     *
     * @return the size
     */
    public int size() {
        return size;
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

        for (int index = 0; index < size; index++) {
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

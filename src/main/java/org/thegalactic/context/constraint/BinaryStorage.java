package org.thegalactic.context.constraint;

/*
 * BinaryStorage.java
 *
 * Copyright: 2015-2016 The Galactic Organization, France
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
     * Factory method to construct a binary storage.
     *
     * @param length number of bits
     *
     * @return a new BinaryStorage object
     */
    static BinaryStorage create(int length) {
        return new BinaryStorage(length);
    }

    /**
     * # values.
     */
    private final int length;

    /**
     * Truth and Falsity values.
     */
    private final BitSet values;

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param length number of bits
     */
    private BinaryStorage(int length) {
        this.length = length;
        values = new BitSet(length);
    }

    /**
     * Get truth value.
     *
     * @param index truth value to be values
     *
     * @return truth value
     */
    public boolean getTruth(int index) {
        return values.get(2 * index);
    }

    /**
     * Get falsity value.
     *
     * @param index truth value to be values
     *
     * @return truth value
     */
    public boolean getFalsity(int index) {
        return values.get(2 * index + 1);
    }

    /**
     * Set truth value.
     *
     * @param index truth value to be values
     * @param value new truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage setTruth(int index, boolean value) {
        values.set(2 * index, value);
        return this;
    }

    /**
     * Set falsity value.
     *
     * @param index truth value to be values
     * @param value new truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage setFalsity(int index, boolean value) {
        values.set(2 * index + 1, value);
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

        for (int index = 0; index < length; index++) {
            if (values.get(2 * index)) {
                if (values.get(2 * index + 1)) {
                    stringBuilder.append("!");
                } else {
                    stringBuilder.append("1");
                }
            } else if (values.get(2 * index + 1)) {
                stringBuilder.append("0");
            } else {
                stringBuilder.append("?");
            }
        }

        return stringBuilder.toString();
    }
}

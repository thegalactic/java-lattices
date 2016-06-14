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
     * Truth values.
     */
    private final BitSet truth;

    /**
     * Falsity values.
     */
    private final BitSet falsity;

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param length number of bits
     */
    private BinaryStorage(int length) {
        this.length = length;
        truth = new BitSet(length);
        falsity = new BitSet(length);
    }

    /**
     * Get truth value.
     *
     * @param index truth value to be set
     *
     * @return truth value
     */
    public boolean getTruth(int index) {
        return truth.get(index);
    }

    /**
     * Get falsity value.
     *
     * @param index truth value to be set
     *
     * @return truth value
     */
    public boolean getFalsity(int index) {
        return falsity.get(index);
    }

    /**
     * Set truth value.
     *
     * @param index truth value to be set
     * @param value new truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage setTruth(int index, boolean value) {
        truth.set(index, value);
        return this;
    }

    /**
     * Set falsity value.
     *
     * @param index truth value to be set
     * @param value new truth value
     *
     * @return this for chaining.
     */
    public BinaryStorage setFalsity(int index, boolean value) {
        falsity.set(index, value);
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

        for (int i = 0; i < length; i++) {
            if (truth.get(i)) {
                if (falsity.get(i)) {
                    stringBuilder.append("c");
                } else {
                    stringBuilder.append("t");
                }
            } else if (falsity.get(i)) {
                stringBuilder.append("f");
            } else {
                stringBuilder.append("i");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}

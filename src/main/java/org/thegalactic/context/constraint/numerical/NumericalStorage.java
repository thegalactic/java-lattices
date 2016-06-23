package org.thegalactic.context.constraint.numerical;

/*
 * NumericalStorage.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Binary Storage.
 */
public final class NumericalStorage {

    /**
     * inf values.
     */
    private final List<Double> inf;

    /**
     * sup values.
     */
    private final List<Double> sup;

    /**
     * EXCEPTION_SIZE.
     */
    private static final String EXCEPTION_SIZE = "NumericalStorage objects must have the same size";

    /**
     * Factory method to construct a numerical storage.
     *
     * @param size number of values
     *
     * @return a new NumericalStorage object
     */
    public static NumericalStorage create(final int size) {
        return new NumericalStorage(size);
    }

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param size number of values
     */
    private NumericalStorage(final int size) {
        this.inf = new ArrayList<Double>(size);
        this.sup = new ArrayList<Double>(size);
        // Initialise as an empty set
        for (int index = 0; index < size; index++) {
            this.inf.add(index, Double.POSITIVE_INFINITY);
            this.sup.add(index, Double.NEGATIVE_INFINITY);
        }
    }

    /**
     * Set the set at specified index empty.
     *
     * @param index index of the numerical set
     *
     * @return this for chaining
     */
    public NumericalStorage setEmpty(final int index) {
        this.inf.set(index, Double.POSITIVE_INFINITY);
        this.sup.set(index, Double.NEGATIVE_INFINITY);
        return this;
    }

    /**
     * Is the set at specified index empty?
     *
     * @param index index of the numerical set
     *
     * @return this for chaining
     */
    public boolean isEmpty(final int index) {
        return this.inf.get(index) > this.sup.get(index);
    }

    /**
     * Set the set at specified index a point.
     *
     * @param index index of the numerical set
     * @param value the new value
     *
     * @return this for chaining
     */
    public NumericalStorage setPoint(final int index, final double value) {
        this.inf.set(index, value);
        this.sup.set(index, value);
        return this;
    }

    /**
     * Is the set at specified index a point?
     *
     * @param index index of the numerical set
     *
     * @return this for chaining
     */
    public boolean isPoint(final int index) {
        return this.inf.get(index).equals(this.sup.get(index));
    }

    /**
     * Set the inf mark to specified value.
     *
     * @param index the specified index
     * @param value the new value
     *
     * @return this for chaining
     */
    public NumericalStorage setInf(final int index, final double value) {
        if (this.isEmpty(index)) {
            this.setPoint(index, value);
        } else if (value > this.sup.get(index)) {
            this.setEmpty(index);
        } else {
            this.inf.set(index, value);
        }
        return this;
    }

    /**
     * Set the sup mark to specified value.
     *
     * @param index the specified index
     * @param value the new value
     *
     * @return this for chaining
     */
    public NumericalStorage setSup(final int index, final double value) {
        if (this.isEmpty(index)) {
            this.setPoint(index, value);
        } else if (value < this.inf.get(index)) {
            this.setEmpty(index);
        } else {
            this.sup.set(index, value);
        }
        return this;
    }

    /**
     * Get the inf value at specified index.
     *
     * @param index the specified index
     *
     * @return the inf value
     */
    public double getInf(final int index) {
        return this.inf.get(index);
    }

    /**
     * Get the sup value at specified index.
     *
     * @param index the specified index
     *
     * @return the inf value
     */
    public double getSup(final int index) {
        return this.sup.get(index);
    }

    /**
     * Reduce by inf value.
     *
     * @param index value to be reduced
     * @param value value used to reduce
     *
     * @return this for chaining.
     */
    public NumericalStorage reduceInf(final int index, final double value) {
        this.inf.set(index, Math.max(this.inf.get(index), value));
        return this;
    }

    /**
     * Reduce by sup value.
     *
     * @param index value to be reduced
     * @param value value used to reduce
     *
     * @return this for chaining.
     */
    public NumericalStorage reduceSup(final int index, final double value) {
        this.sup.set(index, Math.min(this.sup.get(index), value));
        return this;
    }

    /**
     * Extend by inf value.
     *
     * @param index value to be extended
     * @param value value used to extend
     *
     * @return this for chaining
     */
    public NumericalStorage extendInf(final int index, final double value) {
        this.inf.set(index, Math.min(this.inf.get(index), value));
        return this;
    }

    /**
     * Extend by sup value.
     *
     * @param index value to be extended
     * @param value value used to extend
     *
     * @return this for chaining
     */
    public NumericalStorage extendSup(final int index, final double value) {
        this.sup.set(index, Math.max(this.sup.get(index), value));
        return this;
    }

    /**
     * Extends value.
     *
     * @param index value to be extended
     * @param value value used to extend
     *
     * @return this for chaining.
     */
    public NumericalStorage extend(final int index, final double value) {
        this.extendInf(index, value);
        this.extendSup(index, value);
        return this;
    }

    /**
     * Intersection.
     *
     * @param storage NumericalStorage
     *
     * @return this for chaining
     */
    public NumericalStorage intersection(final NumericalStorage storage) {
        final int size = this.inf.size();
        if (storage.inf.size() == size) {
            for (int index = 0; index < size; index++) {
                this.inf.set(index, Math.max(this.inf.get(index), storage.inf.get(index)));
                this.sup.set(index, Math.min(this.sup.get(index), storage.sup.get(index)));
            }
            return this;
        } else {
            throw new IllegalArgumentException(EXCEPTION_SIZE);
        }
    }

    /**
     * Union.
     *
     * An approximation is made for union when the two intervals are disjoint.
     * The result is the minimum interval containing both intervals.
     *
     * @param storage NumericalStorage
     *
     * @return this for chaining
     */
    public NumericalStorage union(final NumericalStorage storage) {
        final int size = this.inf.size();
        if (storage.inf.size() == size) {
            for (int index = 0; index < size; index++) {
                this.inf.set(index, Math.min(this.inf.get(index), storage.inf.get(index)));
                this.sup.set(index, Math.max(this.sup.get(index), storage.sup.get(index)));
            }
            return this;
        } else {
            throw new IllegalArgumentException(EXCEPTION_SIZE);
        }
    }

    /**
     * Get the size.
     *
     * @return the size
     */
    public int size() {
        return this.inf.size();
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

        final int size = this.inf.size();
        for (int index = 0; index < size; index++) {
            if (index != 0) {
                builder.append(", ");
            }
            if (this.isEmpty(index)) {
                builder.append('@');
            } else if (this.isPoint(index)) {
                builder.append(this.inf.get(index));
            } else {
                builder.append('(');
                builder.append(this.inf.get(index));
                builder.append(',');
                builder.append(this.sup.get(index));
                builder.append(')');
            }
        }

        builder.append(']');

        return builder.toString();
    }
}

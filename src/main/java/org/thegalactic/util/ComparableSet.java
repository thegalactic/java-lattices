package org.thegalactic.util;

/*
 * ComparableSet.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class gives a minimal representation of a comparable set where sets are
 * compared using the lectic order.
 *
 * This class extends class `TreeSet`, implements class `Comparable` and
 * provides a {@link #compareTo} method that implements the lectic order between
 * two sets.
 *
 * Therefore, a comparable set can be stored in a sorted collection, and in
 * particular in a sorted set where set operations are provided.
 *
 * The lectic order extends the inclusion, and is defined only for comparable
 * elements, i.e. elements that can be sorted, as follows:
 *
 * "a set `A` is smaller than a set `B` iff there exists an element in `B\A`
 * such that any smaller element belonging to `A` also belongs to `B`."
 *
 * @param <E> Element type
 *
 * @todo Check if this class is correctly used (performance). Overload
 * modification method to compute the hashCode only once.
 *
 * ![ComparableSet](ComparableSet.png)
 *
 * @uml ComparableSet.png
 * !include resources/org/thegalactic/util/ComparableSet.iuml
 *
 * hide members
 * show ComparableSet members
 * class ComparableSet #LightCyan
 * title ComparableSet UML graph
 */
public class ComparableSet<E extends Comparable> extends TreeSet<E> implements Comparable<ComparableSet>, Cloneable {

    /*
     * ------------- CONSTRUCTORS ------------------
     */
    /**
     * Constructs a new and empty ComparableSet.
     */
    public ComparableSet() {
        super();
    }

    /**
     * Constructs a new ComparableSet with the set from the specified set.
     *
     * @param set a comparable set
     */
    public ComparableSet(final SortedSet<E> set) {
        super(set);
    }

    /*
     * --------------- OVERLAPPING METHODS ------------
     */
    /**
     * Returns a clone of this component.
     *
     * @return a clone of this component.
     */
    @Override
    public ComparableSet clone() {
        return (ComparableSet) super.clone();
    }

    /**
     * Compares this component with those in parameter according to the lectic
     * order.
     *
     * The lectic order defines a sort on sets of elements extending the
     * inclusion order as follows:
     *
     * A set `A` is smaller than a set `B` iff there exists an element in `B\A`
     * such that any smaller element belonging to A also belongs to B. The
     * result is
     *
     * - zero if the identifiers are equal;
     * - 1 if this component's identifier is greater;
     * - -1 otherwise.
     *
     * This comparison method is needed to define a natural and total sort on a
     * sets.
     *
     * It allows to use sets of this class in a sorted collection
     *
     * @param set the specified element to be compared with this component
     *
     * @return a negative integer, zero, or a positive integer as this component
     *         is less than, equal to, or greater than the specified object according to
     *         the lectic order.
     *
     * @todo Is this correct? (see test)
     */
    public int compareTo(final ComparableSet set) {

        int cmp;

        // case of equality between this component and object
        if (this.equals(set)) {
            cmp = 0;
        } else {
            // computes index i of the first element in set minus this
            // if i doesn't exist, then this component is not smaller than the set by lectic order
            final TreeSet<Comparable> setMinusThis = new TreeSet(set);
            setMinusThis.removeAll(this);
            if (setMinusThis.isEmpty()) {
                cmp = 1;
            } else {
                final Comparable i = setMinusThis.first();
                // compute this inter {1, ..., i-1}
                final TreeSet setAiMinus1 = new TreeSet();
                for (final Object c : this) {
                    if (i.compareTo(c) > 0) {
                        setAiMinus1.add(c);
                    }
                }
                // compute set inter {1, ..., i-1}
                final TreeSet setBiMinus1 = new TreeSet();
                for (final Object c : set) {
                    if (i.compareTo(c) > 0) {
                        setBiMinus1.add(c);
                    }
                }
                // if setAiminus1 and setBiminus1 are equal then this component is smaller than B by lectic order
                if (setAiMinus1.equals(setBiMinus1)) {
                    cmp = -1;
                } else {
                    cmp = 1;
                }
            }
        }
        return cmp;
    }
}

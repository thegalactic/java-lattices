package fr.kbertet.util;

/*
 * ComparableSet.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * This class gives a minimal representation of a comparable set where sets
 * are compared using the lectic order.
 *
 * This class extends class `TreeSet`, implements class `Comparable` and provides
 * a {@link #compareTo} method that implements the lectic order between two sets.
 *
 * Therefore, a comparable set can be stored in a sorted collection, and in particular in a sorted set where
 * set operations are provided.
 *
 * The lectic order extends the inclusion, and is defined only for comparable elements,
 * i.e. elements that can be sorted, as follows:
 *
 * "a set `A` is smaller than a set `B` iff there exists an element in `B\A`
 * such that any smaller element belonging to `A` also belongs to `B`."
 *
 * @todo  Check if this class is correctly used (performance). Overload modification method to compute the hashCode only once.
 *
 * ![ComparableSet](ComparableSet.png)
 *
 * @uml ComparableSet.png
 * !include resources/fr/kbertet/util/ComparableSet.iuml
 *
 * hide members
 * show ComparableSet members
 * class ComparableSet #LightCyan
 * title ComparableSet UML graph
 */
public class ComparableSet extends TreeSet implements Comparable, Cloneable {
    /* ------------- CONSTRUCTORS ------------------ */

    /**
     * Constructs a new and empty ComparableSet.
     */
    public ComparableSet() {
        super();
    }

    /**
     * Constructs a new ComparableSet with the set from the specified set.
     *
     * @param   set  a comparable set
     */
    public ComparableSet(TreeSet<Comparable> set) {
        super(set);
    }

    /* --------------- OVERLAPPING METHODS ------------ */

    /**
     * Returns a string representation of this component without spaces.
     *
     * @return  a string representation of this component without spaces
     */
     public String toString() {
        String res = "";
        StringTokenizer st = new StringTokenizer(super.toString());
        while (st.hasMoreTokens()) {
            res += st.nextToken();
        }
        return res;
    }

    /**
     * Returns a clone of this component.
     *
     * @return  a clone of this component.
     */
    public ComparableSet clone() {
        return new ComparableSet((TreeSet) super.clone());
    }

    /**
     * Compares this component with the specified one.
     *
     * @param   object  An object to compare with
     *
     * @return  true or false as this component is equal to the specified object.
     */
    public boolean equals(Object object) {
        if (!(object instanceof ComparableSet)) {
            return false;
        }
        return super.equals(object);
    }

    /**
     * Compute the hash code.
     *
     * @return  an integer representing the object
     */
    public int hashCode() {
        return super.hashCode();
    }

   /**
    * Compares this component with those in parameter according to the lectic order.
    *
    * The lectic order defines a sort on sets of elements extending the inclusion order
    * as follows:
    *
    * A set `A` is smaller than a set `B` iff there exists an element in `B\A`
    * such that any smaller element belonging to A also belongs to B.
    * The result is
    * - zero if the identifiers are equal;
    * - 1 if this component's identifier is greater,
    * - -1 otherwise.
    *
    * This comparison method is needed to define a natural and total sort on a sets.
    *
    * It allows to use sets of this class in a sorted collection
    *
    * @param   object  the specified element to be compared with this component
    *
    * @return  a negative integer, zero, or a positive integer as this component is less than,
    * equal to, or greater than the specified object according to the lectic order.
    *
    * @todo  Is this correct? (see test)
    */
   public int compareTo(Object object) {
        // case of an object not instance of ComparableSet
        if (!(object instanceof ComparableSet)) {
            return -1;
        }

        // case of equality between this component and object
        if (this.equals(object)) {
            return 0;
        }

        // test if this component is smaller than the set by lectic order
        ComparableSet set = (ComparableSet) object;

        // computes index i of the first element in set minus this
        // if i doesn't exist, then this component is not smaller than the set by lectic order
        TreeSet<Comparable> setMinusThis = new TreeSet(set);
        setMinusThis.removeAll(this);
        if (setMinusThis.isEmpty()) {
            return 1;
        }

        Comparable i = setMinusThis.first();
        // compute this inter {1, ..., i-1}
        TreeSet setAiMinus1 = new TreeSet();
        for (Object c : this) {
            if (i.compareTo(c) > 0) {
                setAiMinus1.add(c);
            }
        }
        // compute set inter {1, ..., i-1}
        TreeSet setBiMinus1 = new TreeSet();
        for (Object c : set) {
            if (i.compareTo(c) > 0) {
                setBiMinus1.add(c);
            }
        }
        // if setAiminus1 and setBiminus1 are equal then this component is smaller than B by lectic order
        if  (setAiMinus1.equals(setBiMinus1)) {
            return -1;
        } else {
            return 1;
        }
    }
}


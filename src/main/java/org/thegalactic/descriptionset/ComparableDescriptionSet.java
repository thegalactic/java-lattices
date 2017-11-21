package org.thegalactic.descriptionset;

import java.util.TreeSet;

import org.thegalactic.util.ComparableSet;

/**
 * This class allows to manage set of descriptions as a patterns.
 * The descriptions in the description set have to be of the same type E.
 *
 * Description sets should be ordered by lectic order (to be defined with CompareTo).
 * This order depends on the type of E, and should be implemented in a concrete sub-class.
 *
 * This class adds 3 new abstract methods to ComparableSet:
 *  - {@link #getSimilarity(ComparableDescriptionSet)} returning the similarity of two description sets.
 *  - {@link #isIncludedIn(ComparableDescriptionSet)} returning true if the current description set is
 *      included in the specified one, else false (define the subsumption relation).
 *  - {@link #includes(E)} returning true if the specified description is in the description set.
 *  - {@link #setBot()} initialising the description set to represents to bottom concept intent.
 *  - {@link #isBot()} returning true if the description represents to bottom concept intent.
 *
 *  The super class ComparableSet initially took binary attributes arbitrary ordered.
 *
 * @author Jessie Carbonnel
 *
 * @param <E> the type of descriptions managed in the set
 */
public abstract class ComparableDescriptionSet<E extends Description> extends ComparableSet<E> implements Cloneable {

    /**
     * Generated Version UID.
     */
    private static final long serialVersionUID = 6939073041798850884L;

        /*
         * ------------- CONSTRUCTORS ------------------
         */

    /**
     * Constructs a new and empty ComparableDescriptionSet.
     */
    public ComparableDescriptionSet() {
        super();
    }

    /**
     * Constructs a new ComparableDescriptionSet
     * from an existing sorted set containing elements of the same type.
     *
     * @param set an existing set of descriptions.
     */
    public ComparableDescriptionSet(final TreeSet<E> set) {
        super(set);
    }

    /**
     * Constructs a new ComparableDescriptionSet
     * copying the specified one.
     *
     * @param set the existing ComparableDescriptionSet
     */
    public ComparableDescriptionSet(final ComparableDescriptionSet<E> set) {
        super(set);
    }

        /*
         * ------------ FACTORY ---------------
         */

    /**
     * Returns an instance of a description set depending on the specified Description type.
     *
     * @param type the type of the Descriptions of the ComparableDescriptionSet instance
     * @return an instance of the description set handling descriptions with the specified type
     *             or null if the specified type is unknown.
     */
    public static ComparableDescriptionSet create(String type) {
        if (type.equals("attribute")) {
            return new AttributeDescriptionSet(new TreeSet<AttributeDescription>());
        } else if (type.equals("interval")) {
            return new IntervalDescriptionSet(new TreeSet<IntervalDescription>());
        } else {
            return null;
        }
    }

        /*
         * ------------- ABSTRACT METHODS ------------------
         */

    /**
     * To be overridden in the sub-classes depending on E.
     * Compares description set depending on the lectic order.
     *
     * @param set a description set of the same description type
     * @return a negative integer, zero, or a positive integer as this descriptions set
     *         is less than, equal to, or greater than the specified description set
     *          according to the lectic order.
     */
    public abstract int compareTo(final ComparableDescriptionSet<E> set);

    /**
     * To be overridden in the sub-classes depending on E.
     * Returns true if the current description set includes the specified description, else false.
     * Does not means that the description set "contains" the specified description.
     *
     * @param desc a description of the same type of the ones handled in the description set.
     * @return true if the current description set includes the specified description, else false.
     */
    public boolean includes(final E desc) {
        return this.contains(desc);
    }

    /**
     * To be overridden in the sub-classes depending on E.
     * Returns true if the current description set is included in the specified one, else false.
     *
     * @param set a description set handling the same type of description as the current one.
     * @return true if the current description set is included in the specified one, else false.
     */
    public abstract boolean isIncludedIn(final ComparableDescriptionSet<E> set);

    /**
     * Returns a description set representing the similarity of the current description set
     * and the specified one.
     *
     * @param set a description set handling the same type of description as the current one.
     * @return the similarity of the current description set and the specified one.
     */
    public abstract ComparableDescriptionSet<E> getSimilarity(final ComparableDescriptionSet<E> set);

    /**
     * Returns true if the comparable set represents the bottom concept intent, else false.
     *
     * @return true if the comparable set represents the bottom concept intent, else false
     */
    public abstract boolean isBot();

    /**
     * Initialises the description set as the bottom concept intent.
     */
    public abstract void setBot();

        /*
         * ------------- IMPLEMENTED METHODS ------------------
         */

    /**
     * Returns a String representing the value of the description.
     *
     * @return a String representing the description.
     */
    public String toString() {
        String res = "{ ";
        for (E d : this) {
            res += d.toString() + " ";
        }
        res += "}";
        return res;
    }

    @Override
    /**
     * Returns a clone of this component.
     *
     * @return a clone of this component.
     */
    public ComparableDescriptionSet<E> clone() {
        return (ComparableDescriptionSet<E>) super.clone();
    }
}

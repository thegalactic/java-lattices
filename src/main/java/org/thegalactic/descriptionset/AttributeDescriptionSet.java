package org.thegalactic.descriptionset;

import java.util.TreeSet;
/**
 * This class represents Description Sets of AttributeDescription.
 * An attribute description is a set of binary attributes.
 * Thus it represents a set of attribute set.
 *
 *  Attribute description sets will always contain at most one attribute description,
 *  as the similarity of two attribute sets is another attribute set.
 *
 * @author Jessie Carbonnel
 *
 */
public class AttributeDescriptionSet extends ComparableDescriptionSet<AttributeDescription> {

    /**
     * Generated Version UID.
     */
    private static final long serialVersionUID = -5574025662501568193L;

        /*
         * ------------ CONSTRUCTORS ----------------
         */

    /**
     * Constructs a new description set of binary attribute sets based on an existing set.
     *
     * @param s the existing set of descriptions representing binary attribute sets.
     */
    public AttributeDescriptionSet(TreeSet<AttributeDescription> s) {
        super(s);
    }

        /*
         * ------------ INHERITED METHODS ----------------
         */

    @Override
    /**
     * Copy/paste from ComparableSet<E>.
     * Not verified.
     *
     * Compares two attribute sets (AttributeDescription) depending on the lectic order.
     *
     * Each AttributeDescriptionSet contains only one attribute set, i.e., an AttributeDescription.
     * The compareTo is performed on each set of the two AttributeDescriptionSets.
     *
     * @param set an AttributeDescriptionSet
     * @return a negative integer, zero, or a positive integer as this AttributeDescriptionSet
     *         is less than, equal to, or greater than the specified AttributeDescriptionSet
     *          according to the lectic order.
     */
    public int compareTo(ComparableDescriptionSet<AttributeDescription> set) {

        // attribute set of the current AttributeDescriptionSet
        TreeSet<Comparable> currentSet = new TreeSet<Comparable>();
        currentSet.addAll(this.first().getValue());

        // attribute set of the AttributeDescriptionSet in parameter
        TreeSet<Comparable> comparedSet = new TreeSet<Comparable>();
        comparedSet.addAll(set.first().getValue());

        int cmp;

        // case of equality between this component and object
        if (currentSet.equals(comparedSet)) {
            cmp = 0;
        } else {
            // computes index i of the first element in set minus this
            // if i doesn't exist, then this component is not smaller than the set by lectic order
            final TreeSet<Comparable> setMinusThis = new TreeSet(comparedSet);
            setMinusThis.removeAll(currentSet);
            if (setMinusThis.isEmpty()) {
                cmp = 1;
            } else {
                final Comparable i = setMinusThis.first();
                // compute this inter {1, ..., i-1}
                final TreeSet setAiMinus1 = new TreeSet();
                for (final Object c : currentSet) {
                    if (i.compareTo(c) > 0) {
                        setAiMinus1.add(c);
                    }
                }
                // compute set inter {1, ..., i-1}
                final TreeSet setBiMinus1 = new TreeSet();
                for (final Object c : comparedSet) {
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

    @Override
    /**
     * Returns true if all attributes from the AttributeDescription
     * of the current AttributeDescriptionSet
     * are included in the attribute set
     * of the AttributeDescriptionSet in parameter.
     * (Implementation of the set inclusion)
     *
     * @param set an AttributeDescriptionSet
     * @return true if the current attribute set is included in the specified one, else false
     */
    public boolean isIncludedIn(ComparableDescriptionSet<AttributeDescription> set) {
        return (set.first().getValue().containsAll(this.first().getValue()));
    }

    @Override
    /**
     * Returns true if the attribute set in parameter
     * is included in the attribute set (AttributeDescription)
     * of the current AttributeDescriptionSet.
     *
     * @param desc an AttributeDescription
     * @return true if the specified attributeDescription is included in the AttributeDescriptionSet,
     *             else false.
     */
    public boolean includes(AttributeDescription desc) {
        return (this.first().getValue().contains(desc.getValue()));
    }

    @Override
    /**
     * Returns an AttributeDescriptionSet
     * containing one AttributeDescription (i.e., a set of binary attributes)
     * corresponding to the set intersection of the two AttributeDescriptions
     * of the two AttributeDescriptionSets.
     *
     * @param set an AttributeDescriptionSet
     * @return set intersection of the two description sets.
     */
    public ComparableDescriptionSet<AttributeDescription> getSimilarity(
            ComparableDescriptionSet<AttributeDescription> set) {

        // buffer for the intersection
        TreeSet<String> intersection = new TreeSet<String>();

        // if an attribute is present in both sets
        for (String c : this.first().getValue()) {
            if (set.first().getValue().contains(c)) {
                // adds it in the intersection
                intersection.add(c);
            }
        }

        // creates a new AttributeDescription containing the intersection.
        TreeSet<AttributeDescription> ad = new TreeSet<AttributeDescription>();
        ad.add(new AttributeDescription(intersection));

        // returns an AttributeDescriptionSet containing the AttributeDescription representing the intersection
        return new AttributeDescriptionSet(ad);
    }

     @Override
     /**
     * Compares the two AttributeDescriptionSets.
     *
     * @return true if the two AttributeDescriptions
     *             contained in the two AttributeDescriptionSets
     *             have the same value.
     */
    public boolean equals(Object s) {
     return (this.first().getValue().equals(((AttributeDescriptionSet) s).first().getValue()));
    }

     @Override
    /**
     * Returns a clone of this component.
     *
     * @return a clone of this component.
     */
    public AttributeDescriptionSet clone() {
        return (AttributeDescriptionSet) super.clone();
    }

     @Override
     /**
      * Calls HashCode from super class.
      */
     public int hashCode() {
         return super.hashCode();
     }
}

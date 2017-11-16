package org.thegalactic.descriptionset;

import java.util.TreeSet;

import org.thegalactic.dgraph.Node;
import org.thegalactic.util.ComparableSet;

/**
 * This class represents concepts that have
 * a set of observations for extent
 * and a description set for intent.
 *
 * @author Jessie Carbonnel.
 *
 * @param <E> the type of the descriptions.
 */
public class DescriptionSetConcept<E extends Description> extends Node {

         /*
         * ------------- ATTRIBUTES ------------------
         */

    /**
     * Description set of the concept.
     * Represents the intent.
     */
    private ComparableDescriptionSet<E> intent = null;

    /**
     * Observation set of the concept.
     * Represents the extent.
     */
    private ComparableSet<String> extent = null;

        /*
         * ------------- CONSTRUCTORS ------------------
         */

    /**
     * Constructs a new DescriptionSetConcept with an extent being a ComparableSet
     * and an intent being a ComparableDescriptionSet.
     *
     * @param a the intent
     * @param b the extent
     */
    public DescriptionSetConcept(ComparableDescriptionSet<E> a, TreeSet<String> b) {
        intent = a;
        extent = new ComparableSet<String>();
        extent.addAll(b);
    }

    /**
     * Constructs a new DescriptionSetConcept
     * having the specified observation set as extent
     * and an empty intent.
     *
     * @param o a set of observations.
     * @param type the type of descriptions of the intent.
     */
    public DescriptionSetConcept(TreeSet<String> o, String type) {

        extent = new ComparableSet<String>();
        extent.addAll(o);

        this.intent = ComparableDescriptionSet.create(type);
    }

        /*
         * ------------ METHODS TO HANDLE EXTENT AND INTENT -------------
         */

    /**
     * Verifies if the concept has an intent.
     *
     * @return true if intent is different from null.
     */
    public boolean hasIntent() {
        return (intent == null);
    }

    /**
     * Verifies if the concept has an extent.
     *
     * @return true if extent is different from null.
     */
    public boolean hasExtent() {
        return (this.extent == null);
    }

    /**
     * Retrieves the concept's intent.
     * Returns a description set.
     *
     * @return the concept's intent.
     */
    public ComparableDescriptionSet<E> getIntent() {
        return this.intent;
    }

    /**
     * Retrieves the concept's extent.
     * Returns a comparable set of String representing observations.
     *
     * @return the concept's extent.
     */
    public ComparableSet<String> getExtent() {
        return this.extent;
    }

    /**
     * Returns true if the specified description is included in the intent, else false.
     * Warning: included =! contained for description sets.
     *
     * @param d the description
     * @return true if the specified description is included in the intent, else false.
     */
    public boolean isIncludedInIntent(E d) {
        return intent.includes(d);
    }

    /**
     * Returns true if the specified description is contained in the intent, else false.
     *
     * @param d the description
     * @return true if the specified description is contained in the intent, else false.
     */
    public boolean isContainedInIntent(E d) {
        return intent.contains(d);
    }

    /**
     * Returns true if the observation in parameter is included in the extent, else false.
     *
     * @param o the observation.
     * @return true if the specified observation is included in the extent, else false.
     */
    public boolean isIncludedInExtent(String o) {
        return extent.contains(o);
    }

    /**
     * Returns true if all the descriptions contained in the set in parameter
     * are included in the concept's intent. Else, false.
     *
     * @param set the description set
     * @return true if the description set is included in the intent.
     */
    public boolean allIncludedInIntent(ComparableDescriptionSet<E> set) {
        return set.isIncludedIn(intent);
    }

    /**
     * Returns true if all the specified observations
     * are included in the concept's extent. Else, false.
     *
     * @param set the observations set
     * @return true if the specified observation set is included in the extent.
     */
    public boolean allIncludedInExtent(ComparableSet<String> set) {
        return extent.containsAll(set);
    }

        /*
         * ------------------- INHERITED METHODS ---------------
         */

    @Override
    /**
     * Returns a String representing the concept's intent and extent.
     */
    public String toString() {
        return "(" + extent + "," + intent + ")";
    }

    @Override
    /**
     * Compare the equality of DescriptionSetConcepts
     * based on their extent.
     */
    public boolean equals(Object o) {
        return this.getExtent().equals(((DescriptionSetConcept<E>) o).getExtent());
    }

    @Override
    /**
     * Calls hashode from super class.
     */
    public int hashCode() {
        return super.hashCode();
    }
}

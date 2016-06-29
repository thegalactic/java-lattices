package org.thegalactic.lattice;

/*
 * Rule.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.Collection;
import java.util.SortedSet;

import org.thegalactic.util.ComparableSet;

/**
 * This class gives a representation for an implicational rule.
 *
 * A rule is composed of a premise and a conclusion that are comparable sets,
 * i.e. sets of elements that can be sorted by the lectic order defined by class
 * `ComparableSet`.
 *
 * This class implements class `Comparable` aiming at sorting rules by providing
 * the {@link #compareTo} method.
 *
 * Comparison between this component and those in parameter is realised by
 * sorting their premises (lecticaly order), or their conclusion in case of
 * equality of the premises.
 *
 * The coherence of the lectically sort between rules isn't ensured in case of
 * modification of the rule. Therefore, it is strongly advised to replace each
 * modified rule by a new one.
 *
 * ![Rule](Rule.png)
 *
 * @uml
 *
 * Rule.png
 *
 * !include resources/org/thegalactic/lattice/Rule.iuml
 *
 * hide members
 * show Rule members
 * class Rule #LightCyan
 * title Rule UML graph
 */
public class Rule implements Comparable<Rule> {

    /*
     * ------------- FIELDS ------------------
     */
    /**
     * The premise of the rule.
     */
    private final ComparableSet premise;

    /**
     * The conclusion of the rule.
     */
    private final ComparableSet conclusion;

    /* ------------- CONSTRUCTORS ------------------ */
    /**
     * Constructs a new empty Rule with a empty premise and an empty conclusion.
     */
    public Rule() {
        this.premise = new ComparableSet();
        this.conclusion = new ComparableSet();
    }

    /**
     * Constructs a new Rule with the premise and the conclusion given in
     * parameters.
     *
     * @param premise    a set of indexed elements
     * @param conclusion a set of indexed elements
     */
    public Rule(final SortedSet<Comparable> premise, final SortedSet<Comparable> conclusion) {
        this.premise = new ComparableSet(premise);
        this.conclusion = new ComparableSet(conclusion);
    }

    /*
     * ------------- ACCESSORS METHODS ------------------
     */
    /**
     * Returns a copy of the premise of the Rule.
     *
     * @return premises of this component
     */
    public final ComparableSet getPremise() {
        return this.premise;
    }

    /**
     * Returns a copy of the conclusion of the Rule.
     *
     * @return conclusions of this component
     */
    public final ComparableSet getConclusion() {
        return this.conclusion;
    }

    /*
     * ------------- MODIFICATION METHODS ------------------
     */
    /**
     * Adds the specified comparable to the premise of this component.
     *
     * @param a Comparable to add to this component's premises.
     *
     * @return true if addition is successfull.
     */
    public final boolean addToPremise(final Comparable a) {
        return this.premise.add(a);
    }

    /**
     * Removes the specified comparable from the premise of this component.
     *
     * @param a Comparable to remove to this component's premises.
     *
     * @return true if addition is successfull.
     */
    public final boolean removeFromPremise(final Comparable a) {
        return this.premise.remove(a);
    }

    /**
     * Adds the specified element to the conclusion of this component.
     *
     * @param a Comparable to add to this component's conclusions.
     *
     * @return true if addition is successfull.
     */
    public final boolean addToConclusion(final Comparable a) {
        return this.conclusion.add(a);
    }

    /**
     * Removes the specified comparable from the conclusion of this component.
     *
     * @param a Comparable to remove to this component's conclusions.
     *
     * @return true if addition is successfull.
     */
    public final boolean removeFromConclusion(final Object a) {
        return this.conclusion.remove(a);
    }

    /**
     * Adds the specified collection of indexed elements to the premise of this
     * component.
     *
     * @param a Collection of comparables to add to this component's premises.
     *
     * @return true if addition is successfull.
     */
    public final boolean addAllToPremise(final Collection<Comparable> a) {
        return this.premise.addAll(a);
    }

    /**
     * Removes the specified collection of indexed elements from the premise of
     * this component.
     *
     * @param a Collection of comparables to remove to this component's
     *          premises.
     *
     * @return true if addition is successfull.
     */
    public final boolean removeAllFromPremise(final Collection<Comparable> a) {
        return this.premise.removeAll(a);
    }

    /**
     * Adds the specified collection of indexed elements to the conclusion of
     * this component.
     *
     * @param a Collection of comparables to add to this component's
     *          conclusions.
     *
     * @return true if addition is successfull.
     */
    public final boolean addAllToConclusion(final Collection<Comparable> a) {
        return this.conclusion.addAll(a);
    }

    /**
     * Removes the specified collection of indexed elements from the conclusion
     * of this component.
     *
     * @param a Collection of comparables to remove to this component's
     *          conclusions.
     *
     * @return true if addition is successfull.
     */
    public final boolean removeAllFromConclusion(final Collection<Comparable> a) {
        return this.conclusion.removeAll(a);
    }

    /*
     * ------------- OVERRIDDEN METHODS ------------------
     */
    /**
     * Returns a String representation of this component.
     *
     * The following format is used:
     *
     * ~~~
     * [elements of the premise separated by a space] -> [elements of the conclusion separated by a space]
     * ~~~
     *
     * a StringTokenizer is used to delete spaces in the string description of
     * each element of premise and conclusion
     *
     * @return a string made of premises followed by -> and the conclusions.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Object e : this.getPremise()) {
            builder.append(e).append(' ');
        }
        builder.append("->");
        for (Object e : this.getConclusion()) {
            builder.append(' ').append(e);
        }
        return builder.toString();
    }

    /**
     * Returns the hash code of this component.
     *
     * @return hash code of this component
     */
    @Override
    public int hashCode() {
        return 1013 * this.premise.hashCode() ^ 1009 * this.conclusion.hashCode();
    }

    /**
     * Compares this component with the specified one.
     *
     * @param o object to compare to this component.
     *
     * @return true or false as this componant is equals to the specified
     *         object.
     */
    @Override
    public boolean equals(Object object) {
        return this == object || object != null && this.getClass() == object.getClass()
                && this.getPremise().equals(((Rule) object).getPremise())
                && this.getConclusion().equals(((Rule) object).getConclusion());
    }

    /**
     * Compares this component with the specified one by comparing their
     * premises, or their conclusion in case of equality of the premises.
     *
     * @param rule object to compare to this component.
     *
     * @return a negative integer, zero, or a positive integer as this component
     *         is less than, equal to, or greater than the specified object.
     */
    public final int compareTo(final Rule rule) {
        final ComparableSet thisPremise = (ComparableSet) this.getPremise();
        final ComparableSet thisConclusion = (ComparableSet) this.getConclusion();
        int cmp = thisPremise.compareTo(rule.getPremise());
        if (cmp == 0) {
            cmp = thisConclusion.compareTo(rule.getConclusion());
        }
        return cmp;
    }
}

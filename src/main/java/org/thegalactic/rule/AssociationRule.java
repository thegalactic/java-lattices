package org.thegalactic.rule;

/*
 * AssociationRule.java
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

/**
 * This class gives a representation for an association rule.
 *
 * A rule is composed of a premise and a conclusion that are comparable sets,
 * i.e. sets of elements that can be sorted by the lectic order defined by class
 * `ComparableSet`. An association rule extends regular rule with the notion of
 * support and confidence. The confidence represents the number of observations
 * for which the rule is true. The support represents the minimum number of
 * observations for the rule to be relevant.
 *
 * @todo do we use composition over inheritance?
 */
public class AssociationRule extends Rule {

    /* ------------- FIELDS ------------------ */
    /**
     * The confidence of the rule.
     */
    private double confidence;

    /**
     * The support of the rule.
     */
    private double support;

    /* ------------- CONSTRUCTORS ------------------ */
    /**
     * Constructs a new empty Rule with a empty premise and an empty conclusion.
     */
    public AssociationRule() {
        super();
    }

    /**
     * Constructs a new Rule with the premise and the conclusion given in
     * parameters.
     *
     * @param premise    a set of indexed elements
     * @param conclusion a set of indexed elements
     */
    public AssociationRule(final SortedSet<Comparable> premise, final SortedSet<Comparable> conclusion) {
        super(premise, conclusion);
    }

    /**
     * Constructs a new Rule with the premise, the conclusion, the confidence
     * and the support given in parameters.
     *
     * @param premise    a set of indexed elements
     * @param conclusion a set of indexed elements
     * @param support    a support value
     * @param confidence a confidence value
     */
    public AssociationRule(
            final SortedSet<Comparable> premise,
            final SortedSet<Comparable> conclusion,
            final double support,
            final double confidence
    ) {
        super(premise, conclusion);
        this.support = support;
        this.confidence = confidence;
    }

    /* ------------- ACCESSORS METHODS ------------------ */
    /**
     * Returns the confidence value of the rule.
     *
     * @return confidence value
     */
    public final double getConfidence() {
        return this.confidence;
    }

    /**
     * Returns the support value of the rule.
     *
     * @return support value
     */
    public final double getSupport() {
        return this.support;
    }

    /* ------------- MODIFICATION METHODS ------------------ */
    /**
     * Set the confidence value of the rule.
     *
     * @param confidence the confidence value
     */
    public final void setConfidence(final double confidence) {
        this.confidence = confidence;
    }

    /**
     * Set the support value of the rule.
     *
     * @param support the support value
     */
    public final void setSupport(final double support) {
        this.support = support;
    }

    /* ------------- OVERRIDEN METHODS ------------------ */
    /**
     * Returns a String representation of this component.
     *
     * The following format is used:
     *
     * ~~~
     * [premise] -> [conclusion] : s:support/c:confidence
     * ~~~
     *
     * @return a string made of premises followed by -> and the conclusions.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString()).append(" : s:").append(this.support).append("/c:").append(this.confidence);
        return builder.toString();
    }
}

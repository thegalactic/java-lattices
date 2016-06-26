package org.thegalactic.lattice;

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
import java.util.TreeSet;

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
    public AssociationRule(TreeSet<Comparable> premise, TreeSet<Comparable> conclusion) {
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
    public AssociationRule(TreeSet<Comparable> premise, TreeSet<Comparable> conclusion, double support, double confidence) {
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
    public double getConfidence() {
        return confidence;
    }

    /**
     * Returns the support value of the rule.
     *
     * @return support value
     */
    public double getSupport() {
        return support;
    }

    /* ------------- MODIFICATION METHODS ------------------ */
    /**
     * Set the confidence value of the rule.
     *
     * @param confidence the confidence value
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * Set the support value of the rule.
     *
     * @param support the support value
     */
    public void setSupport(double support) {
        this.support = support;
    }

    /* ------------- OVERLAPING METHODS ------------------ */
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
        StringBuilder builder = new StringBuilder();
        for (Object element : this.getPremise()) {
            builder.append(element).append(' ');
        }
        builder.append("->");
        for (Object element : this.getConclusion()) {
            builder.append(' ').append(element);
        }
        builder.append(" : ").append("s:").append(this.support).append("/c:").append(this.confidence);
        return builder.toString();
    }
}

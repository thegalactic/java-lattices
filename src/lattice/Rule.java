package lattice;

/*
 * Rule.java
 *
 * Copyright: 2013 University of La Rochelle, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2014
 */

import java.util.Collection;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * This class gives a representation for an implicational rule.
 *
 * A rule is composed of a premise and a conclusion that are comparable sets,
 * i.e. sets of elements that can be sorted by the lectic order defined
 * by class `ComparableSet`.
 *
 * This class implements class `Comparable` aiming at
 * sorting rules by providing the {@link #compareTo} method.
 *
 * Comparison between this component and those in parameter is realised by sorting their premises (lecticaly order),
 * or their conclusion in case of equality of the premises.
 *
 * The coherence of the lectically sort between rules isn't ensured in case of modification
 * of the rule. Therefore, it is strongly advised to replace each modified rule by a new one.
 *
 * ![Rule](Rule.png)
 *
 * @uml Rule.png
 * !include src/lattice/Rule.iuml
 *
 * hide members
 * show Rule members
 * class Rule #LightCyan
 * title Rule UML graph
 *
 * @todo  Do we use yaml representation?
 */
public class Rule implements Comparable {
    /* ------------- FIELDS ------------------ */

    /**
     * The premise of the rule.
     */
    private ComparableSet premise;

    /**
     * The conclusion of the rule.
     */
    private ComparableSet conclusion;

    /* ------------- CONSTRUCTORS ------------------ */

    /**
     * Constructs a new empty Rule with a empty premise and an empty conclusion.
     */
    public Rule() {
        premise = new ComparableSet();
        conclusion = new ComparableSet();
    }

    /**
     * Constructs a new Rule with the premise and the conclusion given in parameters.
     *
     * @param   premise     a set of indexed elements
     * @param   conclusion  a set of indexed elements
     */
    public Rule(TreeSet<Comparable> premise, TreeSet<Comparable> conclusion) {
        this.premise = new ComparableSet(premise);
        this.conclusion = new ComparableSet(conclusion);
    }

    /* ------------- ACCESSORS METHODS ------------------ */

    /**
     * Returns a copy of the premise of the Rule.
     *
     * @return  premises of this component
     */
    public TreeSet<Comparable> getPremise() {
        return this.premise;
    }

    /**
     * Returns a copy of the conclusion of the Rule.
     *
     * @return  conclusions of this component
     */
    public TreeSet<Comparable> getConclusion() {
        return this.conclusion;
    }

    /* ------------- MODIFICATION METHODS ------------------ */

    /**
     * Adds the specified comparable to the premise of this component.
     *
     * @param   a  Comparable to add to this component's premises.
     *
     * @return  true if addition is successfull.
     */
    public boolean addToPremise(Comparable a) {
        return this.premise.add(a);
    }

    /**
     * Removes the specified comparable from the premise of this component.
     *
     * @param   a  Comparable to remove to this component's premises.
     *
     * @return  true if addition is successfull.
     */
    public boolean removeFromPremise(Comparable a) {
        return this.premise.remove(a);
    }

    /**
     * Adds the specified element to the conclusion of this component.
     *
     * @param   a  Comparable to add to this component's conclusions.
     *
     * @return  true if addition is successfull.
     */
    public boolean addToConclusion(Comparable a) {
        return this.conclusion.add(a);
    }

    /**
     * Removes the specified comparable from the conclusion of this component.
     *
     * @param   a  Comparable to remove to this component's conclusions.
     *
     * @return  true if addition is successfull.
     */
    public boolean removeFromConclusion(Object a) {
        return this.conclusion.remove(a);
    }

    /**
     * Adds the specified collection of indexed elements to the premise of this component.
     *
     * @param   a  Collection of comparables to add to this component's premises.
     *
     * @return  true if addition is successfull.
     */
    public boolean addAllToPremise(Collection<Comparable> a) {
        return this.premise.addAll(a);
    }

    /**
     * Removes the specified collection of indexed elements from the premise of this component.
     *
     * @param   a  Collection of comparables to remove to this component's premises.
     *
     * @return  true if addition is successfull.
     */
    public boolean removeAllFromPremise(Collection<Comparable> a) {
        return premise.removeAll(a);
    }

    /**
     * Adds the specified collection of indexed elements to the conclusion of this component.
     *
     * @param   a  Collection of comparables to add to this component's conclusions.
     *
     * @return  true if addition is successfull.
     */
    public boolean addAllToConclusion(Collection<Comparable> a) {
        return conclusion.addAll(a);
    }

    /**
     * Removes the specified collection of indexed elements from the conclusion of this component.
     *
     * @param   a  Collection of comparables to remove to this component's conclusions.
     *
     * @return  true if addition is successfull.
     */
    public boolean removeAllFromConclusion(Collection<Comparable> a) {
        return conclusion.removeAll(a);
    }

    /* ------------- OVERLAPING METHODS ------------------ */

    /**
     * Returns a String representation of this component.
     *
     * The following format is used:
     *
     * ~~~
     * [elements of the premise separated by a space] -> [elements of the conclusion separated by a space]
     * ~~~
     *
     * a StringTokenizer is used to delete spaces in the
     * string description of each element of premise and conclusion
     *
     * @return  a string made of premises followed by -> and the conclusions.
     */
    public String toString() {
        String s = "";
        for (Object e : this.getPremise()) {
            StringTokenizer st = new StringTokenizer(e.toString());
            while (st.hasMoreTokens()) {
                s += st.nextToken();
            }
            s += " ";
            }
        s += " -> ";
        for (Object e : this.getConclusion()) {
            StringTokenizer st = new StringTokenizer(e.toString());
            while (st.hasMoreTokens()) {
                s += st.nextToken();
            }
            s += " ";
            }
        return s;
    }

    /**
     * Returns the hash code of this component.
     *
     * @return  hash code of this component
     */
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Compares this component with the specified one.
     *
     * @param   o  object to compare to this component.
     *
     * @return  true or false as this componant is equals to the specified object.
     */
    public boolean equals(Object o) {
        if (!(o instanceof Rule)) {
            return false;
        }
        Rule r = (Rule) o;
        return (this.getPremise().equals(r.getPremise()) && this.getConclusion().equals(r.getConclusion()));
    }

    /**
     * Compares this component with the specified one by comparing their premises,
     * or their conclusion in case of equality of the premises.
     *
     * @param   o  object to compare to this component.
     *
     * @return  a negative integer, zero, or a positive integer as this component is less than,
     * equal to, or greater than the specified object.
     */
    public int compareTo(Object o) {
        Rule r = (Rule) o;
        ComparableSet thisPremise = (ComparableSet) this.getPremise();
        ComparableSet thisConclusion = (ComparableSet) this.getConclusion();
        if (thisPremise.compareTo(r.getPremise()) == 0) {
            return (thisConclusion.compareTo(r.getConclusion()));
        } else {
            return (thisPremise.compareTo(r.getPremise()));
        }
    }
}

package org.thegalactic.context.attribute.binary;

/*
 * BinaryConstraint.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.thegalactic.context.attribute.AbstractConstraint;
import org.thegalactic.context.attribute.Constraint;

/**
 * BinaryConstraint implements boolean constraints.
 */
public final class BinaryConstraint extends AbstractConstraint {

    /**
     * The set of successors for the domain.
     */
    private static final HashMap<Constraint, Set<Constraint>> SUCCESSORS = new HashMap<Constraint, Set<Constraint>>();

    /**
     * Set of domains for boolean attributes.
     */
    private static final HashMap<BinaryAttribute, BinaryConstraint> DOMAINS = new HashMap<BinaryAttribute, BinaryConstraint>();

    /**
     * Get the domain associated with a boolean attribute.
     *
     * @param attribute the attribute linked to this constraint.
     *
     * @return The domain
     */
    public static BinaryConstraint getDomain(BinaryAttribute attribute) {
        BinaryConstraint domain = BinaryConstraint.DOMAINS.get(attribute);
        if (domain == null) {
            domain = new BinaryConstraint(attribute, false);
            BinaryConstraint successor = new BinaryConstraint(attribute, true);
            BinaryConstraint.DOMAINS.put(attribute, domain);
            BinaryConstraint.SUCCESSORS.put(domain, Collections.singleton((Constraint) successor));
            BinaryConstraint.SUCCESSORS.put(successor, Collections.EMPTY_SET);
        }
        return domain;
    }

    /**
     * Is this constraint set?.
     */
    private final boolean isSet;

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param attribute the attribute linked to this constraint.
     * @param isSet     is this boolean constraint set?
     */
    private BinaryConstraint(BinaryAttribute attribute, boolean isSet) {
        super(attribute);
        this.isSet = isSet;
    }

    /**
     * Get the successors of a constraint.
     *
     * @return the successors of this.
     */
    public Set<Constraint> getSuccessors() {
        return BinaryConstraint.SUCCESSORS.get(this);
    }

    /**
     * Return the fact that this constraint is set.
     *
     * @return true if this constraint is set.
     */
    public boolean isSet() {
        return this.isSet;
    }

    /**
     * Return the fact that this constraint is unset.
     *
     * @return true if this constraint is unset.
     */
    public boolean isUnset() {
        return !this.isSet;
    }

    /**
     * String conversion.
     *
     * @return the string representation of this.
     */
    @Override
    public String toString() {
        if (this.isSet) {
            return this.getAttribute().toString() + ":set";
        } else {
            return this.getAttribute().toString() + ":unset";
        }
    }
}

package fr.kbertet.context.attribute;

/*
 * BooleanConstraint.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;


/**
 * BooleanConstraint implements boolean constraints.
 */
public final class BooleanConstraint extends AbstractConstraint {

    /**
     * This class is not designed to be publicly instantiated.
     *
     * @param attribute the attribute linked to this constraint.
     * @param isSet     is this boolean constraint set?
     */
    private BooleanConstraint(BooleanAttribute attribute, boolean isSet) {
        super(attribute);
        this.isSet = isSet;
    }

    /**
     * Get the domain associated with a boolean attribute.
     *
     * @param attribute the attribute linked to this constraint.
     *
     * @return The domain
     */
    public static BooleanConstraint getDomain(BooleanAttribute attribute) {
        BooleanConstraint domain = BooleanConstraint.DOMAINS.get(attribute);
        if (domain == null) {
            domain = new BooleanConstraint(attribute, false);
            BooleanConstraint successor = new BooleanConstraint(attribute, true);
            BooleanConstraint.DOMAINS.put(attribute, domain);
            BooleanConstraint.SUCCESSORS.put(domain, Collections.singleton((Constraint) successor));
            BooleanConstraint.SUCCESSORS.put(successor, Collections.EMPTY_SET);
        }
        return domain;
    }

    /**
     * Get the successors of a constraint.
     *
     * @return the successors of this.
     */
    public Set<Constraint> getSuccessors() {
        return BooleanConstraint.SUCCESSORS.get(this);
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

    /**
     * Is this constraint set?.
     */
    private final boolean isSet;

    /**
     * The set of successors for the domain.
     */
    private static final HashMap<Constraint, Set<Constraint>> SUCCESSORS;

    /**
     * Set of domains for boolean attributes.
     */
    private static final HashMap<BooleanAttribute, BooleanConstraint> DOMAINS;

    static {
        DOMAINS = new HashMap<BooleanAttribute, BooleanConstraint>();
        SUCCESSORS = new HashMap<Constraint, Set<Constraint>>();
    }
}

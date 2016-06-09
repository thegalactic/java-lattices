package org.thegalactic.context.memory;

/*
 * InMemoryContext.java
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
import java.util.LinkedHashSet;
import java.util.Set;

import org.thegalactic.context.AbstractContext;
import org.thegalactic.context.attribute.Attribute;
import org.thegalactic.context.attribute.AttributeFactory;
import org.thegalactic.context.attribute.Constraint;
import org.thegalactic.context.observation.Observation;
import org.thegalactic.context.value.Value;

/**
 * The class InMemoryContext implements contexts in memory.
 */
public class InMemoryContext extends AbstractContext {

    /**
     * The set of attributes.
     */
    private final LinkedHashSet<Attribute> attributes = new LinkedHashSet<Attribute>();

    /**
     * The set of observations.
     */
    private final LinkedHashSet<Observation> observations = new LinkedHashSet<Observation>();

    /**
     * The storages.
     */
    private final HashMap<Attribute, Storage> storages = new HashMap<Attribute, Storage>();

    /**
     * Get the set of attributes.
     *
     * @return the attributes
     */
    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(this.attributes);
    }

    /**
     * Get the set of observations.
     *
     * @return the set of observations
     */
    public Set<Observation> getObservations() {
        return Collections.unmodifiableSet(this.observations);
    }

    /**
     * Get the value associated with an observation and an attribute.
     *
     * @param observation The observation
     * @param attribute   The attribute
     *
     * @return The value associated with an observation and an attribute
     */
    public Value getValue(Observation observation, Attribute attribute) {
        return this.storages.get(attribute).getValue(observation, attribute);
    }

    /**
     * Get an attribute given its name.
     *
     * @param name the attribute name
     *
     * @return The attribute
     */
    public Attribute getAttribute(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get the set of closed constraints for this context.
     *
     * @return The set of closed constraints for this context.
     */
    public Set<Constraint> getDomain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Add an attribute to this in-memory context.
     *
     * @param type the attribute type identifer
     * @param name the attribute name
     *
     * @return the attribute
     */
    public Attribute addAttribute(String type, String name) {
        Attribute attribute = AttributeFactory.getInstance().create(type, name, this);
        attributes.add(attribute);
        return attribute;
    }
}

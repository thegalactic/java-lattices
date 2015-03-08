package org.thegalactic.context.memory;

/*
 * InMemoryContext.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.thegalactic.context.AbstractContext;
import org.thegalactic.context.observation.Observation;
import org.thegalactic.context.value.Value;
import org.thegalactic.context.attribute.Attribute;

/**
 * The class InMemoryContext implements contexts in memory.
 */
public class InMemoryContext extends AbstractContext {

    /**
     * The set of attributes.
     */
    private final LinkedHashSet<Attribute> attributes;

    /**
     * The set of observations.
     */
    private final LinkedHashSet<Observation> observations;

    /**
     * The storages.
     */
    private final HashMap<Attribute, Storage> storages;

    /**
     * Initialization of fields.
     */
    {
        attributes = new LinkedHashSet<Attribute>();
        observations = new LinkedHashSet<Observation>();
        storages = new HashMap<Attribute, Storage>();
    }

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
}

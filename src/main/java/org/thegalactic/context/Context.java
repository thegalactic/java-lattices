package org.thegalactic.context;

/*
 * Context.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.value.Value;
import org.thegalactic.context.observation.Observation;

import java.util.Set;

import org.thegalactic.context.attribute.Attribute;

/**
 * The interface Context deals with contexts.
 */
public interface Context {

    /**
     * Get the attribute given its name.
     *
     * @param name the attribute name
     *
     * @return the attribute
     */
    Attribute getAttribute(String name);

    /**
     * Get the set of attributes.
     *
     * @return the set of attributes
     */
    Set<Attribute> getAttributes();

    /**
     * Get the set of observations.
     *
     * @return the set of observations
     */
    Set<Observation> getObservations();

    /**
     * Get the size of the set of attributes.
     *
     * @return the size of the set of attributes
     */
    int sizeAttributes();

    /**
     * Get the size of the set of observations.
     *
     * @return the size of the set of observations
     */
    int sizeObservations();

    /**
     * Get the value associated with an observation and an attribute.
     *
     * @param observation the observation
     * @param attribute   the attribute
     *
     * @return The value associated with an observation and an attribute
     */
    Value getValue(Observation observation, Attribute attribute);
}

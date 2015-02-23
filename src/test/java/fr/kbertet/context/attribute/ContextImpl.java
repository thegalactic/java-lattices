package fr.kbertet.context.attribute;

/*
 * ContextImpl.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.Set;
import java.util.Collections;

import fr.kbertet.context.AbstractContext;
import fr.kbertet.context.Observation;
import fr.kbertet.context.Value;

/**
 * Basic implementation of AbstractContext.
 */
class ContextImpl extends AbstractContext {

    /**
     * Get the set of attributes.
     *
     * @return the set of attributes
     */
    public Set<Attribute> getAttributes() {
            return Collections.EMPTY_SET;
    }

    /**
     * Get the set of observations.
     *
     * @return the set of observations
     */
    public Set<Observation> getObservations() {
            return Collections.EMPTY_SET;
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
            return new ValueImpl();
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

package org.thegalactic.context.attribute;

/*
 * Attribute.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.Context;
import org.thegalactic.context.Observation;
import org.thegalactic.context.Value;

/**
 * An attribute for a context.
 */
public interface Attribute {

    /**
     * Get the context.
     *
     * @return The context
     */
    Context getContext();

    /**
     * Get the domain associated with an attribute.
     *
     * @return The domain
     */
    Constraint getDomain();

    /**
     * Get the name of an attribute.
     *
     * @return The name
     */
    String getName();

    /**
     * Get the type of an attribute.
     *
     * @return The type
     */
    String getType();

    /**
     * Get the value associated with an observation.
     *
     * @param observation The observation
     *
     * @return The value associated with an observation
     */
    Value getValue(Observation observation);
}

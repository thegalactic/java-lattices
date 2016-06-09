package org.thegalactic.context.memory;

/*
 * Storage.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.context.attribute.Attribute;
import org.thegalactic.context.observation.Observation;
import org.thegalactic.context.value.Value;

/**
 * The interface Storage implements generic storage.
 */
public interface Storage {

    /**
     * Get the value associated with an attribute.
     *
     * @param observation The observation to look for
     * @param attribute   The attribute to look for
     *
     * @return The value
     */
    Value getValue(Observation observation, Attribute attribute);
}

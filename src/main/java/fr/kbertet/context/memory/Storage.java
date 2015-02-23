package fr.kbertet.context.memory;

/*
 * Storage.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.context.attribute.Attribute;
import fr.kbertet.context.Observation;
import fr.kbertet.context.Value;

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

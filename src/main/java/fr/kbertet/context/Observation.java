package fr.kbertet.context;

import fr.kbertet.context.attribute.Attribute;

/*
 * Observation.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

/**
 * An observation for a context.
 */
public interface Observation {
    /**
     * Get the context.
     *
     * @return The context
     */
    Context getContext();

    /**
     * Get the value associated with an attribute.
     *
     * @param attribute The attribute
     *
     * @return The value associated with an attribute
     */
    Value getValue(Attribute attribute);
}

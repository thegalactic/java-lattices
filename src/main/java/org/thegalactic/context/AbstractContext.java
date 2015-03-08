package org.thegalactic.context;

/*
 * AbstractContext.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * The class AbstractContext implements generic methods on Context.
 */
public abstract class AbstractContext implements Context {

    /**
     * Get the size of the set of attributes.
     *
     * This is a proxy to Context#getAttributes().size
     *
     * @return the size of the set of attributes
     */
    public int sizeAttributes() {
        return this.getAttributes().size();
    }

    /**
     * Get the size of the set of observations.
     *
     * This is a proxy to Context#getObservations().size
     *
     * @return the size of the set of observations
     */
    public int sizeObservations() {
        return this.getObservations().size();
    }
}

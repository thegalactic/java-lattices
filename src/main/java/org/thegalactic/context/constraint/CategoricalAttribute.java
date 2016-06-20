package org.thegalactic.context.constraint;

/*
 * CategoricalAttribute.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.ArrayList;

/**
 * Categorical Attribute.
 */
public final class CategoricalAttribute {

    /**
     * Create a CategoricalAttribute.
     *
     * @return new CategoricalAttribute
     */
    public static CategoricalAttribute create() {
        return new CategoricalAttribute();
    }

    /**
     * Values.
     */
    private final ArrayList<CategoricalValue> values;

    /**
     * This class is not designed to be publicly instantiated.
     */
    private CategoricalAttribute() {
        values = new ArrayList();
    }

    /**
     * Add a value to a CategoricalAttribute.
     *
     * @param value value to be added
     *
     * @return this for chaining
     */
    public CategoricalAttribute add(CategoricalValue value) {
        values.add(value);
        return this;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return values.toString();
    }
}

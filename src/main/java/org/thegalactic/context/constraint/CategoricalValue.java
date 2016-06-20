package org.thegalactic.context.constraint;

/*
 * CategoricalValue.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * Categorical Value.
 */
public final class CategoricalValue {
    /**
     * Create a new CategoricalValue.
     *
     * @param object Object
     *
     * @return a new CategoricalValue
     */
    public static CategoricalValue create(Object object) {
        return new CategoricalValue(object);
    }

    /**
     * object.
     */
    private final Object object;

    /**
     * This class is nod designed to be publicly instantiated.
     *
     * @param object Object
     */
    private CategoricalValue(Object object) {
        this.object = object;
    }


    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String string = object.toString();
        if (string.contains(" ") || string.contains("\"")) {
            return "\"" + string.replace("\"", "\\\"") + "\"";
        } else {
            return string;
        }
    }
}

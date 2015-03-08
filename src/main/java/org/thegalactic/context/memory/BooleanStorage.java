package org.thegalactic.context.memory;

/*
 * BooleanStorage.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.BitSet;
import java.util.HashMap;

import org.thegalactic.context.Observation;
import org.thegalactic.context.Value;
import org.thegalactic.context.attribute.Attribute;

/**
 * A boolean attribute for a context.
 */
public class BooleanStorage implements Storage {

    /**
     * instances of this class.
     */
    private static final HashMap<InMemoryContext, BooleanStorage> INSTANCES = new HashMap<InMemoryContext, BooleanStorage>();

    /**
     * BooleanStorage factory.
     *
     * @param context A context
     *
     * @return An instance of boolean storage for the context
     */
    public static BooleanStorage getInstance(InMemoryContext context) {
        BooleanStorage storage = INSTANCES.get(context);
        if (storage == null) {
            storage = new BooleanStorage();
            INSTANCES.put(context, storage);
        }
        return storage;
    }

    /**
     * The bit sets.
     */
    private HashMap<Observation, BitSet> bitsets;

    /**
     * Get the value between an observation and an attribute.
     *
     * @param observation The observation to look for
     * @param attribute   The attribute to look for
     *
     * @return The value
     */
    public Value getValue(Observation observation, Attribute attribute) {
        return new BooleanValue();
    }

    /**
     * Boolean Value.
     */
    private class BooleanValue implements Value {

        /**
         * Basic constructor.
         */
        public BooleanValue() {
        }

        /**
         * String conversion.
         *
         * @return The string representation
         */
        public String toString() {
            return "test";
        }
    }
}

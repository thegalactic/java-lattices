package org.thegalactic.context.memory;

/*
 * BinaryStorage.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.BitSet;
import java.util.HashMap;

import org.thegalactic.context.observation.Observation;
import org.thegalactic.context.value.Value;
import org.thegalactic.context.attribute.Attribute;

/**
 * A boolean attribute for a context.
 */
public class BinaryStorage implements Storage {

    /**
     * instances of this class.
     */
    private static final HashMap<InMemoryContext, BinaryStorage> INSTANCES = new HashMap<InMemoryContext, BinaryStorage>();

    /**
     * BinaryStorage factory.
     *
     * @param context A context
     *
     * @return An instance of boolean storage for the context
     */
    public static BinaryStorage getInstance(InMemoryContext context) {
        BinaryStorage storage = INSTANCES.get(context);
        if (storage == null) {
            storage = new BinaryStorage();
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
        return new BoolValue();
    }

    /**
     * Boolean Value.
     */
    private class BoolValue implements Value {

        /**
         * Basic constructor.
         */
        public BoolValue() {
            /**
             * Get the set of closed constraints for this context.
             *
             * @return The set of closed constraints for this context.
             */

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

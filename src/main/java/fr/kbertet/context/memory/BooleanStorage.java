package fr.kbertet.context.memory;

/*
 * BooleanStorage.java
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
import java.util.BitSet;
import java.util.HashMap;

/**
 * A boolean attribute for a context.
 */
public class BooleanStorage implements Storage {

    /**
     * instances of this class.
     */
    private static HashMap<InMemoryContext, BooleanStorage> instances = new HashMap<InMemoryContext, BooleanStorage>();

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
     * BooleanStorage factory.
     *
     * @param context A context
     *
     * @return An instance of boolean storage for the context
     */
    public static BooleanStorage getInstance(InMemoryContext context) {
        BooleanStorage storage = instances.get(context);
        if (storage == null) {
            storage = new BooleanStorage();
            instances.put(context, storage);
        }
        return storage;
    }
}

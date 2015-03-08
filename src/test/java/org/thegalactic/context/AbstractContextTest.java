package org.thegalactic.context;

/*
 * AbstractContextTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.Collections;

import org.thegalactic.context.attribute.Attribute;

/**
 * Test AbstractContext class.
 */
public class AbstractContextTest {

    /**
     * Constructor.
     */
    public AbstractContextTest() {
    }

    /**
     * Run before class.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Run after class.
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Run before tests.
     */
    @Before
    public void setUp() {
    }

    /**
     * Run after tests.
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of sizeAttributes method, of class AbstractContext.
     */
    @Test
    public void testSizeAttributes() {
        AbstractContext instance = new AbstractContextImpl();
        int expResult = 0;
        int result = instance.sizeAttributes();
        assertEquals(expResult, result);
    }

    /**
     * Test of sizeObservations method, of class AbstractContext.
     */
    @Test
    public void testSizeObservations() {
        AbstractContext instance = new AbstractContextImpl();
        int expResult = 0;
        int result = instance.sizeObservations();
        assertEquals(expResult, result);
    }

    /**
     * Basic implementation of AbstractContext.
     */
    public class AbstractContextImpl extends AbstractContext {

        /**
         * Get an attribute given its name.
         *
         * @param name the attribute name
         *
         * @return the attribute
         */
        public Attribute getAttribute(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Get the set of attributes.
         *
         * @return the set of attributes
         */
        public Set<Attribute> getAttributes() {
            return Collections.EMPTY_SET;
        }

        /**
         * Get the set of observations.
         *
         * @return the set of observations
         */
        public Set<Observation> getObservations() {
            return Collections.EMPTY_SET;
        }

        /**
         * Get the value associated with an observation and an attribute.
         *
         * @param observation the observation
         * @param attribute   the attribute
         *
         * @return The value associated with an observation and an attribute
         */
        public Value getValue(Observation observation, Attribute attribute) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

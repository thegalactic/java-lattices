package org.thegalactic.context;

/*
 * AbstractContextTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test AbstractContext class.
 */
public class AbstractContextTest {

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
     * Constructor.
     */
    public AbstractContextTest() {
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
        AbstractContext instance = new ContextImpl();
        int expResult = 0;
        int result = instance.sizeAttributes();
        assertEquals(expResult, result);
    }

    /**
     * Test of sizeObservations method, of class AbstractContext.
     */
    @Test
    public void testSizeObservations() {
        AbstractContext instance = new ContextImpl();
        int expResult = 0;
        int result = instance.sizeObservations();
        assertEquals(expResult, result);
    }
}

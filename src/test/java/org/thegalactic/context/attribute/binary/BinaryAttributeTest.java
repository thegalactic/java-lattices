package org.thegalactic.context.attribute.binary;

/*
 * BinaryAttributeTest.java
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
import static org.junit.Assert.assertTrue;

import org.thegalactic.context.attribute.Constraint;

/**
 * Test BinaryAttribute class.
 */
public class BinaryAttributeTest {

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
    public BinaryAttributeTest() {
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
     * Test of getType method, of class BinaryAttribute.
     */
    @Test
    public void testGetType() {
        BinaryAttribute instance = new BinaryAttribute("", null);
        String expResult = "bool";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDomain method, of class BinaryAttribute.
     */
    @Test
    public void testGetDomain() {
        BinaryAttribute instance = new BinaryAttribute("", null);
        Constraint result = instance.getDomain();
        assertTrue(result instanceof Constraint);
    }
}

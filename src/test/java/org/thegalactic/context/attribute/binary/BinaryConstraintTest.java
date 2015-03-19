package org.thegalactic.context.attribute.binary;

/*
 * BinaryConstraintTest.java
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

import java.util.Set;

import org.thegalactic.context.attribute.Constraint;

/**
 * Test BinaryConstraint class.
 */
public class BinaryConstraintTest {

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
    public BinaryConstraintTest() {
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
     * Test of getDomain method, of class BinaryConstraint.
     */
    @Test
    public void testGetDomain() {
        BinaryAttribute attribute = new BinaryAttribute("", null);
        Constraint expResult = attribute.getDomain();
        Constraint result = attribute.getDomain();
        assertTrue(result instanceof BinaryConstraint);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuccessors method, of class BinaryConstraint.
     */
    @Test
    public void testGetSuccessors() {
        BinaryAttribute attribute = new BinaryAttribute("", null);
        BinaryConstraint instance = (BinaryConstraint) attribute.getDomain();
        Set<Constraint> successors = instance.getSuccessors();

        int expResult = 1;
        int result = successors.size();
        assertEquals(expResult, result);

        for (Constraint successor : successors) {
            assertTrue(successor instanceof BinaryConstraint);
        }
    }

    /**
     * Test of isSet method, of class BinaryConstraint.
     */
    @Test
    public void testIsSet() {
        BinaryAttribute attribute = new BinaryAttribute("", null);
        BinaryConstraint instance = (BinaryConstraint) attribute.getDomain();

        Boolean expResult = false;
        Boolean result = instance.isSet();
        assertEquals(expResult, result);
    }

    /**
     * Test of isUnset method, of class BinaryConstraint.
     */
    @Test
    public void testIsUnset() {
        BinaryAttribute attribute = new BinaryAttribute("", null);
        BinaryConstraint instance = (BinaryConstraint) attribute.getDomain();

        Boolean expResult = true;
        Boolean result = instance.isUnset();
        assertEquals(expResult, result);

        expResult = false;
        for (Constraint successor : instance.getSuccessors()) {
            result = ((BinaryConstraint) successor).isUnset();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of toString method, of class BinaryConstraint.
     */
    @Test
    public void testToString() {
        BinaryAttribute attribute = new BinaryAttribute("AttributeName", null);
        BinaryConstraint instance = (BinaryConstraint) attribute.getDomain();

        String expResult = "AttributeName(bool):unset";
        String result = instance.toString();
        assertEquals(expResult, result);

        expResult = "AttributeName(bool):set";
        for (Constraint successor : instance.getSuccessors()) {
            result = successor.toString();
            assertEquals(expResult, result);
        }
    }
}

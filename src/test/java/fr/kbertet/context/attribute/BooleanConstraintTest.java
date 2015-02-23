package fr.kbertet.context.attribute;

/*
 * BooleanConstraintTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

/**
 * Test BooleanConstraint class.
 */
public class BooleanConstraintTest {

    /**
     * Constructor.
     */
    public BooleanConstraintTest() {
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
     * Test of getDomain method, of class BooleanConstraint.
     */
    @Test
    public void testGetDomain() {
        BooleanAttribute attribute = new BooleanAttribute("", null);
        Constraint expResult = attribute.getDomain();
        Constraint result = attribute.getDomain();
        assertTrue(result instanceof BooleanConstraint);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuccessors method, of class BooleanConstraint.
     */
    @Test
    public void testGetSuccessors() {
        BooleanAttribute attribute = new BooleanAttribute("", null);
        BooleanConstraint instance = (BooleanConstraint) attribute.getDomain();
        Set<Constraint> successors = instance.getSuccessors();

        int expResult = 1;
        int result = successors.size();
        assertEquals(expResult, result);

        for (Constraint successor : successors) {
            assertTrue(successor instanceof BooleanConstraint);
        }
    }

    /**
     * Test of isSet method, of class BooleanConstraint.
     */
    @Test
    public void testIsSet() {
        BooleanAttribute attribute = new BooleanAttribute("", null);
        BooleanConstraint instance = (BooleanConstraint) attribute.getDomain();

        Boolean expResult = false;
        Boolean result = instance.isSet();
        assertEquals(expResult, result);
    }

    /**
     * Test of isUnset method, of class BooleanConstraint.
     */
    @Test
    public void testIsUnset() {
        BooleanAttribute attribute = new BooleanAttribute("", null);
        BooleanConstraint instance = (BooleanConstraint) attribute.getDomain();

        Boolean expResult = true;
        Boolean result = instance.isUnset();
        assertEquals(expResult, result);

        expResult = false;
        for (Constraint successor : instance.getSuccessors()) {
            result = ((BooleanConstraint) successor).isUnset();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of toString method, of class BooleanConstraint.
     */
    @Test
    public void testToString() {
        BooleanAttribute attribute = new BooleanAttribute("AttributeName", null);
        BooleanConstraint instance = (BooleanConstraint) attribute.getDomain();

        String expResult = "AttributeName(boolean):unset";
        String result = instance.toString();
        assertEquals(expResult, result);

        expResult = "AttributeName(boolean):set";
        for (Constraint successor : instance.getSuccessors()) {
            result = successor.toString();
            assertEquals(expResult, result);
        }
    }
}

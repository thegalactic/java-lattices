package org.thegalactic.context.attribute;

/*
 * AbstractConstraintTest.java
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
 * Test AbstractConstraint class.
 */
public class AbstractConstraintTest {

    /**
     * Constructor.
     */
    public AbstractConstraintTest() {
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
     * Test of getAttribute method, of class AbstractConstraint.
     */
    @Test
    public void testGetAttribute() {
        Attribute expResult = new AttributeImpl(null, "", "");
        AbstractConstraint instance = new ConstraintImpl(expResult);
        Attribute result = instance.getAttribute();
        assertEquals(expResult, result);
    }
}

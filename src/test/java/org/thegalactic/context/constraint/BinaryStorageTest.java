package org.thegalactic.context.constraint;

/*
 * BinaryStorageTest.java
 *
 * Copyright: 2015-2016 The Galactic Organization, France
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
 * BinaryStorage test.
 */
public class BinaryStorageTest {

    /**
     * Constructor.
     */
    public BinaryStorageTest() {
    }

    /**
     * BeforeClass.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * AfterClass.
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Before.
     */
    @Before
    public void setUp() {
    }

    /**
     * After.
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class BinaryStorage.
     */
    @Test
    public void testCreate() {
        BinaryStorage expResult = null;
        BinaryStorage result = BinaryStorage.create(10);
        assertEquals("[iiiiiiiiii]", result.toString());
    }

    /**
     * Test of setTruth method, of class BinaryStorage.
     */
    @Test
    public void testSetTruth() {
        BinaryStorage instance = BinaryStorage.create(10);
        BinaryStorage expResult = instance;
        assertEquals(expResult, instance.setTruth(0, true));
        assertEquals("[tiiiiiiiii]", instance.toString());
    }

    /**
     * Test of setFalsity method, of class BinaryStorage.
     */
    @Test
    public void testSetFalsity() {
        BinaryStorage instance = BinaryStorage.create(10);
        BinaryStorage expResult = instance;
        assertEquals(expResult, instance.setFalsity(0, true));
        assertEquals("[fiiiiiiiii]", instance.toString());
    }

    /**
     * Test of getTruth method, of class BinaryStorage.
     */
    @Test
    public void testGetTruth() {
        BinaryStorage instance = BinaryStorage.create(10);
        boolean expResult = false;
        assertEquals(expResult, instance.getTruth(0));
        expResult = true;
        assertEquals(expResult, instance.setTruth(0, true).getTruth(0));
    }

    /**
     * Test of getFalsity method, of class BinaryStorage.
     */
    @Test
    public void testGetFalsity() {
        BinaryStorage instance = BinaryStorage.create(10);
        boolean expResult = false;
        assertEquals(expResult, instance.getFalsity(0));
        expResult = true;
        assertEquals(expResult, instance.setFalsity(0, true).getFalsity(0));
    }

    /**
     * Test of toString method, of class BinaryStorage.
     */
    @Test
    public void testToString() {
        BinaryStorage instance = BinaryStorage.create(10);
        instance.setTruth(0, true);
        instance.setFalsity(1, true);
        instance.setTruth(2, true).setFalsity(2, true);
        assertEquals("[tfciiiiiii]", instance.toString());
    }
}

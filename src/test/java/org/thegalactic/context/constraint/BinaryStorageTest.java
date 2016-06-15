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
     * Constructor.
     */
    public BinaryStorageTest() {
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
        BinaryStorage result = BinaryStorage.create(10);
        assertEquals("??????????", result.toString());
    }

    /**
     * Test of setTruth method, of class BinaryStorage.
     */
    @Test
    public void testSetTruth() {
        BinaryStorage instance = BinaryStorage.create(10);
        assertEquals(instance, instance.setTruth(0, true));
        assertEquals("1?????????", instance.toString());
    }

    /**
     * Test of setFalsity method, of class BinaryStorage.
     */
    @Test
    public void testSetFalsity() {
        BinaryStorage instance = BinaryStorage.create(10);
        assertEquals(instance, instance.setFalsity(0, true));
        assertEquals("0?????????", instance.toString());
    }

    /**
     * Test of getTruth method, of class BinaryStorage.
     */
    @Test
    public void testGetTruth() {
        BinaryStorage instance = BinaryStorage.create(10);
        assertEquals(false, instance.getTruth(0));
        assertEquals(true, instance.setTruth(0, true).getTruth(0));
    }

    /**
     * Test of getFalsity method, of class BinaryStorage.
     */
    @Test
    public void testGetFalsity() {
        BinaryStorage instance = BinaryStorage.create(10);
        assertEquals(false, instance.getFalsity(0));
        assertEquals(true, instance.setFalsity(0, true).getFalsity(0));
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
        assertEquals("10!???????", instance.toString());
    }
}

package fr.kbertet.context.attribute;

/*
 * BooleanAttributeTest.java
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

/**
 * Test BooleanAttribute class.
 */
public class BooleanAttributeTest {

    /**
     * Constructor.
     */
    public BooleanAttributeTest() {
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
     * Test of getType method, of class BooleanAttribute.
     */
    @Test
    public void testGetType() {
        BooleanAttribute instance = new BooleanAttribute("", null);
        String expResult = "boolean";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDomain method, of class BooleanAttribute.
     */
    @Test
    public void testGetDomain() {
        BooleanAttribute instance = new BooleanAttribute("", null);
        Constraint result = instance.getDomain();
        assertTrue(result instanceof Constraint);
    }
}

package org.thegalactic.context.attribute;

/*
 * AttributeFactoryTest.java
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
import static org.junit.Assert.assertTrue;

import org.thegalactic.context.Context;

/**
 * Test the AttributeFactory class.
 */
public class AttributeFactoryTest {

    /**
     * Constructor.
     */
    public AttributeFactoryTest() {
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
     * Test of create method, of class AttributeFactory.
     */
    @Test
    public void testCreate() {
        String type = "boolean";
        Context context = null;
        String name = "test";
        String expResult = "test(boolean)";
        String result = AttributeFactory.getInstance().create(type, name, context).toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of register method, of class AttributeFactory.
     */
    @Test
    public void testRegister() {
        String type = "test";
        AttributeBuilder builder = new AttributeBuilderImpl();
        AttributeFactory.getInstance().register(type, builder);
        Attribute attribute = AttributeFactory.getInstance().create("test", "", null);
        assertTrue(attribute instanceof AttributeImpl);
    }

    /**
     * Test of unregister method, of class AttributeFactory.
     */
    @Test(expected = NullPointerException.class)
    public void testUnregister() {
        String type = "test";
        AttributeBuilder builder = new AttributeBuilderImpl();
        AttributeFactory.getInstance().register(type, builder);
        AttributeFactory.getInstance().unregister(type);
        Attribute attribute = AttributeFactory.getInstance().create("test", "", null);
    }
}

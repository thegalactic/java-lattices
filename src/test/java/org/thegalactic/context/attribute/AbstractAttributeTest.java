package org.thegalactic.context.attribute;

/*
 * AbstractAttributeTest.java
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
import org.thegalactic.context.Observation;
import org.thegalactic.context.Value;

/**
 * Test AbstractConstraint class.
 */
public class AbstractAttributeTest {

    /**
     * Constructor.
     */
    public AbstractAttributeTest() {
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
     * Test of getContext method, of class AbstractAttribute.
     */
    @Test
    public void testGetContext() {
        ContextImpl context = new ContextImpl();
        AbstractAttribute instance = new AttributeImpl(context, "", "");
        Context expResult = context;
        Context result = instance.getContext();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class AbstractAttribute.
     */
    @Test
    public void testToString() {
        AbstractAttribute instance = new AttributeImpl(null, "AttributeName", "test");
        String expResult = "AttributeName(test)";
        String result = instance.toString();
        assertEquals(expResult, result);

        instance = new AttributeImpl(null, "Attribute Name", "type test");
        expResult = "\"Attribute Name\"(\"type test\")";
        result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class AbstractAttribute.
     */
    @Test
    public void testGetName() {
        AbstractAttribute instance = new AttributeImpl(null, "AttributeName", "");
        String expResult = "AttributeName";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class AbstractAttribute.
     */
    @Test
    public void testGetValue() {
        Observation observation = null;
        AbstractAttribute instance = new AttributeImpl(new ContextImpl(), "", "");
        Value result = instance.getValue(observation);
        assertTrue(result instanceof ValueImpl);
    }
}

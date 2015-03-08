package org.thegalactic.context.attribute.bool;

/*
 * BoolAttributeBuilderTest.java
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

import org.thegalactic.context.Context;
import org.thegalactic.context.attribute.Attribute;
import org.thegalactic.context.attribute.AttributeBuilder;
import org.thegalactic.context.attribute.ContextImpl;

/**
 * Test the BoolAttributeBuilder class.
 */
public class BoolAttributeBuilderTest {

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
    public BoolAttributeBuilderTest() {
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
     * Test of create method, of class BoolAttributeBuilder.
     */
    @Test
    public void testCreate() {
        BoolAttributeBuilder instance = new BoolAttributeBuilder();
        Attribute result = instance.create();
        assertEquals(null, result.getContext());
        assertEquals(null, result.getName());
    }

    /**
     * Test of setContext method, of class BoolAttributeBuilder.
     */
    @Test
    public void testSetContext() {
        Context context = new ContextImpl();
        BoolAttributeBuilder instance = new BoolAttributeBuilder();
        AttributeBuilder result = instance.setContext(context);
        assertEquals(context, result.create().getContext());
    }

    /**
     * Test of setName method, of class BoolAttributeBuilder.
     */
    @Test
    public void testSetName() {
        String name = "test";
        BoolAttributeBuilder instance = new BoolAttributeBuilder();
        AttributeBuilder result = instance.setName(name);
        assertEquals(name, result.create().getName());
    }
}

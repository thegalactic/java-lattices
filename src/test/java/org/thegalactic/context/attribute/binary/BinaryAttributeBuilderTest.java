package org.thegalactic.context.attribute.binary;

/*
 * BinaryAttributeBuilderTest.java
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

import org.thegalactic.context.Context;
import org.thegalactic.context.attribute.Attribute;
import org.thegalactic.context.attribute.AttributeBuilder;
import org.thegalactic.context.ContextImpl;

/**
 * Test the BinaryAttributeBuilder class.
 */
public class BinaryAttributeBuilderTest {

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
    public BinaryAttributeBuilderTest() {
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
     * Test of create method, of class BinaryAttributeBuilder.
     */
    @Test
    public void testCreate() {
        BinaryAttributeBuilder instance = new BinaryAttributeBuilder();
        Attribute result = instance.create();
        assertEquals(null, result.getContext());
        assertEquals(null, result.getName());
    }

    /**
     * Test of setContext method, of class BinaryAttributeBuilder.
     */
    @Test
    public void testSetContext() {
        Context context = new ContextImpl();
        BinaryAttributeBuilder instance = new BinaryAttributeBuilder();
        AttributeBuilder result = instance.setContext(context);
        assertEquals(context, result.create().getContext());
    }

    /**
     * Test of setName method, of class BinaryAttributeBuilder.
     */
    @Test
    public void testSetName() {
        String name = "test";
        BinaryAttributeBuilder instance = new BinaryAttributeBuilder();
        AttributeBuilder result = instance.setName(name);
        assertEquals(name, result.create().getName());
    }
}

package fr.kbertet.context.attribute;

/*
 * BooleanAttributeBuilderTest.java
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


import fr.kbertet.context.Context;

/**
 * Test the BooleanAttributeBuilder class.
 */
public class BooleanAttributeBuilderTest {

    /**
     * Constructor.
     */
    public BooleanAttributeBuilderTest() {
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
     * Test of create method, of class BooleanAttributeBuilder.
     */
    @Test
    public void testCreate() {
        BooleanAttributeBuilder instance = new BooleanAttributeBuilder();
        Attribute result = instance.create();
        assertEquals(null, result.getContext());
        assertEquals(null, result.getName());
    }

    /**
     * Test of setContext method, of class BooleanAttributeBuilder.
     */
    @Test
    public void testSetContext() {
        Context context = new ContextImpl();
        BooleanAttributeBuilder instance = new BooleanAttributeBuilder();
        AttributeBuilder result = instance.setContext(context);
        assertEquals(context, result.create().getContext());
    }

    /**
     * Test of setName method, of class BooleanAttributeBuilder.
     */
    @Test
    public void testSetName() {
        String name = "test";
        BooleanAttributeBuilder instance = new BooleanAttributeBuilder();
        AttributeBuilder result = instance.setName(name);
        assertEquals(name, result.create().getName());
    }
}

package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalValueTest.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * CategoricalValue test.
 */
public class CategoricalValueTest {

    /**
     * Test of create method, of class CategoricalValue.
     */
    @Test
    public void testCreate() {
        CategoricalValue value = CategoricalValue.create(
                "Test",
                CategoricalAttribute.create(CategoricalModel.create())
        );
        assertTrue(value instanceof CategoricalValue);
    }

    /**
     * Test of getData method, of class CategoricalValue.
     */
    @Test
    public void testGetData() {
        String object = "Test";
        CategoricalValue value = CategoricalValue.create(
                object,
                CategoricalAttribute.create(CategoricalModel.create())
        );
        assertEquals(object, value.getData());
    }

    /**
     * Test of getData method, of class CategoricalValue.
     */
    @Test
    public void testGetAttribute() {
        CategoricalAttribute attribute = CategoricalAttribute.create(CategoricalModel.create());
        CategoricalValue value = CategoricalValue.create("Test", attribute);
        assertEquals(attribute, value.getAttribute());
    }

    /**
     * Test of setData method, of class CategoricalValue.
     */
    @Test
    public void testSetData() {
        String object = "Test2";
        CategoricalValue value = CategoricalValue.create(
                "Test",
                CategoricalAttribute.create(CategoricalModel.create())
        );
        assertEquals(value, value.setData(object));
        assertEquals(object, value.getData());
    }

    /**
     * Test of index method, of class CategoricalValue.
     */
    @Test
    public void testIndex() {
        CategoricalAttribute attribute = CategoricalAttribute.create(CategoricalModel.create());
        assertEquals(0, attribute.addValue("Test1").index());
        assertEquals(1, attribute.addValue("Test2").index());
        assertEquals(2, attribute.addValue("Test3").index());
    }

    /**
     * Test of toString method, of class CategoricalValue.
     */
    @Test
    public void testToString() {
        CategoricalAttribute attribute = CategoricalAttribute.create(CategoricalModel.create());
        CategoricalValue value = CategoricalValue.create("Test", attribute);
        assertEquals("Test", value.toString());
        value = CategoricalValue.create("Test with spaces", attribute);
        assertEquals("\"Test with spaces\"", value.toString());
        value = CategoricalValue.create("Test\"quotes", attribute);
        assertEquals("\"Test\\\"quotes\"", value.toString());
    }
}

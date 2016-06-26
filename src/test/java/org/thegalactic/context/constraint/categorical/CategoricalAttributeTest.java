package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalAttributeTest.java
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
import static org.junit.Assert.fail;

import java.util.ArrayList;

/**
 * CategoricalAttribute test.
 */
public class CategoricalAttributeTest {

    /**
     * Test of addValue method, of class CategoricalAttribute.
     */
    @Test
    public void testAddValue() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        assertTrue(attribute.addValue("Test") instanceof CategoricalValue);
        try {
            model.instantiate();
            attribute.addValue("Test2");
            fail("An IllegalStateException must be thrown");
        } catch (IllegalStateException e) {
            assertEquals("The underlying model has already been instantiated", e.getMessage());
        }
    }

    /**
     * Test of getValues method, of class CategoricalAttribute.
     */
    @Test
    public void testGetValues() {
        CategoricalAttribute attribute = CategoricalModel.create().addAttribute();
        ArrayList<CategoricalValue> values = new ArrayList<CategoricalValue>();
        values.add(attribute.addValue("Test1"));
        values.add(attribute.addValue("Test2"));
        values.add(attribute.addValue("Test3"));
        int i = 0;
        for (CategoricalValue value : attribute.getValues()) {
            assertEquals(values.get(i), value);
            i++;
        }
    }

    /**
     * Test of size method, of class CategoricalAttribute.
     */
    @Test
    public void testSize() {
        CategoricalAttribute attribute = CategoricalModel.create().addAttribute();
        assertEquals(0, attribute.size());
        attribute.addValue("Test1");
        assertEquals(1, attribute.size());
        attribute.addValue("Test2");
        assertEquals(2, attribute.size());
        attribute.addValue("Test3");
        assertEquals(3, attribute.size());
    }

    /**
     * Test of startIndex method, of class CategoricalAttribute.
     */
    @Test
    public void testStartIndex() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute1 = model.addAttribute();
        assertEquals(0, attribute1.startIndex());
        attribute1.addValue("Test1");
        attribute1.addValue("Test2");
        attribute1.addValue("Test3");
        CategoricalAttribute attribute2 = model.addAttribute();
        assertEquals(3, attribute2.startIndex());
        attribute1.addValue("Test4");
        assertEquals(4, attribute2.startIndex());
    }

    /**
     * Test of indexOf method, of class CategoricalAttribute.
     */
    @Test
    public void testIndexOf() {
        CategoricalAttribute attribute = CategoricalModel.create().addAttribute();
        assertEquals(0, attribute.indexOf(attribute.addValue("Test1")));
        assertEquals(1, attribute.indexOf(attribute.addValue("Test2")));
        assertEquals(2, attribute.indexOf(attribute.addValue("Test3")));
    }

    /**
     * Test of getModel method, of class CategoricalAttribute.
     */
    @Test
    public void testGetModel() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        assertEquals(model, attribute.getModel());
    }

    /**
     * Test of toString method, of class CategoricalAttribute.
     */
    @Test
    public void testToString() {
        CategoricalAttribute attribute = CategoricalModel.create().addAttribute();
        attribute.addValue("Test");
        attribute.addValue("Test with spaces");
        attribute.addValue("Test\"quotes");
        assertEquals("[Test, \"Test with spaces\", \"Test\\\"quotes\"]", attribute.toString());
    }
}

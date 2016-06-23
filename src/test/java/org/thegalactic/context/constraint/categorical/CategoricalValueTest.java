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

/**
 * CategoricalValue test.
 */
public class CategoricalValueTest {

    /**
     * Test of getData method, of class CategoricalValue.
     */
    @Test
    public void testGetAttribute() {
        CategoricalAttribute attribute = CategoricalModel.create().addAttribute();
        CategoricalValue value = CategoricalValue.create("Test", attribute);
        assertEquals(attribute, value.getAttribute());
    }

    /**
     * Test of getModel method, of class CategoricalValue.
     */
    @Test
    public void testGetModel() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalValue value = model.addAttribute().addValue("Test");
        assertEquals(model, value.getModel());
    }

    /**
     * Test of getData method, of class CategoricalValue.
     */
    @Test
    public void testGetData() {
        String object = "Test";
        CategoricalValue value = CategoricalModel.create().addAttribute().addValue(object);
        assertEquals(object, value.getData());
    }

    /**
     * Test of setData method, of class CategoricalValue.
     */
    @Test
    public void testSetData() {
        String object = "Test2";
        CategoricalValue value = CategoricalModel.create().addAttribute().addValue("Test");
        assertEquals(value, value.setData(object));
        assertEquals(object, value.getData());
    }

    /**
     * Test of index method, of class CategoricalValue.
     */
    @Test
    public void testIndex() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute1 = model.addAttribute();
        assertEquals(0, attribute1.addValue("Test1").index());
        assertEquals(1, attribute1.addValue("Test2").index());
        assertEquals(2, attribute1.addValue("Test3").index());
        CategoricalAttribute attribute2 = model.addAttribute();
        assertEquals(3, attribute2.addValue("Test4").index());
        assertEquals(4, attribute2.addValue("Test5").index());
    }

    /**
     * Test of toString method, of class CategoricalValue.
     */
    @Test
    public void testToString() {
        CategoricalAttribute attribute = CategoricalModel.create().addAttribute();
        CategoricalValue value = attribute.addValue("Test");
        assertEquals("Test", value.toString());
        value = attribute.addValue("Test with spaces");
        assertEquals("\"Test with spaces\"", value.toString());
        value = attribute.addValue("Test\"quotes");
        assertEquals("\"Test\\\"quotes\"", value.toString());
    }
}

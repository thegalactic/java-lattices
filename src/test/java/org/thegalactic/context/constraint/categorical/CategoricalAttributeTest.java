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

/**
 * CategoricalAttribute test.
 */
public class CategoricalAttributeTest {

    /**
     * Test of create method, of class CategoricalAttribute.
     */
    @Test
    public void testCreate() {
        CategoricalAttribute result = CategoricalAttribute.create(CategoricalModel.create());
        assertTrue(result instanceof CategoricalAttribute);
    }

    /**
     * Test of addValue method, of class CategoricalAttribute.
     */
    @Test
    public void testAddValue() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = CategoricalAttribute.create(model);
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
        CategoricalAttribute attribute = CategoricalAttribute.create(CategoricalModel.create());
        attribute.addValue("Test1");
        attribute.addValue("Test2");
        attribute.addValue("Test3");
        int i = 0;
        for (CategoricalValue value : attribute.getValues()) {
            i++;
            assertEquals("Test" + i, value.toString());
        }
    }

    /**
     * Test of getModel method, of class CategoricalAttribute.
     */
    @Test
    public void testGetModel() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = CategoricalAttribute.create(model);
        assertEquals(model, attribute.getModel());
    }

    /**
     * Test of size method, of class CategoricalAttribute.
     */
    @Test
    public void testSize() {
        CategoricalAttribute attribute = CategoricalAttribute.create(CategoricalModel.create());
        attribute.addValue("Test1");
        attribute.addValue("Test2");
        attribute.addValue("Test3");
        assertEquals(3, attribute.size());
    }

    /**
     * Test of toString method, of class CategoricalAttribute.
     */
    @Test
    public void testToString() {
        CategoricalAttribute attribute = CategoricalAttribute.create(CategoricalModel.create());
        attribute.addValue("Test");
        attribute.addValue("Test with spaces");
        attribute.addValue("Test\"quotes");
        assertEquals("[Test, \"Test with spaces\", \"Test\\\"quotes\"]", attribute.toString());
    }
}

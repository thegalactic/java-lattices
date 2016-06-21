package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalModelTest.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * CategoricalModel test.
 */
public class CategoricalModelTest {

    /**
     * Test of create method, of class CategoricalModel.
     */
    @Test
    public void testCreate() {
        CategoricalModel result = CategoricalModel.create();
        assertTrue(result instanceof CategoricalModel);
    }

    /**
     * Test of addAttribute method, of class CategoricalModel.
     */
    @Test
    public void testAddAttribute() {
        CategoricalModel model = CategoricalModel.create();
        assertTrue(model.addAttribute() instanceof CategoricalAttribute);
        model.instantiate();
        try {
            model.addAttribute();
            fail("An IllegalStateException must be thrown");
        } catch (IllegalStateException e) {
            assertEquals("This model has already been instantiated", e.getMessage());
        }
    }

    /**
     * Test of getAttributes method, of class CategoricalModel.
     */
    @Test
    public void testGetAttributes() {
        ArrayList<CategoricalAttribute> attributes = new ArrayList<CategoricalAttribute>();
        CategoricalModel model = CategoricalModel.create();
        attributes.add(model.addAttribute());
        attributes.add(model.addAttribute());
        attributes.add(model.addAttribute());
        int i = 0;
        for (CategoricalAttribute attribute : model.getAttributes()) {
            assertEquals(attributes.get(i), attribute);
            i++;
        }
    }

    /**
     * Test of getInstantiated method, of class CategoricalModel.
     */
    @Test
    public void testGetInstantiated() {
        CategoricalModel model = CategoricalModel.create();
        assertEquals(false, model.getInstantiated());
        model.instantiate();
        assertEquals(true, model.getInstantiated());
    }

    /**
     * Test of size method, of class CategoricalModel.
     */
    @Test
    public void testSize() {
        ArrayList<CategoricalAttribute> attributes = new ArrayList<CategoricalAttribute>();
        CategoricalModel model = CategoricalModel.create();
        attributes.add(model.addAttribute());
        attributes.add(model.addAttribute());
        attributes.add(model.addAttribute());
        assertEquals(attributes.size(), model.size());
    }

    /**
     * Test of indexOf method, of class CategoricalModel.
     */
    @Test
    public void testIndexOf() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute1 = model.addAttribute();
        CategoricalValue value1 = attribute1.addValue("Test1");
        CategoricalValue value2 = attribute1.addValue("Test2");
        CategoricalAttribute attribute2 = model.addAttribute();
        CategoricalValue value3 = attribute2.addValue("Test3");
        assertEquals(0, model.indexOf(value1));
        assertEquals(1, model.indexOf(value2));
        assertEquals(2, model.indexOf(value3));
        try {
            model = CategoricalModel.create();
            model.indexOf(value1);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("The CategoricalValue is not in this model", e.getMessage());
        }
    }

    /**
     * Test of toString method, of class CategoricalModel.
     */
    @Test
    public void testToString() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute1 = model.addAttribute();
        attribute1.addValue("Test");
        attribute1.addValue("Test with spaces");
        attribute1.addValue("Test\"quotes");
        CategoricalAttribute attribute2 = model.addAttribute();
        attribute2.addValue("Test2");
        assertEquals("[[Test, \"Test with spaces\", \"Test\\\"quotes\"], [Test2]]", model.toString());
    }
}

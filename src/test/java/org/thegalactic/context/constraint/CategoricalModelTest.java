package org.thegalactic.context.constraint;

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
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
     * Test of add method, of class CategoricalModel.
     */
    @Test
    public void testAdd() {
        CategoricalModel instance = CategoricalModel.create();
        assertEquals(instance, instance.add(CategoricalAttribute.create()));
    }

    /**
     * Test of toString method, of class CategoricalModel.
     */
    @Test
    public void testToString() {
        CategoricalModel instance = CategoricalModel.create();
        CategoricalAttribute attribute1 = CategoricalAttribute.create();
        attribute1.add(CategoricalValue.create("Test"));
        attribute1.add(CategoricalValue.create("Test with spaces"));
        attribute1.add(CategoricalValue.create("Test\"quotes"));
        instance.add(attribute1);
        CategoricalAttribute attribute2 = CategoricalAttribute.create();
        attribute2.add(CategoricalValue.create("Test2"));
        instance.add(attribute2);
        assertEquals("[[Test, \"Test with spaces\", \"Test\\\"quotes\"], [Test2]]", instance.toString());
    }
}

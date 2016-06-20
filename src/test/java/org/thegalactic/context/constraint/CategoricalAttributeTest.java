package org.thegalactic.context.constraint;

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

/**
 * CategoricalAttribute test.
 */
public class CategoricalAttributeTest {

    /**
     * Test of create method, of class CategoricalAttribute.
     */
    @Test
    public void testCreate() {
        CategoricalAttribute result = CategoricalAttribute.create();
        assertTrue(result instanceof CategoricalAttribute);
    }

    /**
     * Test of add method, of class CategoricalAttribute.
     */
    @Test
    public void testAdd() {
        CategoricalValue value = CategoricalValue.create("Test");
        CategoricalAttribute instance = CategoricalAttribute.create();
        assertEquals(instance, instance.add(value));
    }

    /**
     * Test of toString method, of class CategoricalAttribute.
     */
    @Test
    public void testToString() {
        CategoricalValue value1 = CategoricalValue.create("Test");
        CategoricalValue value2 = CategoricalValue.create("Test with spaces");
        CategoricalValue value3 = CategoricalValue.create("Test\"quotes");
        CategoricalAttribute instance = CategoricalAttribute.create();
        instance.add(value1).add(value2).add(value3);
        assertEquals("[Test, \"Test with spaces\", \"Test\\\"quotes\"]", instance.toString());
    }
}

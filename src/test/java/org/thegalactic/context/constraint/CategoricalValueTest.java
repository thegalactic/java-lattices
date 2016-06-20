package org.thegalactic.context.constraint;

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
        CategoricalValue result = CategoricalValue.create("Test");
        assertTrue(result instanceof CategoricalValue);
    }

    /**
     * Test of toString method, of class CategoricalValue.
     */
    @Test
    public void testToString() {
        CategoricalValue instance = CategoricalValue.create("Test");
        assertEquals("Test", instance.toString());
        instance = CategoricalValue.create("Test with spaces");
        assertEquals("\"Test with spaces\"", instance.toString());
        instance = CategoricalValue.create("Test\"quotes");
        assertEquals("\"Test\\\"quotes\"", instance.toString());
    }
}

package org.thegalactic.context.constraint.categorical;

/*
 * CategoricalStorageTest.java
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
 * CategoricalModel test.
 */
public class CategoricalStorageTest {

    /**
     * Test of create method, of class CategoricalStorage.
     */
    @Test
    public void testCreate() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalStorage storage = CategoricalStorage.create(model);
        assertTrue(storage instanceof CategoricalStorage);
    }

    /**
     * Test of get method, of class CategoricalStorage.
     */
    @Test
    public void testGet() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value = attribute.addValue("Test");
        CategoricalStorage storage = CategoricalStorage.create(model);
        assertEquals(true, storage.get(value));
        try {
            value = CategoricalModel.create().addAttribute().addValue("Test");
            storage.get(value);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("The CategoricalValue is not in the model of the CategoricalStorage", e.getMessage());
        }
    }

    /**
     * Test of set method, of class CategoricalStorage.
     */
    @Test
    public void testSet() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value = attribute.addValue("Test");
        CategoricalStorage storage = CategoricalStorage.create(model);
        storage.set(value, false);
        assertEquals(false, storage.get(value));
        try {
            value = CategoricalModel.create().addAttribute().addValue("Test");
            storage.set(value, true);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("The CategoricalValue is not in the model of the CategoricalStorage", e.getMessage());
        }
    }

    /**
     * Test of reduce method, of class CategoricalStorage.
     */
    @Test
    public void testReduce() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value = attribute.addValue("Test");
        CategoricalStorage storage = CategoricalStorage.create(model);
        storage.reduce(value, true);
        assertEquals(true, storage.get(value));
        storage.reduce(value, false);
        assertEquals(false, storage.get(value));
        storage.reduce(value, true);
        assertEquals(false, storage.get(value));
        storage.reduce(value, false);
        assertEquals(false, storage.get(value));
        try {
            value = CategoricalModel.create().addAttribute().addValue("Test");
            storage.reduce(value, true);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("The CategoricalValue is not in the model of the CategoricalStorage", e.getMessage());
        }
    }

    /**
     * Test of extend method, of class CategoricalStorage.
     */
    @Test
    public void testExtend() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value = attribute.addValue("Test");
        CategoricalStorage storage = CategoricalStorage.create(model);
        storage.set(value, false);
        storage.extend(value, false);
        assertEquals(false, storage.get(value));
        storage.extend(value, true);
        assertEquals(true, storage.get(value));
        storage.extend(value, false);
        assertEquals(true, storage.get(value));
        storage.extend(value, true);
        assertEquals(true, storage.get(value));
        try {
            value = CategoricalModel.create().addAttribute().addValue("Test");
            storage.extend(value, true);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("The CategoricalValue is not in the model of the CategoricalStorage", e.getMessage());
        }
    }

    /**
     * Test of intersection method, of class CategoricalStorage.
     */
    @Test
    public void testIntersection() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value1 = attribute.addValue("Test1");
        CategoricalValue value2 = attribute.addValue("Test2");
        CategoricalValue value3 = attribute.addValue("Test3");
        CategoricalValue value4 = attribute.addValue("Test4");
        CategoricalStorage storage1 = CategoricalStorage.create(model);
        CategoricalStorage storage2 = CategoricalStorage.create(model);
        storage1.set(value1, true);
        storage1.set(value2, true);
        storage1.set(value3, false);
        storage1.set(value4, false);
        storage2.set(value1, true);
        storage2.set(value2, false);
        storage2.set(value3, true);
        storage2.set(value4, false);
        storage1.intersection(storage2);
        assertEquals(true, storage1.get(value1));
        assertEquals(false, storage1.get(value2));
        assertEquals(false, storage1.get(value3));
        assertEquals(false, storage1.get(value4));
        try {
            storage2 = CategoricalStorage.create(CategoricalModel.create());
            storage1.union(storage2);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("CategoricalStorage objects must have the same model", e.getMessage());
        }
    }

    /**
     * Test of union method, of class CategoricalStorage.
     */
    @Test
    public void testUnion() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value1 = attribute.addValue("Test1");
        CategoricalValue value2 = attribute.addValue("Test2");
        CategoricalValue value3 = attribute.addValue("Test3");
        CategoricalValue value4 = attribute.addValue("Test4");
        CategoricalStorage storage1 = CategoricalStorage.create(model);
        CategoricalStorage storage2 = CategoricalStorage.create(model);
        storage1.set(value1, true);
        storage1.set(value2, true);
        storage1.set(value3, false);
        storage1.set(value4, false);
        storage2.set(value1, true);
        storage2.set(value2, false);
        storage2.set(value3, true);
        storage2.set(value4, false);
        storage1.union(storage2);
        assertEquals(true, storage1.get(value1));
        assertEquals(true, storage1.get(value2));
        assertEquals(true, storage1.get(value3));
        assertEquals(false, storage1.get(value4));
        try {
            storage2 = CategoricalStorage.create(CategoricalModel.create());
            storage1.intersection(storage2);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("CategoricalStorage objects must have the same model", e.getMessage());
        }
    }

    /**
     * Test of toString method, of class CategoricalStorage.
     */
    @Test
    public void testToString() {
        CategoricalModel model = CategoricalModel.create();
        CategoricalAttribute attribute = model.addAttribute();
        CategoricalValue value1 = attribute.addValue("Test1");
        CategoricalValue value2 = attribute.addValue("Test2");
        CategoricalValue value3 = attribute.addValue("Test3");
        CategoricalStorage storage = CategoricalStorage.create(model);
        assertEquals("[[Test1, Test2, Test3]]", storage.toString());
        storage.set(value1, false);
        assertEquals("[[Test2, Test3]]", storage.toString());
        storage.set(value2, false);
        assertEquals("[[Test3]]", storage.toString());
        storage.set(value3, false);
        assertEquals("[[]]", storage.toString());

        model = CategoricalModel.create();
        CategoricalAttribute attribute1 = model.addAttribute();
        value1 = attribute1.addValue("Test1");
        attribute1.addValue("Test2");
        attribute1.addValue("Test3");
        CategoricalAttribute attribute2 = model.addAttribute();
        CategoricalValue value4 = attribute2.addValue("Test4");
        attribute2.addValue("Test5");
        storage = CategoricalStorage.create(model);
        assertEquals("[[Test1, Test2, Test3], [Test4, Test5]]", storage.toString());
        storage.set(value1, false);
        storage.set(value4, false);
        assertEquals("[[Test2, Test3], [Test5]]", storage.toString());
    }
}

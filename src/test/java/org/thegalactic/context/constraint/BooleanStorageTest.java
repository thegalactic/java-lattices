package org.thegalactic.context.constraint;

/*
 * BooleanStorageTest.java
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
 * BooleanStorage test.
 */
public class BooleanStorageTest {

   /**
     * Test of create method, of class BooleanStorage.
     */
    @Test
    public void testCreate() {
        BooleanStorage result = BooleanStorage.create(10);
        assertEquals("[0000000000]", result.toString());
    }

    /**
     * Test of set method, of class BooleanStorage.
     */
    @Test
    public void testSet() {
        BooleanStorage instance = BooleanStorage.create(10);
        assertEquals(instance, instance.set(0, true));
        assertEquals("[1000000000]", instance.toString());
    }

    /**
     * Test of get method, of class BooleanStorage.
     */
    @Test
    public void testGet() {
        BooleanStorage instance = BooleanStorage.create(10);
        assertEquals(false, instance.get(0));
        assertEquals(true, instance.set(0, true).get(0));
    }

    /**
     * Test of reduce method, of class BooleanStorage.
     */
    @Test
    public void testReduce() {
        BooleanStorage instance = BooleanStorage.create(10);
        instance.set(0, true);
        instance.set(1, true);
        instance.set(2, false);
        instance.set(3, false);
        instance.reduce(0, true).reduce(1, false).reduce(2, true).reduce(3, false);
        assertEquals("[1110000000]", instance.toString());
    }

    /**
     * Test of extend method, of class BooleanStorage.
     */
    @Test
    public void testExtend() {
        BooleanStorage instance = BooleanStorage.create(10);
        instance.set(0, true);
        instance.set(1, true);
        instance.set(2, false);
        instance.set(3, false);
        instance.extend(0, true).extend(1, false).extend(2, true).extend(3, false);
        assertEquals("[1000000000]", instance.toString());
    }

    /**
     * Test of intersection method, of class BooleanStorage.
     */
    @Test
    public void testIntersection() {
        BooleanStorage instance = BooleanStorage.create(10);
        BooleanStorage storage = BooleanStorage.create(10);
        instance.set(0, true);
        storage.set(0, true);
        instance.set(1, true);
        storage.set(1, false);
        instance.set(2, false);
        storage.set(2, true);
        instance.set(3, false);
        storage.set(3, false);
        assertEquals("[1000000000]", instance.intersection(storage).toString());
        assertEquals("[1010000000]", storage.toString());
    }

    /**
     * Test of union method, of class BooleanStorage.
     */
    @Test
    public void testUnion() {
        BooleanStorage instance = BooleanStorage.create(10);
        BooleanStorage storage = BooleanStorage.create(10);
        instance.set(0, true);
        storage.set(0, true);
        instance.set(1, true);
        storage.set(1, false);
        instance.set(2, false);
        storage.set(2, true);
        instance.set(3, false);
        storage.set(3, false);
        assertEquals("[1110000000]", instance.union(storage).toString());
        assertEquals("[1010000000]", storage.toString());
    }

    /**
     * Test of toString method, of class BooleanStorage.
     */
    @Test
    public void testToString() {
        BooleanStorage instance = BooleanStorage.create(10);
        instance.set(1, true);
        assertEquals("[0100000000]", instance.toString());
    }
}

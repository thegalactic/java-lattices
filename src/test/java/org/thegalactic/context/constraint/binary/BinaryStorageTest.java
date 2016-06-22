package org.thegalactic.context.constraint.binary;

/*
 * BinaryStorageTest.java
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
import static org.junit.Assert.fail;

/**
 * BinaryStorage test.
 */
public class BinaryStorageTest {

   /**
     * Test of create method, of class BinaryStorage.
     */
    @Test
    public void testCreate() {
        BinaryStorage result = BinaryStorage.create(10);
        assertEquals("[0000000000]", result.toString());
    }

    /**
     * Test of set method, of class BinaryStorage.
     */
    @Test
    public void testSet() {
        BinaryStorage storage = BinaryStorage.create(10);
        assertEquals(storage, storage.set(0, true));
        assertEquals("[1000000000]", storage.toString());
    }

    /**
     * Test of get method, of class BinaryStorage.
     */
    @Test
    public void testGet() {
        BinaryStorage storage = BinaryStorage.create(10);
        assertEquals(false, storage.get(0));
        assertEquals(true, storage.set(0, true).get(0));
    }

    /**
     * Test of reduce method, of class BinaryStorage.
     */
    @Test
    public void testReduce() {
        BinaryStorage storage = BinaryStorage.create(10);
        storage.set(0, true);
        storage.set(1, true);
        storage.set(2, false);
        storage.set(3, false);
        storage.reduce(0, true).reduce(1, false).reduce(2, true).reduce(3, false);
        assertEquals("[1110000000]", storage.toString());
    }

    /**
     * Test of extend method, of class BinaryStorage.
     */
    @Test
    public void testExtend() {
        BinaryStorage storage = BinaryStorage.create(10);
        storage.set(0, true);
        storage.set(1, true);
        storage.set(2, false);
        storage.set(3, false);
        storage.extend(0, true).extend(1, false).extend(2, true).extend(3, false);
        assertEquals("[1000000000]", storage.toString());
    }

    /**
     * Test of intersection method, of class BinaryStorage.
     */
    @Test
    public void testIntersection() {
        BinaryStorage storage1 = BinaryStorage.create(10);
        BinaryStorage storage2 = BinaryStorage.create(10);
        storage1.set(0, true);
        storage2.set(0, true);
        storage1.set(1, true);
        storage2.set(1, false);
        storage1.set(2, false);
        storage2.set(2, true);
        storage1.set(3, false);
        storage2.set(3, false);
        assertEquals("[1000000000]", storage1.intersection(storage2).toString());
        assertEquals("[1010000000]", storage2.toString());
        try {
            storage2 = BinaryStorage.create(9);
            storage1.intersection(storage2);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("BooleanStorage objects must have the same size", e.getMessage());
        }
    }

    /**
     * Test of union method, of class BinaryStorage.
     */
    @Test
    public void testUnion() {
        BinaryStorage storage1 = BinaryStorage.create(10);
        BinaryStorage storage2 = BinaryStorage.create(10);
        storage1.set(0, true);
        storage2.set(0, true);
        storage1.set(1, true);
        storage2.set(1, false);
        storage1.set(2, false);
        storage2.set(2, true);
        storage1.set(3, false);
        storage2.set(3, false);
        assertEquals("[1110000000]", storage1.union(storage2).toString());
        assertEquals("[1010000000]", storage2.toString());
        try {
            storage2 = BinaryStorage.create(9);
            storage1.union(storage2);
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("BooleanStorage objects must have the same size", e.getMessage());
        }
    }

    /**
     * Test of toString method, of class BinaryStorage.
     */
    @Test
    public void testToString() {
        BinaryStorage storage = BinaryStorage.create(10);
        storage.set(1, true);
        assertEquals("[0100000000]", storage.toString());
    }
}

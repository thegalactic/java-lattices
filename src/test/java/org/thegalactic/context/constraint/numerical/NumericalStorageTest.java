package org.thegalactic.context.constraint.numerical;

/*
 * NumericalStorageTest.java
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * NumericalStorage test.
 */
public class NumericalStorageTest {

    /**
     * Test of create method, of class NumericalStorage.
     */
    @Test
    public void testCreate() {
        assertTrue(NumericalStorage.create(10) instanceof NumericalStorage);
    }

    /**
     * Test of setEmpty and isEmpty methods, of class NumericalStorage.
     */
    @Test
    public void testEmpty() {
        NumericalStorage storage = NumericalStorage.create(10);
        storage.setInf(0, 0);
        storage.setSup(0, 10);
        assertFalse(storage.isEmpty(0));
        assertTrue(storage.setEmpty(0) instanceof NumericalStorage);
        assertTrue(storage.isEmpty(0));
    }

    /**
     * Test of isPoint and setPoint method, of class NumericalStorage.
     */
    @Test
    public void testPoint() {
        NumericalStorage storage = NumericalStorage.create(10);
        assertFalse(storage.isPoint(0));
        assertEquals(storage, storage.setPoint(0, 0));
        assertTrue(storage.isPoint(0));
        storage.setSup(0, 10);
        assertFalse(storage.isPoint(0));
    }

    /**
     * Test of setInf and getInf method, of class NumericalStorage.
     */
    @Test
    public void testInf() {
        NumericalStorage storage = NumericalStorage.create(10);
        assertEquals(storage, storage.setInf(0, 0));
        assertEquals(0, storage.getInf(0), 0.01);
        assertEquals(0, storage.getSup(0), 0.01);
        storage.setInf(0, 10);
        assertEquals(Double.POSITIVE_INFINITY, storage.getInf(0), 0.01);
        assertEquals(Double.NEGATIVE_INFINITY, storage.getSup(0), 0.01);
        storage.setInf(0, 10);
        assertEquals(10, storage.getInf(0), 0.01);
        storage.setInf(0, 0);
        assertEquals(0, storage.getInf(0), 0.01);
    }

    /**
     * Test of setSup and getSup method, of class NumericalStorage.
     */
    @Test
    public void testSup() {
        NumericalStorage storage = NumericalStorage.create(10);
        assertEquals(storage, storage.setSup(0, 10));
        assertEquals(10, storage.getInf(0), 0.01);
        assertEquals(10, storage.getSup(0), 0.01);
        storage.setSup(0, 0);
        assertEquals(Double.POSITIVE_INFINITY, storage.getInf(0), 0.01);
        assertEquals(Double.NEGATIVE_INFINITY, storage.getSup(0), 0.01);
        storage.setSup(0, 0);
        assertEquals(0, storage.getSup(0), 0.01);
        storage.setSup(0, 10);
        assertEquals(10, storage.getSup(0), 0.01);
    }

    /**
     * Test of reduceInf method, of class NumericalStorage.
     */
    @Test
    public void testReduceInf() {
        NumericalStorage storage = NumericalStorage.create(10);
        storage.setInf(0, 0);
        storage.setSup(0, 10);
        assertEquals(storage, storage.reduceInf(0, 5));
        assertEquals(5, storage.getInf(0), 0.01);
        storage.reduceInf(0, 15);
        assertTrue(storage.isEmpty(0));
    }

    /**
     * Test of reduceSup method, of class NumericalStorage.
     */
    @Test
    public void testReduceSup() {
        NumericalStorage storage = NumericalStorage.create(10);
        storage.setInf(0, 0);
        storage.setSup(0, 10);
        assertEquals(storage, storage.reduceSup(0, 5));
        assertEquals(5, storage.getSup(0), 0.01);
        storage.reduceSup(0, -5);
        assertTrue(storage.isEmpty(0));
    }

    /**
     * Test of extendInf method, of class NumericalStorage.
     */
    @Test
    public void testExtendInf() {
        NumericalStorage storage = NumericalStorage.create(10);
        storage.setInf(0, 0);
        storage.setSup(0, 10);
        assertEquals(storage, storage.extendInf(0, 5));
        assertEquals(0, storage.getInf(0), 0.01);
        storage.extendInf(0, -5);
        assertEquals(-5, storage.getInf(0), 0.01);
    }

    /**
     * Test of extendSup method, of class NumericalStorage.
     */
    @Test
    public void testExtendSup() {
        NumericalStorage storage = NumericalStorage.create(10);
        storage.setInf(0, 0);
        storage.setSup(0, 10);
        assertEquals(storage, storage.extendSup(0, 5));
        assertEquals(10, storage.getSup(0), 0.01);
        storage.extendSup(0, 15);
        assertEquals(15, storage.getSup(0), 0.01);
    }

    /**
     * Test of extend method, of class NumericalStorage.
     */
    @Test
    public void testExtend() {
        NumericalStorage storage = NumericalStorage.create(10);
        storage.setInf(0, 0);
        storage.setSup(0, 10);
        assertEquals(storage, storage.extend(0, 5));
        assertEquals(0, storage.getInf(0), 0.01);
        assertEquals(10, storage.getSup(0), 0.01);
        assertEquals(storage, storage.extend(0, 15));
        assertEquals(0, storage.getInf(0), 0.01);
        assertEquals(15, storage.getSup(0), 0.01);
        assertEquals(storage, storage.extend(0, -5));
        assertEquals(-5, storage.getInf(0), 0.01);
        assertEquals(15, storage.getSup(0), 0.01);
    }

    /**
     * Initialize Allen's intervals.
     *
     * @param storage1 first storage
     * @param storage2 second storage
     */
    void initializeAllen(NumericalStorage storage1, NumericalStorage storage2) {
        // See https://en.wikipedia.org/wiki/Allen's_interval_algebra
        storage1.setInf(0, 0);
        storage1.setSup(0, 10);
        storage2.setInf(0, 20);
        storage2.setSup(0, 30);

        storage1.setInf(1, 0);
        storage1.setSup(1, 10);
        storage2.setInf(1, 10);
        storage2.setSup(1, 20);

        storage1.setInf(2, 0);
        storage1.setSup(2, 10);
        storage2.setInf(2, 5);
        storage2.setSup(2, 15);

        storage1.setInf(3, 0);
        storage1.setSup(3, 10);
        storage2.setInf(3, 0);
        storage2.setSup(3, 15);

        storage1.setInf(4, 0);
        storage1.setSup(4, 10);
        storage2.setInf(4, -5);
        storage2.setSup(4, 15);

        storage1.setInf(5, 0);
        storage1.setSup(5, 10);
        storage2.setInf(5, -5);
        storage2.setSup(5, 10);

        storage1.setInf(6, 0);
        storage1.setSup(6, 10);
        storage2.setInf(6, 0);
        storage2.setSup(6, 10);
    }

    /**
     * Test of intersection method, of class NumericalStorage.
     */
    @Test
    public void testIntersection() {
        NumericalStorage storage1 = NumericalStorage.create(10);
        NumericalStorage storage2 = NumericalStorage.create(10);
        initializeAllen(storage1, storage2);

        storage1.intersection(storage2);

        assertTrue(storage1.isEmpty(0));

        assertEquals(10, storage1.getInf(1), 0.01);
        assertEquals(10, storage1.getSup(1), 0.01);

        assertEquals(5, storage1.getInf(2), 0.01);
        assertEquals(10, storage1.getSup(2), 0.01);

        assertEquals(0, storage1.getInf(3), 0.01);
        assertEquals(10, storage1.getSup(3), 0.01);

        assertEquals(0, storage1.getInf(4), 0.01);
        assertEquals(10, storage1.getSup(4), 0.01);

        assertEquals(0, storage1.getInf(5), 0.01);
        assertEquals(10, storage1.getSup(5), 0.01);

        assertEquals(0, storage1.getInf(6), 0.01);
        assertEquals(10, storage1.getSup(6), 0.01);

        try {
            storage1.intersection(NumericalStorage.create(5));
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("NumericalStorage objects must have the same size", e.getMessage());
        }
    }

    /**
     * Test of union method, of class NumericalStorage.
     */
    @Test
    public void testUnion() {
        NumericalStorage storage1 = NumericalStorage.create(10);
        NumericalStorage storage2 = NumericalStorage.create(10);
        initializeAllen(storage1, storage2);

        storage1.union(storage2);

        assertEquals(0, storage1.getInf(0), 0.01);
        assertEquals(30, storage1.getSup(0), 0.01);

        assertEquals(0, storage1.getInf(1), 0.01);
        assertEquals(20, storage1.getSup(1), 0.01);

        assertEquals(0, storage1.getInf(2), 0.01);
        assertEquals(15, storage1.getSup(2), 0.01);

        assertEquals(0, storage1.getInf(3), 0.01);
        assertEquals(15, storage1.getSup(3), 0.01);

        assertEquals(-5, storage1.getInf(4), 0.01);
        assertEquals(15, storage1.getSup(4), 0.01);

        assertEquals(-5, storage1.getInf(5), 0.01);
        assertEquals(10, storage1.getSup(5), 0.01);

        assertEquals(0, storage1.getInf(6), 0.01);
        assertEquals(10, storage1.getSup(6), 0.01);

        try {
            storage1.union(NumericalStorage.create(5));
            fail("An IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("NumericalStorage objects must have the same size", e.getMessage());
        }
    }

    /**
     * Test of toString method, of class NumericalStorage.
     */
    @Test
    public void testToString() {
        NumericalStorage storage = NumericalStorage.create(10);
        assertEquals("[@, @, @, @, @, @, @, @, @, @]", storage.toString());
        storage.setPoint(0, 0);
        storage.setInf(1, 0).setSup(1, 10);
        assertEquals("[0.0, (0.0,10.0), @, @, @, @, @, @, @, @]", storage.toString());
    }

    /**
     * Test of size method, of class NumericalStorage.
     */
    @Test
    public void testSize() {
        NumericalStorage storage = NumericalStorage.create(10);
        assertEquals(10, storage.size());
    }
}

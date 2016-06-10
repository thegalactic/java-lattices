package org.thegalactic.util;

/*
 * ComparableSetTest.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of CeCILL-B license.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.TreeSet;

/**
 * Test class for the ComparableSet class.
 */
public class ComparableSetTest {
    /**
     * Test the empty constructor.
     */
    @Test
    public void testEmptyConstructor() {
        ComparableSet set = new ComparableSet();
        assertTrue(set.isEmpty());
    }

    /**
     * Test the constructor by copy.
     */
    @Test
    public void testCopyConstructor() {
        TreeSet<Comparable> set = new TreeSet<Comparable>();
        set.add(new Integer(1));
        set.add(new Integer(2));
        set.add(new Integer(3));
        ComparableSet comparableSet = new ComparableSet(set);
        assertEquals(comparableSet.size(), set.size());
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        ComparableSet set = new ComparableSet();
        set.add(new String("Hello world"));
        assertEquals(set.toString(), "[Helloworld]");
    }

    /**
     * Test the clone method.
     */
    @Test
    public void testClone() {
        ComparableSet set = new ComparableSet();
        set.add(new Integer(1));
        set.add(new Integer(2));
        set.add(new Integer(3));
        ComparableSet copy = set.clone();
        assertEquals(set, copy);
    }

    /**
     * Test the equals method.
     */
    @Test
    public void testEquals() {
        ComparableSet set1 = new ComparableSet();
        set1.add(new Integer(1));
        set1.add(new Integer(2));
        set1.add(new Integer(3));

        ComparableSet set2 = new ComparableSet();
        set2.add(new Integer(1));
        set2.add(new Integer(2));
        set2.add(new Integer(3));

        assertTrue(set1.equals(set2));

        set2.add(new Integer(4));
        assertFalse(set1.equals(set2));

        assertFalse(set1.equals(new Integer(0)));
    }

    /**
     * Test the hashCode method.
     */
    @Test
    public void testHashCode() {
        TreeSet<Comparable> set = new TreeSet<Comparable>();
        set.add(new Integer(1));
        set.add(new Integer(2));
        set.add(new Integer(3));
        ComparableSet comparableSet = new ComparableSet(set);
        assertEquals(set.hashCode(), comparableSet.hashCode());
    }

    /**
     * Test the compareTo method.
     */
    @Test
    public void testCompareTo() {
        ComparableSet set1 = new ComparableSet();
        set1.add(new Integer(1));
        set1.add(new Integer(2));
        set1.add(new Integer(3));
        assertTrue(set1.compareTo(new Integer(1)) < 0);

        ComparableSet set2 = new ComparableSet();

        set2.clear();
        set2.add(new Integer(1));
        set2.add(new Integer(2));
        assertTrue(set1.compareTo(set2) > 0);
        assertTrue(set2.compareTo(set1) < 0);

        set2.clear();
        set2.add(new Integer(2));
        set2.add(new Integer(3));
        set2.add(new Integer(4));
        assertTrue(set1.compareTo(set2) > 0);
        assertTrue(set2.compareTo(set1) < 0);

        set1.clear();
        set2.clear();
        set1.add(new Integer(1));
        set1.add(new Integer(3));
        set1.add(new Integer(5));
        set2.add(new Integer(2));
        set2.add(new Integer(3));
        set2.add(new Integer(6));
        assertTrue(set1.compareTo(set2) > 0);
        assertTrue(set2.compareTo(set1) < 0);
    }
}

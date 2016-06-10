package org.thegalactic.util;

/*
 * Couple.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jean-François
 */
public class CoupleTest {
    /**
     * Test Couple constructor.
     */
    @Test
    public void testCouple() {
        Couple c = new Couple("A", "B");
        assertEquals(c.getLeft(), "A");
        assertEquals(c.getRight(), "B");
    }
    /**
     * Test getLeft method.
     */
    @Test
    public void testgetLeft() {
        Couple c = new Couple("A", "B");
        assertEquals(c.getLeft(), "A");
    }
    /**
     * Test getRight method.
     */
    @Test
    public void testgetRight() {
        Couple c = new Couple("A", "B");
        assertEquals(c.getRight(), "B");
    }
    /**
     * Test setLeft method.
     */
    @Test
    public void testsetLeft() {
        Couple c = new Couple("A", "B");
        c.setLeft("C");
        assertEquals(c.getLeft(), "C");
    }
    /**
     * Test setRight method.
     */
    @Test
    public void testsetRight() {
        Couple c = new Couple("A", "B");
        c.setRight("C");
        assertEquals(c.getRight(), "C");
    }
    /**
     * Test equals method.
     */
    @Test
    public void testequals() {
        Couple c1 = new Couple("A", "B");
        Couple c2 = new Couple("A", "B");
        assertTrue(c1.equals(c2));
    }
    /**
     * Test hashCode method.
     */
    @Test
    public void testhashcode() {
        Couple c = new Couple("A", "B");
        Object cc = new Object();
        cc = (Object) c;
        assertEquals(c.hashCode(), cc.hashCode());
    }
    /**
     * Test toString method.
     */
    public void testtoString() {
        Couple c = new Couple("A", "B");
        String str = c.toString();
        assertEquals(str, "(A,B)");
    }
}

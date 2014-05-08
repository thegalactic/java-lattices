package lattice;

/*
 * PermutationTest.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test class for the Permutation class.
 *
 * @author jeff
 */
public class PermutationTest {
    /**
     * Test for the Permutation constructor.
     */
    @Test
    public void testPermutation() {
        Permutation s = new Permutation(2);
        assertEquals(s.getLength(), 2);
        assertEquals(s.getContent()[0], 0);
        assertEquals(s.getContent()[1], 1);
    }
    /**
     * Test for the getContent method.
     */
    @Test
    public void testgetContent() {
        Permutation s = new Permutation(5);
        assertEquals(s.getContent()[0], 0);
        assertEquals(s.getContent()[3], 3);
    }
    /**
     * Test the setContent method.
     */
    @Test
    public void testsetContent() {
        Permutation s = new Permutation(2);
        int[] c = new int[2];
        c[0] = 1;
        c[1] = 0;
        s.setContent(c);
        assertEquals(s.getContent()[0], 1);
        assertEquals(s.getContent()[1], 0);
    }
    /**
     * Test the setlength method.
     */
    @Test
    public void testsetlength() {
        Permutation s = new Permutation(2);
        assertFalse(s.setLength(3));
        int[] c = new int[3];
        c[0] = 1;
        c[1] = 0;
        c[2] = 2;
        s.setContent(c);
        assertTrue(s.setLength(3));
    }
    /**
     * Test the getlength method.
     */
    @Test
    public void testgetlength() {
        Permutation s = new Permutation(2);
        assertTrue(s.getLength() == 2);
    }
    /**
     * Test the toString method.
     */
    @Test
    public void testtoString() {
        Permutation s = new Permutation(2);
        assertEquals(s.toString(), "01");
    }
    /**
     * Test the equals method.
     */
    @Test
    public void testequals() {
        Permutation s = new Permutation(3);
        int[] c = new int[3];
        c[0] = 0;
        c[1] = 1;
        c[2] = 2;
        Permutation sCopy = new Permutation(3);
        sCopy.setContent(c);
        assertTrue(s.equals(sCopy));
    }
    /**
     * Test hashCode method.
     */
    @Test
    public void testhashcode() {
        Permutation s = new Permutation(3);
        Object p = new Object();
        p = (Object) s;
        assertEquals(s.hashCode(), p.hashCode());
    }
}

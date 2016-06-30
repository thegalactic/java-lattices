package org.thegalactic.dgraph;

/*
 * DAGraphFactoryTest.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * DAGraphFactoryTest.
 */
public class DAGraphFactoryTest {

    /**
     * Test of divisors method, of class DAGraphFactory.
     */
    @Test
    public void testDivisors() {
        DAGraph dag = DAGraphFactory.getInstance().divisors(20);
        assertEquals(dag.sizeNodes(), 19);
        assertEquals(dag.sizeEdges(), 27);
    }

    /**
     * Test of random method, of class DAGraphFactory.
     */
    @Test
    public void testRandom() {
        DAGraph dag = DAGraphFactory.getInstance().random(10, 0);
        assertEquals(dag.sizeNodes(), 10);
        assertEquals(dag.sizeEdges(), 0);

        dag = DAGraphFactory.getInstance().random(10, 1);
        assertEquals(dag.sizeNodes(), 10);
        assertEquals(dag.sizeEdges(), 45);
    }
}

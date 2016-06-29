package org.thegalactic.dgraph;

/*
 * DGraphFactoryTest.java
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
 * Test the dgraph.DGraphFactoryTest class.
 */
public class DGraphFactoryTest {

    /**
     * Test the random static method.
     */
    @Test
    public void testRandom() {
        ConcreteDGraph graph = DGraphFactory.getInstance().random(10, 0);
        assertEquals(graph.sizeNodes(), 10);
        assertEquals(graph.sizeEdges(), 0);

        graph = DGraphFactory.getInstance().random(10, 1);
        assertEquals(graph.sizeNodes(), 10);
        assertEquals(graph.sizeEdges(), 100);
    }
}

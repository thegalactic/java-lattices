package org.thegalactic.dgraph.io;

/*
 * DGraphWriterFactoryTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test the org.thegalactic.dgraph.io.DGraphWriterFactory class.
 */
public class DGraphWriterFactoryTest {

    /**
     * Test unregister.
     */
    @Test
    public void testUnregister() {
        DGraphWriterDot.register();
        DGraphWriter writer = DGraphWriterFactory.unregister("dot");
        assertTrue(writer instanceof DGraphWriter);
        assertEquals(DGraphWriterFactory.get("dot"), null);
    }
}

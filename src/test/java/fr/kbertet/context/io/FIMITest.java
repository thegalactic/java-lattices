package fr.kbertet.context.io;

/*
 * FIMITest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;

import fr.kbertet.context.Context;

/**
 * Test the fr.kbertet.dgraph.io.FIMITest class.
 */
public class FIMITest {
    /**
     * Test getInstance.
     */
    @Test
    public void testGetInstance() {
        FIMI serializer = FIMI.getInstance();
        assertEquals(serializer, FIMI.getInstance());
    }

    /**
     * Test read.
     */
    @Test
    public void testRead() {
        try {
            File file = File.createTempFile("junit", ".dat");
            String filename = file.getPath();
            Context context = new Context();
            context.addToAttributes("a");
            context.addToAttributes("b");
            context.addToAttributes("c");
            context.addToObservations("1");
            context.addToObservations("2");
            context.addToObservations("3");
            context.addExtentIntent("1", "a");
            context.addExtentIntent("1", "b");
            context.addExtentIntent("2", "a");
            context.addExtentIntent("3", "b");
            context.addExtentIntent("3", "c");
            context.save(filename);
            Context copy = new Context(filename);
            assertEquals(context.getAttributes().size(), copy.getAttributes().size());
            assertEquals(context.getObservations().size(), copy.getObservations().size());
            new File(filename).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


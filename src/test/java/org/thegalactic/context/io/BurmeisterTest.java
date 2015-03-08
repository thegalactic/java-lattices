package org.thegalactic.context.io;

/*
 * BurmeisterTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.thegalactic.context.TemporaryContext;

/**
 * Test the org.thegalactic.dgraph.io.ContextBurmeisterTest class.
 */
public class BurmeisterTest {

    /**
     * Test getInstance.
     */
    @Test
    public void testGetInstance() {
        Burmeister serializer = Burmeister.getInstance();
        assertEquals(serializer, Burmeister.getInstance());
    }

    /**
     * Test read.
     */
    @Test
    public void testRead() {
        try {
            File file = File.createTempFile("junit", ".cxt");
            String filename = file.getPath();
            TemporaryContext context = new TemporaryContext();
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
            TemporaryContext copy = new TemporaryContext(filename);
            assertEquals(context.getAttributes(), copy.getAttributes());
            assertEquals(context.getObservations(), copy.getObservations());
            assertEquals(context.getIntent("1"), copy.getIntent("1"));
            assertEquals(context.getExtent("c"), copy.getExtent("c"));
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test first line without magic header.
     */
    @Test
    public void testWithoutMagicHeader() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".cxt");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("Example");
            writer.newLine();
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            assertEquals(context.getExtent("a").toString(), "[1, 2]");
            assertEquals(context.getExtent("b").toString(), "[2]");
            assertEquals(context.getExtent("c").toString(), "[]");
        } catch (Exception e) {
            assertEquals(e.toString(), "java.io.IOException: Burmeister magic header not found");
            new File(filename).delete();
        }
    }
}

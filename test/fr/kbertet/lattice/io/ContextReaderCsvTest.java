package fr.kbertet.lattice.io;

/*
 * ContextReaderCsvTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import fr.kbertet.lattice.Context;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test the fr.kbertet.dgraph.io.ContextReaderCsvTest class.
 */
public class ContextReaderCsvTest {
    /**
     * Test getInstance.
     */
    @Test
    public void testGetInstance() {
        ContextReaderCsv reader = ContextReaderCsv.getInstance();
        assertEquals(reader, ContextReaderCsv.getInstance());
    }

    /**
     * Test read.
     */
    @Test
    public void testRead() {
        try {
            File file = File.createTempFile("junit", ".csv");
            String filename = file.getPath();
            file.delete();
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
            assertEquals(context.getAttributes(), copy.getAttributes());
            assertEquals(context.getObservations(), copy.getObservations());
            assertEquals(context.getIntent("1"), copy.getIntent("1"));
            assertEquals(context.getExtent("c"), copy.getExtent("c"));
            new File(filename).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test read empty.
     */
    @Test
    public void testReadEmpty() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            Context context = new Context(filename);
            fail();
        } catch (IOException e) {
            new File(filename).delete();
        }
    }

    /**
     * Test bad first line.
     */
    @Test
    public void testBadFirstLine() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("a,b,c");
            writer.close();
            Context context = new Context(filename);
            fail();
        } catch (IOException e) {
            new File(filename).delete();
        }
    }

    /**
     * Test bad first line.
     */
    @Test
    public void testBadFirstLine2() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("a");
            writer.close();
            Context context = new Context(filename);
            fail();
        } catch (IOException e) {
            new File(filename).delete();
        }
    }

    /**
     * Test bad line.
     */
    @Test
    public void testBadLine() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(",a");
            writer.newLine();
            writer.write("1,2,1");
            writer.close();
            Context context = new Context(filename);
            fail();
        } catch (IOException e) {
            new File(filename).delete();
        }
    }

    /**
     * Test attributes.
     */
    @Test
    public void testAttributes() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(",a,a");
            writer.close();
            Context context = new Context(filename);
            fail();
        } catch (IOException e) {
            new File(filename).delete();
        }
    }
}


package org.thegalactic.context.io;

/*
 * CsvTest.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-thegalactic.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.thegalactic.context.TemporaryContext;

/**
 * Test the org.thegalactic.dgraph.io.CsvTest class.
 */
public class CsvTest {
    /**
     * Test getInstance.
     */
    @Test
    public void testGetInstance() {
        Csv serializer = Csv.getInstance();
        assertEquals(serializer, Csv.getInstance());
    }

    /**
     * Test read.
     */
    @Test
    public void testRead() {
        try {
            File file = File.createTempFile("junit", ".csv");
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
     * Test first line without individual identifiers.
     */
    @Test
    public void testWithoutIndividualIdentifiers() {
        try {
            File file;
            String filename = "";
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("a,b,c");
            writer.newLine();
            writer.write("1,0,0");
            writer.newLine();
            writer.write("1,1,0");
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            assertEquals(context.getExtent("a").toString(), "[1, 2]");
            assertEquals(context.getExtent("b").toString(), "[2]");
            assertEquals(context.getExtent("c").toString(), "[]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test one attribute.
     */
    @Test
    public void testOneAttribute() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("a");
            writer.newLine();
            writer.write("1");
            writer.newLine();
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            assertEquals(context.getExtent("a").toString(), "[1]");
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
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            fail();
        } catch (IOException e) {
            assertEquals(e.toString(), "java.io.IOException: CSV cannot be empty");
            new File(filename).delete();
        }
    }

    /**
     * Test no attribute.
     */
    @Test
    public void testNoAttribute() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("");
            writer.newLine();
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            fail();
        } catch (IOException e) {
            assertEquals(e.toString(), "java.io.IOException: Attribute size cannot be 0");
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
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            fail();
        } catch (IOException e) {
            assertEquals(e.toString(), "java.io.IOException: Line does not have the correct number of attributes");
            new File(filename).delete();
        }
    }

    /**
     * Test empty attribute.
     */
    @Test
    public void testEmptyAttribute() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(",a,");
            writer.newLine();
            writer.write("1,0,1");
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            fail();
        } catch (IOException e) {
            assertEquals(e.toString(), "java.io.IOException: Empty attribute");
            new File(filename).delete();
        }
    }

    /**
     * Test duplicated attributes.
     */
    @Test
    public void testDuplicatedAttributes() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(",a,a");
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            fail();
        } catch (IOException e) {
            assertEquals(e.toString(), "java.io.IOException: Duplicated attribute");
            new File(filename).delete();
        }
    }

    /**
     * Test duplicated identifiers.
     */
    @Test
    public void testDuplicatedIdentifiers() {
        File file;
        String filename = "";
        try {
            file = File.createTempFile("junit", ".csv");
            filename = file.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(",a,b,c");
            writer.newLine();
            writer.write("1,0,1,1");
            writer.newLine();
            writer.write("1,1,1,1");
            writer.close();
            TemporaryContext context = new TemporaryContext(filename);
            file.delete();
            fail();
        } catch (IOException e) {
            assertEquals(e.toString(), "java.io.IOException: Duplicated identifier");
            new File(filename).delete();
        }
    }
}


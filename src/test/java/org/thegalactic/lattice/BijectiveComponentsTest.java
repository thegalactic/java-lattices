package org.thegalactic.lattice;

/*
 * BijectiveComponentsTest.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import org.thegalactic.rule.ImplicationalSystem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TreeSet;
import java.io.File;
import java.util.Scanner;

import org.thegalactic.util.ComparableSet;
import org.thegalactic.dgraph.DGraph;
import org.thegalactic.context.Context;

/**
 *
 * @author JeanFrancoisViaud
 */
public class BijectiveComponentsTest {

    /**
     * Test the constructor from an implication system.
     */
    @Test
    public void testBijectiveComponent() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.compute();
        assertTrue(bc.getClosureSystem() != null);
        assertTrue(bc.getLattice() != null);
        assertTrue(bc.getReducedLattice() != null);
        assertTrue(bc.getDependencyGraph() != null);
        assertTrue(bc.getMinimalGenerators() != null);
        assertTrue(bc.getCanonicalDirectBasis() != null);
        assertTrue(bc.getCanonicalBasis() != null);
        assertTrue(bc.getTable() != null);
    }

    /**
     * Test the compute method with an implicationnal system.
     */
    @Test
    public void testCompute() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.compute();
        assertTrue(bc.getClosureSystem() != null);
        assertTrue(bc.getLattice() != null);
        assertTrue(bc.getReducedLattice() != null);
        assertTrue(bc.getDependencyGraph() != null);
        assertTrue(bc.getMinimalGenerators() != null);
        assertTrue(bc.getCanonicalDirectBasis() != null);
        assertTrue(bc.getCanonicalBasis() != null);
        assertTrue(bc.getTable() != null);
    }

    /**
     * Test the compute method with a context.
     */
    @Test
    public void testinitalizeContext() {
        Context context = new Context();
        BijectiveComponents bc = new BijectiveComponents(context);
        bc.compute();
        assertTrue(bc.getClosureSystem() != null);
        assertTrue(bc.getLattice() != null);
        assertTrue(bc.getReducedLattice() != null);
        assertTrue(bc.getDependencyGraph() != null);
        assertTrue(bc.getMinimalGenerators() != null);
        assertTrue(bc.getCanonicalDirectBasis() != null);
        assertTrue(bc.getCanonicalBasis() != null);
        assertTrue(bc.getTable() != null);
    }

    /**
     * Test the save method.
     *
     * TODO make the test Windows compliant
     */
    @Test
    public void testsave() {
        try {
            ImplicationalSystem is = new ImplicationalSystem();
            BijectiveComponents bc = new BijectiveComponents(is);
            bc.compute();

            File dir = File.createTempFile("junit", "");
            String directory = dir.getPath();
            dir.delete();
            dir.mkdir();

            bc.save(directory, "");
            File file = new File(directory + File.separator + "BijectiveComponents" + File.separator + "Readme.txt");
            Scanner scanner = new Scanner(file);
            String content = "";
            String newLine = System.getProperty("line.separator");
            while (scanner.hasNextLine()) {
                content += scanner.nextLine() + newLine;
            }
            File[] tabfile = (new File(directory, "BijectiveComponents")).listFiles();
            for (int i = 0; i < tabfile.length; i++) {
                (new File(tabfile[i].getPath())).delete();
            }

            (new File(directory + File.separator + "BijectiveComponents")).delete();
            dir.delete();
            String test = "";
            String safeString = file.getParent() + File.separator;
            test += "-> Initial closure system saved in " + safeString + "InitialClosureSystem.txt: " + newLine;
            test += newLine;
            test += newLine;
            test += "-> Closed set or concept lattice saved in " + safeString + "Lattice.dot" + newLine;
            test += "-> Reduced lattice saved in " + safeString + "ReducedLattice.dot" + newLine;
            test += "-> Table of the reduced lattice saved in " + safeString + "Table.txt" + newLine;
            test += "Observations: " + newLine;
            test += "Attributes: " + newLine;
            test += newLine;
            test += "-> Canonical basis saved in " + safeString + "CanonicalBasis.txt: " + newLine;
            test += newLine;
            test += newLine;
            test += "-> Canonical direct basis of the reduced lattice saved in " + safeString + "CanonicalDirectBasis.txt: " + newLine;
            test += newLine;
            test += newLine;
            test += "-> Dependency Graph  of the reduced lattice saved in " + safeString + "DependencyGraph.dot " + newLine;
            test += "-> Minimal generators  of the reduced lattice are []" + newLine;
            assertEquals(content, test);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Test the getClosureSystem method.
     */
    @Test
    public void testgetClosureSystem() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        assertEquals(bc.getClosureSystem(), is);
    }

    /**
     * Test the setClosureSystem method.
     */
    @Test
    public void testsetClosureSystem() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.setClosureSystem(is);
        assertEquals(bc.getClosureSystem(), is);
    }

    /**
     * Test the setLattice method.
     */
    @Test
    public void testsetLattice() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        ConceptLattice cl = new ConceptLattice();
        bc.setLattice(cl);
        assertEquals(bc.getLattice(), cl);
    }

    /**
     * Test the getLattice method.
     */
    @Test
    public void testgetLattice() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        ConceptLattice cl = new ConceptLattice();
        bc.setLattice(cl);
        assertEquals(bc.getLattice(), cl);
    }

    /**
     * Test the setReducedLattice method.
     */
    @Test
    public void testsetReducedLattice() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        Lattice l = new Lattice();
        bc.setReducedLattice(l);
        assertEquals(bc.getReducedLattice(), l);
    }

    /**
     * Test the getReducedLattice method.
     */
    @Test
    public void testgetReducedLattice() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        Lattice l = new Lattice();
        bc.setReducedLattice(l);
        assertEquals(bc.getReducedLattice(), l);
    }

    /**
     * Test the setDependencyGraph method.
     */
    @Test
    public void testsetDependencyGraph() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        DGraph dg = new DGraph();
        bc.setDependencyGraph(dg);
        assertEquals(bc.getDependencyGraph(), dg);
    }

    /**
     * Test the getDependencyGraph method.
     */
    @Test
    public void testgetDependencyGraph() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        Lattice l = new Lattice();
        bc.setReducedLattice(l);
        assertEquals(bc.getReducedLattice(), l);
    }

    /**
     * Test the setMinimalGenerators method.
     */
    @Test
    public void testsetMinimalGenerators() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        TreeSet<ComparableSet> mingen = new TreeSet<ComparableSet>();
        bc.setMinimalGenerators(mingen);
        assertEquals(bc.getMinimalGenerators(), mingen);
    }

    /**
     * Test the getMinimalGenerators method.
     */
    @Test
    public void testgetMinimalGenerators() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        Context table = new Context();
        bc.setTable(table);
        assertEquals(bc.getTable(), table);
    }

    /**
     * Test the setCanonicalDirectBasis method.
     */
    @Test
    public void testsetCanonicalDirectBasis() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.setCanonicalDirectBasis(is);
        assertEquals(bc.getCanonicalDirectBasis(), is);
    }

    /**
     * Test the getCanonicalDirectBasis method.
     */
    @Test
    public void testgetCanonicalDirectBasis() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.setCanonicalDirectBasis(is);
        assertEquals(bc.getCanonicalDirectBasis(), is);
    }

    /**
     * Test the setCanonicalBasis method.
     */
    @Test
    public void testsetCanonicalBasis() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.setCanonicalBasis(is);
        assertEquals(bc.getCanonicalBasis(), is);
    }

    /**
     * Test the getCanonicalBasis method.
     */
    @Test
    public void testgetCanonicalBasis() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.setCanonicalBasis(is);
        assertEquals(bc.getCanonicalBasis(), is);
    }

    /**
     * Test the setTable method.
     */
    @Test
    public void testsetTable() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        Context table = new Context();
        bc.setTable(table);
        assertEquals(bc.getTable(), table);
    }

    /**
     * Test the getTable method.
     */
    @Test
    public void testgetTable() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        Context table = new Context();
        bc.setTable(table);
        assertEquals(bc.getTable(), table);
    }
}

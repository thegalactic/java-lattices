package fr.kbertet.lattice;

/*
 * BijectiveComponentsTest.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.TreeSet;
import fr.kbertet.dgraph.DGraph;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
        long time = bc.compute();
        assertTrue(bc.getInit() != null);
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
    public void testinitalizeIS() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        long time = bc.compute();
        assertTrue(bc.getInit() != null);
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
        long time = bc.compute();
        assertTrue(bc.getInit() != null);
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
     * @todo make the test Windows compliant
     */
    @Test
    public void testsave() {
        try {
            ImplicationalSystem is = new ImplicationalSystem();
            BijectiveComponents bc = new BijectiveComponents(is);
            long time = bc.compute();
            File file = File.createTempFile("junit", ".txt");
            String filename = file.getName();
            String directory = file.getParent();
            bc.save(directory, "");
            file.delete();
            file = new File(directory + File.separator + "BijectiveComponents" + File.separator + "Readme.txt");
            Scanner scanner = new Scanner(file);
            String content = "";
            while (scanner.hasNextLine()) {
                content += scanner.nextLine() + "\n";
            }
            File dir = new File(directory, "BijectiveComponents");
            File[] tabfile = dir.listFiles();
            for (int i = 0; i < tabfile.length; i++) {
            System.out.println(tabfile[i]);
            File tmp = new File(tabfile[i].getPath());
            tmp.delete();
            }
            dir.delete();
            file.delete();
            String test = "";
            String safeString = file.getParent() + File.separator;
            test += "-> Initial closure system saved in " + safeString + "InitialClosureSystem.txt: \n";
            test += "\n";
            test += "\n";
            test += "-> Closed set or concept lattice saved in " + safeString + "Lattice.dot\n";
            test += "-> Reduced lattice saved in " + safeString + "ReducedLattice.dot\n";
            test += "-> Table of the reduced lattice saved in " + safeString + "Table.txt\n";
            test += "Observations: \n";
            test += "Attributes: \n";
            test += "\n";
            test += "-> Canonical basis saved in " + safeString + "CanonicalBasis.txt: \n";
            test += "\n";
            test += "\n";
            test += "-> Canonical direct basis of the reduced lattice saved in " + safeString + "CanonicalDirectBasis.txt: \n";
            test += "\n";
            test += "\n";
            test += "-> Dependency Graph  of the reduced lattice saved in " + safeString + "DependencyGraph.dot \n";
            test += "-> Minimal generators  of the reduced lattice are []\n";
            assertEquals(content, test);
        } catch (IOException e) { System.out.println("IOException : " + e.getMessage()); }
    }
    /**
     * Test the getInit method.
     */
    @Test
    public void testgetInit() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        assertEquals(bc.getInit(), is);
    }
    /**
     * Test the setInit method.
     */
    @Test
    public void testsetInit() {
        ImplicationalSystem is = new ImplicationalSystem();
        BijectiveComponents bc = new BijectiveComponents(is);
        bc.setInit(is);
        assertEquals(bc.getInit(), is);
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

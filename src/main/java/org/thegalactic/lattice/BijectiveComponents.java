package org.thegalactic.lattice;

/*
 * BijectiveComponents.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */

import java.util.Date;
import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.thegalactic.util.ComparableSet;
import org.thegalactic.dgraph.DGraph;
import org.thegalactic.context.Context;

/**
 * This class generates bijective components issued from lattice theory for a specified
 * closure system.
 *
 * Bijective components are:
 * closed set lattice or concept lattice, reduced lattice, reduced context, canonical direct basis, minimal generators
 * and canonical basis, dependency graph.
 *
 * A closure system is described by the abstract class {@link ClosureSystem}.
 * In this package, a closure system can be instancied by an implicational
 * system described by class {@link ImplicationalSystem}) or a context described by
 * class {@link Context}).
 *
 * This class provides a constructor, and only two methods: the method {@link #compute}
 * generates all the bijective components of the specified closure system; and the method
 * {@link #save} saves theses components in files.
 *
 * This class can be used as follows:
 *
 * ~~~Java
 * BijectiveComponents bc = new BijectiveComponents(initialClosureSystem);
 * bc.compute();
 * bc.save(dirString,nameString);
 * ~~~
 *
 * ![BijectiveComponents](BijectiveComponents.png)
 *
 * @uml BijectiveComponents.png
 * !include resources/org/thegalactic/lattice/BijectiveComponents.iuml
 * !include resources/org/thegalactic/lattice/ClosureSystem.iuml
 *
 * hide members
 * show BijectiveComponents members
 * class BijectiveComponents #LightCyan
 * title BijectiveComponents UML graph
 */
public class BijectiveComponents {
    /* ------------- FIELDS ------------------ */

    /**
     * The initial closure system.
     */

    private ClosureSystem closureSystem;

    /**
     * The closed set lattice of the closure system when closure system is an implicational system.
     *
     * The concept lattice of the closure system when closure system is a context
     */
    private ConceptLattice lattice = null;

    /**
     * The reduced lattice.
     */
    private Lattice reducedLattice = null;

    /**
     * The dependency graph of the reduced lattice.
     */
    private DGraph dependencyGraph = null;

    /**
     * The minimal generators of the reduced lattice.
     */
    private TreeSet<ComparableSet> minimalGenerators = null;

    /**
     * The canonical direct basis of the reduced lattice.
     */
    private ImplicationalSystem canonicalDirectBasis = null;

    /**
     * The canonical basis of the reduced lattice.
     */
    private ImplicationalSystem canonicalBasis = null;

    /**
     * The table of the reduced lattice.
     */
    private Context table = null;

    /**
     * Constructs this component with the specified Closure System as initial closure system.
     *
     * @param   closureSystem  initial closure system
     */
    public BijectiveComponents(ClosureSystem closureSystem) {
        this.initialise(closureSystem);
    }

    /**
     * Initialise the closure system.
     *
     * @param   closureSystem  initial closure system
     *
     * @return  this for chaining
     */
    public BijectiveComponents initialise(ClosureSystem closureSystem) {
        this.closureSystem = closureSystem;
        this.lattice = null;
        this.reducedLattice = null;
        this.table = null;
        this.dependencyGraph = null;
        this.minimalGenerators = null;
        this.canonicalDirectBasis = null;
        this.canonicalBasis = null;
        return this;
    }

    /**
     * Generates all the bijective components included in this component
     * issued from the initial closure system `closureSystem`.
     *
     * The closed set lattice is generated and obtained by
     *
     * ~~~Java
     * this.getClosureSystem().lattice();
     * ~~~
     *
     * The reduced lattice is obtained by
     *
     * ~~~Java
     * this.getLattice().getIrreduciblesReduction();
     * ~~~
     *
     * The reduced table is obtained by
     *
     * ~~~Java
     * this.getReducedLattice().getTable();
     * ~~~
     *
     * The dependency graph is obtained by
     *
     * ~~~Java
     * this.getReducedLattice().getDependencyGraph();
     * ~~~
     *
     * Minimal generators are obtained by
     *
     * ~~~Java
     * this.getReducedLattice().getMinimalGenerators();
     * ~~~
     *
     * The canonical direct basis is obtained by
     *
     * ~~~Java
     * this.getReducedLattice().getCanonicalDirectBasis();
     * ~~~
     *
     * The canonical basis is obtained by
     *
     * ~~~Java
     * (new ImplicationalSystem(this.canonicalDirectBasis)).makeCanonicalBasis();
     * ~~~
     *
     * @return  time of computation
     */
    public long compute() {
        this.initialise(closureSystem);
        long debut = new Date().getTime();
        this.getLattice();
        this.getReducedLattice();
        this.getTable();
        this.getDependencyGraph();
        this.getMinimalGenerators();
        this.getCanonicalDirectBasis();
        this.getCanonicalBasis();
        long fin = new Date().getTime();
        return fin - debut;
    }

    /**
     * Saves all the bijective components included in this component in files
     * saved in the specified directory.
     * A global description is saved in file `name+"Readme.txt"`.
     *
     * The specified name is used to defined a name for each file.
     *
     * @param   directory  location to save file
     * @param   name       name of the files
     *
     * @throws  IOException  When an IOException occurs
     */
    public void save(String directory, String name) throws IOException {
        // create the directory
        directory = directory + File.separator + name + "BijectiveComponents" + File.separator;
        File f = new File(directory);
        f.mkdir();
        directory += name;
        BufferedWriter file = new BufferedWriter(new FileWriter(directory + "Readme.txt"));
        // saves the inital closure system
        String nameInit = directory + "InitialClosureSystem.txt";
        this.closureSystem.save(nameInit);
        file.write("-> Initial closure system saved in " + nameInit + ": \n");
        file.write(this.closureSystem.toString() + "\n");
        // saves the closed set lattice
        String nameLattice = directory + "Lattice.dot";
        this.getLattice().save(nameLattice);
        file.write("-> Closed set or concept lattice saved in " + nameLattice + "\n");
        // saves the reduced lattice
        String nameReducedLattice = directory + "ReducedLattice.dot";
        this.getReducedLattice().save(nameReducedLattice);
        file.write("-> Reduced lattice saved in " + nameReducedLattice + "\n");
        // saves the reduced table
        String nameTable = directory + "Table.txt";
        this.getTable().save(nameTable);
        file.write("-> Table of the reduced lattice saved in " + nameTable + "\n");
        file.write(this.table.toString() + "\n");
        // saves the canonical basis
        String nameCB = directory + "CanonicalBasis.txt";
        this.getCanonicalBasis().save(nameCB);
        file.write("-> Canonical basis saved in " + nameCB + ": \n");
        file.write(this.canonicalBasis.toString() + "\n");
        // saves the canonical direct basis
        String nameCDB = directory + "CanonicalDirectBasis.txt";
        this.getCanonicalDirectBasis().save(nameCDB);
        file.write("-> Canonical direct basis of the reduced lattice saved in " + nameCDB + ": \n");
        file.write(this.canonicalDirectBasis.toString() + "\n");
        // saves the dependency graph
        String nameODGraph = directory + "DependencyGraph.dot";
        this.getDependencyGraph().save(nameODGraph);
        file.write("-> Dependency Graph  of the reduced lattice saved in " + nameODGraph + " \n");
        // saves the minimal generators
        file.write("-> Minimal generators  of the reduced lattice are " + this.minimalGenerators + "\n");
        file.close();
    }

    /**
     * Returns the closure system of this component.
     *
     * @return  closure system of this component
     */
    public ClosureSystem getClosureSystem() {
        return closureSystem;
    }

    /**
     * Set the closure system of this component.
     *
     * @param   closureSystem  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setClosureSystem(ClosureSystem closureSystem) {
        this.closureSystem = closureSystem;
        return this;
    }

    /**
     * Returns the lattice of this component.
     *
     * @return  lattice of this component
     */
    public ConceptLattice getLattice() {
        if (lattice == null) {
            lattice = this.closureSystem.lattice();
        }
        return lattice;
    }

    /**
     * Set the lattice of this component.
     *
     * @param   lattice  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setLattice(ConceptLattice lattice) {
        this.lattice = lattice;
        return this;
    }

    /**
     * Returns the reduced lattice of this component.
     *
     * @return  reduced lattice of this component
     */
    public Lattice getReducedLattice() {
        if (reducedLattice == null) {
            reducedLattice = this.getLattice().getIrreduciblesReduction();
        }
        return reducedLattice;
    }

    /**
     * Set the reduced lattice of this component.
     *
     * @param   reducedLattice  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setReducedLattice(Lattice reducedLattice) {
        this.reducedLattice = reducedLattice;
        return this;
    }

    /**
     * Returns the dependency graph of this component.
     *
     * @return  dependencyGraph  dependency graph of this component
     */
    public DGraph getDependencyGraph() {
        if (dependencyGraph == null) {
            // FIXME: do we use getLattice or getReducedLattice ?
            dependencyGraph = this.getReducedLattice().getDependencyGraph();
        }
        return dependencyGraph;
    }

    /**
     * Set the dependency graph of this component.
     *
     * @param   dependencyGraph  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setDependencyGraph(DGraph dependencyGraph) {
        this.dependencyGraph = dependencyGraph;
        return this;
    }

    /**
     * Returns the minimal generators of this component.
     *
     * @return  minimal generators of this component
     */
    public TreeSet<ComparableSet> getMinimalGenerators() {
        if (minimalGenerators == null) {
            // FIXME: do we use getLattice or getReducedLattice ?
            minimalGenerators = this.getReducedLattice().getMinimalGenerators();
        }
        return minimalGenerators;
    }

    /**
     * Set the minimal generators of this component.
     *
     * @param   minimalGenerators  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setMinimalGenerators(TreeSet<ComparableSet> minimalGenerators) {
        this.minimalGenerators = minimalGenerators;
        return this;
    }

    /**
     * Returns the canonical direct basis of this component.
     *
     * @return  the canonical direct basis of this component
     */
    public ImplicationalSystem getCanonicalDirectBasis() {
        if (canonicalDirectBasis == null) {
            // FIXME: do we use getLattice or getReducedLattice ?
            canonicalDirectBasis = this.getReducedLattice().getCanonicalDirectBasis();
        }
        return canonicalDirectBasis;
    }

    /**
     * Set the canonical direct basis of this component.
     *
     * @param   canonicalDirectBasis  used to define field of this component
     *
     * @return  return this for chaining
     */
    protected BijectiveComponents setCanonicalDirectBasis(ImplicationalSystem canonicalDirectBasis) {
        this.canonicalDirectBasis = canonicalDirectBasis;
        return this;
    }

    /**
     * Returns the canonical basis of this component.
     *
     * @return  the canonical basis of this component
     */
    public ImplicationalSystem getCanonicalBasis() {
        if (canonicalBasis == null) {
            canonicalBasis = new ImplicationalSystem(this.getCanonicalDirectBasis());
            canonicalBasis.makeCanonicalBasis();
        }
        return canonicalBasis;
    }

    /**
     * Set the canonical basis of this component.
     *
     * @param   canonicalBasis  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setCanonicalBasis(ImplicationalSystem canonicalBasis) {
        this.canonicalBasis = canonicalBasis;
        return this;
    }

    /**
     * Returns the Table of this component.
     *
     * @return  table of this component
     */
    public Context getTable() {
        if (table == null) {
            // FIXME: do we use getLattice or getReducedLattice ?
            table = this.getReducedLattice().getTable();
        }
        return table;
    }
    /**
     * Set the Table of this component.
     *
     * @param   table  used to define field of this component
     *
     * @return  this for chaining
     */
    protected BijectiveComponents setTable(Context table) {
        this.table = table;
        return this;
    }

/** Returns the informativ generic basis
 * public IS getApproximativBasis () {
 * IS IGB = new IS(this.canonicalDirectBasis);
 * IS tmp = new IS(this.canonicalDirectBasis);
 * for (Rule r : tmp.getRules()) {
 *  TreeSet premise = new TreeSet(r.getPremise());
 *  Concept c = new Concept(this.closure(premise),false);
 *  for (TreeSet conclusion : this.immediateSuccessors(c)) {
 *      TreeSet concl = new TreeSet(conclusion);
 *      conclusion.removeAll(premise);
 *      IGB.addRule(new Rule(premise,concl));
 *  }
 * }
 * return IGB;
 * }
 **/
} // end of BijectiveComponents
